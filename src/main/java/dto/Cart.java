package dto;

public class Cart {
    private Long cartItemId;
    private String eventTitle;
    private String scheduleTime;
    private String seatPosition;
    private Integer price;

    public Cart(Long cartItemId, String eventTitle, String scheduleTime, String seatPosition, Integer price) {
        this.cartItemId = cartItemId;
        this.eventTitle = eventTitle;
        this.scheduleTime = scheduleTime;
        this.seatPosition = seatPosition;
        this.price = price;
    }

    public Long getCartItemId() { return cartItemId; }
    public String getEventTitle() { return eventTitle; }
    public String getScheduleTime() { return scheduleTime; }
    public String getSeatPosition() { return seatPosition; }
    public Integer getPrice() { return price; }
}


