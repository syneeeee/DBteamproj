/**
 * ScheduleDAO
 * - 구현된 CRUD 기능:
 *      R: getScheduleByDateTime() — 날짜·시간 입력으로 해당 공연 스케줄 조회
 */

package dao;

import db.DBConnection;
import dto.Schedule;
import java.sql.*;

public class ScheduleDAO {

    /** 입력된 날짜·시간과 일치하는 공연 스케줄 조회 (초 단위 보정 포함) */
    public Schedule getScheduleByDateTime(String dateTimeInput) {

        // 초가 없으면 자동으로 ":00" 보정
        if (dateTimeInput.length() == 16) {
            dateTimeInput += ":00";
        }

        String sql = "SELECT * FROM schedule " +
                "WHERE start_datetime >= ?::timestamp " +
                "AND start_datetime < (?::timestamp + INTERVAL '1 minute')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dateTimeInput);
            pstmt.setString(2, dateTimeInput);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Schedule s = new Schedule();
                s.setScheduleId(rs.getLong("schedule_id"));
                s.setEventId(rs.getLong("event_id"));
                s.setVenueId(rs.getLong("venue_id"));
                s.setStartDatetime(rs.getTimestamp("start_datetime").toString());
                s.setEndDatetime(rs.getTimestamp("end_datetime").toString());
                s.setScheduleStatus(rs.getString("schedule_status"));
                return s;
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }

}





