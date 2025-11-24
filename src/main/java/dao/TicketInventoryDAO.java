/**
 * TicketInventoryDAO
 * - 구현된 CRUD 기능:
 *      R: getTicketInventory() — ticket_inventory_id 로 티켓 재고 단건 조회
 *         getTicketInventoryByScheduleAndSeat() — scheduleId + seatId 로 티켓 재고 조회
 *      U: hold() — 티켓 상태를 AVAILABLE → HOLD 로 변경
 *         updateToSoldByCartId() — 특정 cart 에 담긴 티켓들을 SOLD 로 변경
 */

package dao;

import db.DBConnection;
import dto.TicketInventory;
import java.sql.*;

public class TicketInventoryDAO {

    /** ticket_inventory_id 로 해당 티켓 재고 단건 조회 */
    public TicketInventory getTicketInventory(Long ticketInventoryId) {
        String sql = "SELECT * FROM ticket_inventory WHERE ticket_inventory_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ticketInventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                TicketInventory t = new TicketInventory();
                t.setTicketInventoryId(rs.getLong("ticket_inventory_id"));
                t.setScheduleId(rs.getLong("schedule_id"));
                t.setSeatId(rs.getLong("seat_id"));
                t.setTicketClassId(rs.getLong("ticket_class_id"));
                t.setTicketStatus(rs.getString("ticket_status"));
                t.setPrice(rs.getInt("price"));
                t.setUpdatedAt(rs.getTimestamp("updated_at"));
                return t;
            }

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /** scheduleId + seatId 조합으로 티켓 재고 조회 (장바구니 추가 시 사용) */
    public TicketInventory getTicketInventoryByScheduleAndSeat(Long scheduleId, Long seatId) {
        String sql = "SELECT * FROM ticket_inventory WHERE schedule_id = ? AND seat_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, scheduleId);
            pstmt.setLong(2, seatId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                TicketInventory t = new TicketInventory();
                t.setTicketInventoryId(rs.getLong("ticket_inventory_id"));
                t.setScheduleId(rs.getLong("schedule_id"));
                t.setSeatId(rs.getLong("seat_id"));
                t.setTicketClassId(rs.getLong("ticket_class_id"));
                t.setTicketStatus(rs.getString("ticket_status"));
                t.setPrice(rs.getInt("price"));
                t.setUpdatedAt(rs.getTimestamp("updated_at"));
                return t;
            }

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    /** 상태를 AVAILABLE → HOLD 로 변경 (좌석 임시 확보) */
    public boolean hold(Long ticketInventoryId) {
        String sql = "UPDATE ticket_inventory SET ticket_status = 'HOLD', updated_at = NOW() " +
                "WHERE ticket_inventory_id = ? AND ticket_status = 'AVAILABLE'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ticketInventoryId);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    /** 특정 cart 에 담긴 티켓들을 SOLD 로 일괄 변경 (결제 완료 시) */
    public void updateToSoldByCartId(Long cartId) {
        String sql =
                "UPDATE ticket_inventory ti " +
                        "SET ticket_status = 'SOLD', updated_at = NOW() " +
                        "FROM cart_item ci " +
                        "WHERE ci.ticket_inventory_id = ti.ticket_inventory_id " +
                        "  AND ci.cart_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, cartId);
            pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }
}


