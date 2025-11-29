package org.example.cart;

import dao.*;
import db.DBConnection;
import dto.*;

import java.util.Scanner;

public class CartAddMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("=====  장바구니 담기 =====");

        // 이메일/비밀번호 입력 → user_id 조회
        System.out.print("이메일 입력: ");
        String email = sc.nextLine();

        System.out.print("비밀번호 입력: ");
        String password = sc.nextLine();

        UserDAO userDAO = new UserDAO();
        Long userId = userDAO.getUserIdByEmailAndPassword(email, password);
        if (userId == null) {
            System.out.println("사용자 인증 실패 (email 또는 password가 일치하지 않습니다)");
            return;
        }


        CartDAO cartDAO = new CartDAO();

        // ① 이미 존재하는 장바구니가 있다면 재사용
        Long cartId = cartDAO.getCartIdByUserId(userId);
        if (cartId == null) {
            cartId = cartDAO.createCart(userId);
            System.out.println("\n새 장바구니가 생성되었습니다!\n");
        } else {
            System.out.println("\n기존 장바구니를 사용합니다.\n");
        }

        // ② 공연 정보 찾기
        System.out.print("공연 제목 입력: ");
        String eventTitle = sc.nextLine();
        EventDAO eventDAO = new EventDAO();
        Event event = eventDAO.getEventByTitle(eventTitle);
        if (event == null) {
            System.out.println("해당 공연을 찾을 수 없습니다.");
            return;
        }

        // ③ Schedule 찾기
        System.out.print("관람 날짜/시간 입력 (예: 2025-01-10 19:00): ");
        String startTime = sc.nextLine();
        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule = scheduleDAO.getScheduleByDateTime(startTime);

        if (schedule == null) {
            System.out.println("해당 시간의 스케줄이 없습니다.");
            return;
        }

        // ④ Seat 찾기
        System.out.print("좌석 입력 (예: A12): ");
        String seatLabel = sc.nextLine();
        SeatDAO seatDAO = new SeatDAO();
        Seat seat = seatDAO.getSeatByLabel(seatLabel);
        if (seat == null) {
            System.out.println("존재하지 않는 좌석입니다.");
            return;
        }

        // ⑤ TicketInventory 찾기
        TicketInventoryDAO tiDAO = new TicketInventoryDAO();
        TicketInventory ticketInventory = tiDAO.getTicketInventoryByScheduleAndSeat(schedule.getScheduleId(), seat.getSeatId());
        if (ticketInventory == null) {
            System.out.println("해당 좌석/스케줄의 티켓이 없습니다.");
            return;
        }

        // ⑥ 장바구니에 추가
        boolean addResult = cartDAO.addItem(cartId, ticketInventory.getTicketInventoryId());
        if (!addResult) {
            System.out.println("장바구니 담기 실패");
            return;
        }

        // ⑦ 좌석 상태 HOLD 처리
        boolean holdResult = tiDAO.hold(ticketInventory.getTicketInventoryId());
        if (!holdResult) {
            System.out.println("⚠ 장바구니 추가 성공했지만 좌석 HOLD 실패 (중복 HOLD 가능성 있음)");
        }

        // ⑧ 완료 메시지
        System.out.println("\n===== 결과 =====");
        System.out.println("좌석 상태 변경: " + (holdResult ? "HOLD 완료" : "HOLD 실패"));
        System.out.println("\n장바구니 담기가 완료되었습니다!");
    }
}







