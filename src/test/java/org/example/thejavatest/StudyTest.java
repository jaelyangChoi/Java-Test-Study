package org.example.thejavatest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*; //JUnit이 제공하는 기능

//전체 적용 : 메소드 명의 언더스코어를 공백으로 치환
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    /*@Test
    @Tag("fast")*/
    @DisplayName("스터디 객체 만들기 fast")
    @FastTest
    void create_new_study() {
        Study study = new Study(10);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 DRAFT여야 한다"),
                () -> assertTrue(study.getLimit() > 0, () -> "스터디 최대 참석 인원은 0보다 커야 한다.")
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-1));
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());

        assertTimeout(Duration.ofSeconds(1), () -> {
            new Study(0);
            Thread.sleep(100);
        });
    }

    @DisplayName("스터디 객체 만들기 slow")
    @SlowTest
    void create_new_study_again() {
        System.out.println("오래걸리니까 LOCAL에서 돌리지 말고 CI 환경에서 돌리자");
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