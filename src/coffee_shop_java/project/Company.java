/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Action {
    PreparedStatement stmt = null;
    ResultSet rs; 
    private String name;
    private String phone;
    private String email;
    private String address;
    private int userId;
    
    @Override
    public void insert() {
        String sql = "INSERT INTO companies(name, phone, email, address, user_id)"
                + "VALUES(?, ?, ?, ?, ?)";
        
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setInt(5, userId);
            int i = stmt.executeUpdate();
            if(i > 0){
                JOptionPane.showMessageDialog(null, "Data Save");
            } else{
                JOptionPane.showMessageDialog(null, "Failed to Save");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
            
        }
        
    }

    @Override
    public void update(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
