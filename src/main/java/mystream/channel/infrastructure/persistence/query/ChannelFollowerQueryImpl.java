package mystream.channel.infrastructure.persistence.query;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import mystream.channel.domain.channel.entity.QChannel;
import mystream.channel.domain.follow.entity.ChannelFollower;
import mystream.channel.domain.follow.entity.QChannelFollower;
import mystream.channel.domain.follow.entity.QFollower;
import mystream.channel.domain.follow.repository.ChannelFollowerQuery;

@Repository
public class ChannelFollowerQueryImpl implements ChannelFollowerQuery {

  private final JPAQueryFactory queryFactory;

  public ChannelFollowerQueryImpl(EntityManager entityManager) {
    queryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public Optional<ChannelFollower> findMatchChannelFollower(Long channelId, Long userId) {
    ChannelFollower contents = 
      queryFactory
        .select(QChannelFollower.channelFollower)
        .from(QChannelFollower.channelFollower)
        .leftJoin(QChannelFollower.channelFollower.channel, QChannel.channel).fetchJoin()
        .leftJoin(QChannelFollower.channelFollower.follower, QFollower.follower).fetchJoin()
        .where(
          channelIdEq(channelId),
          followerUserIdEq(userId))
        .fetchFirst();

    return Optional.ofNullable(contents);
  }
  
  private BooleanExpression channelIdEq(Long channelId) {
    return QChannel.channel.id.eq(channelId);
  }

  private BooleanExpression followerUserIdEq(Long userId) {
    return QFollower.follower.userId.eq(userId);
  }

}
