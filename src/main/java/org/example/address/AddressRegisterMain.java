package org.example.address;

import dao.AddressDAO;
import dto.Address;
import java.util.Scanner;

public class AddressRegisterMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddressDAO addressDAO = new AddressDAO();

        System.out.println("=== 주소 등록 ===");

        System.out.print("user_id: ");
        Long userId = Long.parseLong(sc.nextLine());

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
            System.out.println("📍 주소 등록 완료!");
        } else {
            System.out.println("❌ 주소 등록 실패");
        }

        sc.close();
    }
}

