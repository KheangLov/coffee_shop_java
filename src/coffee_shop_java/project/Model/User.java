/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Model;

import coffee_shop_java.project.Action.Action;
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
    private int tblId;
    private int id;
    private String firstname;
    private String lastname;
    private String fullname;
    private String email;
    private String password;
    private String gender;
    private String status;
    private int user_type;
    private int role_id;
    private String role_name;
    private String created_date;
    private String updated_date;

    public User(
        int tblId, 
        String fullname, 
        String email, 
        String gender, 
        String status, 
        String role_name, 
        int id
    )
    {
        this.tblId = tblId;
        this.fullname = fullname;
        this.email = email;
        this.gender = gender;
        this.status = status;
        this.role_name = role_name;
        this.id = id;
    }

    public User(int i, int aInt, String string, String string0, String string1, String string2, int aInt0, int aInt1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO users("
                + "firstname, "
                + "lastname, "
                + "fullname, "
                + "email, "
                + "password, "
                + "gender, "
                + "status, "
                + "role_id, "
                + "user_type, "
                + "login_count, "
                + "created_date, "
                + "updated_date"
                + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, fullname);
            stmt.setString(4, email);
            stmt.setString(5, password);
            stmt.setString(6, gender);
            stmt.setString(7, status);
            stmt.setInt(8, role_id);
            stmt.setInt(9, user_type);
            stmt.setInt(10, 0);
            stmt.setString(11, created_date);
            stmt.setString(12, updated_date);
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
        String sql = "UPDATE `users` SET "
                + "`firstname` = ?, "
                + "`lastname` = ?, "
                + "`fullname` = ?, "
                + "`email` = ?, "
                + "`gender` = ?, "
                + "`status` = ?, "
                + "`role_id` = ?, "
                + "`updated_date` = ?"
                + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, fullname);
            stmt.setString(4, email);
            stmt.setString(5, gender);
            stmt.setString(6, status);
            stmt.setInt(7, role_id);
            stmt.setString(8, updated_date);
            stmt.setInt(9, id);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Data updated!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to update!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM `users` WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, id);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Data deleted!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to delete!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void updatePassword(int id) {
        String sql = "UPDATE `users` SET "
            + "`password` = ? "
            + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setInt(2, id);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Password updated!");
            } else {
                JOptionPane.showMessageDialog(null, "Password failed to update!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
