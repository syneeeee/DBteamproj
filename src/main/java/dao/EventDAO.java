/**
 * EventDAO
 * - 구현된 CRUD 기능:
 *      R: getEventByTitle() — 이벤트 제목으로 이벤트 상세 조회
 *         getSeatInfoByEventId() — 이벤트 ID로 좌석/티켓 정보 조회
 */

package dao;

import db.DBConnection;
import dto.Event;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class EventDAO {

    /** 이벤트 제목(event_title)으로 이벤트 상세 정보 조회 */
    public Event getEventByTitle(String title) {
        String sql = "SELECT * FROM event WHERE event_title = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Event(
                        rs.getLong("event_id"),
                        rs.getString("event_title"),
                        rs.getString("description"),
                        rs.getString("event_status"),
                        rs.getString("city"),
                        rs.getString("state")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 이벤트 ID로 해당 공연의 좌석/가격/등급/예매상태 목록 조회 */
    public List<String> getSeatInfoByEventId(Long eventId) {
        List<String> list = new ArrayList<>();

        String sql =
                "SELECT CONCAT(area.area_type, '-', seat.seat_row, seat.seat_number) AS seat_pos, " +
                        "       tc.class_name, ti.price, ti.ticket_status " +
                        "FROM schedule s " +
                        "JOIN ticket_inventory ti ON s.schedule_id = ti.schedule_id " +
                        "JOIN seat ON ti.seat_id = seat.seat_id " +
                        "JOIN venue_area area ON seat.area_id = area.area_id " +
                        "JOIN ticket_class tc ON ti.ticket_class_id = tc.ticket_class_id " +
                        "WHERE s.event_id = ? " +
                        "ORDER BY area.area_type, seat.seat_row, seat.seat_number";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String seatInfo = String.format(
                        "%s | %s석 | %d원 | %s",
                        rs.getString("seat_pos"),
                        rs.getString("class_name"),
                        rs.getInt("price"),
                        rs.getString("ticket_status")
                );
                list.add(seatInfo);
            }

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

}
