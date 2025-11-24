package dto;

import java.sql.Timestamp;

public class Order {
    private Long orderId;
    private Long userId;
    private String orderStatus;
    private Integer finalAmount;
    private Timestamp createdAt;

    public Order() {}

    public Order(Long orderId, Long userId, String orderStatus,
                 Integer finalAmount, Timestamp createdAt) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.finalAmount = finalAmount;
        this.createdAt = createdAt;
    }

    public Long getOrderId() { return orderId; }
    public Long getUserId() { return userId; }
    public String getOrderStatus() { return orderStatus; }
    public Integer getFinalAmount() { return finalAmount; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }
    public void setFinalAmount(Integer finalAmount) { this.finalAmount = finalAmount; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}



