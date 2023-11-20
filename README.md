# mystream-channel
mystream channel service

## 요구사항

  - 채널
    - 채널의 이름은 사용자의 이름으로 지정됨
    - 회원은 자신의 채널 정보 텍스트를 설정하거나 변경할 수 있음
      - 채널의 정보는 255 글자 미만으로 설정이 가능
    - 채널은 현재 및 이전 방송의 정보를 기록하고 있음
    - 채널은 자신을 팔로우한 회원들을 저장하고 있음
    - 채널은 자신을 구독한 회원들을 저장하고 있음

## mystream-channel

### Event Storming

![event_storming](doc/assets/event_storming.png)

### Context Map

![context_map](doc/assets/context_map.png)


### Domain Modeling

![domain](https://www.plantuml.com/plantuml/png/POvDReCX34JtdC9YMOb5Rn2fKNTTSGB2voQenaR6DChjs_me0cK5UczWPjSjgOrApeeHWg-NRegfXdnAp4WGKNzBHjXBJONXIpOaxrp_AToIfWfPQXD6jcWNGb3CblX8wDpJmCVzirSIRsMlV8yzQsbMX2SPn-6A3wWJ_1GYEVyZztWB_jr362s0V_DFlTfDpXokrtN86j9oFDcIccSjKS9lk_TD6zqEUHlrST0V)

### Entity Modeling

![entity](https://www.plantuml.com/plantuml/png/dL8zJyCm4DtxAwoCI1rOcLeebHYOAcRanjVMaZyY-znI8VuxvyJg1KSGYINolNllvVdEguWoOB96DE1Gu_NTUWL6ksEpM51jG3Js6Z60jCls99q38tOGphe5yTmhn6jM4BsKJmChYi-545hHf-XslOjuo0okkR-1s0RTeVQEIIEGc0Tlp0K2jxS4U4Ae5zC-K_VZzkw6vT_Gxn3MICVKgU7unR77eJZxSFMtOEWiMzHdc7UQ96UxVgIHx_oOl-75okEyxtQuF9kbI58rfwj7DTaBZwjCs0yZvjaMT8s4RYJEH-Mv_pSjE_oHjzuwYyQTSMpuw7GWvFSOj-VRt3TY6VJnXCl-F5rhnLN-ufZA3xoY6JXanpkcSUe9GZoMa9exjMtgVfZUbbVLn2baKfKdHLuhS2hPRm00)

### Table Modeling

![table](https://www.plantuml.com/plantuml/png/fPB1Se8m48RlUOgjlGXJV0B7iHSldPxgdGcmQAQ1C4bG7F7T6yfA3ThH6Rd0_ldpxRyaIsEvjdKXcHLM8aoAWx4QUH6bMrwMAE4BzKwa26kUkFLFbQ6SCFQIOIvA1DkfqoQ0L79Ze87v7ARhjzVLwjqz0ImMWpLN6iMcZB_nq6q8mmQcguyW31dhATJH171a05J58dD554KCFC9vkK_hNC4_XWnDgaLbXIh9bReT5cDktNTTPVHzeXWZCeNnjCjCfDHQkeAdLkpmqHPUDh-F7tfezdj6viANT2yTYu9l7cYRAodtgFjML5pYQqFre_axXXg8O_NUqLsV6MMYbPxZ6jyFPEga-s79ESfGFpVV63lMdvvn24fPiAc4nZiZ4zj0sywqs6pjh6rltHpFcAbzwNoZc-p39EPsfeutncJN4fnKly51_rGVM5U4y-u5yuKUvQmEH3-7iIMMMLtGI_u0)
