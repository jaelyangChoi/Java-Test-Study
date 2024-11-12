package org.example.thejavatest.study;

import org.example.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService(){
        MemberService memberService = Mockito.mock(MemberService.class);
        //인터페이스만 있고 구현체가 없을 때 mocking
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }


}