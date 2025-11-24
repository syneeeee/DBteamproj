/*/**
 * OrderItemDAO
 * - 구현된 CRUD 기능:
 *      C: createOrderItems() — cart에 담긴 티켓들을 order_item 테이블로 일괄 생성
 */

package dao;

import db.DBConnection;
import java.sql.*;

public class OrderItemDAO {

    /** cart_id의 모든 ticket_inventory 데이터를 order_item 테이블로 복사하여 주문 항목 생성 */
    public int createOrderItems(Long orderId, Long cartId) {

        String sql =
                "INSERT INTO order_item " +
                        " (order_id, ticket_inventory_id, schedule_id, seat_id, " +
                        "   price, discount_amount, final_price, order_item_status, created_at, updated_at) " +
                        "SELECT ?, ci.ticket_inventory_id, ti.schedule_id, ti.seat_id, " +
                        "       ti.price, 0, ti.price, 'CONFIRMED', NOW(), NOW() " +
                        "FROM cart_item ci " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "WHERE ci.cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, orderId);
            pstmt.setLong(2, cartId);

            int rows = pstmt.executeUpdate();

            // 디버깅 및 안전장치 (0건일 경우 경고 메시지)
            if (rows == 0) {
                System.out.println("⚠ order_item 생성 실패: cart_id=" + cartId + ", order_id=" + orderId);
            }

            return rows;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}






