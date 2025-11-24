/**
 * CartDAO
 * - 구현된 CRUD 기능:
 *      C: createCart() — 새로운 장바구니 생성
 *         addItem() — 장바구니에 좌석(티켓) 추가
 *
 *      R: getCartIdByUserId() — 활성 카트 ID 조회
 *         getCartItems() — 장바구니 상품 목록 조회
 *         getActiveCartId() — 활성 카트 ID 조회 (중복 용도)
 *         getTotalAmount() — 장바구니 총 금액 조회
 *
 *      U: updateTotalAmount() — 장바구니 총 금액 자동 업데이트
 *         updateStatusToOrdered() — 주문 완료 후 장바구니 상태 변경
 *
 *      D: deleteCartItems() — 장바구니 내 모든 CartItem 삭제
 */

package dao;

import db.DBConnection;
import dto.Cart;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    /** userId 기준으로 활성 cart_id 조회 */
    public Long getCartIdByUserId(Long userId) {
        String sql = "SELECT cart_id FROM cart WHERE user_id = ? AND cart_status = 'ACTIVE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("cart_id");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /** 새로운 장바구니 생성 후 cart_id 반환 */
    public Long createCart(Long userId) {
        String sql =
                "INSERT INTO cart (user_id, cart_status, total_amount, created_at, updated_at, expired_at) " +
                        "VALUES (?, 'ACTIVE', 0, NOW(), NOW(), NOW() + INTERVAL '1 DAY') RETURNING cart_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getLong("cart_id");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /** 장바구니에 좌석(티켓) 추가 */
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
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /** 활성화된 장바구니에 담긴 상품 정보 목록 조회 */
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

    /** 장바구니 내 금액을 cart_item 가격 합으로 업데이트 */
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

    /** 장바구니 상태를 ORDERED 로 변경 */
    public void updateStatusToOrdered(Long cartId) {
        String sql = "UPDATE cart SET cart_status = 'ORDERED', updated_at = NOW() WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** cart_item 테이블에서 해당 장바구니의 모든 상품 삭제 (cart는 유지) */
    public void deleteCartItems(Long cartId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    /** userId 기준 활성 cart_id 조회 (getCartIdByUserId와 동일 기능) */
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

    /** 장바구니 총 금액 조회 */
    public int getTotalAmount(Long cartId) {
        String sql = "SELECT total_amount FROM cart WHERE cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("total_amount");
        } catch (Exception e) { e.printStackTrace(); }

        return 0;
    }

}









