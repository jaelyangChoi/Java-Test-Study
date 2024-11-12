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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {
    @Mock
    MemberService memberService;
    @Mock
    StudyRepository studyRepository;

    @Test
    void stubbing(@Mock MemberService memberService) {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@Test.com");

        //stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThat(member).isEqualTo(memberService.findById(1L).get());
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));

        //stubbing : 여러번 호출될 때 각기 다르게 행동하도록 지정
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new IllegalArgumentException())
                .thenReturn(Optional.empty());

        assertEquals(member, memberService.findById(7L).get());
        assertThrows(IllegalArgumentException.class, () -> memberService.findById(1L));
        assertEquals(Optional.empty(), memberService.findById(1L));
    }

    @Test
    void stubbingPractice() {
        Study study = new Study(10, "테스트");
        Member member = new Member();
        member.setId(1L);

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        study = studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());

    }

    @Test
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@Test.com");

        //stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(memberService.findById(2L)).thenThrow(new RuntimeException());
        Mockito.doThrow(new IllegalArgumentException()).when(memberService).validate(any());

        assertThat(member).isEqualTo(memberService.findById(1L).get());
        assertThrows(RuntimeException.class, () -> memberService.findById(2L).get());
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));
    }


}