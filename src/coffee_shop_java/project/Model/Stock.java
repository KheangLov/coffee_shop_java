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
public class Stock extends Action {
    PreparedStatement stmt;
    private int id;
    private int tblId;
    private String name;
    private String expiredDate;
    private double qty;
    private String measureUnit;
    private double alertQty;
    private int alerted;
    private int stockCateId;
    private int companyId;
    private int branchId;
    private int userId;
    private String stockCateName;
    private boolean inserted;

    public Stock(int tblId, String name, double qty, String measureUnit, String stockCateName, int id) {
        this.tblId = tblId;
        this.name = name;
        this.qty = qty;
        this.measureUnit = measureUnit;
        this.stockCateName = stockCateName;
        this.id = id;
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `stocks`("
            + "`name`, "
            + "`expired_date`, "
            + "`qty`, "
            + "`measure_unit`, "
            + "`alert_qty`, "
            + "`alerted`, "
            + "`stock_category_id`, "
            + "`company_id`, "
            + "`branch_id`, "
            + "`user_id`"
            + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, expiredDate);
            stmt.setDouble(3, qty);
            stmt.setString(4, measureUnit);
            stmt.setDouble(5, alertQty);
            stmt.setInt(6, alerted);
            stmt.setInt(7, stockCateId);
            stmt.setInt(8, companyId);
            stmt.setInt(9, branchId);
            stmt.setInt(10, userId);
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.id = AppHelper.getLastRecordId("stocks");
                this.inserted = true;
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to save!");
                this.inserted = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id) {
        
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM stocks "
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
    
    public void update(int id, String col, double data) {
        String sql = "UPDATE stocks SET "
            + col + " = ? "
            + "WHERE id = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setDouble(1, data);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
