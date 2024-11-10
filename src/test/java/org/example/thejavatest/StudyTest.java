package org.example.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

//전체 적용 : 메소드 명의 언더스코어를 공백으로 치환
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //순서
//@ExtendWith(FindSlowTestExtension.class)
class StudyTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

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
//    @SlowTest
    @Test
    void create_new_study_again() throws InterruptedException {
        Thread.sleep(1005L);
        System.out.println("오래걸리니까 LOCAL에서 돌리지 말고 CI 환경에서 돌리자");
    }


    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "맨 앞은 안되네 {displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo) {
        System.out.println("StudyTest.repeatTest " + repetitionInfo.getCurrentRepetition()
                + "/" + repetitionInfo.getTotalRepetitions());
    }
    //@CsvSource({"java, 10", ""})

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName}, study={0}}")
//    @NullAndEmptySource // Null, empty 값 추가
    @ValueSource(ints = {10, 20, 30})
    void parameterizedTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println("study = " + study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Study convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, () -> "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("스터디 만들기 CSV")
    @ParameterizedTest(name = "{index} {displayName}, study={0}}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTestCsv(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    //제약 조건 : static class 여야 한다.
    static class StudyAggregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(1));
        }
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