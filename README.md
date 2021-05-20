# Cinecok - 영화 검색 및 추천 앱       

<img src="https://user-images.githubusercontent.com/26836974/112165839-74c12300-8c32-11eb-9c59-e02a0f6f268c.PNG" align="left" width="150" hspace="3" vspace="3">

Cinecok은 Naver 검색API와 Naver 영화 웹 페이지 크롤링을 기반으로 개발된  
영화 검색 및 추천 앱입니다. 궁금한 영화를 검색해보고 정보를 스크랩 할 수 있으며,  
현재 상영중인 영화를 평점순/예매순으로 순위을 간편하게 확인하실 수 있습니다.

#### 　　　　
---
## 개발 환경
- IDE : Android Studio 4.1.2
- Language : kotlin

## 사용환경
- minSdkVersion : 19
- targetSdkVersion : 30

## Architecture
- MVVM

## Usage
- Android Architecture Component
  - ViewModel
  - LiveData
  - DataBinding
- Networks
  - Retrofit2
- Image
  - Glide
  - Lottie
- Local DataBase
  - Room
- DI
  - Koin
- Web Crawling
  - Jsoup
- 3rd Party Libraries
  - Nice Spinner
  
  
## Features
- Repository 패턴 적용
- DI (Dependency Injection) 사용
- Binding Adapter 사용
- Base Abastract class 정의
- 사용하기 편리한 UI

## Preview 화면
## 1. 홈 탭 ( 개봉 예정 기대작 추천 및 현재 상영작 예매순/평점순 영화 순위 )

  <img width="200" src="https://user-images.githubusercontent.com/26836974/112174885-1dbf4c00-8c3a-11eb-83ed-292cef3e9a0b.gif">
  
## 2. 검색 탭  
  2-1. 검색 결과가 있는 경우
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112175070-45161900-8c3a-11eb-853d-08969ad9799a.gif">
  
  2-2. 검색 결과가 없는 경우
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112176191-3b40e580-8c3b-11eb-9bdb-a466a26045a1.gif">
  
  2-3. 필터 적용
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112175160-59f2ac80-8c3a-11eb-9a62-a982b1705554.gif">
  
  2-4. 상세 정보 확인
   
   <img width="200" src="https://user-images.githubusercontent.com/26836974/112176557-89ee7f80-8c3b-11eb-9449-3eba35e6ce24.gif">
  
  2-5. 고화질 로딩중 저화질 썸네일 적용 
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112172145-ce781c00-8c37-11eb-820e-4914d3532adb.gif">
  
  
## 3. 스크랩 탭
  3-1. 스크랩 버튼 ( 별 아이콘 )
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112175452-9920fd80-8c3a-11eb-9301-dac387f5d7ee.gif">
  
  3-2. 스크랩 내용 확인
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112175524-a76f1980-8c3a-11eb-9f0b-ebe33647b530.gif">

  3-3. 스크랩 지우기
  
  <img width="200" src="https://user-images.githubusercontent.com/26836974/112175567-b0f88180-8c3a-11eb-9c28-5c5dfd631f07.gif">