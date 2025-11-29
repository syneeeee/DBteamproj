package org.example.address;

import dao.AddressDAO;
import dto.Address;
import java.util.Scanner;
import dao.UserDAO;

public class AddressRegisterMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressDAO addressDAO = new AddressDAO();

        UserDAO userDAO = new UserDAO();  // 추가

        System.out.println("=== 주소 등록 ===");

        // user_id 입력 대신 email + password
        System.out.print("이메일: ");
        String email = sc.nextLine();

        System.out.print("비밀번호: ");
        String password = sc.nextLine();

        Long userId = userDAO.getUserIdByEmailAndPassword(email, password);

        if (userId == null) {
            System.out.println("사용자 인증 실패");
            return;
        }

        System.out.print("별칭: ");
        String nickname = sc.nextLine();

        System.out.print("수령인 이름: ");
        String recipientName = sc.nextLine();

        System.out.print("기본주소: ");
        String address1 = sc.nextLine();

        System.out.print("상세주소: ");
        String address2 = sc.nextLine();

        System.out.print("도시: ");
        String city = sc.nextLine();

        System.out.print("우편번호: ");
        String postal = sc.nextLine();

        // country, address_type, is_default 값은 고정
        Address address = new Address(
                userId, nickname, recipientName, address1, address2,
                city, "", postal, "한국", "HOME", true
        );

        boolean result = addressDAO.insertAddress(address);

        if (result) {
            System.out.println("주소 등록 완료!");
        } else {
            System.out.println("주소 등록 실패");
        }

        sc.close();
    }
}



