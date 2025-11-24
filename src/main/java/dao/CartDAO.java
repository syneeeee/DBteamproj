/*package dao;

import db.DBConnection;
import dto.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    *//** 활성화된 cart_id 조회 *//*
public Long getCartIdByUserId(Long userId) {
    String sql = "SELECT cart_id FROM cart WHERE user_id = ? AND cart_status = 'ACTIVE'";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) return rs.getLong("cart_id");

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

*//** 새로운 장바구니 생성 *//*
public Long createCart(Long userId) {
    String sql = "INSERT INTO cart (user_id, cart_status, total_amount, created_at, updated_at, expired_at) " +
            "VALUES (?, 'ACTIVE', 0, NOW(), NOW(), NOW() + INTERVAL '1 DAY') RETURNING cart_id";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) return rs.getLong("cart_id");

    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

*//** cart_item에 좌석 추가 *//*
public boolean addItem(Long cartId, Long ticketInventoryId) {
    String sql = "INSERT INTO cart_item (cart_id, ticket_inventory_id) VALUES (?, ?)";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, cartId);
        pstmt.setLong(2, ticketInventoryId);

        int row = pstmt.executeUpdate();
        if (row == 0) return false;

        updateTotalAmount(cartId);
        return true;

    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

*//** 장바구니 목록 조회 *//*
public List<Cart> getCartItems(Long userId) {
    List<Cart> list = new ArrayList<>();

    String sql =
            "SELECT ci.cart_item_id, e.event_title, s.start_datetime, " +
                    "CONCAT(seat.seat_row, seat.seat_number) AS seat_pos, ti.price " +
                    "FROM cart_item ci " +
                    "JOIN cart c ON ci.cart_id = c.cart_id " +
                    "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                    "JOIN schedule s ON ti.schedule_id = s.schedule_id " +
                    "JOIN event e ON s.event_id = e.event_id " +
                    "JOIN seat ON ti.seat_id = seat.seat_id " +
                    "WHERE c.user_id = ? AND c.cart_status = 'ACTIVE'";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, userId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Cart item = new Cart(
                    rs.getLong("cart_item_id"),
                    rs.getString("event_title"),
                    rs.getString("start_datetime"),
                    rs.getString("seat_pos"),
                    rs.getInt("price")
            );
            list.add(item);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

*//** total_amount 자동 업데이트 *//*
private void updateTotalAmount(Long cartId) {
    String sql =
            "UPDATE cart SET total_amount = (" +
                    "   SELECT COALESCE(SUM(ti.price), 0) " +
                    "   FROM cart_item ci " +
                    "   JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                    "   WHERE ci.cart_id = ?" +
                    ") WHERE cart_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, cartId);
        pstmt.setLong(2, cartId);
        pstmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

*//** 주문 완료 후 장바구니 비활성화 *//*
public void updateCartStatusToPaid(Long cartId) {
    String sql = "UPDATE cart SET cart_status = 'PAID', updated_at = NOW() WHERE cart_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, cartId);
        pstmt.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

// 장바구니 상태를 ORDERED 로 변경
public void updateStatusToOrdered(Long cartId) {
    String sql = "UPDATE cart SET cart_status = 'ORDERED', updated_at = NOW() WHERE cart_id = ?";

    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, cartId);
        pstmt.executeUpdate();

    } catch (Exception e) { e.printStackTrace(); }
}

// 1. 유저의 ACTIVE 카트 ID 조회
public Long getActiveCartId(Long userId) {
    String sql = "SELECT cart_id FROM cart WHERE user_id = ? AND cart_status = 'ACTIVE'";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) return rs.getLong("cart_id");
    } catch (Exception e) { e.printStackTrace(); }
    return null;
}

// 2. 장바구니 내 품목 전체 삭제
public void deleteCartItems(Long cartId) {
    String sql = "DELETE FROM cart_item WHERE cart_id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setLong(1, cartId);
        pstmt.executeUpdate();
    } catch (Exception e) { e.printStackTrace(); }
  }
}*/

package dao;

import db.DBConnection;
import dto.Cart;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    /** 활성화된 cart_id 조회 */
    public Long getCartIdByUserId(Long userId) {
        String sql = "SELECT cart_id FROM cart WHERE user_id = ? AND cart_status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("cart_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 새로운 장바구니 생성 */
    public Long createCart(Long userId) {
        String sql =
                "INSERT INTO cart (user_id, cart_status, total_amount, created_at, updated_at, expired_at) " +
                        "VALUES (?, 'ACTIVE', 0, NOW(), NOW(), NOW() + INTERVAL '1 DAY') RETURNING cart_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("cart_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 장바구니에 좌석 추가 */
    public boolean addItem(Long cartId, Long ticketInventoryId) {
        String sql = "INSERT INTO cart_item (cart_id, ticket_inventory_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.setLong(2, ticketInventoryId);
            int row = pstmt.executeUpdate();
            if (row == 0) return false;

            updateTotalAmount(cartId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /** 장바구니 목록 조회 */
    public List<Cart> getCartItems(Long userId) {
        List<Cart> list = new ArrayList<>();

        String sql =
                "SELECT ci.cart_item_id, e.event_title, s.start_datetime, " +
                        "CONCAT(seat.seat_row, seat.seat_number) AS seat_pos, ti.price " +
                        "FROM cart_item ci " +
                        "JOIN cart c ON ci.cart_id = c.cart_id " +
                        "JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "JOIN schedule s ON ti.schedule_id = s.schedule_id " +
                        "JOIN event e ON s.event_id = e.event_id " +
                        "JOIN seat ON ti.seat_id = seat.seat_id " +
                        "WHERE c.user_id = ? AND c.cart_status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Cart item = new Cart(
                        rs.getLong("cart_item_id"),
                        rs.getString("event_title"),
                        rs.getString("start_datetime"),
                        rs.getString("seat_pos"),
                        rs.getInt("price")
                );
                list.add(item);
            }
        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    /** total_amount 자동 업데이트 */
    private void updateTotalAmount(Long cartId) {
        String sql =
                "UPDATE cart SET total_amount = (" +
                        "   SELECT COALESCE(SUM(ti.price), 0) " +
                        "   FROM cart_item ci " +
                        "   JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "   WHERE ci.cart_id = ?" +
                        ") WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.setLong(2, cartId);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** 주문 완료 후 CART 상태를 ORDERED로 변경 */
    public void updateStatusToOrdered(Long cartId) {
        String sql = "UPDATE cart SET cart_status = 'ORDERED', updated_at = NOW() WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** CART_ITEM 전체 삭제(카트는 유지) */
    public void deleteCartItems(Long cartId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Long getActiveCartId(Long userId) {
        String sql = "SELECT cart_id FROM cart WHERE user_id = ? AND cart_status = 'ACTIVE'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("cart_id");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public int getTotalAmount(Long cartId) {
        String sql =
                "SELECT total_amount FROM cart WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("total_amount");

        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

}







