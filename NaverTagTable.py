import json
import wget
import os
import pandas as pd
import pymysql

''' json 파일 생성 밑 DataFrame 만드는 함수
json 경로: "./json/Tag.json"
DataFrame 칼럼명
1. Genre_Id: 장르코드
2. Kr_Label: 한글제목
3. Kind: 장르종류
4. En_Label: 영어제목
'''
''' json 파일에서 담을 변수 선언
genre_code_list: Genre_Id[장르코드]
name_list: Kr_Label[한글제목]
kind_list: Kind[장르종류]
eng_name_list: En_Label[영어제목]
'''
''' json 파일 구조
    'tagItemList'
        -> 'text' : 한글제목
        -> 'type' : 장르종류
        -> 'name' : 영어제목
        -> 'id'   : 장르코드 [단: 장르종류의 ganre는 이 필드를 가지고 있지 않다.]
'''
def DataFrame_Hash_Tag():
    
    # URL / 파일 경로 정리
    url = "https://comic.naver.com/api/webtoon/tagList/shortcut"
    json_file_name = "Tag.json"
    json_dir_name  = "./json/"
    json_path = json_dir_name + json_file_name
    
    
    genre_code_list = []
    name_list = []
    kind_list = []
    eng_name_list = []

    # 장르코드 부여 위한 변수
    count = 1

    # 디렉터리 유/무 검사 [없을시 생성]
    if not os.path.isdir(json_dir_name):
        os.mkdir(json_dir_name)

    # json 파일 유/무 검사 [없을 시 생성]
    if not os.path.isfile(json_path):
        wget.download(url=url, out= json_path)
    
    # json 파일 열기
    su_json = json.load(open(json_path, encoding='utf-8-sig'))

    for tag in su_json['tagItemList']:
        name_list.append(tag['text'])
        kind_list.append(tag['type'])
        eng_name_list.append(tag['name'])
        
        # KeyError 예외 상황을 활용 하여 id 값 부여하기
        try:
            code = "C" + str(tag['id'])
        except KeyError:
            code = "G" + str(count)
            count += 1
        genre_code_list.append(code)

    # 생성된 리스트를 DataFrame화
    df = pd.DataFrame({
        'Genre_Id': genre_code_list,
        'Kr_Label':name_list,
        'Kind':kind_list,
        'En_Label':eng_name_list
    })

    return df

''' 장르 DataFrame 생성 후 DB에 넣는 함수
df: 장르 DataFrame
user: SQL 계정명
password: SQL 패스워드
port: 포트 번호
database: 데이터베이스 이름
'''
def DataBase_Hash_Tag(df, host, user, password, port, database):
    # Genre_Id: 기본키 / 널 값 입력 불가
    # VARCHAR:
    create_table = '''
    CREATE TABLE GENRE (
        Genre_Id VARCHAR(5) NOT NULL PRIMARY KEY,
        Kr_Label VARCHAR(255),
        Kind VARCHAR(255),
        En_Label VARCHAR(255)
    )
    '''
    sql = "INSERT INTO GENRE VALUES(%s, %s, %s, %s)"

    # DB 연결
    try:
        con = pymysql.connect(host=host, user=user, password=password, port=port, db=database, charset='utf8')
        cur = con.cursor()
    except Exception as e:
        print (e)

    # DB 확인
    check = cur.execute("show tables like 'GENRE'")

    # DB가 없을 시
    if check == False:
        cur.execute(create_table)

    # DataFrame 객체 1행을 튜플화 후 리스트에다가 담기
    # ex) [('G1', '로맨스', 'GENRE', 'PURE'),('G2', '판타지', 'GENRE', 'FANTASY'), ...]
    tuples = [tuple(x) for x in df.values]
    
    # executemany: 리스트 객체를 한번에 execute 하기
    try:
        cur.executemany(sql, tuples)
    except Exception as e:
        print(e)

    con.commit()

'''
host: 호스트 이름
user: SQL 계정명
password: SQL 패스워드
port: 포트 번호
database: 데이터베이스 이름
df: 장르 DataFrame
'''
if __name__ == "__main__":
    user = 'root'
    password = '8489'
    database = 'encore'
    port = 3306
    host = 'localhost'
    
    df = DataFrame_Hash_Tag()
    DataBase_Hash_Tag(df, host, user, password, port, database)

