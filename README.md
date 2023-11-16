# BoriPaw Project

-----------------------------------------

## Social Network Service API Server

-----------------------------------------
## Test Ground
### Test Pyramid 적용
#### Large Test(E2E Test)
- 비율 5%
- Front 와 직접 데이터 요청 및 응답 테스트

#### Medium Test(Integration Test) 
- 비율 15% 
- 시스템과 시스템의 연결 및 동작 테스트
  - ex) application <-> MySQL, application <-> Redis
  - TestContainer 을 사용해 멱등성 보장 
- 테스트가 느림 개선 방안을 찾아야함

#### Small Test(Unit Test)
- 비율 80%
- 메서드 단위 테스트 작성
- Application Code Level 테스트 작성
- 외부 의존성 Fake 객체로 생성해서 테스트
