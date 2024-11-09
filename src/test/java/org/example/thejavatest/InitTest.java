package org.example.thejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

//전체 적용 : 메소드 명의 언더스코어를 공백으로 치환
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InitTest {

    @Test
    @DisplayName("스터디 객체 만들기")
    void create_new_study() {
        Init study = new Init();
        assertNotNull(study);
        System.out.println("InitTest.create_new_study");
    }

    @Test
    @Disabled //깨지는 테스트에 활용
    void create_new_study_again() {
        System.out.println("InitTest.create_new_study_again");
    }

    //전체 테스트를 실행하기 전에 한번 실행
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        System.out.println("StudyTest.setUpBeforeClass");
    }

    //전체 테스트를 실행한 후에 한번 실행한다
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        System.out.println("StudyTest.tearDownAfterClass");
    }

    //각각의 테스트를 실행하기 전에 실행
    @BeforeEach
    void setUp() throws Exception {
        System.out.println("StudyTest.setUp");
    }

    //각각의 테스트를 실행한 후에 실행
    @AfterEach
    void tearDown() throws Exception {
        System.out.println("StudyTest.tearDown");
    }

}