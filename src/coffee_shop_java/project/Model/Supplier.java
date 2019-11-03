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
 * @author LYOKO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier extends Action {
    PreparedStatement stmt = null;
    private int tblId;
    private String name;
    private String address;
    private String email;
    private String phone;
    private int company_id;
    private int branch_id;
    
    public Supplier(
        int tblId, 
        String name, 
        String address, 
        String email,
        String phone,
        int company_id, 
        int branch_id
    )
    {
        this.tblId = tblId;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.company_id = company_id;
        this.branch_id = branch_id;
    }

    public Supplier(int i, int aInt, String string, String string0, String string1, String string2, int aInt0, int aInt1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public void insert() {
        String sql = "INSERT INTO users("
                + "name, "
                + "address, "
                + "phone, "
                + "email, "
                + "company_id, "
                + "branch_id, "
                + ") VALUES(?, ?, ?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setInt(5, company_id);
            stmt.setInt(6, branch_id);
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
        String sql = "UPDATE `suppliers` SET "
                + "`name` = ?, "
                + "`address` = ?, "
                + "`eamil` = ?, "
                + "`phone` = ?, "
                + "`company_id` = ?, "
                + "`branch_id` = ?, "
                + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setInt(5, company_id);
            stmt.setInt(6, branch_id);
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
        String sql = "DELETE FROM `suppliers` WHERE `id` = ?";
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
