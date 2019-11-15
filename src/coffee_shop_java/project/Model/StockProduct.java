/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Model;

import coffee_shop_java.project.Action.Action;
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
public class StockProduct extends Action {
    PreparedStatement stmt;
    private int id;
    private int tblId;
    private int stockId;
    private int proVarId;
    private int userId;
    private double usage;
    private String stock;
    private String product;

    public StockProduct(int tblId, String stock, String product, double usage, int id) {
        this.tblId = tblId;
        this.stock = stock;
        this.product = product;
        this.usage = usage;
        this.id = id;
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `stock_products`("
            + "`stock_id`, "
            + "`product_variant_id`, "
            + "`usage`, "
            + "`user_id`"
            + ") VALUES(?, ?, ?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, stockId);
            stmt.setInt(2, proVarId);
            stmt.setDouble(3, usage);
            stmt.setInt(4, userId);
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
        String sql = "UPDATE `stock_products` SET "
            + "`stock_id` = ?, "
            + "`product_variant_id` = ?, "
            + "`usage` = ? "
            + "`user_id` = ? "
            + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, stockId);
            stmt.setInt(2, proVarId);
            stmt.setDouble(3, usage);
            stmt.setInt(4, userId);
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
        String sql = "DELETE FROM `stock_products` "
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
