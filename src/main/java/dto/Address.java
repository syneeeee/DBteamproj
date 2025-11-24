package dto;

public class Address {

    private Long userId;
    private String nickname;
    private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String addressType;
    private boolean isDefault;

    public Address(Long userId, String nickname, String recipientName, String addressLine1,
                   String addressLine2, String city, String state, String postalCode,
                   String country, String addressType, boolean isDefault) {
        this.userId = userId;
        this.nickname = nickname;
        this.recipientName = recipientName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.addressType = addressType;
        this.isDefault = isDefault;
    }

    // 🔽 getter methods
    public Long getUserId() { return userId; }
    public String getNickname() { return nickname; }
    public String getRecipientName() { return recipientName; }
    public String getAddressLine1() { return addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public String getCountry() { return country; }
    public String getAddressType() { return addressType; }
    public boolean getIsDefault() { return isDefault; }
}
