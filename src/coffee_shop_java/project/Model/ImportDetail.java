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
public class ImportDetail extends Action {
    PreparedStatement stmt;
    private int id;
    private int importId;
    private int stockId;
    private double qty;
    private double price;
    private boolean inserted;
    private String message;

    @Override
    public void insert() {
        String sql = "INSERT INTO `import_details`("
            + "`import_id`, "
            + "`stock_id`, "
            + "`qty`, "
            + "price"
            + ") VALUES(?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, importId);
            stmt.setInt(2, stockId);
            stmt.setDouble(3, qty);
            stmt.setDouble(4, price);
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.message = "Data saved!";
                this.inserted = true;
            } else {
                this.message = "Data failed to saved!";
                this.inserted = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImportDetail.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM import_details "
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
