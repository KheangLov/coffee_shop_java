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
public class Import extends Action {
    PreparedStatement stmt;
    private int id;
    private String createdDate;
    private String updatedDate;
    private int userId;
    private int supplierId;
    private boolean inserted;
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `imports`("
            + "`user_id`, "
            + "`supplier_id`, "
            + "`created_date`, "
            + "`updated_date`"
            + ") VALUES(?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);            
            stmt.setInt(1, userId);
            stmt.setInt(2, supplierId);
            stmt.setString(3, createdDate);
            stmt.setString(4, updatedDate);
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.id = AppHelper.getLastRecordId("imports");
                this.inserted = true;
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to saved!");
                this.inserted = false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Import.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM imports "
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
