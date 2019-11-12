/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Helper;

import coffee_shop_java.project.Model.DbConn;
import coffee_shop_java.project.UserActions;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
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
    
    public static ResultSet selectQuery(String sql) {
        PreparedStatement stmt = null;
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }
    
    public static ResultSet selectQuery(String sql, int frtData) {
        PreparedStatement stmt = null;
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setInt(1, frtData);
            return stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }
    
    public static boolean isExist(String tblName, String colName, String name) {
        PreparedStatement st = null;
        ResultSet rs;
        String sql = "SELECT * FROM `" + tblName + "` "
            + "WHERE LOWER(`" + colName + "`) = ?";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setString(1, name.toLowerCase());
            rs = st.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return false;
    }
    
    public static boolean isExist(String tblName, String colName, String name, int id) {
        PreparedStatement st = null;
        ResultSet rs;
        String sql = "SELECT * FROM `" + tblName + "` "
            + "WHERE LOWER(`" + colName + "`) = ? AND `id` != ?";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setString(1, name.toLowerCase());
            st.setInt(2, id);
            rs = st.executeQuery();
            if(rs.next())
                return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return false;
    }
    
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
    
    public static void fieldRequiredMsg() {
        JOptionPane.showMessageDialog(null, "Please input the required fields!");
    }
    
    public static void existMsg() {
        JOptionPane.showMessageDialog(null, "Data already existed!");
    }
    
    public static ArrayList<String> getCombos(String col, String tblName) {
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT `" + col + "` FROM `" + tblName + "` "
            + "ORDER BY `" + col + "`";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            rs = st.executeQuery();
            while(rs.next())
                list.add(rs.getString(col));
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static ArrayList<String> getCombos(String col, String tblName, String whereCol, int id) {
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT `" + col + "` FROM `" + tblName + "` "
            + "WHERE `" + whereCol + "` = ? "
            + "ORDER BY `" + col + "`";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            while(rs.next())
                list.add(rs.getString(col));
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Integer getId(String data, String colName, String tblName, String colWhere) {
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT " + colName + " FROM `" + tblName + "` "
            + "WHERE LOWER(`" + colWhere + "`) = ?";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setString(1, data.toLowerCase());
            rs = st.executeQuery();
            if(rs.next())
                return rs.getInt(colName);
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Integer getLastRecordId(String tblName) {
        PreparedStatement st;
        ResultSet rs;
        String sql = "SELECT `id` FROM `" + tblName + "` "
            + "ORDER BY `id` DESC LIMIT 1";
        try {
            st = DbConn.getConnection().prepareStatement(sql);
            rs = st.executeQuery();
            if(rs.next())
                return rs.getInt("id");
        } catch (SQLException ex) {
            Logger.getLogger(AppHelper.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return 0;
    }
    
    public static boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    
    public static boolean alphOnly(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if(Character.isLetter(c) || Character.isISOControl(c))
            return true;
        return false;
    }
    
    public static boolean numberOnly(java.awt.event.KeyEvent evt) {
        if(evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9' ||  Character.isISOControl(evt.getKeyChar()))
            return true;
        return false;
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
    
    public static void addBackground(JFrame frame,ImageIcon img) {
        frame.setExtendedState(UserActions.MAXIMIZED_BOTH);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        frame.setSize(new Dimension(screenSize.width, screenSize.height));
        frame.setLayout(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JLabel background = new JLabel("", JLabel.LEFT);
        background.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        background.setSize(new Dimension(screenSize.width, screenSize.height));
        background.setBounds(0, 0, screenSize.width, screenSize.height);
        background.setIcon(img);
        frame.add(background);
    }
}
