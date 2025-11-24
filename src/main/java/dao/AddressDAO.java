/**
 * AddressDAO
 * - 구현된 CRUD 기능:
 *      C: insertAddress() — 신규 주소 등록
 */

package dao;

import db.DBConnection;
import dto.Address;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddressDAO {

    /** 신규 주소 생성 및 저장 */
    public boolean insertAddress(Address address) {
        String sql = "INSERT INTO address " +
                "(user_id, nickname, recipient_name, address_line1, address_line2, city, state, postal_code, country, address_type, is_default, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, address.getUserId());
            pstmt.setString(2, address.getNickname());
            pstmt.setString(3, address.getRecipientName());
            pstmt.setString(4, address.getAddressLine1());
            pstmt.setString(5, address.getAddressLine2());
            pstmt.setString(6, address.getCity());
            pstmt.setString(7, address.getState());
            pstmt.setString(8, address.getPostalCode());
            pstmt.setString(9, address.getCountry());
            pstmt.setString(10, address.getAddressType());
            pstmt.setBoolean(11, address.getIsDefault());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}



