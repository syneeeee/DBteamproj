//package dto;
//
//public class Address {
//
//    private Long userId;
//    private String nickname;
//    private String recipientName;
//    private String addressLine1;
//    private String addressLine2;
//    private String city;
//    private String state;
//    private String postalCode;
//    private String country;
//    private String addressType;
//    private boolean isDefault;
//
//    public Address(Long userId, String nickname, String recipientName, String addressLine1,
//                   String addressLine2, String city, String state, String postalCode,
//                   String country, String addressType, boolean isDefault) {
//        this.userId = userId;
//        this.nickname = nickname;
//        this.recipientName = recipientName;
//        this.addressLine1 = addressLine1;
//        this.addressLine2 = addressLine2;
//        this.city = city;
//        this.state = state;
//        this.postalCode = postalCode;
//        this.country = country;
//        this.addressType = addressType;
//        this.isDefault = isDefault;
//    }
//
//    // 🔽 getter methods
//    public Long getUserId() { return userId; }
//    public String getNickname() { return nickname; }
//    public String getRecipientName() { return recipientName; }
//    public String getAddressLine1() { return addressLine1; }
//    public String getAddressLine2() { return addressLine2; }
//    public String getCity() { return city; }
//    public String getState() { return state; }
//    public String getPostalCode() { return postalCode; }
//    public String getCountry() { return country; }
//    public String getAddressType() { return addressType; }
//    public boolean getIsDefault() { return isDefault; }
//}

package dto;

public class Address {

    private Long userId;
    private Long addressId; // 🔄 addressId가 PK인 경우 추가
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

    // ✅ 기본 생성자
    public Address() {}

    // ✅ 전체 필드 생성자
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

    // ✅ Getter
    public Long getUserId() { return userId; }
    public Long getAddressId() { return addressId; } // addressId도 getter 추가
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

    // ✅ Setter
    public void setUserId(Long userId) { this.userId = userId; }
    public void setAddressId(Long addressId) { this.addressId = addressId; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public void setCity(String city) { this.city = city; }
    public void setState(String state) { this.state = state; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setCountry(String country) { this.country = country; }
    public void setAddressType(String addressType) { this.addressType = addressType; }
    public void setIsDefault(boolean isDefault) { this.isDefault = isDefault; }
}
