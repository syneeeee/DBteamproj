package org.example.order;

import dao.*;
import java.util.Scanner;

import dto.Address;
import java.util.List;


public class OrderMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        CartDAO cartDAO = new CartDAO();
        OrderDAO orderDAO = new OrderDAO();
        OrderItemDAO orderItemDAO = new OrderItemDAO();
        PaymentDAO paymentDAO = new PaymentDAO();
        PaymentMethodDAO paymentMethodDAO = new PaymentMethodDAO();
        TicketInventoryDAO ticketInventoryDAO = new TicketInventoryDAO();

        System.out.println("===== 주문 생성 및 결제 =====");

        // 이메일 + 비밀번호를 입력받아 user_id 조회
        System.out.print("이메일 입력: ");
        String email = sc.nextLine();

        System.out.print("비밀번호 입력: ");
        String password = sc.nextLine();

        Long userId = userDAO.getUserIdByEmailAndPassword(email, password);
        if (userId == null) {
            System.out.println("사용자 인증 실패 (email 또는 password 불일치)");
            return;
        }

        // AddressDAO 불러오기
        AddressDAO addressDAO = new AddressDAO();

        // 0. 사용자에게 주소 선택받기
        List<Address> addresses = addressDAO.getAddressesByUserId(userId);

        if (addresses.isEmpty()) {
            System.out.println("등록된 주소가 없습니다. 먼저 주소를 등록해주세요.");
            return;
        }


        System.out.println("\n등록된 주소 목록:");
        for (int i = 0; i < addresses.size(); i++) {
            Address a = addresses.get(i);
            System.out.println((i + 1) + ". " + a.getAddressLine1() + " " + a.getAddressLine2() + " (" + a.getNickname() + ")");
        }


        System.out.print("주소를 선택하세요 (번호): ");
        int addressChoice = Integer.parseInt(sc.nextLine());
        Long addressId = addresses.get(addressChoice - 1).getAddressId();



        // 1. ACTIVE Cart ID 조회
        Long cartId = cartDAO.getActiveCartId(userId);
        if (cartId == null) {
            System.out.println("활성화된 장바구니가 없습니다.");
            return;
        }

        // 2. 장바구니 금액 조회 (주문 금액 미리 보여주기)
        int totalAmount = cartDAO.getTotalAmount(cartId);
        if (totalAmount <= 0) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }
        System.out.println("\n결제 예정 금액 : " + totalAmount + "원");


        // 3. 주문 여부 확인
        System.out.print("주문하시겠습니까? (YES/NO) : ");
        String confirm = sc.next().trim().toUpperCase();
        if (!confirm.equals("YES")) {
            System.out.println("주문이 취소되었습니다.");
            return;
        }

        // 4. 주문 생성
        Long orderId = orderDAO.createOrder(userId, cartId, addressId);
        if (orderId == null) {
            System.out.println("주문 생성 실패");
            return;
        }
        System.out.println("주문 생성 완료!");

        // 5. OrderItem 생성
        int items = orderItemDAO.createOrderItems(orderId, cartId);


        // 6. 결제 수단 선택
        System.out.println("\n=== 결제수단 선택 ===");
        System.out.print("결제 수단 입력 (예: CARD 또는 payment_method_id): ");
        String input = sc.next();

        Long paymentMethodId = null;
        String methodType;

        if (input.matches("\\d+")) { // 숫자 → payment_method_id
            paymentMethodId = Long.parseLong(input);
            if (!paymentMethodDAO.isValidMethod(userId, paymentMethodId)) {
                System.out.println("잘못된 결제수단입니다.");
                return;
            }
            methodType = paymentMethodDAO.getMethodType(paymentMethodId);
        } else { // 문자열 → 직접 입력한 method_type
            methodType = input.toUpperCase();
        }

        // 7. 결제 생성
        Long paymentId = paymentDAO.createPayment(orderId, paymentMethodId, methodType, totalAmount);
        if (paymentId == null) {
            System.out.println("결제 실패");
            return;
        }
        System.out.println("결제 완료!");

        // 8. 주문 상태 변경
        orderDAO.markOrderPaid(orderId);

        // 9. INVENTORY SOLD 업데이트
        ticketInventoryDAO.updateToSoldByCartId(cartId);
        System.out.println("좌석 상태: SOLD 적용 완료");

        // 10. CART_ITEM 비우기
        cartDAO.deleteCartItems(cartId);

        // 11. 상태 유지 (ORDERED)
        cartDAO.updateStatusToOrdered(cartId);

        System.out.println("\n주문 및 결제가 정상적으로 완료되었습니다!");
    }
}


