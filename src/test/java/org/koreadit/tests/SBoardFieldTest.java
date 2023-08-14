package org.koreadit.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreadit.controllers.board.BoardField;
import org.koreadit.models.board.AddService;
import org.koreadit.models.board.BoardData;
import org.koreadit.models.board.ValidException;
import org.koreadit.models.board.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("게시글 서비스 테스트")
@Transactional  // 초기 2회 test 분만 반영하고 이후는 반영하지 않음
public class SBoardFieldTest {

    @Autowired
    private AddService addService;

    @Autowired
    private ViewService viewService;

    private BoardField boardField;

    @BeforeEach
    void init() {
        boardField = getField();
    }

    private BoardField getField() {   // 테스트용 BoardField
        BoardField boardField = new BoardField();
        boardField.setPoster("테스트용작성자"+System.currentTimeMillis());
        boardField.setSubject("테스트용제목");
        boardField.setContent("테스트용내용");

        return boardField;
    }

    @Test
    @DisplayName("게시글 추가, 수정 시에 성공 여부 테스트 : 성공하면 예외발생하지 않음")
    void addSuccessTest() {
        assertDoesNotThrow(() -> {
            addService.addBoard(boardField);
        });
    }

    // Begin ------------ 필수 항목 null 또는 Blank test
    private void requiredFieldTest(BoardField boardField, String message) {   // 검증 항목과 message를 연결해주는 메서드
        ValidException thrown = assertThrows(ValidException.class, () -> {
            addService.addBoard(boardField);
        });
        assertTrue(thrown.getMessage().contains(message));
    }

    @Test
    @DisplayName("작성자-field 검증 : null 또는 blank면 ValidException 발생")
    void posterFieldTest() {
        boardField = getField();
        boardField.setPoster(null);
        requiredFieldTest(boardField, "작성자");

        boardField.setPoster("     ");
        requiredFieldTest(boardField, "작성자");
    }

    @Test
    @DisplayName("제목-field 검증 : null 또는 blank면 ValidException 발생")
    void subjectFieldTest() {
        boardField = getField();
        boardField.setSubject(null);
        requiredFieldTest(boardField, "제목");

        boardField.setSubject("     ");
        requiredFieldTest(boardField, "제목");
    }

    @Test
    @DisplayName("내용-field 검증 : null 또는 blank면 ValidException 발생")
    void contentFieldTest() {
        boardField = getField();
        boardField.setContent(null);
        requiredFieldTest(boardField, "내용");

        boardField.setContent("     ");
        requiredFieldTest(boardField, "내용");
    }
    // End ------------ 필수 항목 null 또는 Blank test


    //  ------------ 게시글의 저장 전후에 일치하는 지 여부를 검증
    @Test
    @DisplayName("게시글 저장 검증 테스트 : 전후 데이터가 일치하면 예외발생하지 않음")
    void afterEuqalTest() {
        addService.addBoard(boardField);
        BoardData afterBoard = viewService.getBoard(boardField.getId());

        assertAll(
                () -> assertEquals(boardField.getPoster(), afterBoard.getPoster()),
                () -> assertEquals(boardField.getSubject(), afterBoard.getSubject()),
                () -> assertEquals(boardField.getContent(), afterBoard.getContent())
        );
    }

    //  ------------ 저장된 게시글을 조회하는 부분의 성공여부를 검증
    @Test
    @DisplayName("게시글 조회 성공 여부 테스트 : 성공하면 예외발생하지 않음")
    void getBoardDataSuccessTest() {
        assertDoesNotThrow(() -> {
            viewService.getBoard(boardField.getId());
        });
    }
}
