package dto;

public class CartItem {
    private Long cartItemId;
    private Long cartId;
    private Long ticketInventoryId;
    private int price;
    private String eventTitle;
    private String startDatetime;
    private String seatLabel;

    public CartItem(Long cartItemId, Long cartId, Long ticketInventoryId, int price,
                    String eventTitle, String startDatetime, String seatLabel) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.ticketInventoryId = ticketInventoryId;
        this.price = price;
        this.eventTitle = eventTitle;
        this.startDatetime = startDatetime;
        this.seatLabel = seatLabel;
    }

    public Long getCartItemId() { return cartItemId; }
    public Long getCartId() { return cartId; }
    public Long getTicketInventoryId() { return ticketInventoryId; }
    public int getPrice() { return price; }
    public String getEventTitle() { return eventTitle; }
    public String getStartDatetime() { return startDatetime; }
    public String getSeatLabel() { return seatLabel; }
}
