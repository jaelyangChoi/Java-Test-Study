package org.example.thejavatest.study;

import org.example.thejavatest.domain.Member;
import org.example.thejavatest.domain.Study;
import org.example.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

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
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

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
    void createNewStudy() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@Test.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
        verify(memberService, times(1)).notify(study);
//        verifyNoMoreInteractions(memberService);
        verify(memberService, times(1)).notify(member);
        verify(memberService, never()).validate(anyLong());

        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);
    }

    //Mockito BDD 스타일 API (Behavior Driven Development)
    @Test
    void BDD_Style(){
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@Test.com");

        Study study = new Study(10, "테스트");

        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
        then(memberService).should(times(1)).notify(study);
        then(memberService).should(times(1)).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();
    }

}