/*
package dao;

import db.DBConnection;
import dto.TicketInventory;
import java.sql.*;

public class TicketInventoryDAO {

    // 📌 ticket_inventory_id 로 조회
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

    // ⭐ scheduleId + seatId 로 조회 (CartAddMain이 요구함)
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

    // 📌 HOLD → 상태 업데이트
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

    // 📌 결제 처리 → SOLD
    public void updateToSoldByCart(Long ticketInventoryId) {
        String sql = "UPDATE ticket_inventory SET ticket_status = 'SOLD', updated_at = NOW() " +
                "WHERE ticket_inventory_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ticketInventoryId);
            pstmt.executeUpdate();

        } catch (Exception e) { e.printStackTrace(); }
    }

    public Long getScheduleId(Long ticketInventoryId) {
        String sql = "SELECT schedule_id FROM ticket_inventory WHERE ticket_inventory_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ticketInventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return rs.getLong("schedule_id");

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public Long getSeatId(Long ticketInventoryId) {
        String sql = "SELECT seat_id FROM ticket_inventory WHERE ticket_inventory_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, ticketInventoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) return rs.getLong("seat_id");

        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // 특정 cart 에 담긴 티켓들을 SOLD 로 변경
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
*/

package dao;

import db.DBConnection;
import dto.TicketInventory;

import java.sql.*;

public class TicketInventoryDAO {

    // 📌 ticket_inventory_id 로 조회
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

    // ⭐ scheduleId + seatId 로 조회
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

    // 📌 HOLD → 상태 업데이트
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

    // 📌 결제 처리 → SOLD
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

