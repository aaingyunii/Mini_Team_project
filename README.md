# 미니 팀 프로젝트
플레이데이터 부트캠프 데이터 엔지니어링 과정 - '데이터엔지니어링' 의 학습을 활용한 미니 팀 프로젝트 결과물 입니다.


## 웹 크롤링
- `crawling_test.ipynb` 파일을 생성해 "네이버 웹툰 사이트" 를 참고하여 크롤링을 진행했습니다.
    
1. [https://comic.naver.com/webtoon](https://comic.naver.com/webtoon) 에서 `크롬 개발자 도구`를 통해 전체 웹툰에 대한 데이터를 담은 `GET API`를 이용해
1. 연재 중인 모든 웹툰들의 `Id, starScore(별점)` 데이터를 얻을 수 있었습니다.

    - 해당 `API` 매일마다 연재 중인 웹툰 리스트가 바뀌면서 데이터가 바뀌었기 때문에
    - 2023-08-29 기준으로 크롤링한 데이터들을 각각 `webtoon_id.pkl` , `webtoon_star_score.pkl` 파일로 저장했습니다.

1.  [https://comic.naver.com/webtoon/list?titleId=758037](https://comic.naver.com/webtoon/list?titleId=758037) : 한 웹툰으로 들어가 해당 웹툰의 상세 내용이 담긴 `GET API`를 이용해
1. `Id, Title, Writer, Painter, Age, Favorite, ThumbnailUrl, PublishDay, HashTags` 데이터들을 `dictionary` 에 담았습니다.
1. 여기에 `webtoon_star_score.pkl` 를 통해 `StarScore` 도 추가할 수 있었습니다.

1. 최종적으로, `webtoon_id.pkl` 을 통해 전체 웹툰의 상세 데이터들을 담은 `csv` 파일을 만들 수 있었고, DB에 저장하는 과정을 완료했습니다.

> ### **`naver_webtoon_crawling.py`** 
> 파일을 작성해 위의 과정을 모두 한번에 실행할 수 있습니다.


