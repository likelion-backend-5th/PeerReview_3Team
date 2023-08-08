# 📮 멋사 SNS 📮
> *지금까지 진행했던 미션형 프로젝트[1]를 마무리하고, 새로운 미션형 프로젝트를 시작해볼 시간입니다. 미션을 바탕으로 여러분들만의 멋사 SNS를 만들어보세요!*
## 🗓 프로젝트 기간
>2023.08.03 ~ 
## 🛠 개발환경
<img src="https://img.shields.io/badge/intellij-000000?style=for-the-badge&logo=intellijidea&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/springsecurity-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/sqlite-003B57?style=for-the-badge&logo=sqlite&logoColor=white">

## 📋 ERD 
![image](https://github.com/likelion-backend-5th/Project_2_YuChaeyeon/assets/52392720/e4531079-b567-4ea1-ae75-d2f2c9b37be1)
## 📌 Endpoint
### User
- *POST /users/register*
  - request body
    ```
    {
        "username": "yeoon",
        "password": "asdf"
    }
    ```
  - response body
    ```
    {
        "response": {
            "message": "가입이 완료되었습니다."
        }
    }
    ```
- *POST /users/login*
  - request body
    ```
    {
        "username": "yeoon",
        "password": "asdf"
    }
    ```
  - response body
    ```
    {
        "response": {
            "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ5ZW9vbiIsImlhdCI6MTY5MTA3MjY5MCwiZXhwIjoxNjkxMDc2MjkwfQ.qPLr-xrcdVC2eB7qv7EHkmqIs2dtqfVs2LcOgJ0d2BHCQ7EwodMesOWPzuewNAg6"
        }
    }
    ```
