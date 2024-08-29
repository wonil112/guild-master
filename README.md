
<a href="https://club-project-one.vercel.app/" target="_blank">
<img src="https://github.com/user-attachments/assets/daa622b9-7c69-4786-8db3-4996b7f140be" alt="배너" width="100%"/>
</a>

<br/>
<br/>

# 0. Getting Started (시작하기)
```bash
$ npm start
```
[서비스 링크](https://club-project-one.vercel.app/)

<br/>
<br/>

# 1. Project Overview (프로젝트 개요)
- 프로젝트 이름: GUILD MASTER
- 프로젝트 설명: 전국 게임 길드의 일정관리

<br/>
<br/>

# 2. Key Features (주요 기능)
- **회원가입**:
  - 회원가입 시 DB에 유저정보가 등록됩니다.

- **로그인**:
  - 사용자 인증 정보를 통해 로그인합니다.

- **게임 별 길드 찾기**:
  - 캘린더 UI를 통해 동아리 관련 일정 추가&삭제가 가능합니다.
  - 체크박스를 통해 종료되거나 이미 수행한 일정을 표시할 수 있습니다.

- **동아리 찾기**:
  - 대학 내 동아리를 검색할 수 있습니다.
  - 검색 시 해당 동아리가 업로드한 홍보글이 보여집니다.

- **동아리 홍보**:
  - 홍보글 등록을 통해 동아리를 홍보할 수 있습니다.

- **동아리 만들기**:
  - 새로운 동아리를 만들어 관리할 수 있습니다.

- **동아리 프로필**:
  - 동아리 홍보글에서 동아리 이름(링크)를 클릭하면 해당 동아리 프로필로 이동합니다.
  - 동아리 프로필에서는 동아리 소개, 동아리 활동사진 갤러리, 동아리 홍보글 기록관 등을 볼 수 있습니다.

<br/>
<br/>

# 3. Tasks & Responsibilities (작업 및 역할 분담)
|  |  |  |
|-----------------|-----------------|-----------------|
| &nbsp;&nbsp;&nbsp;👑<br>박원일    |  <img src="https://github.com/user-attachments/assets/fa2563b2-d0ef-4869-8c99-edfdc3711cd6" alt="박원일" width="100"> | <ul><li>프로젝트 계획, Git을 활용한 프로젝트 관리</li><li>팀 리딩 및 팀원간 커뮤니케이션 관리</li><li>JWT Security 구현</li></li><li>전반적인 UI/UX 구현 및 전역 로그인 상태관리기능 구현</li></ul>     |
| 은하늘   |  <img src="https://github.com/user-attachments/assets/7311b866-a90a-4228-a554-da1a6eca2862" alt="은하늘" width="100">| <ul><li>길드 검색, 길드 보드, 길드원 관리 페이지 게시판 제작</li><li>길드, 이벤트 조회 및 상세 확인, 길드원 가입 신청, 수락 기능</li><li>Axios 를 활용한 백엔드와의 비동기 통신 구현, REST API 문서 작성 </li><li>페이지별 컴포넌트 세분화, 코드 베이스 일관화</li></ul> |
| 김영진   |  <img src="https://github.com/user-attachments/assets/d270d709-8afa-46c9-b94a-97ef98cd71ec" alt="김영진" width="100">    |<ul><li>테이블 설계 및 객체 연관 관계 매핑</li><li>인증, 인가를 위한 시큐리티 적용 및 JWT 구현 </li><li>레디스를 활용한 로그아웃 구현</li><li>계층 구조에 따른 회원가입, 길드 가입요청, 가입요청수락 등 전반적인 핵심 서비스 로직 구현</li><li>전역 예외 처리 구현</li></ul>  |
| 김준하    |  <img src="https://github.com/user-attachments/assets/a0153d6c-1b8f-44be-aadd-9bcf7ae1f2ff" alt="김준하" width="100">    | <ul><li>길드 이벤트 CRUD 기능 설계 및 구현</li><li>길드원 이벤트 참가 기능 개발</li><li>이벤트 참가자 정보 관리 및 통계 구현</li><li>요구사항 분석을 통한 테이블 관계 정의 및 ERD 정규화</li><li>AWS 클라우드 인프라 배포</li></ul>    |

<br/>
<br/>

# 4. Technology Stack (기술 스택)
### 🔨 Front-end
| Html | JavaScript | React | CSS |
| :---: | :---: | :---: | :---: |
| <img alt="Html" src ="https://upload.wikimedia.org/wikipedia/commons/thumb/6/61/HTML5_logo_and_wordmark.svg/440px-HTML5_logo_and_wordmark.svg.png" width="65" height="65" /> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/js-icon.svg" alt="icon" width="75" height="75" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/react-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/8e99f402-391a-45f6-9312-05be3ce887f0" alt="icon" width="65" height="65" /></div> |
<br/>

### ⛏ Back-end
| Java | mySQL | Redis | AWS | Spring | Spring<br>Boot |
| :---: | :---: | :---: | :---: | :---: | :---: |
| <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/java-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/mysql-icon.svg" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://github.com/user-attachments/assets/ee9ac300-f713-4b48-9bc2-9f5ec1774ae9" alt="icon" width="65" height="65" /></div> | <div style="display: flex; align-items: flex-start;"><img src="https://techstack-generator.vercel.app/aws-icon.svg" alt="icon" width="65" height="65" /></div> | <img alt="spring logo" src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" height="50" width="50" > | <img alt="spring-boot logo" src="https://t1.daumcdn.net/cfile/tistory/27034D4F58E660F616" width="65" height="65" > |
<br/>

## 4.5 Cooperation
|  |  |
|-----------------|-----------------|
| Git    |  <img src="https://github.com/user-attachments/assets/483abc38-ed4d-487c-b43a-3963b33430e6" alt="git" width="100">    |
| Git Kraken    |  <img src="https://github.com/user-attachments/assets/32c615cb-7bc0-45cd-91ea-0d1450bfc8a9" alt="git kraken" width="100">    |
| Notion    |  <img src="https://github.com/user-attachments/assets/34141eb9-deca-416a-a83f-ff9543cc2f9a" alt="Notion" width="100">    |

<br/>

# 5. Project Structure (프로젝트 구조)
<br>

## 📂 프로젝트 폴더 구조

```
🏠 최애의 i
├─ .github
│  └─ ISSUE_TEMPLATE  ─────────────── 📝 이슈 템플릿
│
├─ 📂 client
│  │─ .env.sample
│  │─ .eslintrc.json  ──────────────── ⚙️ eslint 설정 파일
│  │─ .gitignore
│  │─ .prettierrc.json ─────────────── ⚙️ prettier 설정 파일
│  │─ package-lock.json
│  │─ package.json
│  │
│  ├─ 📂 public ────────────────────── 📦 FE 공통 컴포넌트
│  │  ├─ 📂 data
│  │  │  ├─ allQuestions.json ──────── 🎭 질문 리스트 Mock 데이터
│  │  │  └─ questionDetail.json ────── 🎭 질문 상세 페이지 Mock 데이터
│  │  └─ index.html
│  │
│  └─ 📂 src
│     ├─ 📂 components ─────────────── 📦 모든 페이지에서 공통으로 사용되는 컴포넌트
│     │  ├─ 📂 detail ──────────────── 📦 질문 상세 페이지에서 사용되는 컴포넌트
│     │  │  ├─ AnswerSection.jsx
│     │  │  ├─ ProfileCard.jsx
│     │  │  ├─ QuestionSection.jsx
│     │  │  └─ TitleSection.jsx
│     │  ├─ 📂 myPage ──────────────── 📦 마이 페이지에서 사용되는 컴포넌트
│     │  │  ├─ ActivityBox.jsx
│     │  │  ├─ ProfileBox.jsx
│     │  │  ├─ SavesBox.jsx
│     │  │  └─ SettingBox.jsx
│     │  ├─ 📂 Post ────────────────── 📦 질문 작성 페이지에서 사용되는 컴포넌트
│     │  │  ├─ Card.jsx
│     │  │  ├─ DetailSection.jsx
│     │  │  ├─ NoticeSection.jsx
│     │  │  └─ TitileSection.jsx
│     │  ├─ Button.jsx
│     │  ├─ Editor.jsx
│     │  ├─ Footer.jsx
│     │  ├─ MyPageBoxes.jsx
│     │  ├─ Nav.jsx
│     │  ├─ ProfileIcon.jsx
│     │  ├─ RightSidebar.jsx
│     │  ├─ Sidebar.jsx
│     │  ├─ SignupDropdown.jsx
│     │  └─ Viewer.jsx
│     │
│     ├─ 📂 imgs   ─────────────────── 📦 모든 페이지에서 공통으로 사용되는 이미지 폴더
│     │  ├─ 📂 profile ─────────────── 📦 프로필 사진으로 사용되는 이미지
│     │  └─ ...
│     │
│     ├─ 📂 pages   ────────────────── 📦 라우팅이 적용된 API를 요청하는 페이지 폴더
│     │  ├─ 📂 questions
│     │  │  ├─ Detail.jsx ──────────── 📲 질문 상세 페이지
│     │  │  ├─ Edit.jsx ────────────── 📲 질문 수정 페이지
│     │  │  ├─ Main.jsx ────────────── 📲 질문 메인 페이지
│     │  │  ├─ Post.jsx ────────────── 📲 질문 작성 페이지
│     │  │  └─ Search.jsx ──────────── 📲 질문 검색 페이지
│     │  ├─ 📂 users
│     │  │  ├─ Login.jsx ───────────── 🙋‍♂️ 유저 로그인 페이지
│     │  │  ├─ Mypage.jsx ──────────── 🙋‍♂️ 유저 마이 페이지
│     │  │  └─ Register.jsx ────────── 🙋‍♂️ 유저 회원가입 페이지
│     │  ├─ Home.jsx ───────────────── 🏠 홈페이지
│     │  └─ NotFound.jsx ───────────── 🚫 404페이지
│     │
│     ├─ 📂 utils    ───────────────── 📡 공통으로 사용되는 유틸 함수 폴더
│     │  ├─ axios.js
│     │  └─ profiles.js
│     ├─ App.css
│     ├─ App.js
│     ├─ index.css
│     └─ index.js
│
└─ 📂 server
   │─ .gitignore
   │─ build.gradle
   │─ gradlew
   │─ gradlew.bat
   │─ settings.gradle
   │
   ├─ 📂 gradle-wrapper
   │  ├─ gradle-wrapper.jar
   │  └─ gradle-wrapper.properties
   │
   └─ 📂 src
      ├─ 📂 main
      │  ├─ 📂 java/com/teamtwentyone
      │  │  ├─ 📂 advice ────────────────────── 🤚 서버 내 api, service 계층에서 발생하는 에러를 처리하는 핸들러
      │  │  │  └─ GlobalExceptionAdvice.java
      │  │  ├─ 📂 auth ──────────────────────── 🍃 Spring Security 패키지
      │  │  │  ├─ 📂 config ─────────────────── 🍃 Spring Security 설정 class
      │  │  │  │  └─ SecurityConfig.java
      │  │  │  ├─ 📂 dto
      │  │  │  │  ├─ LoginDto.java ──────────── 🙋‍♂️ 유저의 로그인 정보를 담을 DTO class
      │  │  │  │  └─ PrincipalDto.java ──────── 🙋‍♂️ 유저의 인증 정보를 담을 DTO class
      │  │  │  ├─ 📂 filter ─────────────────── 🪪 JWT 인증, 인가 필터
      │  │  │  │  ├─ JwtAuthenticationFilter.java
      │  │  │  │  └─ JwtVerificationFilter.java
      │  │  │  ├─ 📂 handler ────────────────── 🤚 유저 인증/인가의 성공 및 실패를 처리하는 핸들러
      │  │  │  │  ├─ MemberAccessDeniedHandler.java
      │  │  │  │  ├─ MemberAuthenticationEntryPoint.java
      │  │  │  │  ├─ MemberAuthenticationFailureHandler.java
      │  │  │  │  └─ MemberAuthenticationSuccessHandler.java
      │  │  │  ├─ 📂 jwt ────────────────────── 🪪 JWT 토큰 생성을 위한 class
      │  │  │  │  └─ JwtTokenizer.java
      │  │  │  ├─ 📂 userdetails ────────────── 🔎 인증된 유저의 정보 조회 service class
      │  │  │  │  └─ MemberUserDetailService.java
      │  │  │  └─ 📂 utils ──────────────────── 📡 인증 정보의 권한 변환 및 에러처리
      │  │  │  │  ├─ CustomAuthorityUtils.java
      │  │  │  │  └─ ErrorResponseSender.java
      │  │  ├─ 📂 exception
      │  │  │  ├─ BusinessLoginException.java ─ 📡 비즈니스 로직에서 발생하는 예외 처리를 위한 class
      │  │  │  └─ ExceptionCode.java ────────── 📡 예외 코드 모음
      │  │  ├─ 📂 response
      │  │  │  └─ ErrorResponse.java ────────── 📡 예외 응답을 위한 DTO class
      │  │  ├─ 📂 time
      │  │  │  ├─ DateTimeEntity.java ───────── 📝 질문, 답변용 시간기록 Entity
      │  │  │  └─ UserDateEntity.java ───────── 📝 유저용 시간기록 Entity
      │  │  ├─ 📂 users
      │  │  │  ├─ 📂 Dto ────────────────────── 📡 회원 데이터의 계층간 교환을 위한 DTO class
      │  │  │  │  └─ UserDto.java
      │  │  │  ├─ 📂 controller ─────────────── 📡 회원 기능 api 컨트롤러
      │  │  │  │  └─ UserController.java
      │  │  │  ├─ 📂 entity ─────────────────── 📝 회원 데이터 객체
      │  │  │  │  └─ User.java
      │  │  │  ├─ 📂 mapper ─────────────────── 📝 회원 DTO와 Entity 간 매핑을 위한 mapper class
      │  │  │  │  └─ UserMapper.java
      │  │  │  ├─ 📂 repository ─────────────── 📝 Spring Data JPA 사용을 위한 회원 repository
      │  │  │  │  └─ UserRepository.java
      │  │  │  └─ 📂 service ────────────────── 🙋‍♂️ 회원 기능의 비즈니스 로직
      │  │  │     └─ UserService.java
      │  │  └─ BackendServerApplication.java ── 🏠 App 실행을 위한 main class
      │  └─ 📂 resources
      │     ├─ application-local.yml
      │     ├─ application-mysql.yml
      │     └─ application-server.yml
      │
      └─ 📂 test/java/com/teamtwentyone
         └─ BackendServerApplicationTests.java

```

<br>
<br/>

# 6. Development Workflow (개발 워크플로우)
## 브랜치 전략 (Branch Strategy)
우리의 브랜치 전략은 Git Flow를 기반으로 하며, 다음과 같은 브랜치를 사용합니다.

- Main Branch
  - 배포 가능한 상태의 코드를 유지합니다.
  - 모든 배포는 이 브랜치에서 이루어집니다.
  
- {name} Branch
  - 팀원 각자의 개발 브랜치입니다.
  - 모든 기능 개발은 이 브랜치에서 이루어집니다.

<br/>
<br/>

# 7. Coding Convention

## 🤙 컨벤션

<details>
  <summary><h4>📌 Issue</h4></summary>
  
  1. 제목
    
  - 작업 내용에 따라 커밋 메시지에 사용하는 Gitmoji를 적절하게 작성
  - 자신이 작업한 내용을 한 눈에 파악하기 쉽도록 명사형으로 작성
    
```
[FE/BE] ✨ OOO 컴포넌트 구현
```

2. 내용


    ## Abstracts
    * 간략하게 할 일에 대한 설명을 작성해주세요.

    ## To Do
    - [ ] 구현할 기능 1
    - [ ] 구현할 기능 2

    ## ETC
    * 추가적인 안내 사항이 있다면 작성해주세요.

</details>

<details>
  <summary><h4>📌 Commit</h4></summary>

- 커밋 메시지

1. 적절한 커밋 접두사 작성
2. 커밋 메시지 내용 작성
3. 내용 뒤에 이슈 (#이슈 번호)와 같이 작성하여 이슈 연결


    [FE/BE] 🔧 Conf: 초기 환경 설정 (#1)

| 접두사        | 설명                           |
| ------------- | ------------------------------ |
| ✨ Feat :     | 새로운 기능 구현               |
| 🍱 Add :      | 에셋 파일 추가                 |
| 🐛 Fix :      | 버그 수정                      |
| 📝 Docs :     | 문서 추가 및 수정              |
| 💄 Style :    | 스타일링 작업                  |
| ♻️ Refactor : | 코드 리팩토링 (동작 변경 없음) |
| 🧪 Test :     | 테스트                         |
| 🚀 Deploy :   | 배포                           |
| 🔧 Conf :     | 빌드, 환경 설정                |
| ✏️ Chore :    | 기타 작업                      |

</details>

<details>
  <summary><h4>📌 Pull Request</h4></summary>

1. 제목

- 작업 내용에 따라 커밋 메시지에 사용하는 Gitmoji를 적절하게 작성
- 자신이 작업한 내용을 한 눈에 파악하기 쉽도록 명사형으로 작성

```
 [FE/BE] ✨ OOO 컴포넌트 구현
```

2. 내용


    ## Title
    * 제목은 '✨ 홈 페이지 구현'과 같이 작성합니다.

    ## PR Type
    - [ ] FEAT: 새로운 기능 구현
    - [ ] ADD : 에셋 파일 추가
    - [ ] FIX: 버그 수정
    - [ ] DOCS: 문서 추가 및 수정
    - [ ] STYLE: 포맷팅 변경
    - [ ] REFACTOR: 코드 리팩토링
    - [ ] TEST: 테스트 관련
    - [ ] DEPLOY: 배포 관련
    - [ ] CONF: 빌드, 환경 설정
    - [ ] CHORE: 기타 작업

    ## Abstracts
    * 작업 내용에 대해 간략하게 설명을 작성해주세요.

    ## Description
    * 구체적인 작업 내용을 작성해주세요.
    * 이미지를 별도로 첨부하면 더 좋습니다 👍

    ## Discussion
    * 추후 논의할 점에 대해 작성해주세요.

    ---
    Close #1
    (작성한 Issue를 연결해주세요.)

</details>

<br/>
<br/>

# 8. 커밋 컨벤션
## 기본 구조
```
type : subject

body 
```

<br/>

## type 종류
```
feat : 새로운 기능 추가
fix : 버그 수정
docs : 문서 수정
style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
refactor : 코드 리펙토링
test : 테스트 코드, 리펙토링 테스트 코드 추가
chore : 빌드 업무 수정, 패키지 매니저 수정
```

<br/>

## 커밋 이모지
```
== 코드 관련
📝	코드 작성
🔥	코드 제거
🔨	코드 리팩토링
💄	UI / style 변경

== 문서&파일
📰	새 파일 생성
🔥	파일 제거
📚	문서 작성

== 버그
🐛	버그 리포트
🚑	버그를 고칠 때

== 기타
🐎	성능 향상
✨	새로운 기능 구현
💡	새로운 아이디어
🚀	배포
```

<br/>

## 커밋 예시
```
== ex1
✨Feat: "회원 가입 기능 구현"

SMS, 이메일 중복확인 API 개발

== ex2
📚chore: styled-components 라이브러리 설치

UI개발을 위한 라이브러리 styled-components 설치
```

<br/>
<br/>

## 9. 🖥 구현 이미지

| 페이지 (기능)         | 이미지                                                                                                                          |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------- |
| 메인                  | ![메인 페이지](https://github.com/nalsae/seb45_main_011/assets/101828759/7e293d8a-9934-4aa8-b3af-e6ccd7aa112b)                  |
| 로그인                | ![로그인 페이지](https://github.com/nalsae/seb45_main_011/assets/101828759/7b393893-9c3b-45a3-aa21-748f46b8b522)                |
| 회원 가입             | ![회원 가입 페이지](https://github.com/nalsae/seb45_main_011/assets/101828759/9b6db6ec-0e98-4267-a172-c2adf29a9fb9)             |
| 정원 (꾸미기)         | ![정원 페이지 - 꾸미기](https://github.com/nalsae/seb45_main_011/assets/101828759/de3caba6-42f5-4708-ad9c-8bd2e50a4231)         |
| 정원 (장식품 구매)    | ![정원 페이지 - 구매](https://github.com/nalsae/seb45_main_011/assets/101828759/49c9acf3-b06d-44e9-9a16-208f8a022b63)           |
| 정원 (식물 카드 연동) | ![정원 페이지 - 식물 카드 연동](https://github.com/nalsae/seb45_main_011/assets/101828759/ef6da970-f632-45b8-b0f9-eb737d5b9903) |
| 식물 카드 목록, 등록  | ![식물카드 목록](https://github.com/nalsae/seb45_main_011/assets/101828759/a7f05c66-9a9b-4ff0-a32d-08599fb4dd9a)                |
| 식물 카드 상세        | ![식물 카드 상세](https://github.com/nalsae/seb45_main_011/assets/101828759/38ab89f9-ec9c-4410-aeb9-83e9cd09ca7e)               |
| 식물 카드 수정, 삭제  | ![식물 카드 수정, 삭제](https://github.com/nalsae/seb45_main_011/assets/101828759/6fe42722-8676-4eee-bd00-27bc16c9c9f0)         |
| 커뮤니티              | ![커뮤니티](https://github.com/nalsae/seb45_main_011/assets/101828759/a27f3bfd-3346-4070-a6b8-082b5708f73a)                     |
| 게시글 상세           | ![게시글 상세](https://github.com/nalsae/seb45_main_011/assets/101828759/128584db-95cd-4edf-9161-74962f67d645)                  |
| 게시글 등록           | ![게시글 등록](https://github.com/nalsae/seb45_main_011/assets/101828759/211bd724-984e-45b8-989a-4c59246dbec2)                  |
| 게시글 수정, 삭제     | ![게시글 수정, 삭제](https://github.com/nalsae/seb45_main_011/assets/101828759/1ea79b6f-2717-406b-89f4-adbdfcaa785e)            |
| 댓글 등록, 수정, 삭제 | ![댓글 등록, 수정, 삭제](https://github.com/nalsae/seb45_main_011/assets/101828759/52ccd8f0-5e30-4198-bfc9-2dca9b28b870)        |
| 정보 수정             | ![정보 수정](https://github.com/nalsae/seb45_main_011/assets/101828759/a74749c1-d239-48cc-ac55-f6eda46ff228)                    |
| 내 게시글             | ![내 게시글](https://github.com/nalsae/seb45_main_011/assets/101828759/9d05dd7d-0cd3-4f86-b0b8-43b5438ba724)                    |
| 회원 탈퇴             | ![회원 탈퇴](https://github.com/nalsae/seb45_main_011/assets/101828759/120777f3-9c77-4681-bd3c-78cd5204c90e)                    |

<br>
