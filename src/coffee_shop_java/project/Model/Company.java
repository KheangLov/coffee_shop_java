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
 * @author ASUS
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company extends Action {
    PreparedStatement stmt;
    private int id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private int userId;
    private int tblId;

    public Company(int i, String name, String address, String email, String phone, int id) {
        this.tblId = i;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.id = id;
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO companies("
                + "name, "
                + "email, "
                + "address, "
                + "phone, "
                + "user_id"
                + ") VALUES(?, ?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setInt(5, userId);
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
        String sql = "UPDATE `companies` SET "
            + "`name` = ?, "
            + "`email` = ?, "
            + "`address` = ?, "
            + "`phone` = ? "
            + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, address);
            stmt.setString(4, phone);
            stmt.setInt(5, id);
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
        String sql = "DELETE FROM companies "
            + "WHERE id = ?";
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
    
}
