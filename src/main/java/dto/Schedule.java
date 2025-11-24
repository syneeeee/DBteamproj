package dto;

public class Schedule {

    private Long scheduleId;
    private Long eventId;
    private Long venueId;
    private String startDatetime;   // timestamp → String 변환 저장
    private String endDatetime;
    private String scheduleStatus;

    // ⭐ 기본 생성자 (DAO에서 new Schedule()으로 호출하기 위해 필요)
    public Schedule() {}

    // ⭐ 전체 생성자 (기존 생성자 유지)
    public Schedule(Long scheduleId, Long eventId, Long venueId,
                    String startDatetime, String endDatetime, String scheduleStatus) {
        this.scheduleId = scheduleId;
        this.eventId = eventId;
        this.venueId = venueId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.scheduleStatus = scheduleStatus;
    }

    // ===== GETTERS =====
    public Long getScheduleId() { return scheduleId; }
    public Long getEventId() { return eventId; }
    public Long getVenueId() { return venueId; }
    public String getStartDatetime() { return startDatetime; }
    public String getEndDatetime() { return endDatetime; }
    public String getScheduleStatus() { return scheduleStatus; }

    // ===== SETTERS =====  ← DAO에서 값을 할당할 때 필수
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public void setVenueId(Long venueId) { this.venueId = venueId; }
    public void setStartDatetime(String startDatetime) { this.startDatetime = startDatetime; }
    public void setEndDatetime(String endDatetime) { this.endDatetime = endDatetime; }
    public void setScheduleStatus(String scheduleStatus) { this.scheduleStatus = scheduleStatus; }
}


