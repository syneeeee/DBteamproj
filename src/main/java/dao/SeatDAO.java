/**
 * SeatDAO
 * - 구현된 CRUD 기능:
 *      R: getSeatByLabel() — 좌석 라벨(예: A12)로 좌석 정보 조회
 */

package dao;

import db.DBConnection;
import dto.Seat;
import java.sql.*;

public class SeatDAO {

    /** 좌석 라벨 (예: A12) 를 행/번호로 분리하여 해당 좌석 정보 조회 */
    public Seat getSeatByLabel(String seatLabel) {
        String row = seatLabel.substring(0, 1);
        String number = seatLabel.substring(1);

        String sql = "SELECT * FROM seat WHERE seat_row = ? AND seat_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, row);
            pstmt.setString(2, number);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Seat(
                        rs.getLong("seat_id"),
                        rs.getLong("area_id"),
                        rs.getString("seat_row"),
                        rs.getString("seat_number"),
                        rs.getString("seat_type")
                );
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
}



