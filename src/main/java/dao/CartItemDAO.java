package dao;

import db.DBConnection;
import dto.CartItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {

    public List<CartItem> getItemsByCartId(Long cartId) {
        List<CartItem> list = new ArrayList<>();

        String sql = """
            SELECT ci.cart_item_id, ci.cart_id, ci.ticket_inventory_id, ti.price,
                   e.event_title, s.start_datetime,
                   CONCAT(seat.seat_row, seat.seat_number) AS seat_label
            FROM cart_item ci
            JOIN ticket_inventory ti ON ci.ticket_inventory_id = ti.ticket_inventory_id
            JOIN schedule s ON ti.schedule_id = s.schedule_id
            JOIN event e ON s.event_id = e.event_id
            JOIN seat ON ti.seat_id = seat.seat_id
            WHERE ci.cart_id = ?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem(
                        rs.getLong("cart_item_id"),
                        rs.getLong("cart_id"),
                        rs.getLong("ticket_inventory_id"),
                        rs.getInt("price"),
                        rs.getString("event_title"),
                        rs.getString("start_datetime"),
                        rs.getString("seat_label")
                );
                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteByCartId(Long cartId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
