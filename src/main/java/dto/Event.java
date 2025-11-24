package dto;

public class Event {
    private Long eventId;
    private String title;
    private String description;
    private String status;
    private String city;
    private String state;

    public Event(Long eventId, String title, String description, String status, String city, String state) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.city = city;
        this.state = state;
    }

    public Long getEventId() { return eventId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public String getCity() { return city; }
    public String getState() { return state; }
}
