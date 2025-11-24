package dto;

import java.sql.Timestamp;

public class TicketInventory {
    private Long ticketInventoryId;
    private Long scheduleId;
    private Long seatId;
    private Long ticketClassId;
    private String ticketStatus;
    private int price;
    private Timestamp updatedAt; // DB에서 timestamp 받아오기 위해 추가

    // ⭐ 기본 생성자 (DAO에서 new 할 수 있도록)
    public TicketInventory() {}

    // ⭐ 전체 속성 초기화 생성자 (기존 생성자 유지)
    public TicketInventory(Long ticketInventoryId, Long scheduleId,
                           Long seatId, Long ticketClassId,
                           String ticketStatus, int price) {
        this.ticketInventoryId = ticketInventoryId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.ticketClassId = ticketClassId;
        this.ticketStatus = ticketStatus;
        this.price = price;
    }

    // ===== GETTERS =====
    public Long getTicketInventoryId() { return ticketInventoryId; }
    public Long getScheduleId() { return scheduleId; }
    public Long getSeatId() { return seatId; }
    public Long getTicketClassId() { return ticketClassId; }
    public String getTicketStatus() { return ticketStatus; }
    public int getPrice() { return price; }
    public Timestamp getUpdatedAt() { return updatedAt; }

    // ===== SETTERS =====
    public void setTicketInventoryId(Long ticketInventoryId) {
        this.ticketInventoryId = ticketInventoryId;
    }
    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }
    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }
    public void setTicketClassId(Long ticketClassId) {
        this.ticketClassId = ticketClassId;
    }
    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}



