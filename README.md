# Payment Service

결제 서비스를 담당하는 MSA(Microservice Architecture) 기반의 Spring Boot 애플리케이션입니다.

## 🚀 기술 스택

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: JPA, QueryDSL
- **Service Discovery**: Netflix Eureka
- **Build Tool**: Gradle
- **Container**: Docker
- **CI/CD**: GitHub Actions
- **Payment Gateway**: Toss Payments API

## 📋 주요 기능

### 💳 결제 기능
- Toss Payments API 연동
- 결제 생성 및 확인
- 결제 상태 관리
- 결제 이력 조회

### 💰 머니 관리
- 사용자 머니 잔액 조회
- 머니 입출금 내역 관리
- 머니 히스토리 조회

### 🏦 BrandPay 기능
- 카드 등록 및 관리
- 자동 결제 설정
- 토큰 관리

## 🏗️ 아키텍처

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │    │  Auth Service   │    │ Payment Service │
│                 │    │                 │    │                 │
│ - 라우팅        │    │ - JWT 토큰      │    │ - 결제 처리     │
│ - 인증/인가     │    │ - 사용자 검증   │    │ - 머니 관리     │
│ - 로드밸런싱    │    │ - 권한 관리     │    │ - BrandPay      │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │   Eureka Server │
                    │                 │
                    │ - 서비스 디스커버리
                    │ - 헬스 체크
                    └─────────────────┘
```

## 📁 프로젝트 구조

```
src/main/java/com/pieceofcake/paymentservice/
├── common/                    # 공통 모듈
│   ├── config/               # 설정 클래스
│   ├── entity/               # 공통 엔티티
│   └── exception/            # 예외 처리
├── money/                    # 머니 도메인
│   ├── application/          # 비즈니스 로직
│   ├── dto/                  # 데이터 전송 객체
│   ├── entity/               # 엔티티
│   ├── infrastructure/       # 데이터 접근 계층
│   ├── presentation/         # 컨트롤러
│   └── vo/                   # 뷰 객체
├── payment/                  # 결제 도메인
│   ├── application/          # 비즈니스 로직
│   ├── brandpay/             # BrandPay 기능
│   ├── dto/                  # 데이터 전송 객체
│   ├── entity/               # 엔티티
│   ├── infrastructure/       # 데이터 접근 계층
│   ├── presentation/         # 컨트롤러
│   └── vo/                   # 뷰 객체
└── PaymentserviceApplication.java
```

## 🛠️ 개발 환경 설정

### Prerequisites
- Java 17
- Gradle 8.0+
- Docker
- MySQL 8.0

### 로컬 개발 환경 설정

1. **저장소 클론**
```bash
git clone <repository-url>
cd 0078-paymentService
```

2. **환경 변수 설정**
```bash
cp .env.example .env
# .env 파일에 필요한 환경 변수 설정
```

3. **애플리케이션 실행**
```bash
./gradlew bootRun
```

### Docker 실행
```bash
docker-compose up -d
```

## 🔧 설정

### 환경 변수

| 변수명 | 설명 | 필수 |
|--------|------|------|
| `SPRING_DATASOURCE_USERNAME` | 데이터베이스 사용자명 | ✅ |
| `SPRING_DATASOURCE_PASSWORD` | 데이터베이스 비밀번호 | ✅ |
| `EC2_HOST` | Eureka 서버 호스트 | ✅ |
| `EC2_DB` | 데이터베이스 호스트 | ✅ |
| `REDIS_PASSWORD` | Redis 비밀번호 | ✅ |
| `TOSS_SECRET_KEY` | Toss Payments Secret Key | ✅ |
| `TOSS_CLIENT_KEY` | Toss Payments Client Key | ✅ |
| `TOSS_WIDGET_SECRET_KEY` | Toss Payments Widget Secret Key | ✅ |

### 프로파일

- `dev`: 개발 환경
- `prod`: 프로덕션 환경

## 📚 API 문서

### Swagger UI
- URL: `http://localhost:8200/swagger-ui/index.html`
- API 문서 자동 생성

### 주요 API 엔드포인트

#### 결제 API
```
POST /api/v1/payment/create     # 결제 생성
POST /api/v1/payment/confirm    # 결제 확인
```

#### 머니 API
```
GET  /api/v1/money              # 머니 잔액 조회
GET  /api/v1/money/history      # 머니 히스토리 조회
POST /api/v1/money              # 머니 생성
POST /api/v1/money/withdraw     # 머니 출금
```

#### BrandPay API
```
POST /api/v1/brandpay/register  # 카드 등록
POST /api/v1/brandpay/callback  # 카드 등록 콜백
```

## 🚀 배포

### GitHub Actions를 통한 자동 배포

1. **브랜치 푸시**
```bash
git push origin develop
```

2. **자동 배포 프로세스**
- GitHub Actions 워크플로우 실행
- Docker 이미지 빌드 및 푸시
- EC2 서버에 자동 배포

### 수동 배포
```bash
# Docker 이미지 빌드
docker build -t payment-service .

# 컨테이너 실행
docker run -d -p 8200:8200 --env-file .env payment-service
```

## 🧪 테스트

### 단위 테스트 실행
```bash
./gradlew test
```

### 통합 테스트 실행
```bash
./gradlew integrationTest
```

## 📊 모니터링

### 헬스 체크
- URL: `http://localhost:8200/actuator/health`

### 메트릭
- URL: `http://localhost:8200/actuator/metrics`

## 🔒 보안

### 인증
- JWT 토큰 기반 인증
- 별도 인증 서비스와 통신

### 시크릿 관리
- 환경 변수를 통한 시크릿 관리
- GitHub Secrets 활용

### 입력 검증
- Bean Validation을 통한 입력 검증
- XSS 방지

## 🐛 문제 해결

### 일반적인 문제

1. **데이터베이스 연결 실패**
   - 환경 변수 확인
   - 데이터베이스 서버 상태 확인

2. **Eureka 서버 연결 실패**
   - 네트워크 연결 확인
   - Eureka 서버 상태 확인

3. **Toss API 호출 실패**
   - API 키 설정 확인
   - 네트워크 연결 확인

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 📞 문의

- 프로젝트 관리자: [관리자 이메일]
- 기술 문의: [개발팀 이메일]

---

**Version**: 1.0.0  
**Last Updated**: 2024년 12월 