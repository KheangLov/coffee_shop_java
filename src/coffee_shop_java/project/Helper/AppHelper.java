/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Helper;

import coffee_shop_java.project.Model.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;

/**
 *
 * @author KHEANG
 */
public class AppHelper {
    public static void setColWidth(JTable table, int col, int width){
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        columnModel.getColumn(col).setWidth(width);
        columnModel.getColumn(col).setPreferredWidth(width);
        columnModel.getColumn(col).setMinWidth(width);
        columnModel.getColumn(col).setMaxWidth(width);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.LEFT);
        columnModel.getColumn(col).setCellRenderer(renderer);
    }
    
    public static String toCapitalize(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }
    
//    public static String getCurrentRoleName(int roleId) {
//        PreparedStatement st;
//        ResultSet rs;
//        String sql = "SELECT * FROM `roles` WHERE `id` = ?";
//        try {
//            st = DbConn.getConnection().prepareStatement(sql);
//            st.setInt(1, roleId);
//            rs = st.executeQuery();
//            if(rs.next())
//                return rs.getString("name");
//        } catch (SQLException ex) {
//            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    
    public static Boolean currentUserCan(int roleId, String permissionName, String permissionAction) {
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT * FROM `role_permissions` "
            + "INNER JOIN `permissions` "
            + "ON `role_permissions`.`permission_id` = `permissions`.`id` "
            + "WHERE `role_permissions`.`role_id` = ? "
            + "AND LOWER(`permissions`.`name`) = ? "
            + "AND LOWER(`permissions`.`action`) = ?";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setInt(1, roleId);
            st.setString(2, permissionName);
            st.setString(3, permissionAction);
            rs = st.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static void permissionMessage() {
        JOptionPane.showMessageDialog(null, "You don't have permission to access this path!");
    }
    
    public static ArrayList<String> getAllRoles() {
        ArrayList<String> roles = new ArrayList<String>();
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT `name` FROM `roles` ORDER BY `name`";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next())
                roles.add(rs.getString("name"));
            return roles;
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Integer getRoleId(String role) {
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT `id` FROM `roles` WHERE LOWER(`name`) = ?";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setString(1, role.toLowerCase());
            rs = st.executeQuery();
            if(rs.next())
                return rs.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    
    public static boolean isMatchLength(String type, int maxMin, int inputLength) {
        if("max".equals(type) && inputLength > maxMin)
            return false;
        if("min".equals(type) && inputLength < maxMin)
            return false;
        return true;
    }
    
    public static void errorMessage(String type, int num) {
        if(type == "email")
            JOptionPane.showMessageDialog(null, "Email is not valid!");
        else if(type == "name")
            JOptionPane.showMessageDialog(null, "Maximum length is " + num + "!");
        else if(type == "password")
            JOptionPane.showMessageDialog(null, "Minimum length is " + num + "!");
    }
}
