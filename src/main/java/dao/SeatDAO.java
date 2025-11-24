package dao;

import db.DBConnection;
import dto.Seat;
import java.sql.*;

public class SeatDAO {

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



