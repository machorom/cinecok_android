package com.daou.cinecok.data.restapi

/*
* start -검색의 시작 위치
* query -질의어
* genre -장르
    {1: 드라마 2: 판타지 3: 서부 4: 공포
    5: 로맨스 6: 모험 7: 스릴러 8: 느와르
    9: 컬트 10: 다큐멘터리 11: 코미디 12: 가족
    13: 미스터리 14: 전쟁 15: 애니메이션 16: 범죄
    17: 뮤지컬 18: SF 19: 액션20: 무협
    21: 에로 22: 서스펜스 23: 서사 24: 블랙코미디
    25: 실험 26: 영화카툰 27: 영화음악 28: 영화패러디포스터}

* country -국가 ( 국가코드는 영어 대문자만 사용 가능하다 )
    {KR: 한국 , JP: 일본 , US: 미국 , HK : 홍콩, GB : 영국, FR : 프랑스, ETC : 기타}
*/

data class NSearchMovieRequest (
    val query : String,
    val genre : Int,
    var start : Int,
    val country : String
    )
// infinite scroll 에서 다음 페이지 가져올때 start만 변경해서 다시 요청하기위해 start는 mutable(var)로 선언
