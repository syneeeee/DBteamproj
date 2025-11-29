/**
 * OrderDAO
 * - 구현된 CRUD 기능:
 *      C: createOrder() — 장바구니에 담긴 티켓들로 주문 생성 (order_id 반환)
 *      R: getFinalAmount() — 결제 금액 계산을 위한 장바구니 총 금액 조회
 *      U: markOrderPaid() — 주문 상태를 PAID로 변경
 */

package dao;

import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAO {

//    /** cartId의 티켓 가격 합을 계산 후 orders 테이블에 주문 생성 (order_id 반환) */
//    public Long createOrder(Long userId, Long cartId) {
//
//        String sumSql =
//                "SELECT COALESCE(SUM(ti.price), 0) AS total_amount " +
//                        "FROM cart_item ci " +
//                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
//                        "WHERE ci.cart_id = ?";
//
//        int totalAmount = 0;
//        int totalDiscount = 0;
//        int finalAmount;
//
//        try (Connection conn = DBConnection.getConnection()) {
//
//            // 총 금액 계산
//            try (PreparedStatement pstmt = conn.prepareStatement(sumSql)) {
//                pstmt.setLong(1, cartId);
//                ResultSet rs = pstmt.executeQuery();
//                if (rs.next()) {
//                    totalAmount = rs.getInt("total_amount");
//                }
//            }
//
//            if (totalAmount <= 0) {
//                System.out.println("❌ 장바구니에 담긴 티켓이 없습니다.");
//                return null;
//            }
//
//            finalAmount = totalAmount - totalDiscount;
//
//            // 주문 번호 생성 (간단 버전)
//            String orderNumber = "ORD-" + System.currentTimeMillis();
//
//            // 주문 INSERT 후 order_id 반환
//            String sql =
//                    "INSERT INTO orders " +
//                            " (order_number, user_id, order_status, " +
//                            "  total_amount, total_discount_amount, final_amount, created_at) " +
//                            "VALUES (?, ?, 'CREATED', ?, ?, ?, NOW()) " +
//                            "RETURNING order_id";
//
//            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//                pstmt.setString(1, orderNumber);
//                pstmt.setLong(2, userId);
//                pstmt.setInt(3, totalAmount);
//                pstmt.setInt(4, totalDiscount);
//                pstmt.setInt(5, finalAmount);
//
//                ResultSet rs = pstmt.executeQuery();
//                if (rs.next()) {
//                    return rs.getLong("order_id");
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
    /** cartId의 티켓 가격 합을 계산 후 orders 테이블에 주문 생성 (order_id 반환) */
    public Long createOrder(Long userId, Long cartId, Long addressId) {

        String sumSql =
                "SELECT COALESCE(SUM(ti.price), 0) AS total_amount " +
                        "FROM cart_item ci " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "WHERE ci.cart_id = ?";

        int totalAmount = 0;
        int totalDiscount = 0;
        int finalAmount;

        try (Connection conn = DBConnection.getConnection()) {

            // 1. 장바구니 총 금액 계산
            try (PreparedStatement pstmt = conn.prepareStatement(sumSql)) {
                pstmt.setLong(1, cartId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    totalAmount = rs.getInt("total_amount");
                }
            }

            // 2. 장바구니가 비어 있으면 실패
            if (totalAmount <= 0) {
                System.out.println("❌ 장바구니에 담긴 티켓이 없습니다.");
                return null;
            }

            finalAmount = totalAmount - totalDiscount;

            // 3. 주문 번호 생성
            String orderNumber = "ORD-" + System.currentTimeMillis();

            // 4. 주문 INSERT (✅ cart_id 제거됨)
            String sql =
                    "INSERT INTO orders " +
                            " (order_number, user_id, address_id, order_status, " +
                            "  total_amount, total_discount_amount, final_amount, created_at) " +
                            "VALUES (?, ?, ?, 'CREATED', ?, ?, ?, NOW()) " +
                            "RETURNING order_id";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, orderNumber);
                pstmt.setLong(2, userId);
                pstmt.setLong(3, addressId);
                pstmt.setInt(4, totalAmount);
                pstmt.setInt(5, totalDiscount);
                pstmt.setInt(6, finalAmount);

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

    /** 주문의 결제 금액 조회 */
    public Integer fetchFinalAmountFromDB(Long orderId) {
        String sql = "SELECT final_amount FROM orders WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("final_amount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /** 주문을 결제 완료 상태로 변경 */
    public boolean markOrderPaid(Long orderId) {
        String sql = "UPDATE orders SET order_status = 'PAID', paid_at = NOW() WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, orderId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}

