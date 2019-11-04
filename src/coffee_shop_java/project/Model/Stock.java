/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Model;

import coffee_shop_java.project.Action.Action;
import coffee_shop_java.project.Helper.AppHelper;
import java.util.Date;
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
    private int alertQty;
    private int alerted;
    private int stockCateId;
    private int companyId;
    private int branchId;
    private String stockCateName;

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
            + "`branch_id`"
            + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.id = AppHelper.getLastRecordId("stocks");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
