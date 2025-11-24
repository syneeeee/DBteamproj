package org.example.user;

import dao.UserDAO;
import dto.User;
import java.util.Scanner;

public class UserRegisterMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        System.out.println("=== 회원가입 ===");
        System.out.print("이메일: ");
        String email = sc.nextLine();
        System.out.print("비밀번호: ");
        String password = sc.nextLine();

        String status = "ACTIVE"; // 기본 상태

        User user = new User(email, password, status);
        boolean result = userDAO.insertUser(user);

        if (result) {
            System.out.println("🎉 회원가입 성공!");

            // user_id 조회해 User 객체에 저장
            Long userId = userDAO.getUserIdByEmail(email);
            user.setUserId(userId);

            System.out.println("📌 user_id = " + user.getUserId());
        } else {
            System.out.println("❌ 회원가입 실패");
        }

        sc.close();
    }
}

