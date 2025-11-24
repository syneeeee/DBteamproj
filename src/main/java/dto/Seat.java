package dto;

public class Seat {
    private Long seatId;
    private Long areaId;
    private String seatRow;
    private String seatNumber;
    private String seatType;

    public Seat(Long seatId, Long areaId, String seatRow,
                String seatNumber, String seatType) {
        this.seatId = seatId;
        this.areaId = areaId;
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public Long getSeatId() { return seatId; }
    public Long getAreaId() { return areaId; }
    public String getSeatRow() { return seatRow; }
    public String getSeatNumber() { return seatNumber; }
    public String getSeatType() { return seatType; }
}

