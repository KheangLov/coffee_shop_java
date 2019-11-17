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
public class Order extends Action {
    PreparedStatement stmt;
    private int id;
    private int tblId;
    private int waitingNum;
    private String status;
    private String date;
    private int userId;
    private int memberId;
    private int companyId;    
    private int branchId;
    private int inserted;
    
    public Order(int tblId, int id, int waitingNum, String status) {
        this.tblId = tblId;
        this.id = id;
        this.waitingNum = waitingNum;
        this.status = status;        
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO `orders`("
            + "`date`, "
            + "`status`, "
            + "`user_id`, "
            + "`waiting_number`"
            + ") VALUES(?, ?, ?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setString(2, status);
            stmt.setInt(3, userId);
            stmt.setInt(4, waitingNum);
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.id = AppHelper.getLastRecordId("orders");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to save!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM orders "
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
    
    public void update(int id, String col, String data) {
        String sql = "UPDATE orders SET "
            + col + " = ? "
            + "WHERE id = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, data);
            stmt.setInt(2, id);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Order completed!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to delete!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
}
