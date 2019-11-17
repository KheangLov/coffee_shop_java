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
public class OrderDetail extends Action {
    PreparedStatement stmt;
    private int id;
    private int proVarId;
    private double qty;
    private double price;
    private double discount;
    private int orderId;
    private double total;
    private boolean inserted;

    @Override
    public void insert() {
        String sql = "INSERT INTO `order_details`("
            + "`qty`, "
            + "`price`, "
            + "`discount`, "
            + "`order_id`, "
            + "`total`, "
            + "`product_variant_id`"
            + ") VALUES(?, ?, ?, ?, ?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setDouble(1, qty);
            stmt.setDouble(2, price);
            stmt.setDouble(3, discount);
            stmt.setInt(4, orderId);
            stmt.setDouble(5, total);
            stmt.setInt(6, proVarId);
            int i = stmt.executeUpdate();
            if(i > 0) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
