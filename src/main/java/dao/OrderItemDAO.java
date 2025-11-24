/*
package dao;

import db.DBConnection;

import java.sql.*;

public class OrderItemDAO {

    // cart_id 에 있는 모든 ticket_inventory 를 order_item 으로 복사
    public int createOrderItems(Long orderId, Long cartId) {

        String sql =
                "INSERT INTO order_item " +
                        " (order_id, ticket_inventory_id, schedule_id, seat_id, " +
                        "   price, discount_amount, final_price, order_item_status, created_at, updated_at) " +  // ⬅️ updated_at 추가
                        "SELECT ?, ci.ticket_inventory_id, ti.schedule_id, ti.seat_id, " +
                        "       ti.price, 0, ti.price, 'CONFIRMED', NOW(), NOW() " +                          // ⬅️ NOW() 추가
                        "FROM cart_item ci " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "WHERE ci.cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, orderId);
            pstmt.setLong(2, cartId);

            int rows = pstmt.executeUpdate();
            return rows;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
*/

package dao;

import db.DBConnection;
import java.sql.*;

public class OrderItemDAO {

    // cart_id 에 있는 모든 ticket_inventory 를 order_item 으로 복사
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

            // 디버깅 및 안전장치
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





