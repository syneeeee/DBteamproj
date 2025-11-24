/**
 * PaymentMethodDAO
 * - 구현된 CRUD 기능:
 *      R: getPaymentMethodsByUserId() — 사용자 결제수단 목록 조회
 *         isValidMethod() — 특정 결제수단이 해당 사용자의 것인지 검증
 *         getDefaultMethod() — 사용자 기본 결제수단 조회
 *         getMethodType() — 결제수단 타입 조회 (CARD, KAKAO_PAY 등)
 */

package dao;

import db.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethodDAO {

    /** userId 기준으로 등록된 결제수단 전체 조회 */
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

    /** payment_method_id가 실제로 해당 userId의 결제수단인지 검증 */
    public boolean isValidMethod(Long userId, Long paymentMethodId) {
        String sql =
                "SELECT 1 FROM payment_method WHERE user_id = ? AND payment_method_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            pstmt.setLong(2, paymentMethodId);
            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // 존재하면 true

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /** userId 기준 기본 결제수단 ID 조회 */
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

    /** payment_method_id 로 결제 방식(method_type) 조회 (예: CARD, KAKAO_PAY 등) */
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

