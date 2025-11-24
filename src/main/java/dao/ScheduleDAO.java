package dao;

import db.DBConnection;
import dto.Schedule;
import java.sql.*;

public class ScheduleDAO {

    public Schedule getScheduleByDateTime(String dateTimeInput) {

        // 초가 없으면 보정
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




