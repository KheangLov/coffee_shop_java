/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Model;

import coffee_shop_java.project.Action.Action;
import coffee_shop_java.project.Helper.AppHelper;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class Product extends Action {
    PreparedStatement stmt;
    private int id;
    private int tblId;
    private String name;
    private String description;
    private String proCate;
    private int proCateId;
    private int branchId;
    private int comId;
    private int userId;
    
    public Product(int tblId, String name, String proCate, int id) {
        this.tblId = tblId;
        this.name = name;
        this.proCate = proCate;
        this.id = id;
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `products`("
            + "`name`, "
            + "`description`, "
            + "`product_category_id`, "
            + "`company_id`, "
            + "`branch_id`, "
            + "`user_id`"
            + ") VALUES(?, ?, ?, ?, ?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, proCateId);
            stmt.setInt(4, comId);
            stmt.setInt(5, branchId);
            stmt.setInt(6, userId);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Data saved!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to save!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id) {
        String sql = "UPDATE `products` SET "
            + "`name` = ?, "
            + "`description` = ?, "
            + "`product_category_id` = ?, "
            + "`company_id` = ?, "
            + "`branch_id` = ?, "
            + "`user_id` = ? " 
            + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, proCateId);
            stmt.setInt(4, comId);
            stmt.setInt(5, branchId);
            stmt.setInt(6, userId);
            stmt.setInt(7, id);
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
        String sql = "DELETE FROM `products` "
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
