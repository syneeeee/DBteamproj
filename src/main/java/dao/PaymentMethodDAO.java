package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO {

    // 1. 사용자 결제수단 전체 조회
    public List<String> getPaymentMethodsByUserId(Long userId) {
        List<String> list = new ArrayList<>();
        String sql =
                "SELECT payment_method_id, method_type, provider, is_default " +
                        "FROM payment_method WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String row =
                        "[ID: " + rs.getLong("payment_method_id") + "] " +
                                rs.getString("method_type") + " / " +
                                rs.getString("provider") +
                                (rs.getBoolean("is_default") ? " (기본)" : "");
                list.add(row);
            }

        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // 2. 입력한 payment_method_id가 해당 user_id의 것인지 검증
    public boolean isValidMethod(Long userId, Long paymentMethodId) {
        String sql =
                "SELECT 1 FROM payment_method WHERE user_id = ? AND payment_method_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            pstmt.setLong(2, paymentMethodId);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // 존재한다면 true

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    // 3. 사용자 기본 결제수단 조회
    public Long getDefaultMethod(Long userId) {
        String sql =
                "SELECT payment_method_id FROM payment_method " +
                        "WHERE user_id = ? AND is_default = true";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("payment_method_id");

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public String getMethodType(Long paymentMethodId) {
        String sql = "SELECT method_type FROM payment_method WHERE payment_method_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, paymentMethodId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getString("method_type");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

}
