# 미니 팀 프로젝트
플레이데이터 부트캠프 데이터 엔지니어링 과정 - '데이터엔지니어링' 의 학습을 활용한 미니 팀 프로젝트 결과물 입니다.


## 웹 크롤링
- `crawling_test.ipynb` 파일을 생성해 "네이버 웹툰 사이트" 를 참고하여 크롤링을 진행했습니다.
- `requests` 모듈을 사용해 API 데이터를 받아 데이터 전처리를 통해 DB에 저장할 수 있었습니다.
    
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

## 웹 페이지 구현 with `Spring boot`

- `Spring boot` 프레임 워크를 사용해 웹 페이지를 작성했습니다.
    - `Spring boot` : 2.7.15 버전
    - `Java` : 11 버전
    - `Dependency` : `Spring Web, MySQL, Lombok, Spring Boot DevTools, Mustache` 추가

- 뷰에서는 `mustache` 템플릿 엔진을 사용했습니다.<br><br>

- #### DB Tables : 
>1. **`webtoon`** - 크롤링한 2023.08.29 기준 연재 중인 웹툰 데이터 + `toonrank` 테이블 생성 시 추가된 연재 종료된 웹툰 데이터를 포함
>2. **`genre`** - 장르 코드와 장르의 한글 이름, 영어 이름, 장르의 종류 데이터 포함. `toonrank` 테이블과 `Join` 쿼리를 위해 생성
>3. **`toonrank`** - 장르 코드와 웹툰 아이디, 해당하는 랭킹 숫자 데이터 포함. 이후 `genre` 테이블과 `webtoon` 테이블고의 `Join`을 통해 썸네일 이미지 url, 웹툰 명을 전달받아 사용.
>4. **`webtoon_review`** : 미리 생성된 테이블이 아닌 `Entity` 연결을 통해 생성된 테이블. 리뷰에 매칭되는 웹툰 ID, 제목, 썸네일 이미지 url 데이터를 포함하고 리뷰 제목과 내용 데이터도 포함.

- `Entity, Repository, Service, DTO, Controller` 객체를 생성하여 웹 페이지에 데이터를 전달하는 과정에 대해 학습할 수 있었고,
- 특히, DB에 저장된 데이터 값을 `Service` 내에서 변환하여 `Controller`를 통해 웹 뷰에 전달하는 것을 구현하면서 **`Back-end`** 개념을 이해할 수 있었습니다.
- 이외에도 웹 뷰에 뿌려주는 각종 데이터들은 `Repository` 에서 `@Query`를 통해 `JPQL` 문을 작성하거나, `Service` 내에서 전처리 과정을 거쳐서 전달이 가능했습니다.
- 때로는 `Controller` 에서 `@ModelAttribute` 를 통해 웹에 작성된 데이터를 받아서 구현하거나
- `@PathVariable` 을 통해 url API 값을 이용해 `Service` 내의 함수를 사용하기도 했습니다.

- 아래 이미지는 구현한 웹의 메인 페이지 이미지입니다.

![그림1](https://github.com/aaingyunii/mini_team_project/assets/31847834/635ee618-ff29-40e9-a612-63442e0aab07)
