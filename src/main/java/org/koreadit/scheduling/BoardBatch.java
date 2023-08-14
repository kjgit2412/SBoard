package org.koreait.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BoardBatch {

    //    @Scheduled(fixedRate=3000)
    @Scheduled(cron="0 0 1 * * *")      // 새벽1시마다 실행하도록 schdule
    public void makeStat() {
        // System.out.println("3초마다 실행");
        // schedule code 추가

        String sql = " SELECT to_char(REGDT, 'HH24') HOUR, count (to_char(REGDT, 'HH24')) COUNT "+
                     "FROM BOARD_DATA " +
                     "GROUP BY to_char(REGDT, 'HH24') " +
                     "HAVING to_char(REGDT, 'HH24') >= 0 and to_char(REGDT, 'HH24') <= 24 " +
                     "ORDER BY to_char(REGDT, 'HH24')";

    }

}
