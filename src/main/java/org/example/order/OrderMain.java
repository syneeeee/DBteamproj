package org.example.order;

import dao.*;
import java.util.Scanner;

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

        System.out.println("===== 🛒 주문 생성 및 결제 =====");
        System.out.print("사용자 ID 입력 : ");
        Long userId = sc.nextLong();

        // 1. ACTIVE Cart ID 조회
        Long cartId = cartDAO.getActiveCartId(userId);
        if (cartId == null) {
            System.out.println("❌ 활성화된 장바구니가 없습니다.");
            return;
        }

        // 2. 장바구니 금액 조회 (주문 금액 미리 보여주기)
        int finalAmount = orderDAO.getFinalAmount(cartId);
        if (finalAmount <= 0) {
            System.out.println("❌ 장바구니가 비어 있습니다.");
            return;
        }
        System.out.println("\n💰 결제 예정 금액 : " + finalAmount + "원");

        // 3. 주문 여부 확인
        System.out.print("주문하시겠습니까? (YES/NO) : ");
        String confirm = sc.next().trim().toUpperCase();
        if (!confirm.equals("YES")) {
            System.out.println("❌ 주문이 취소되었습니다.");
            return;
        }

        // 4. 주문 생성
        Long orderId = orderDAO.createOrder(userId, cartId);
        if (orderId == null) {
            System.out.println("❌ 주문 생성 실패");
            return;
        }
        System.out.println("🧾 주문 생성 완료 (order_id = " + orderId + ")");

        // 5. OrderItem 생성
        int items = orderItemDAO.createOrderItems(orderId, cartId);

        // 6. 결제 수단 선택
        System.out.println("\n=== 💳 결제수단 선택 ===");
        System.out.print("결제 수단 입력 (예: CARD 또는 payment_method_id): ");
        String input = sc.next();

        Long paymentMethodId = null;
        String methodType;

        if (input.matches("\\d+")) { // 숫자 → payment_method_id
            paymentMethodId = Long.parseLong(input);
            if (!paymentMethodDAO.isValidMethod(userId, paymentMethodId)) {
                System.out.println("❌ 잘못된 결제수단입니다.");
                return;
            }
            methodType = paymentMethodDAO.getMethodType(paymentMethodId);
        } else { // 문자열 → 직접 입력한 method_type
            methodType = input.toUpperCase();
        }

        // 7. 결제 생성
        Long paymentId = paymentDAO.createPayment(orderId, paymentMethodId, methodType, finalAmount);
        if (paymentId == null) {
            System.out.println("❌ 결제 실패");
            return;
        }
        System.out.println("💳 결제 완료 (payment_id = " + paymentId + ")");

        // 8. 주문 상태 변경
        orderDAO.markOrderPaid(orderId);

        // 9. INVENTORY SOLD 업데이트
        ticketInventoryDAO.updateToSoldByCartId(cartId);
        System.out.println("🔒 좌석 상태: SOLD 적용 완료");

        // 10. CART_ITEM 비우기
        cartDAO.deleteCartItems(cartId);

        // 11. 상태 유지 (ORDERED)
        cartDAO.updateStatusToOrdered(cartId);

        System.out.println("\n🎉 주문 및 결제가 정상적으로 완료되었습니다!");
    }
}


