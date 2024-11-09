package org.example.thejavatest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class InitTest {

    @Test
    void create() {
        Init study = new Init();
        assertNotNull(study);
        System.out.println("StudyTest.create");
    }

    @Test
    @Disabled //깨지는 테스트에 활용
    void create1() {
        System.out.println("StudyTest.create1");
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