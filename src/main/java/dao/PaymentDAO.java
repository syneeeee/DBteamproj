/*package dao;

import db.DBConnection;

import java.sql.*;

public class PaymentDAO {

    public Long createPayment(Long orderId, Long paymentMethodId, String methodType, int payAmount) {
        String sql =
                "INSERT INTO payment " +
                        "(order_id, payment_method_id, method_type, pay_amount, payment_status, created_at, updated_at) " +
                        "VALUES (?, ?, ?, ?, 'SUCCESS', NOW(), NOW()) " +
                        "RETURNING payment_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, orderId);

            if (paymentMethodId != null) pstmt.setLong(2, paymentMethodId);
            else pstmt.setNull(2, java.sql.Types.BIGINT);

            pstmt.setString(3, methodType);
            pstmt.setInt(4, payAmount);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("payment_id");

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
}*/

package dao;

import db.DBConnection;
import java.sql.*;

public class PaymentDAO {

    /**
     * 결제 생성
     * payment_method_id (nullable)
     * methodType = "CARD", "KAKAO_PAY" 등
     */
    public Long createPayment(Long orderId, Long paymentMethodId, String methodType, int payAmount) {

        String sql =
                "INSERT INTO payment (" +
                        " order_id, payment_method_id, method_type, pay_amount, payment_status, created_at" +
                        ") VALUES (?, ?, ?, ?, 'SUCCESS', NOW()) " +
                        "RETURNING payment_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, orderId);

            if (paymentMethodId != null) pstmt.setLong(2, paymentMethodId);
            else pstmt.setNull(2, Types.BIGINT);

            pstmt.setString(3, methodType);
            pstmt.setInt(4, payAmount);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("payment_id");

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }
}




