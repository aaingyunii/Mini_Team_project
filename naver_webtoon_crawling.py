# 네이버 웹툰 페이지에서
# 2023년 8월 29일 기준, 연재 중인 웹툰 작품들을 크롤링
# "webtoon_id.pkl" 에 저장된 ID 값을 이용해 
# 다음 컬럼 값들을 DataFrame에 저장.
# 또한 위의 API에서 별점(starScore) 리스트 값도 가져와
# "webtoon_star_score.pkl" 에 저장.

### 컬럼 설명
# Id 웹툰 id 값
# Title 웹툰 제목
# Writer 글 작가
# Painter 그림 작가
# Age 연령 제한
# Favorite 관심도 수
# StarScore 별점 
# ThumbnailUrl 썸네일 사진 링크
# PublishDay 연재 요일
# HashTags 웹툰 내용 관련 해시태그

import requests
import pickle
import pandas as pd
from tqdm import tqdm
import pymysql
import csv

# 각 웹툰의 상세 내용이 담긴 API 주소값
detail_url = "https://comic.naver.com/api/article/list/info?titleId={}"

# 헤더
head = {'User-Agent':
'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36'}

# 각 웹툰의 데이터 dict 을 담을 빈 리스트
data_list = []

# 웹툰 데이터를 크롤링하고 DataFrame으로 저장 후 csv 파일로 저장하는 함수
def _get_webtoon_data(id_list, stars):   
    
    # id 값을 변화시키면서 각 웹툰의 데이터에 대해 GET 방식으로 요청함.
    for k in tqdm(range(len(id_list))) :
        x = id_list[k]

        r2= requests.get(detail_url.format(x), headers=head)

        # 'curationTagList' 부분은 딕셔너리 안에 딕셔너리 구조로 되어 있기 때문에
        ## 이를 각각의 데이터를 뽑아 리스트 안에 넣었고,
        ### 한 컬럼 안에 해당 데이터들을 모두 넣기 위해
        #### ", ".join() 을 통해 문자열로 변환함.
        ##### 또한 replace() 를 통해 문자열 간의 공백을 없애고, ',' 로만 구분짓게 함.
        tmp = []  # 해시태그 값들을 저장하기 위한 빈 리스트
        for i in range(r2.json()['curationTagList'].__len__()):
            tmp.append(r2.json()['curationTagList'][i]['tagName'])
        tags = ", ".join(tmp).replace(" ","")

        # 해시태그와 같은 이유로 구현.
        ## 일주일에 두 번이상 연재하는 웹툰이 있긴 때문에 컬럼 값에 해당 내용을 모두 저장하기 위해서
        tmp2 = []
        for i in range(r2.json()['publishDayOfWeekList'].__len__()):
            tmp2.append(r2.json()['publishDayOfWeekList'][i])   
        days = ", ".join(tmp2).replace(" ","")

        # 최종 데이터프레임에 저장될 각각의 값들에 해당하는 dict 변수.
        temp = {
            'WebtoonId' : x,
            'Title': r2.json()['titleName'],
            'Writer': r2.json()['author']['writers'][0]['name'],
            'Painter' : r2.json()['author']['painters'][0]['name'],
            'Age': r2.json()['age']['type'],
            'Favorite': r2.json()['favoriteCount'],
            'StarScore': stars[k],
            'ThumbnailUrl' :  r2.json()['thumbnailUrl'],
            'PublishDay': days,
            'HashTags': tags
        }
        
        data_list.append(temp)
    
    df = pd.DataFrame(data_list)
    
    df.to_csv("./webtoon.csv", encoding="utf-8-sig", index=False)


# 위에서 저장된 csv 파일을 이용해 MySQL DB에 저장하는 함수.
def _store_db():
    try:
        # 각 MySQL 환경에 맞게 host, user, password, db 값 입력.
        con = pymysql.connect(host='127.0.0.1', user='root', password='8489',  db='encore', charset='utf8')
        cur = con.cursor()
    except Exception as e:
        print (e)
        
    # webtoon 테이블 있는 지 확인
    check = cur.execute("show tables like 'webtoon'")
    
    # 테이블이 없다면 테이블 생성
    if check ==False:  
        cr_table = """CREATE TABLE webtoon(
                        WebtoonId INT(11) NOT NULL PRIMARY KEY,
                        Title VARCHAR(255),
                        Writer VARCHAR(255),
                        Painter VARCHAR(255),
                        Age VARCHAR(255),
                        Favorite INT(11),
                        StarScore FlOAT(7,2),
                        ThumbnailUrl VARCHAR(255),
                        PublishDay VARCHAR(255),
                        HashTags VARCHAR(255)
                    )
                    """
        cur.execute(cr_table)
    
    sql = "INSERT INTO webtoon VALUES(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)"
    
    # 해당 csv 파일을 "csv" 모듈 없이 읽게 되면 반환되는 값이 str 형태로 출력되어
    ## 'not enough arugments for format string' 에러가 sql 실행문에 발생한다.
    ### <== HashTags 컬럼 값들이 ',' 로 구분되어 여러 문자열이 합쳐져서 들어갔기 때문에
    #### 따라서 "csv" 모듈을 통해 delimiter=',' 으로 구분 짓어 list 형태로 값이 나올 수 있게되어
    ##### sql 문을 실행할 수 있게되었다.
    with open("./webtoon.csv", "r", encoding='utf-8-sig') as csvfile:
        reader = csv.reader(csvfile, delimiter=',')
        for idx,row in tqdm(enumerate(reader)):
            if idx > 0: # csv 파일 내 컬럼 값들을 제외시키기 위해.
                try:
                    cur.execute(sql,row)
                except Exception as e:
                    print(e)
    con.commit()

with open("./webtoon_id.pkl","rb") as f:
    ids = pickle.load(f)

with open("./webtoon_star_score.pkl","rb") as f:
    stars = pickle.load(f)
        
# 파이썬 실행이 직렬 프로세스이므로
## 위에서 부터 순서대로 실행된다.
_get_webtoon_data(ids,stars=stars)

_store_db()             
