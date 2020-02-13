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
public class UserBranch extends Action {
    PreparedStatement stmt;
    private int id;
    private int tblId;
    private int userId;
    private int branchId;
    private String userName;
    private String branchName;

    public UserBranch(int tblId, String userName, String branchName, int id) {
        this.tblId = tblId;
        this.userName = userName;
        this.branchName = branchName;
        this.id = id;
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `user_branches`("
            + "`user_id`, "
            + "`branch_id`"
            + ") VALUES(?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, branchId);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM `user_branches` WHERE `id` = ?";
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
