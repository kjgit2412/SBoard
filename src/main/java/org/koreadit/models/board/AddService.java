package org.koreadit.models.board;

import lombok.RequiredArgsConstructor;
import org.koreadit.controllers.board.BoardField;
import org.koreadit.validators.NullBlankCheck;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;

@Service
@RequiredArgsConstructor
public class AddService implements NullBlankCheck {

    private final JdbcTemplate jdbcTemplate;

    // 기본 항목들의 유효성 check .. poster / subject / content
    public void check(BoardField boardField) {
        checkNullBlank(boardField.getPoster(), new ValidException("작성자를 입력하세요"));
        checkNullBlank(boardField.getSubject(), new ValidException("제목를 입력하세요"));
        checkNullBlank(boardField.getContent(), new ValidException("내용을 입력하세요"));
    }

    // 게시글을 추가, 수정하는 Service
    public boolean addBoard(BoardField boardField) {

        // 기본 field chcek
        check(boardField);

        // Id를 읽어서 등록된 번호면 수정, 등록된 번호가 아니면 추가
        long id = boardField.getId();
        System.out.println("addBoard  :" + id);
        int affectedRows = 0;

        if (id > 0) {   // 수정
            String sql = "UPDATE BOARD_DATA " +
                    " SET " +
                    " POSTER = ?, " +
                    " SUBJECT = ?, " +
                    " CONTENT = ?, " +
                    " MODDT = SYSDATE " +
                    " WHERE ID = ?";

            affectedRows = jdbcTemplate.update(sql, boardField.getPoster(),
                    boardField.getSubject(), boardField.getContent(), boardField.getId());

        } else { // 추가
            String sql = "INSERT INTO BOARD_DATA (ID, POSTER, SUBJECT, CONTENT) " +
                    " VALUES (BOARD_DATA_SEQ.nextval, ?, ?, ?)";

            KeyHolder keyHolder = new GeneratedKeyHolder();
            affectedRows = jdbcTemplate.update(con -> {
                PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"ID"});
                pstmt.setString(1, boardField.getPoster());
                pstmt.setString(2, boardField.getSubject());
                pstmt.setString(3, boardField.getContent());

                return pstmt;
            }, keyHolder);

            id = keyHolder.getKey().longValue();
        }

        boardField.setId(id);

        return affectedRows > 0;
    }

}
