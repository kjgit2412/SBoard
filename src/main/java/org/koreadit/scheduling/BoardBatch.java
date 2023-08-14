package org.koreadit.scheduling;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
@RequiredArgsConstructor
public class BoardBatch {

    @Scheduled(cron="0 0 1 * * *")      // 새벽1시마다 실행하도록 schdule
 //   @Scheduled(cron="초(0-39) 분(0-59) 시(0-23) 일(1-31) 월(1-12) 요일(0-7))

    public void makeStat() {

        String sql = " SELECT to_char(REGDT, 'HH24') HOUR, count (to_char(REGDT, 'HH24')) COUNT "+
                     "FROM BOARD_DATA " +
                     "GROUP BY to_char(REGDT, 'HH24') " +
                     "HAVING to_char(REGDT, 'HH24') >= 0 and to_char(REGDT, 'HH24') <= 24 " +
                     "ORDER BY to_char(REGDT, 'HH24')";

        try ( Connection conn = DriverManager.getConnection("spring.datasource.url","spring.datasource.username", "spring.datasource.password" );
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

             while(rs.next()) {
                String hourStr = rs.getString("HOUR");
                int count = rs.getInt("COUNT");

                System.out.println(hourStr+"  |  "+count);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
