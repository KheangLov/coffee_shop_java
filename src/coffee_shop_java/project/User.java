/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author KHEANG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Action {
    PreparedStatement stmt = null;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String password;
    private String gender;
    private String status;
    private int user_type;
    private String created_date;
    private String updated_date;
    
    @Override
    public void insert() {
        String sql = "INSERT INTO users(firstname, lastname, fullname, email, password, gender, status, user_type, login_count, created_date, updated_date)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, fullname);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setString(6, gender);
            stmt.setString(7, status);
            stmt.setInt(8, user_type);
            stmt.setInt(9, 0);
            stmt.setString(10, created_date);
            stmt.setString(11, updated_date);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Data saved!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to save!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
