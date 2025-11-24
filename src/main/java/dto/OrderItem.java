package dto;

public class OrderItem {
    private Long orderItemId;
    private Long orderId;
    private Long ticketInventoryId;
    private Integer price;

    public OrderItem() {}

    public OrderItem(Long orderItemId, Long orderId,
                     Long ticketInventoryId, Integer price) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.ticketInventoryId = ticketInventoryId;
        this.price = price;
    }

    public Long getOrderItemId() { return orderItemId; }
    public Long getOrderId() { return orderId; }
    public Long getTicketInventoryId() { return ticketInventoryId; }
    public Integer getPrice() { return price; }

    public void setOrderItemId(Long orderItemId) { this.orderItemId = orderItemId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setTicketInventoryId(Long ticketInventoryId) { this.ticketInventoryId = ticketInventoryId; }
    public void setPrice(Integer price) { this.price = price; }
}



