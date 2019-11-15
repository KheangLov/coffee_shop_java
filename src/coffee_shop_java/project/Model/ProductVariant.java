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
public class ProductVariant extends Action {
    PreparedStatement stmt;
    private int id;
    private int tblId;
    private String size;
    private double price;
    private double sellingPrice;
    private String image = "\\src\\coffee_shop_java\\products\\images.png";
    private String proName;
    private int proId;

    public ProductVariant(
        int tblId, 
        String proName, 
        String size, 
        double sellingPrice,
        int proId
    ) {
        this.tblId = tblId;
        this.proName = proName;
        this.size = size;
        this.sellingPrice = sellingPrice;
        this.proId = proId;        
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO `product_variants`("
            + "`size`, "
            + "`price`, "
            + "`selling_price`, "
            + "`image`, "
            + "`product_id`"
            + ") VALUES(?, ?, ?, ?, ?)";
        try {        
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, size);
            stmt.setDouble(2, price);
            stmt.setDouble(3, sellingPrice);
            stmt.setString(4, image);
            stmt.setInt(5, proId);
            int i = stmt.executeUpdate();
            if(i > 0) {
                this.id = AppHelper.getLastRecordId("product_variants");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to save!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Stock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(int id) {
        String sql = "UPDATE `product_variants` SET "
            + "`size` = ?, "
            + "`price` = ?, "
            + "`selling_price` = ?, "
            + "`image` = ?, "
            + "`product_id` = ? " 
            + "WHERE `id` = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, size);
            stmt.setDouble(2, price);
            stmt.setDouble(3, sellingPrice);
            stmt.setString(4, image);
            stmt.setInt(5, proId);
            stmt.setInt(6, id);
            int i = stmt.executeUpdate();
            if(i > 0) {
                JOptionPane.showMessageDialog(null, "Data updated!");
            } else {
                JOptionPane.showMessageDialog(null, "Data failed to update!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM `product_variants` "
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
