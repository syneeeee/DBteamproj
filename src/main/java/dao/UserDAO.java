package dao;

import db.DBConnection;
import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;


public class UserDAO {

    public boolean insertUser(User user) {
        String sql = "INSERT INTO public.user (email, password_hash, user_status, created_at, updated_at) " +
                "VALUES (?, ?, ?, NOW(), NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getStatus());

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long getUserIdByEmail(String email) {
        String sql = "SELECT user_id FROM public.user WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("user_id");  // user_id 반환
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // 조회 실패 시
    }

}
