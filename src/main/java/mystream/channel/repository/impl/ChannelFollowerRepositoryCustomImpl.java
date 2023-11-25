package mystream.channel.repository.impl;

import java.util.Optional;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import mystream.channel.entity.ChannelFollower;
import mystream.channel.entity.QChannel;
import mystream.channel.entity.QChannelFollower;
import mystream.channel.entity.QFollower;
import mystream.channel.repository.ChannelFollowerRepositoryCustom;

public class ChannelFollowerRepositoryCustomImpl implements ChannelFollowerRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public ChannelFollowerRepositoryCustomImpl(EntityManager entityManager) {
    queryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public Optional<ChannelFollower> findChannelFollowerByChannelIdAndUserId(Long channelId, Long userId) {
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
