/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Model;

import coffee_shop_java.project.Action.Action;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
public class RolePermission extends Action {
    PreparedStatement stmt = null;
    private int tblId;
    private String role_name;
    private String permission_name;
    private int id;
    private int role_id;
    private int permission_id;

    public RolePermission(int tblId, String role_name, String permission_name, int id) {
        this.tblId = tblId;
        this.role_name = role_name;
        this.permission_name = permission_name;
        this.id = id;
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO role_permissions(role_id, permission_id)"
            + "VALUES(?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, role_id);
            stmt.setInt(2, permission_id);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Data saved!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to save!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
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
