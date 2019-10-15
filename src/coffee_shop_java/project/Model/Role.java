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
public class Role extends Action {
    PreparedStatement stmt = null;
    private int tblId;
    private String name;
    private String description;
    private int id;
    
    public Role(int tblId, String name, String description, int id) {
        this.tblId = tblId;
        this.name = name;
        this.description = description;
        this.id = id;
    }
    
    @Override
    public void insert() {
        String sql = "INSERT INTO roles(name, description)"
                + "VALUES(?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
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
