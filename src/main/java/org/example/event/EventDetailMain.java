package org.example.event;

import dao.EventDAO;
import dto.Event;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;


public class EventDetailMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        EventDAO eventDAO = new EventDAO();

        System.out.print("공연 제목을 입력하세요: ");
        String title = sc.nextLine();

        Event event = eventDAO.getEventByTitle(title);

        if (event == null) {
            System.out.println("❌ 해당 제목의 공연을 찾을 수 없습니다.");
        } else {
            System.out.println("\n===== 🎭 공연 상세 정보 =====\n");
            System.out.println("공연명: " + event.getTitle());
            System.out.println("설명: " + event.getDescription());
            System.out.println("상태: " + event.getStatus());
            System.out.println("지역: " + event.getCity() + " " + event.getState());
        }

        // 좌석 정보 추가
        System.out.println("\n===== 💺 좌석 정보 =====\n");
        List<String> seatList = eventDAO.getSeatInfoByEventId(event.getEventId());

        if (seatList.isEmpty()) {
            System.out.println("⚠ 현재 등록된 좌석 정보가 없습니다.");
        } else {
            for (String seat : seatList) {
                System.out.println(seat);
            }
        }

        sc.close();
    }
}
