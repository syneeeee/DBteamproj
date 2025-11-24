package dto;

public class User {
    private Long userId;
    private String email;
    private String password;   // password_hash에 대응
    private String status;     // user_status

    public User() {}

    public User(String email, String password, String status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

