package org.koreadit.models.board;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final JdbcTemplate jdbcTemplate;

    // 게시글의 내용을 반환하는 Service ( id 값을 매개변수로 없으면 null,, 내용이 있으면 BoardData를 반환
    public BoardData getBoard(long id) {
        try {
            String sql = "SELECT * FROM BOARD_DATA WHERE ID = ?";
            BoardData boardData = jdbcTemplate.queryForObject(sql, this::mapper, id);

            return boardData;
        } catch (Exception e) {
            return null;
        }
    }

    public BoardData mapper(ResultSet rs, int i) throws SQLException {
        Timestamp modDt = rs.getTimestamp("MODDT");
        return BoardData.builder()
                .id(rs.getLong("ID"))
                .poster(rs.getString("POSTER"))
                .subject(rs.getString("SUBJECT"))
                .content(rs.getString("CONTENT"))
                .regDt(rs.getTimestamp("REGDT").toLocalDateTime())
                .modDt(modDt == null ? null : modDt.toLocalDateTime())
                .build();
    }

    public List<BoardData> getBoardAll() {
        String sql = "SELECT * FROM BOARD_DATA ORDER BY ID";
        List<BoardData> items = jdbcTemplate.query(sql, this::mapper);
        return items;
    }

    public void deleteBoard(long id) {

        try {
            String sql = "DELETE FROM BOARD_DATA WHERE ID = ?";
            BoardData boardData = jdbcTemplate.queryForObject(sql, this::mapper, id);

            return ;
        } catch (Exception e) {
            return ;
        }
    }

}
