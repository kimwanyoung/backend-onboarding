# 백엔드 개발 온보딩 과제(JAVA)

## 요구 사항

- [ ] Spring Security를 사용한 인증/인가 구현
- [ ] JWT 사용 Access Token, Refresh Token
- [ ] JUnit를 이용한 JWT 단위 테스트 구현
- [ ] Swagger API 테스트

### Spring Security를 사용한 인증/인가

### 회원가입

- [ ] 요청 경로 - `POST /signup`
- [ ] [Exception] 이미 존재하는 username이면, 예외가 발생한다.

#### 회원가입 요청 예시

```json
{
  "username": "wanyoung",
  "password": "12341234",
  "nickname": "wanyoung"
}
```

#### 회원가입 응답 예시

```json
{
  "username": "wanyoung",
  "nickname": "wanyoung",
  "authorities": [
    {
      "authorityName": "ROLE_USER"
    }
  ]
}
```

### 로그인

- [ ] 요청 경로 - `POST /sign`
- [ ] [Exception] 존재하지 않는 `username`이면 예외가 발생한다.
- [ ] [Exception] 올바르지 않은 `password`면 예외가 발생한다.

#### 로그인 요청 예시

```json
{
  "username": "wanyoung",
  "password": "12341234"
}
```

#### 로그인 응답 예시

```json
{
  "accessToken": "eKDIkdfjoakIdkfjpekdkcjdkoIOdjOKJDFOlLDKFJKL"
}
```
### 배포 주소
ec2 퍼블릭 IPv4 DNS : ec2-54-180-245-156.ap-northeast-2.compute.amazonaws.com
