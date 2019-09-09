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

public class Branch extends Action {
    PreparedStatement stmt = null;
    ResultSet rs;
    private int tblId;
    private String name;
    private String address;
    private String email;
    private String phone;
    private int companyId;
    private String companyName;
    private int id;
            
    public Branch(int tblId, String name, String phone, String email, String address, String companyName, int id) {
        this.tblId = tblId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.companyName = companyName;
        this.id = id;
    }
    
    
        
    @Override
    public void insert() {
        String sql = "INSERT INTO branches(name, phone, email, address, company_id)"
                + "VALUES(?, ?, ?, ?, ?)";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setInt(5, companyId);
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
        
    }
}

