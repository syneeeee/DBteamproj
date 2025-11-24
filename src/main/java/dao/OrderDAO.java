/*package dao;

import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAO {

    // cartId 에 담긴 티켓들로 주문 생성, 생성된 order_id 반환
    public Long createOrder(Long userId, Long cartId) {

        // 1. 장바구니에 담긴 티켓들의 총 금액 계산
        String sumSql =
                "SELECT COALESCE(SUM(ti.price), 0) AS total_amount " +
                        "FROM cart_item ci " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "WHERE ci.cart_id = ?";

        int totalAmount = 0;
        int totalDiscount = 0;              // 지금은 할인 없음 가정
        int finalAmount   = 0;

        try (Connection conn = DBConnection.getConnection()) {

            // 총 금액 구하기
            try (PreparedStatement pstmt = conn.prepareStatement(sumSql)) {
                pstmt.setLong(1, cartId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalAmount = rs.getInt("total_amount");
                }
            }

            if (totalAmount <= 0) {
                System.out.println("❌ 장바구니에 담긴 티켓이 없습니다.");
                return null;
            }

            finalAmount = totalAmount - totalDiscount;

            // 2. 주문 번호 생성 (간단 버전)
            String orderNumber = "ORD-" + System.currentTimeMillis();

            // 3. orders 테이블에 주문 INSERT
            String sql =
                    "INSERT INTO orders " +
                            " (order_number, user_id, order_status, " +
                            "  total_amount, total_discount_amount, final_amount, created_at) " +
                            "VALUES (?, ?, 'CREATED', ?, ?, ?, NOW()) " +
                            "RETURNING order_id";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, orderNumber);
                pstmt.setLong(2, userId);
                pstmt.setInt(3, totalAmount);
                pstmt.setInt(4, totalDiscount);
                pstmt.setInt(5, finalAmount);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong("order_id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 결제 완료 후 상태 변경
    public void markOrderPaid(Long orderId) {
        String sql =
                "UPDATE orders " +
                        "SET order_status = 'PAID', paid_at = NOW() " +
                        "WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, orderId);
            pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }
}*/

package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAO {

    // cartId 에 담긴 티켓들로 주문 생성, 생성된 order_id 반환
    public Long createOrder(Long userId, Long cartId) {

        String sumSql =
                "SELECT COALESCE(SUM(ti.price), 0) AS total_amount " +
                        "FROM cart_item ci " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "WHERE ci.cart_id = ?";

        int totalAmount = 0;
        int totalDiscount = 0;
        int finalAmount;

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement pstmt = conn.prepareStatement(sumSql)) {
                pstmt.setLong(1, cartId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalAmount = rs.getInt("total_amount");
                }
            }

            if (totalAmount <= 0) {
                System.out.println("❌ 장바구니에 담긴 티켓이 없습니다.");
                return null;
            }

            finalAmount = totalAmount - totalDiscount;

            String orderNumber = "ORD-" + System.currentTimeMillis();

            String sql =
                    "INSERT INTO orders " +
                            " (order_number, user_id, order_status, " +
                            "  total_amount, total_discount_amount, final_amount, created_at) " +
                            "VALUES (?, ?, 'CREATED', ?, ?, ?, NOW()) " +
                            "RETURNING order_id";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, orderNumber);
                pstmt.setLong(2, userId);
                pstmt.setInt(3, totalAmount);
                pstmt.setInt(4, totalDiscount);
                pstmt.setInt(5, finalAmount);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    return rs.getLong("order_id");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 결제 완료 후 상태 변경
    public void markOrderPaid(Long orderId) {
        String sql =
                "UPDATE orders " +
                        "SET order_status = 'PAID', paid_at = NOW() " +
                        "WHERE order_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, orderId);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    // 결제 UI 출력용 - 최종 결제 금액 조회
    // 결제 금액 UI 출력용 – 장바구니 금액 조회
    public int getFinalAmount(Long cartId) {
        String sql =
                "SELECT COALESCE(SUM(ti.price), 0) AS final_amount " +
                        "FROM cart_item ci " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "WHERE ci.cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("final_amount");

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

}

