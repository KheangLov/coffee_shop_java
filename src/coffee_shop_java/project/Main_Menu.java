/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Helper.AppHelper;
import coffee_shop_java.project.Model.Supplier;
import coffee_shop_java.project.Model.User;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 *
 * @author LYOKO
 */
public class Main_Menu extends javax.swing.JFrame {
    boolean com = false;
    boolean bra = false;
    boolean prod = false;
    boolean stocks = false;
    boolean staffs = false;
    boolean user = false;
    boolean dash = false;
    boolean sup = false;
    
    /**
     * Creates new form Main_Menu
     */
    
    private int userId;
    private int roleId;
    public Main_Menu(int uId, int rId) {
        initComponents();
        userId = uId;
        roleId = rId;
        this.setExtendedState(Main_Menu.MAXIMIZED_BOTH);
    }

    public Main_Menu() {
        initComponents();
    }
    //User
    public void showUsers(ArrayList<User> list) {
        userTbl.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "#", 
                "Name", 
                "Email", 
                "Gender", 
                "Status", 
                "Role", 
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) userTbl.getModel();
        Object[] rows = new Object[7];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getFullname();
            rows[2] = list.get(i).getEmail();
            rows[3] = list.get(i).getGender();
            rows[4] = list.get(i).getStatus();
            rows[5] = list.get(i).getRole_name();
            rows[6] = list.get(i).getId();
            model.addRow(rows);
        }
        AppHelper.setColWidth(userTbl, 6, 0);
        AppHelper.setColWidth(userTbl, 0, 50);
    }
    
    public ArrayList<User> getAllUsers() {
        ArrayList<User> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "users", "read")) {
            String sql = "SELECT `users`.*, `roles`.`name` AS role_name FROM `users` "
                + "INNER JOIN `roles` ON `users`.`role_id` = `roles`.`id`";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                User user;
                int i = 0;
                while(rs.next()){
                    i++;
                    user = new User(
                        i,
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("status"),
                        rs.getString("role_name"),
                        rs.getInt("id")
                    );
                    list.add(user);
                }      
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public ArrayList<User> searchUser(String text) {
        ArrayList<User> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "users", "read")) {
            String sql = "SELECT `users`.*, `roles`.`name` AS role_name FROM `users` "
                + "INNER JOIN `roles` ON `users`.`role_id` = `roles`.`id` "
                + "WHERE LOWER(`users`.`fullname`) LIKE '%" + text + "%'";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                User user;
                int i = 0;
                while(rs.next()){
                    i++;
                    user = new User(
                        i,
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("gender"),
                        rs.getString("status"),
                        rs.getString("role_name"),
                        rs.getInt("id")
                    );
                    list.add(user);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    // Supplier--------------------------------------------------------------------------------
    public void showSups(ArrayList<Sups> list) {
        supplierTbl1.setModel(new DefaultTableModel(
            null, 
            new String[]{
                "id", 
                "name", 
                "address", 
                "phone", 
                "email", 
                "company_id", 
                "branch_id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) supplierTbl1.getModel();
        Object[] rows = new Object[7];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getid();
            rows[1] = list.get(i).getname();
            rows[2] = list.get(i).getaddress();
            rows[3] = list.get(i).getphone();
            rows[4] = list.get(i).getemail();
            rows[5] = list.get(i).getcompany_id();
            rows[6] = list.get(i).getbranch_id();
            model.addRow(rows);
        }
        AppHelper.setColWidth(supplierTbl1, 6, 0);
        AppHelper.setColWidth(supplierTbl1, 0, 50);
    }
    
    public ArrayList<Sups> getAllSups() {
        ArrayList<Sups> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "suppliers", "read")) {
            String sql = "select * from suppliers";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                Sups sups;
                int i = 0;
                while(rs.next()){
                    i++;
                    sups = new Sups(
                        i,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("company_id"),
                        rs.getInt("branch_id")
                    );
                    list.add(sups);
                }      
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
    
    public ArrayList<Sups> searchSups(String text) {
        ArrayList<Sups> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "supplierss", "read")) {
            String sql = "select * from suppliers"
                + "WHERE LOWER(`suppliers` . `name`) LIKE '%" + text + "%'";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                Sups sups;
                int i = 0;
                while(rs.next()){
                    i++;
                    sups = new Sups(
                        i,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getInt("company_id"),
                        rs.getInt("branch_id")
                    );
                    list.add(sups);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        return list;
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        sidebar = new javax.swing.JPanel();
        comLabel = new javax.swing.JPanel();
        company = new javax.swing.JLabel();
        comIcon = new javax.swing.JLabel();
        branchLabel = new javax.swing.JPanel();
        branch = new javax.swing.JLabel();
        branchIcon = new javax.swing.JLabel();
        proLabel = new javax.swing.JPanel();
        pro = new javax.swing.JLabel();
        proIcon = new javax.swing.JLabel();
        staffLabel = new javax.swing.JPanel();
        staff = new javax.swing.JLabel();
        staffIcon = new javax.swing.JLabel();
        stockLabel = new javax.swing.JPanel();
        stockIcon = new javax.swing.JLabel();
        stock = new javax.swing.JLabel();
        comLayer = new javax.swing.JLayeredPane();
        comBlink = new javax.swing.JPanel();
        comActive = new javax.swing.JPanel();
        braLayer = new javax.swing.JLayeredPane();
        braBlink = new javax.swing.JPanel();
        braActive = new javax.swing.JPanel();
        proLayer = new javax.swing.JLayeredPane();
        proActive = new javax.swing.JPanel();
        proBlink = new javax.swing.JPanel();
        staffLayer = new javax.swing.JLayeredPane();
        staffBlink = new javax.swing.JPanel();
        staffActive = new javax.swing.JPanel();
        stockLayer = new javax.swing.JLayeredPane();
        stockBlink = new javax.swing.JPanel();
        stockActive = new javax.swing.JPanel();
        company1 = new javax.swing.JLabel();
        company2 = new javax.swing.JLabel();
        userPnl = new javax.swing.JPanel();
        userLbl = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        userLayp = new javax.swing.JLayeredPane();
        userBlink = new javax.swing.JPanel();
        userActive = new javax.swing.JPanel();
        dashPnl = new javax.swing.JPanel();
        dashLbl = new javax.swing.JLabel();
        dashIcon = new javax.swing.JLabel();
        dashLayp = new javax.swing.JLayeredPane();
        dashBlink = new javax.swing.JPanel();
        dashActive = new javax.swing.JPanel();
        btnLogout = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        supplierPnl = new javax.swing.JPanel();
        stockIcon1 = new javax.swing.JLabel();
        stock1 = new javax.swing.JLabel();
        supplierLayp = new javax.swing.JLayeredPane();
        supplierBlink = new javax.swing.JPanel();
        supplierActive = new javax.swing.JPanel();
        dynamicPanel = new javax.swing.JLayeredPane();
        supplierPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnDelSup1 = new javax.swing.JPanel();
        btnSupIcon4 = new javax.swing.JLabel();
        btnSupLbl4 = new javax.swing.JLabel();
        btnSupRefresh1 = new javax.swing.JPanel();
        btnRefIcon3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        supplierTbl1 = new javax.swing.JTable();
        btnEditSup1 = new javax.swing.JPanel();
        btnSupIcon5 = new javax.swing.JLabel();
        btnSupLbl5 = new javax.swing.JLabel();
        txtSearchSup1 = new javax.swing.JTextField();
        Line = new javax.swing.JPanel();
        btnAddSup1 = new javax.swing.JPanel();
        btnSupIcon6 = new javax.swing.JLabel();
        btnSupLbl6 = new javax.swing.JLabel();
        comPanel = new javax.swing.JPanel();
        branchPanel = new javax.swing.JPanel();
        proPanel = new javax.swing.JPanel();
        staffPanel = new javax.swing.JPanel();
        stockPanel = new javax.swing.JPanel();
        userPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTbl = new javax.swing.JTable();
        btnAddUser = new javax.swing.JPanel();
        btnUserIcon = new javax.swing.JLabel();
        btnUserLbl = new javax.swing.JLabel();
        btnRole = new javax.swing.JPanel();
        btnRoleIcon = new javax.swing.JLabel();
        btnRoleLbl = new javax.swing.JLabel();
        btnPermission = new javax.swing.JPanel();
        btnRoleIcon1 = new javax.swing.JLabel();
        btnRoleLbl1 = new javax.swing.JLabel();
        btnEditUser = new javax.swing.JPanel();
        btnUserIcon1 = new javax.swing.JLabel();
        btnUserLbl1 = new javax.swing.JLabel();
        btnChangePass = new javax.swing.JPanel();
        btnUserIcon2 = new javax.swing.JLabel();
        btnUserLbl2 = new javax.swing.JLabel();
        btnDelUser = new javax.swing.JPanel();
        btnUserIcon3 = new javax.swing.JLabel();
        btnUserLbl3 = new javax.swing.JLabel();
        btnUserRefresh = new javax.swing.JPanel();
        btnRoleIcon2 = new javax.swing.JLabel();
        txtSearchUser = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dashPanel = new javax.swing.JPanel();
        exitIcon = new javax.swing.JLabel();
        dynamicLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(62, 36, 19));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        background.setBackground(new java.awt.Color(102, 51, 0));
        background.setMaximumSize(new java.awt.Dimension(327, 327));
        background.setPreferredSize(new java.awt.Dimension(300, 804));

        sidebar.setBackground(new java.awt.Color(78, 45, 17));
        sidebar.setForeground(new java.awt.Color(255, 255, 255));

        comLabel.setBackground(new java.awt.Color(78, 45, 17));
        comLabel.setForeground(new java.awt.Color(255, 255, 255));
        comLabel.setPreferredSize(new java.awt.Dimension(336, 80));
        comLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                comLabelMouseExited(evt);
            }
        });

        company.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        company.setForeground(new java.awt.Color(255, 255, 255));
        company.setText("COMPANY");

        comIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_organization_50px.png"))); // NOI18N

        javax.swing.GroupLayout comLabelLayout = new javax.swing.GroupLayout(comLabel);
        comLabel.setLayout(comLabelLayout);
        comLabelLayout.setHorizontalGroup(
            comLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(comIcon)
                .addGap(31, 31, 31)
                .addComponent(company, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        comLabelLayout.setVerticalGroup(
            comLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comLabelLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(comLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comIcon)
                    .addComponent(company, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        branchLabel.setBackground(new java.awt.Color(78, 45, 17));
        branchLabel.setForeground(new java.awt.Color(255, 255, 255));
        branchLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        branchLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                branchLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                branchLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                branchLabelMouseExited(evt);
            }
        });

        branch.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        branch.setForeground(new java.awt.Color(255, 255, 255));
        branch.setText("BRANCH");

        branchIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_company_50px_1.png"))); // NOI18N

        javax.swing.GroupLayout branchLabelLayout = new javax.swing.GroupLayout(branchLabel);
        branchLabel.setLayout(branchLabelLayout);
        branchLabelLayout.setHorizontalGroup(
            branchLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(branchLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(branchIcon)
                .addGap(31, 31, 31)
                .addComponent(branch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        branchLabelLayout.setVerticalGroup(
            branchLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, branchLabelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(branchLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(branchIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(branch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        proLabel.setBackground(new java.awt.Color(78, 45, 17));
        proLabel.setForeground(new java.awt.Color(255, 255, 255));
        proLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        proLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                proLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                proLabelMouseExited(evt);
            }
        });

        pro.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        pro.setForeground(new java.awt.Color(255, 255, 255));
        pro.setText("PRODUCT");

        proIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_coffee_50px.png"))); // NOI18N

        javax.swing.GroupLayout proLabelLayout = new javax.swing.GroupLayout(proLabel);
        proLabel.setLayout(proLabelLayout);
        proLabelLayout.setHorizontalGroup(
            proLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(proLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(proIcon)
                .addGap(31, 31, 31)
                .addComponent(pro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        proLabelLayout.setVerticalGroup(
            proLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, proLabelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(proLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(proIcon)
                    .addComponent(pro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        staffLabel.setBackground(new java.awt.Color(78, 45, 17));
        staffLabel.setForeground(new java.awt.Color(255, 255, 255));
        staffLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        staffLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                staffLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                staffLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                staffLabelMouseExited(evt);
            }
        });

        staff.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        staff.setForeground(new java.awt.Color(255, 255, 255));
        staff.setText("STAFF");

        staffIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_user_filled_50px.png"))); // NOI18N

        javax.swing.GroupLayout staffLabelLayout = new javax.swing.GroupLayout(staffLabel);
        staffLabel.setLayout(staffLabelLayout);
        staffLabelLayout.setHorizontalGroup(
            staffLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(staffLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(staffIcon)
                .addGap(31, 31, 31)
                .addComponent(staff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        staffLabelLayout.setVerticalGroup(
            staffLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, staffLabelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(staffLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffIcon)
                    .addComponent(staff, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        stockLabel.setBackground(new java.awt.Color(78, 45, 17));
        stockLabel.setForeground(new java.awt.Color(255, 255, 255));
        stockLabel.setPreferredSize(new java.awt.Dimension(330, 80));
        stockLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockLabelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                stockLabelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stockLabelMouseExited(evt);
            }
        });

        stockIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_box_50px.png"))); // NOI18N

        stock.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        stock.setForeground(new java.awt.Color(255, 255, 255));
        stock.setText("STOCK");

        javax.swing.GroupLayout stockLabelLayout = new javax.swing.GroupLayout(stockLabel);
        stockLabel.setLayout(stockLabelLayout);
        stockLabelLayout.setHorizontalGroup(
            stockLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLabelLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(stockIcon)
                .addGap(31, 31, 31)
                .addComponent(stock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        stockLabelLayout.setVerticalGroup(
            stockLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLabelLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(stockLabelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stock, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockIcon))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        comLayer.setBackground(new java.awt.Color(255, 255, 255));
        comLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        comLayer.setLayout(new java.awt.CardLayout());

        comBlink.setBackground(new java.awt.Color(78, 45, 17));
        comBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout comBlinkLayout = new javax.swing.GroupLayout(comBlink);
        comBlink.setLayout(comBlinkLayout);
        comBlinkLayout.setHorizontalGroup(
            comBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        comBlinkLayout.setVerticalGroup(
            comBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        comLayer.add(comBlink, "card2");

        comActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout comActiveLayout = new javax.swing.GroupLayout(comActive);
        comActive.setLayout(comActiveLayout);
        comActiveLayout.setHorizontalGroup(
            comActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        comActiveLayout.setVerticalGroup(
            comActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        comLayer.add(comActive, "card3");

        braLayer.setBackground(new java.awt.Color(255, 255, 255));
        braLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        braLayer.setLayout(new java.awt.CardLayout());

        braBlink.setBackground(new java.awt.Color(78, 45, 17));
        braBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout braBlinkLayout = new javax.swing.GroupLayout(braBlink);
        braBlink.setLayout(braBlinkLayout);
        braBlinkLayout.setHorizontalGroup(
            braBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        braBlinkLayout.setVerticalGroup(
            braBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        braLayer.add(braBlink, "card3");

        braActive.setBackground(new java.awt.Color(78, 45, 17));
        braActive.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout braActiveLayout = new javax.swing.GroupLayout(braActive);
        braActive.setLayout(braActiveLayout);
        braActiveLayout.setHorizontalGroup(
            braActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        braActiveLayout.setVerticalGroup(
            braActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        braLayer.add(braActive, "card2");

        proLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        proLayer.setLayout(new java.awt.CardLayout());

        proActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout proActiveLayout = new javax.swing.GroupLayout(proActive);
        proActive.setLayout(proActiveLayout);
        proActiveLayout.setHorizontalGroup(
            proActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        proActiveLayout.setVerticalGroup(
            proActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        proLayer.add(proActive, "card2");

        proBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout proBlinkLayout = new javax.swing.GroupLayout(proBlink);
        proBlink.setLayout(proBlinkLayout);
        proBlinkLayout.setHorizontalGroup(
            proBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        proBlinkLayout.setVerticalGroup(
            proBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        proLayer.add(proBlink, "card3");

        staffLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        staffLayer.setLayout(new java.awt.CardLayout());

        staffBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout staffBlinkLayout = new javax.swing.GroupLayout(staffBlink);
        staffBlink.setLayout(staffBlinkLayout);
        staffBlinkLayout.setHorizontalGroup(
            staffBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        staffBlinkLayout.setVerticalGroup(
            staffBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        staffLayer.add(staffBlink, "card2");

        staffActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout staffActiveLayout = new javax.swing.GroupLayout(staffActive);
        staffActive.setLayout(staffActiveLayout);
        staffActiveLayout.setHorizontalGroup(
            staffActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        staffActiveLayout.setVerticalGroup(
            staffActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        staffLayer.add(staffActive, "card3");

        stockLayer.setPreferredSize(new java.awt.Dimension(17, 80));
        stockLayer.setLayout(new java.awt.CardLayout());

        stockBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout stockBlinkLayout = new javax.swing.GroupLayout(stockBlink);
        stockBlink.setLayout(stockBlinkLayout);
        stockBlinkLayout.setHorizontalGroup(
            stockBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        stockBlinkLayout.setVerticalGroup(
            stockBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        stockLayer.add(stockBlink, "card2");

        stockActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout stockActiveLayout = new javax.swing.GroupLayout(stockActive);
        stockActive.setLayout(stockActiveLayout);
        stockActiveLayout.setHorizontalGroup(
            stockActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        stockActiveLayout.setVerticalGroup(
            stockActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        stockLayer.add(stockActive, "card3");

        company1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        company1.setForeground(new java.awt.Color(255, 255, 255));
        company1.setText("MANAGEMENT SYSTEM");

        company2.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        company2.setForeground(new java.awt.Color(255, 255, 255));
        company2.setText("COFFEE SHOP");

        userPnl.setBackground(new java.awt.Color(78, 45, 17));
        userPnl.setForeground(new java.awt.Color(255, 255, 255));
        userPnl.setPreferredSize(new java.awt.Dimension(336, 80));
        userPnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userPnlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                userPnlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                userPnlMouseExited(evt);
            }
        });

        userLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        userLbl.setForeground(new java.awt.Color(255, 255, 255));
        userLbl.setText("USER");

        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/user_50px.png"))); // NOI18N

        javax.swing.GroupLayout userPnlLayout = new javax.swing.GroupLayout(userPnl);
        userPnl.setLayout(userPnlLayout);
        userPnlLayout.setHorizontalGroup(
            userPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(userIcon)
                .addGap(31, 31, 31)
                .addComponent(userLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        userPnlLayout.setVerticalGroup(
            userPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPnlLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(userPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(userIcon)
                    .addComponent(userLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        userLayp.setPreferredSize(new java.awt.Dimension(17, 80));
        userLayp.setLayout(new java.awt.CardLayout());

        userBlink.setBackground(new java.awt.Color(78, 45, 17));
        userBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout userBlinkLayout = new javax.swing.GroupLayout(userBlink);
        userBlink.setLayout(userBlinkLayout);
        userBlinkLayout.setHorizontalGroup(
            userBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        userBlinkLayout.setVerticalGroup(
            userBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        userLayp.add(userBlink, "card2");

        userActive.setBackground(new java.awt.Color(78, 45, 17));
        userActive.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout userActiveLayout = new javax.swing.GroupLayout(userActive);
        userActive.setLayout(userActiveLayout);
        userActiveLayout.setHorizontalGroup(
            userActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        userActiveLayout.setVerticalGroup(
            userActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        userLayp.add(userActive, "card3");

        dashPnl.setBackground(new java.awt.Color(78, 45, 17));
        dashPnl.setForeground(new java.awt.Color(255, 255, 255));
        dashPnl.setPreferredSize(new java.awt.Dimension(336, 80));
        dashPnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashPnlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dashPnlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dashPnlMouseExited(evt);
            }
        });

        dashLbl.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        dashLbl.setForeground(new java.awt.Color(255, 255, 255));
        dashLbl.setText("DASHBOARD");

        dashIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/monitor_50px.png"))); // NOI18N

        javax.swing.GroupLayout dashPnlLayout = new javax.swing.GroupLayout(dashPnl);
        dashPnl.setLayout(dashPnlLayout);
        dashPnlLayout.setHorizontalGroup(
            dashPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(dashIcon)
                .addGap(31, 31, 31)
                .addComponent(dashLbl, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );
        dashPnlLayout.setVerticalGroup(
            dashPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashPnlLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(dashPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashIcon)
                    .addComponent(dashLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        dashLayp.setBackground(new java.awt.Color(78, 45, 17));
        dashLayp.setPreferredSize(new java.awt.Dimension(17, 80));
        dashLayp.setLayout(new java.awt.CardLayout());

        dashBlink.setBackground(new java.awt.Color(78, 45, 17));
        dashBlink.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout dashBlinkLayout = new javax.swing.GroupLayout(dashBlink);
        dashBlink.setLayout(dashBlinkLayout);
        dashBlinkLayout.setHorizontalGroup(
            dashBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        dashBlinkLayout.setVerticalGroup(
            dashBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        dashLayp.add(dashBlink, "card2");

        dashActive.setBackground(new java.awt.Color(78, 45, 17));
        dashActive.setPreferredSize(new java.awt.Dimension(17, 80));

        javax.swing.GroupLayout dashActiveLayout = new javax.swing.GroupLayout(dashActive);
        dashActive.setLayout(dashActiveLayout);
        dashActiveLayout.setHorizontalGroup(
            dashActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        dashActiveLayout.setVerticalGroup(
            dashActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        dashLayp.add(dashActive, "card2");

        btnLogout.setBackground(new java.awt.Color(78, 45, 17));
        btnLogout.setPreferredSize(new java.awt.Dimension(0, 80));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLogoutMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/shutdown_50px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LOGOUT");

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        supplierPnl.setBackground(new java.awt.Color(78, 45, 17));
        supplierPnl.setForeground(new java.awt.Color(255, 255, 255));
        supplierPnl.setPreferredSize(new java.awt.Dimension(330, 80));
        supplierPnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                supplierPnlMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                supplierPnlMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                supplierPnlMouseExited(evt);
            }
        });

        stockIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/supplier_filled_50px.png"))); // NOI18N

        stock1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        stock1.setForeground(new java.awt.Color(255, 255, 255));
        stock1.setText("SUPPLIER");

        javax.swing.GroupLayout supplierPnlLayout = new javax.swing.GroupLayout(supplierPnl);
        supplierPnl.setLayout(supplierPnlLayout);
        supplierPnlLayout.setHorizontalGroup(
            supplierPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(stockIcon1)
                .addGap(31, 31, 31)
                .addComponent(stock1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        supplierPnlLayout.setVerticalGroup(
            supplierPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPnlLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(supplierPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stock1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stockIcon1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        supplierLayp.setPreferredSize(new java.awt.Dimension(17, 80));
        supplierLayp.setLayout(new java.awt.CardLayout());

        supplierBlink.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout supplierBlinkLayout = new javax.swing.GroupLayout(supplierBlink);
        supplierBlink.setLayout(supplierBlinkLayout);
        supplierBlinkLayout.setHorizontalGroup(
            supplierBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        supplierBlinkLayout.setVerticalGroup(
            supplierBlinkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        supplierLayp.add(supplierBlink, "card2");

        supplierActive.setBackground(new java.awt.Color(78, 45, 17));

        javax.swing.GroupLayout supplierActiveLayout = new javax.swing.GroupLayout(supplierActive);
        supplierActive.setLayout(supplierActiveLayout);
        supplierActiveLayout.setHorizontalGroup(
            supplierActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 17, Short.MAX_VALUE)
        );
        supplierActiveLayout.setVerticalGroup(
            supplierActiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        supplierLayp.add(supplierActive, "card3");

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(staffLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(proLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(comLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(braLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stockLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dashLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dashPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(branchLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(comLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(proLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(staffLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(userPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)))
            .addComponent(btnLogout, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(supplierLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(supplierPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE))
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(company2))
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(company1)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(company2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(company1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(sidebarLayout.createSequentialGroup()
                        .addComponent(dashPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(sidebarLayout.createSequentialGroup()
                                .addComponent(userPnl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(braLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(branchLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(staffLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                    .addComponent(staffLayer, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(proLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(proLayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(stockLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(stockLayer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(userLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(dashLayp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(supplierPnl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(supplierLayp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        branchLabel.getAccessibleContext().setAccessibleName("");

        dynamicPanel.setLayout(new java.awt.CardLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Search:");

        btnDelSup1.setBackground(new java.awt.Color(200, 35, 51));
        btnDelSup1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelSup1MouseClicked(evt);
            }
        });

        btnSupIcon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnSupLbl4.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnSupLbl4.setForeground(new java.awt.Color(255, 255, 255));
        btnSupLbl4.setText("DELETE");

        javax.swing.GroupLayout btnDelSup1Layout = new javax.swing.GroupLayout(btnDelSup1);
        btnDelSup1.setLayout(btnDelSup1Layout);
        btnDelSup1Layout.setHorizontalGroup(
            btnDelSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelSup1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSupIcon4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSupLbl4)
                .addGap(25, 25, 25))
        );
        btnDelSup1Layout.setVerticalGroup(
            btnDelSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelSup1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnSupIcon4)
                .addGap(15, 15, 15))
            .addComponent(btnSupLbl4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnSupRefresh1.setBackground(new java.awt.Color(35, 39, 43));
        btnSupRefresh1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSupRefresh1MouseClicked(evt);
            }
        });

        btnRefIcon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/refresh_40px.png"))); // NOI18N

        javax.swing.GroupLayout btnSupRefresh1Layout = new javax.swing.GroupLayout(btnSupRefresh1);
        btnSupRefresh1.setLayout(btnSupRefresh1Layout);
        btnSupRefresh1Layout.setHorizontalGroup(
            btnSupRefresh1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSupRefresh1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRefIcon3)
                .addGap(25, 25, 25))
        );
        btnSupRefresh1Layout.setVerticalGroup(
            btnSupRefresh1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSupRefresh1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnRefIcon3)
                .addGap(15, 15, 15))
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        jScrollPane2.setOpaque(false);

        supplierTbl1.setAutoCreateRowSorter(true);
        supplierTbl1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        supplierTbl1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        supplierTbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Address", "Phone", "Email", "Company", "Branch"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        supplierTbl1.setGridColor(new java.awt.Color(255, 255, 255));
        supplierTbl1.setOpaque(false);
        supplierTbl1.setRowHeight(34);
        supplierTbl1.setSelectionBackground(new java.awt.Color(0, 0, 0));
        supplierTbl1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(supplierTbl1);

        btnEditSup1.setBackground(new java.awt.Color(19, 132, 150));
        btnEditSup1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditSup1MouseClicked(evt);
            }
        });

        btnSupIcon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnSupLbl5.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnSupLbl5.setForeground(new java.awt.Color(255, 255, 255));
        btnSupLbl5.setText("UPDATE");

        javax.swing.GroupLayout btnEditSup1Layout = new javax.swing.GroupLayout(btnEditSup1);
        btnEditSup1.setLayout(btnEditSup1Layout);
        btnEditSup1Layout.setHorizontalGroup(
            btnEditSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditSup1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSupIcon5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSupLbl5)
                .addGap(25, 25, 25))
        );
        btnEditSup1Layout.setVerticalGroup(
            btnEditSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSupLbl5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEditSup1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnSupIcon5)
                .addGap(15, 15, 15))
        );

        txtSearchSup1.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchSup1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchSup1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchSup1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchSup1KeyPressed(evt);
            }
        });

        Line.setBackground(new java.awt.Color(0, 0, 0));
        Line.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineLayout = new javax.swing.GroupLayout(Line);
        Line.setLayout(LineLayout);
        LineLayout.setHorizontalGroup(
            LineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 759, Short.MAX_VALUE)
        );
        LineLayout.setVerticalGroup(
            LineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnAddSup1.setBackground(new java.awt.Color(144, 202, 249));
        btnAddSup1.setPreferredSize(new java.awt.Dimension(205, 74));
        btnAddSup1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddSup1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAddSup1MouseEntered(evt);
            }
        });

        btnSupIcon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/add_user_40px.png"))); // NOI18N

        btnSupLbl6.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnSupLbl6.setForeground(new java.awt.Color(255, 255, 255));
        btnSupLbl6.setText("CREATE");

        javax.swing.GroupLayout btnAddSup1Layout = new javax.swing.GroupLayout(btnAddSup1);
        btnAddSup1.setLayout(btnAddSup1Layout);
        btnAddSup1Layout.setHorizontalGroup(
            btnAddSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddSup1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnSupIcon6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSupLbl6, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        btnAddSup1Layout.setVerticalGroup(
            btnAddSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddSup1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnSupIcon6)
                .addContainerGap(13, Short.MAX_VALUE))
            .addComponent(btnSupLbl6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout supplierPanelLayout = new javax.swing.GroupLayout(supplierPanel);
        supplierPanel.setLayout(supplierPanelLayout);
        supplierPanelLayout.setHorizontalGroup(
            supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(supplierPanelLayout.createSequentialGroup()
                        .addComponent(Line, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(supplierPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(supplierPanelLayout.createSequentialGroup()
                        .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, supplierPanelLayout.createSequentialGroup()
                                .addComponent(btnAddSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnSupRefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtSearchSup1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(25, 25, 25))))
        );
        supplierPanelLayout.setVerticalGroup(
            supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(supplierPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(43, 43, 43)
                .addComponent(Line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(supplierPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddSup1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSupRefresh1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(txtSearchSup1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addGap(136, 136, 136))
        );

        dynamicPanel.add(supplierPanel, "card6");

        comPanel.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout comPanelLayout = new javax.swing.GroupLayout(comPanel);
        comPanel.setLayout(comPanelLayout);
        comPanelLayout.setHorizontalGroup(
            comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );
        comPanelLayout.setVerticalGroup(
            comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );

        dynamicPanel.add(comPanel, "card2");

        branchPanel.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout branchPanelLayout = new javax.swing.GroupLayout(branchPanel);
        branchPanel.setLayout(branchPanelLayout);
        branchPanelLayout.setHorizontalGroup(
            branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );
        branchPanelLayout.setVerticalGroup(
            branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );

        dynamicPanel.add(branchPanel, "card3");

        javax.swing.GroupLayout proPanelLayout = new javax.swing.GroupLayout(proPanel);
        proPanel.setLayout(proPanelLayout);
        proPanelLayout.setHorizontalGroup(
            proPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        proPanelLayout.setVerticalGroup(
            proPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );

        dynamicPanel.add(proPanel, "card4");

        javax.swing.GroupLayout staffPanelLayout = new javax.swing.GroupLayout(staffPanel);
        staffPanel.setLayout(staffPanelLayout);
        staffPanelLayout.setHorizontalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        staffPanelLayout.setVerticalGroup(
            staffPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );

        dynamicPanel.add(staffPanel, "card5");

        javax.swing.GroupLayout stockPanelLayout = new javax.swing.GroupLayout(stockPanel);
        stockPanel.setLayout(stockPanelLayout);
        stockPanelLayout.setHorizontalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        stockPanelLayout.setVerticalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );

        dynamicPanel.add(stockPanel, "card6");

        userPanel.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        jScrollPane1.setOpaque(false);

        userTbl.setAutoCreateRowSorter(true);
        userTbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        userTbl.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        userTbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "#", "Name", "Email", "Gender", "Status", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        userTbl.setGridColor(new java.awt.Color(255, 255, 255));
        userTbl.setOpaque(false);
        userTbl.setRowHeight(34);
        userTbl.setSelectionBackground(new java.awt.Color(0, 0, 0));
        userTbl.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(userTbl);
        userTbl.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        btnAddUser.setBackground(new java.awt.Color(144, 202, 249));
        btnAddUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddUserMouseClicked(evt);
            }
        });

        btnUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/add_user_40px.png"))); // NOI18N

        btnUserLbl.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl.setText("ADD");

        javax.swing.GroupLayout btnAddUserLayout = new javax.swing.GroupLayout(btnAddUser);
        btnAddUser.setLayout(btnAddUserLayout);
        btnAddUserLayout.setHorizontalGroup(
            btnAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl)
                .addGap(25, 25, 25))
        );
        btnAddUserLayout.setVerticalGroup(
            btnAddUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddUserLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnRole.setBackground(new java.awt.Color(113, 113, 113));
        btnRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRoleMouseClicked(evt);
            }
        });

        btnRoleIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/admin_settings_male_40px.png"))); // NOI18N

        btnRoleLbl.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnRoleLbl.setForeground(new java.awt.Color(255, 255, 255));
        btnRoleLbl.setText("ROLE");

        javax.swing.GroupLayout btnRoleLayout = new javax.swing.GroupLayout(btnRole);
        btnRole.setLayout(btnRoleLayout);
        btnRoleLayout.setHorizontalGroup(
            btnRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnRoleLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRoleIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoleLbl)
                .addGap(25, 25, 25))
        );
        btnRoleLayout.setVerticalGroup(
            btnRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRoleLbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnRoleLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRoleIcon)
                .addGap(15, 15, 15))
        );

        btnPermission.setBackground(new java.awt.Color(113, 113, 113));
        btnPermission.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPermissionMouseClicked(evt);
            }
        });

        btnRoleIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/restriction_shield_40px.png"))); // NOI18N

        btnRoleLbl1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnRoleLbl1.setForeground(new java.awt.Color(255, 255, 255));
        btnRoleLbl1.setText("PERMISSION");

        javax.swing.GroupLayout btnPermissionLayout = new javax.swing.GroupLayout(btnPermission);
        btnPermission.setLayout(btnPermissionLayout);
        btnPermissionLayout.setHorizontalGroup(
            btnPermissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPermissionLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRoleIcon1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRoleLbl1)
                .addGap(25, 25, 25))
        );
        btnPermissionLayout.setVerticalGroup(
            btnPermissionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRoleLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPermissionLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnRoleIcon1)
                .addGap(15, 15, 15))
        );

        btnEditUser.setBackground(new java.awt.Color(19, 132, 150));
        btnEditUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditUserMouseClicked(evt);
            }
        });

        btnUserIcon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLbl1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl1.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl1.setText("EDIT");

        javax.swing.GroupLayout btnEditUserLayout = new javax.swing.GroupLayout(btnEditUser);
        btnEditUser.setLayout(btnEditUserLayout);
        btnEditUserLayout.setHorizontalGroup(
            btnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnEditUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl1)
                .addGap(25, 25, 25))
        );
        btnEditUserLayout.setVerticalGroup(
            btnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnEditUserLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnUserIcon1)
                .addGap(15, 15, 15))
        );

        btnChangePass.setBackground(new java.awt.Color(35, 39, 43));
        btnChangePass.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChangePassMouseClicked(evt);
            }
        });

        btnUserIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLbl2.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl2.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl2.setText("PASSWORD");

        javax.swing.GroupLayout btnChangePassLayout = new javax.swing.GroupLayout(btnChangePass);
        btnChangePass.setLayout(btnChangePassLayout);
        btnChangePassLayout.setHorizontalGroup(
            btnChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnChangePassLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl2)
                .addGap(25, 25, 25))
        );
        btnChangePassLayout.setVerticalGroup(
            btnChangePassLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnChangePassLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnUserIcon2)
                .addGap(15, 15, 15))
        );

        btnDelUser.setBackground(new java.awt.Color(200, 35, 51));
        btnDelUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDelUserMouseClicked(evt);
            }
        });

        btnUserIcon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLbl3.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl3.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl3.setText("DELETE");

        javax.swing.GroupLayout btnDelUserLayout = new javax.swing.GroupLayout(btnDelUser);
        btnDelUser.setLayout(btnDelUserLayout);
        btnDelUserLayout.setHorizontalGroup(
            btnDelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelUserLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl3)
                .addGap(25, 25, 25))
        );
        btnDelUserLayout.setVerticalGroup(
            btnDelUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDelUserLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnUserIcon3)
                .addGap(15, 15, 15))
            .addComponent(btnUserLbl3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnUserRefresh.setBackground(new java.awt.Color(35, 39, 43));
        btnUserRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUserRefreshMouseClicked(evt);
            }
        });

        btnRoleIcon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/refresh_40px.png"))); // NOI18N

        javax.swing.GroupLayout btnUserRefreshLayout = new javax.swing.GroupLayout(btnUserRefresh);
        btnUserRefresh.setLayout(btnUserRefreshLayout);
        btnUserRefreshLayout.setHorizontalGroup(
            btnUserRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUserRefreshLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnRoleIcon2)
                .addGap(25, 25, 25))
        );
        btnUserRefreshLayout.setVerticalGroup(
            btnUserRefreshLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnUserRefreshLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(btnRoleIcon2)
                .addGap(15, 15, 15))
        );

        txtSearchUser.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchUser.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchUserKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Search:");

        javax.swing.GroupLayout userPanelLayout = new javax.swing.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 624, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtSearchUser, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, userPanelLayout.createSequentialGroup()
                                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(userPanelLayout.createSequentialGroup()
                                .addComponent(btnDelUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnPermission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnUserRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(userPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnChangePass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnRole, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPermission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(userPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(userPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, 0)
                        .addComponent(txtSearchUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnUserRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        dynamicPanel.add(userPanel, "card6");

        javax.swing.GroupLayout dashPanelLayout = new javax.swing.GroupLayout(dashPanel);
        dashPanel.setLayout(dashPanelLayout);
        dashPanelLayout.setHorizontalGroup(
            dashPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );
        dashPanelLayout.setVerticalGroup(
            dashPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 876, Short.MAX_VALUE)
        );

        dynamicPanel.add(dashPanel, "card6");

        exitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_delete_sign_50px.png"))); // NOI18N
        exitIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitIconMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(sidebar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dynamicPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dynamicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(exitIcon)
                        .addGap(5, 5, 5))))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidebar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitIcon)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(dynamicLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(dynamicPanel))
        );

        getContentPane().add(background, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 1289, 939);
    }// </editor-fold>//GEN-END:initComponents

    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(dashPanel);
        dynamicLabel.setFont(new Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("DASHBOARD");
        JOptionPane.showMessageDialog(null, "You have been logged in!");
    }//GEN-LAST:event_formWindowOpened

    private void exitIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitIconMouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitIconMouseClicked

    private void comLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comLabelMouseClicked
        // TODO add your handling code here:
        com = true;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dyanmicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(comPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new Font("Segoe UI", 0, 36)); 
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("COMPANY");
        
        //braLayer
        braActive.setBackground(new Color(78, 45, 17));
        branchLabel.setBackground(new Color(78, 45, 17));

        //proLayer
        proActive.setBackground(new Color(78, 45, 17));
        proLabel.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffActive.setBackground(new Color(78, 45, 17));
        staffLabel.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLayer.removeAll();
        comLayer.add(comActive, 0);
        comLayer.add(comBlink, 1);
        comActive.setBackground(new Color(255, 255, 255));
        comLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_comLabelMouseClicked

    private void comLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comLabelMouseEntered
        // TODO add your handling code here:
        if(com == false){
            comLayer.add(comActive, 1);
            comLayer.add(comBlink, 0);
            comBlink.setBackground(new Color(255, 255, 255));
        }
        
    }//GEN-LAST:event_comLabelMouseEntered

    private void comLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comLabelMouseExited
        // TODO add your handling code here:
        if(com == false)
            comBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_comLabelMouseExited

    private void branchLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = true;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(branchPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("BRANCH");
        
        //comLayer
        comActive.setBackground(new Color(78, 45, 17));
        comLabel.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proActive.setBackground(new Color(78, 45, 17));
        proLabel.setBackground(new Color(78, 45, 17));
        
        //stafffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //braLayer
        braLayer.removeAll();       
        braLayer.add(braActive, 0);
        braLayer.add(braBlink, 1);
        braActive.setBackground(new Color(255, 255, 255));
        branchLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_branchLabelMouseClicked

    private void branchLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchLabelMouseEntered
        // TODO add your handling code here:
        if(bra == false){
            braLayer.add(braActive, 1);
            braLayer.add(braBlink, 0);
            braBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_branchLabelMouseEntered

    private void branchLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_branchLabelMouseExited
        // TODO add your handling code here:
        if(bra == false)
            braBlink.setBackground(new Color(78, 45, 17)); 
    }//GEN-LAST:event_branchLabelMouseExited

    private void proLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = true;
        stocks = false;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(proPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("PRODUCT");
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        
        //braLayer
        braActive.setBackground(new Color(78, 45, 17));
        branchLabel.setBackground(new Color(78, 45, 17));

        //comLayer
        comActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLayer.removeAll();
        proLayer.add(proActive, 0);
        proLayer.add(proBlink, 1);
        proActive.setBackground(new Color(255, 255, 255));
        proLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_proLabelMouseClicked

    private void proLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proLabelMouseEntered
        // TODO add your handling code here:
        if(prod == false){
            proLayer.add(proActive, 1);
            proLayer.add(proBlink, 0);
            proBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_proLabelMouseEntered

    private void proLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proLabelMouseExited
        // TODO add your handling code here:
        if(prod == false)
            proBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_proLabelMouseExited

    private void staffLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = true;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(branchPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("STAFF");
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLayer.add(staffBlink, 1);
        staffLayer.add(staffActive, 0);
        staffActive.setBackground(new Color(255, 255, 255));
        staffLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_staffLabelMouseClicked

    private void staffLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffLabelMouseEntered
        // TODO add your handling code here:
        if(staffs == false){
            staffLayer.add(staffActive, 1);
            staffLayer.add(staffBlink, 0);
            staffBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_staffLabelMouseEntered

    private void staffLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_staffLabelMouseExited
        // TODO add your handling code here:
        if(staffs == false)
            staffBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_staffLabelMouseExited

    private void stockLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = true;
        staffs = false;
        user = false;
        dash = false;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(stockPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("STOCK");
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        //stockLayer
        stockLabel.setBackground(new Color(102, 51, 0));
        stockLayer.add(stockBlink, 1);
        stockLayer.add(stockActive, 0);
        stockActive.setBackground(new Color(255, 255, 255));
        stockLabel.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_stockLabelMouseClicked

    private void stockLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseEntered
        // TODO add your handling code here:
        if(stocks == false){
            stockLayer.add(stockActive, 1);
            stockLayer.add(stockBlink, 0);
            stockBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_stockLabelMouseEntered

    private void stockLabelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockLabelMouseExited
        // TODO add your handling code here:
        if(stocks == false)
            stockBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_stockLabelMouseExited

    private void userPnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPnlMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = true;
        dash = false;
        sup = false;

        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(userPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("USER");
        showUsers(getAllUsers());
        JTableHeader header = userTbl.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);

        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));

        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));

        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));

        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));

        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));

        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));

        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));

        userPnl.setBackground(new Color(102, 51, 0));
        userLayp.add(userBlink, 1);
        userLayp.add(userActive, 0);
        userActive.setBackground(new Color(255, 255, 255));
        userPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_userPnlMouseClicked

    private void userPnlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPnlMouseEntered
        // TODO add your handling code here:
        if(user == false){
            userLayp.add(userActive, 1);
            userLayp.add(userBlink, 0);
            userBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_userPnlMouseEntered

    private void userPnlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userPnlMouseExited
        // TODO add your handling code here:
        if(user == false)
            userBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_userPnlMouseExited

    private void dashPnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashPnlMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = true;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(dashPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("DASHBOARD");
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(78, 45, 17));
        supplierActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(102, 51, 0));
        dashLayp.add(dashBlink, 1);
        dashLayp.add(dashActive, 0);
        dashActive.setBackground(new Color(255, 255, 255));
        dashPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_dashPnlMouseClicked

    private void dashPnlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashPnlMouseEntered
        // TODO add your handling code here:
        if(dash == false){
            dashLayp.add(dashActive, 1);
            dashLayp.add(dashBlink, 0);
            dashBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_dashPnlMouseEntered

    private void dashPnlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashPnlMouseExited
        // TODO add your handling code here:
        if(dash == false)
            dashBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_dashPnlMouseExited

    private void btnRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRoleMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "roles", "read"))
            AppHelper.permissionMessage();
        else
            new RoleForm().setVisible(true);
    }//GEN-LAST:event_btnRoleMouseClicked

    private void btnPermissionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPermissionMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "permissions", "read"))
            AppHelper.permissionMessage();
        else
            new PermissionForm().setVisible(true);
    }//GEN-LAST:event_btnPermissionMouseClicked

    private void btnAddUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddUserMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "create"))
            AppHelper.permissionMessage();
        else
            new UserActions().setVisible(true);
    }//GEN-LAST:event_btnAddUserMouseClicked

    private void btnEditUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditUserMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "update"))
            AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                new UserActions("edit", id).setVisible(true);   
            }
        }
    }//GEN-LAST:event_btnEditUserMouseClicked

    private void btnChangePassMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChangePassMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "update"))
            AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                new UserActions("edit_password", id).setVisible(true);   
            }
        }
    }//GEN-LAST:event_btnChangePassMouseClicked

    private void btnDelUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelUserMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "delete"))
            AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                if(id == userId) {
                    JOptionPane.showMessageDialog(null, "You can't delete yourself!");
                } else {
                    int res = JOptionPane.showConfirmDialog(
                        this, 
                        "Are you sure you want to delete this?", 
                        "Confirm message", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if(res == JOptionPane.YES_OPTION) {
                        User myUser = new User();
                        myUser.delete(id);
                        showUsers(getAllUsers());
                    }
                }
            }
        }
    }//GEN-LAST:event_btnDelUserMouseClicked

    private void btnUserRefreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserRefreshMouseClicked
        // TODO add your handling code here:
        showUsers(getAllUsers());
    }//GEN-LAST:event_btnUserRefreshMouseClicked

    private void btnLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseClicked
        // TODO add your handling code here:
        this.dispose();
        new Login().setVisible(true);
        JOptionPane.showMessageDialog(null, "You have logged out!");
    }//GEN-LAST:event_btnLogoutMouseClicked

    private void supplierPnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierPnlMouseClicked
        // TODO add your handling code here:
        com = false;
        bra = false;
        prod = false;
        stocks = false;
        staffs = false;
        user = false;
        dash = true;
        sup = false;
        
        //dynamicPanel
        dynamicPanel.removeAll();
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicPanel.add(supplierPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("SUPPLIER");
        //show(getAllUsers());
        JTableHeader header = supplierTbl1.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
        //braLayer
        branchLabel.setBackground(new Color(78, 45, 17));
        braActive.setBackground(new Color(78, 45, 17));
        
        //comLayer
        comLabel.setBackground(new Color(78, 45, 17));
        comActive.setBackground(new Color(78, 45, 17));
        
        //proLayer
        proLabel.setBackground(new Color(78, 45, 17));
        proActive.setBackground(new Color(78, 45, 17));
        
        //staffLayer
        staffLabel.setBackground(new Color(78, 45, 17));
        staffActive.setBackground(new Color(78, 45, 17));
        
        stockLabel.setBackground(new Color(78, 45, 17));
        stockActive.setBackground(new Color(78, 45, 17));
        
        userPnl.setBackground(new Color(78, 45, 17));
        userActive.setBackground(new Color(78, 45, 17));
        
        dashPnl.setBackground(new Color(78, 45, 17));
        dashActive.setBackground(new Color(78, 45, 17));
        
        supplierPnl.setBackground(new Color(102, 51, 0));
        supplierLayp.add(supplierBlink, 1);
        supplierLayp.add(supplierActive, 0);
        supplierActive.setBackground(new Color(255, 255, 255));
        supplierPnl.setBackground(new Color(102, 51, 0));
    }//GEN-LAST:event_supplierPnlMouseClicked

    private void supplierPnlMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierPnlMouseEntered
        // TODO add your handling code here:
        if(sup == false){
            supplierLayp.add(supplierActive, 1);
            supplierLayp.add(supplierBlink, 0);
            supplierBlink.setBackground(new Color(255, 255, 255));
        }
    }//GEN-LAST:event_supplierPnlMouseEntered

    private void supplierPnlMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_supplierPnlMouseExited
        // TODO add your handling code here:
        if(sup == false)
            supplierBlink.setBackground(new Color(78, 45, 17));
    }//GEN-LAST:event_supplierPnlMouseExited

    private void txtSearchUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchUserKeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
            txtSearchSup1.setEditable(true);
        else
            txtSearchSup1.setEditable(false);
        showUsers(searchUser(txtSearchSup1.getText().trim()));
    }//GEN-LAST:event_txtSearchUserKeyPressed

    private void btnDelSup1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDelSup1MouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "delete"))
        AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
            JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                if(id == userId) {
                    JOptionPane.showMessageDialog(null, "You can't delete yourself!");
                } else {
                    int res = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this?",
                        "Confirm message",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if(res == JOptionPane.YES_OPTION) {
                        Supplier mySup = new Supplier();
                        mySup.delete(id);
                        showSups(getAllSups());
                    }
                }
            }
        }
    }//GEN-LAST:event_btnDelSup1MouseClicked

    private void btnSupRefresh1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSupRefresh1MouseClicked
        // TODO add your handling code here:
        showSups(getAllSups());
    }//GEN-LAST:event_btnSupRefresh1MouseClicked

    private void btnEditSup1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditSup1MouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "update"))
        AppHelper.permissionMessage();
        else {
            int row = userTbl.getSelectedRow();
            if(row < 0)
            JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                int id = (int)userTbl.getValueAt(row, 6);
                new UserActions("edit", id).setVisible(true);
            }
        }
    }//GEN-LAST:event_btnEditSup1MouseClicked

    private void txtSearchSup1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchSup1KeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
        txtSearchUser.setEditable(true);
        else
        txtSearchUser.setEditable(false);
        showSups(searchSups(txtSearchSup1.getText().trim()));
    }//GEN-LAST:event_txtSearchSup1KeyPressed

    private void btnAddSup1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSup1MouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "users", "create"))
        AppHelper.permissionMessage();
        else
        new SupplierActions().setVisible(true);
    }//GEN-LAST:event_btnAddSup1MouseClicked

    private void btnAddSup1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddSup1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddSup1MouseEntered

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Main_Menu().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Line;
    private javax.swing.JPanel background;
    private javax.swing.JPanel braActive;
    private javax.swing.JPanel braBlink;
    private javax.swing.JLayeredPane braLayer;
    private javax.swing.JLabel branch;
    private javax.swing.JLabel branchIcon;
    private javax.swing.JPanel branchLabel;
    private javax.swing.JPanel branchPanel;
    private javax.swing.JPanel btnAddSup1;
    private javax.swing.JPanel btnAddUser;
    private javax.swing.JPanel btnChangePass;
    private javax.swing.JPanel btnDelSup1;
    private javax.swing.JPanel btnDelUser;
    private javax.swing.JPanel btnEditSup1;
    private javax.swing.JPanel btnEditUser;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPermission;
    private javax.swing.JLabel btnRefIcon3;
    private javax.swing.JPanel btnRole;
    private javax.swing.JLabel btnRoleIcon;
    private javax.swing.JLabel btnRoleIcon1;
    private javax.swing.JLabel btnRoleIcon2;
    private javax.swing.JLabel btnRoleLbl;
    private javax.swing.JLabel btnRoleLbl1;
    private javax.swing.JLabel btnSupIcon4;
    private javax.swing.JLabel btnSupIcon5;
    private javax.swing.JLabel btnSupIcon6;
    private javax.swing.JLabel btnSupLbl4;
    private javax.swing.JLabel btnSupLbl5;
    private javax.swing.JLabel btnSupLbl6;
    private javax.swing.JPanel btnSupRefresh1;
    private javax.swing.JLabel btnUserIcon;
    private javax.swing.JLabel btnUserIcon1;
    private javax.swing.JLabel btnUserIcon2;
    private javax.swing.JLabel btnUserIcon3;
    private javax.swing.JLabel btnUserLbl;
    private javax.swing.JLabel btnUserLbl1;
    private javax.swing.JLabel btnUserLbl2;
    private javax.swing.JLabel btnUserLbl3;
    private javax.swing.JPanel btnUserRefresh;
    private javax.swing.JPanel comActive;
    private javax.swing.JPanel comBlink;
    private javax.swing.JLabel comIcon;
    private javax.swing.JPanel comLabel;
    private javax.swing.JLayeredPane comLayer;
    private javax.swing.JPanel comPanel;
    private javax.swing.JLabel company;
    private javax.swing.JLabel company1;
    private javax.swing.JLabel company2;
    private javax.swing.JPanel dashActive;
    private javax.swing.JPanel dashBlink;
    private javax.swing.JLabel dashIcon;
    private javax.swing.JLayeredPane dashLayp;
    private javax.swing.JLabel dashLbl;
    private javax.swing.JPanel dashPanel;
    private javax.swing.JPanel dashPnl;
    private javax.swing.JLabel dynamicLabel;
    private javax.swing.JLayeredPane dynamicPanel;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel pro;
    private javax.swing.JPanel proActive;
    private javax.swing.JPanel proBlink;
    private javax.swing.JLabel proIcon;
    private javax.swing.JPanel proLabel;
    private javax.swing.JLayeredPane proLayer;
    private javax.swing.JPanel proPanel;
    private javax.swing.JPanel sidebar;
    private javax.swing.JLabel staff;
    private javax.swing.JPanel staffActive;
    private javax.swing.JPanel staffBlink;
    private javax.swing.JLabel staffIcon;
    private javax.swing.JPanel staffLabel;
    private javax.swing.JLayeredPane staffLayer;
    private javax.swing.JPanel staffPanel;
    private javax.swing.JLabel stock;
    private javax.swing.JLabel stock1;
    private javax.swing.JPanel stockActive;
    private javax.swing.JPanel stockBlink;
    private javax.swing.JLabel stockIcon;
    private javax.swing.JLabel stockIcon1;
    private javax.swing.JPanel stockLabel;
    private javax.swing.JLayeredPane stockLayer;
    private javax.swing.JPanel stockPanel;
    private javax.swing.JPanel supplierActive;
    private javax.swing.JPanel supplierBlink;
    private javax.swing.JLayeredPane supplierLayp;
    private javax.swing.JPanel supplierPanel;
    private javax.swing.JPanel supplierPnl;
    private javax.swing.JTable supplierTbl1;
    private javax.swing.JTextField txtSearchSup1;
    private javax.swing.JTextField txtSearchUser;
    private javax.swing.JPanel userActive;
    private javax.swing.JPanel userBlink;
    private javax.swing.JLabel userIcon;
    private javax.swing.JLayeredPane userLayp;
    private javax.swing.JLabel userLbl;
    private javax.swing.JPanel userPanel;
    private javax.swing.JPanel userPnl;
    private javax.swing.JTable userTbl;
    // End of variables declaration//GEN-END:variables

    private static class jPanel2 {

        public jPanel2() {
        }
    }

    private static class Sups {

        public Sups() {
        }

        private Sups(int i, int aInt, String string, String string0, String string1, String string2, int aInt0, int aInt1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getid() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getname() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getaddress() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getphone() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getemail() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getcompany_id() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        private Object getbranch_id() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
