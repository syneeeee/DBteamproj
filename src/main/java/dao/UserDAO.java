/**
 * UserDAO
 * - 구현된 CRUD 기능:
 *      C: insertUser() — 사용자 계정 생성
 *      R: getUserIdByEmail() — 이메일로 user_id 조회
 */

package dao;

import db.DBConnection;
import dto.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UserDAO {

    /** 사용자 회원가입 — email, password_hash, status를 user 테이블에 저장 */
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

    /** 이메일로 user_id 조회 (로그인/계정 확인에 사용) */
    public Long getUserIdByEmail(String email) {
        String sql = "SELECT user_id FROM public.user WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("user_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

