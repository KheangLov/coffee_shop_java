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
public class Import extends Action {
    PreparedStatement stmt;
    private int id;
    private String date;
    private int userId;
    private int supplierId;    
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `imports`("
            + "`date`, "
            + "`user_id`, "
            + "`supplier_id`"
            + ") VALUES(?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, date);
            stmt.setInt(2, userId);
            stmt.setInt(3, supplierId);
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.id = AppHelper.getLastRecordId("imports");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to saved!");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
