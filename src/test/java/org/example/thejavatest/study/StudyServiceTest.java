package org.example.thejavatest.study;

import org.assertj.core.api.Assertions;
import org.example.thejavatest.domain.Member;
import org.example.thejavatest.domain.Study;
import org.example.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    void stubbing(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@Test.com");

        //stubbing
        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThat(member).isEqualTo(memberService.findById(1L).get());
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));

        //stubbing : 여러번 호출될 때 각기 다르게 행동하도록 지정
        Mockito.when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new IllegalArgumentException())
                .thenReturn(Optional.empty());

        assertEquals(member, memberService.findById(7L).get());
        assertThrows(IllegalArgumentException.class, () -> memberService.findById(1L));
        assertEquals(Optional.empty(), memberService.findById(1L));
    }

    @Test
    void stubbingPractice(){
        Study study = new Study(10, "테스트");

        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());

    }
    @Test
    void createNewStudy(@Mock MemberService memberService
            , @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@Test.com");

        //stubbing
        Mockito.when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.when(memberService.findById(2L)).thenThrow(new RuntimeException());
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(any());

        assertThat(member).isEqualTo(memberService.findById(1L).get());
        assertThrows(RuntimeException.class, () -> memberService.findById(2L).get());
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));
    }


}