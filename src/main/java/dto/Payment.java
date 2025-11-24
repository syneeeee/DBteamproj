package dto;

import java.sql.Timestamp;

public class Payment {
    private Long paymentId;
    private Long orderId;
    private Integer payAmount;
    private String methodType;
    private String paymentStatus;
    private Timestamp createdAt;

    public Payment() {}

    public Payment(Long paymentId, Long orderId, Integer payAmount,
                   String methodType, String paymentStatus, Timestamp createdAt) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.payAmount = payAmount;
        this.methodType = methodType;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
    }

    public Long getPaymentId() { return paymentId; }
    public Long getOrderId() { return orderId; }
    public Integer getPayAmount() { return payAmount; }
    public String getMethodType() { return methodType; }
    public String getPaymentStatus() { return paymentStatus; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setPayAmount(Integer payAmount) { this.payAmount = payAmount; }
    public void setMethodType(String methodType) { this.methodType = methodType; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}

