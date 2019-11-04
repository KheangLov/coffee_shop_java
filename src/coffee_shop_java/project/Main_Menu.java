/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Helper.AppHelper;
import coffee_shop_java.project.Model.Company;
import coffee_shop_java.project.Model.User;
import com.sun.org.apache.bcel.internal.generic.RETURN;
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
    Company myCompany = new Company();
    public Main_Menu(int uId, int rId) {
        initComponents();
        userId = uId;
        roleId = rId;
        this.setExtendedState(Main_Menu.MAXIMIZED_BOTH);
    }

    public Main_Menu() {
        initComponents();
    }
    
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
    
    public ArrayList<Company> getComList(){
        ArrayList<Company> list = new ArrayList<>();
        String sql = "SELECT * FROM companies";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                Company userCom;
                int i = 0;
                while(rs.next()){
                    i++;
                    userCom = new Company(
                        i,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("id")
                    );
                    list.add(userCom);
                } 
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        return list;
    }
    
    public void showCompanies(ArrayList<Company> list){
        tblCom.setModel(new DefaultTableModel(
            null,
            new String[]{
                "#",
                "Name",
                "Address",
                "Email",
                "Phone",
                "id"
            }
        ));
        DefaultTableModel model = (DefaultTableModel) tblCom.getModel();
        Object[] row = new Object[6];
        for(int i=0; i<list.size(); i++){
            row[0] = list.get(i).getTblId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getEmail();
            row[4] = list.get(i).getPhone();
            row[5] = list.get(i).getId();
            model.addRow(row);
        }
        AppHelper.setColWidth(tblCom, 5, 0);
    }
    
    public ArrayList<Company> searchCom(String text) {
        ArrayList<Company> list = new ArrayList<>();
        if(AppHelper.currentUserCan(roleId, "companies", "read")) {
            String sql = "SELECT * FROM companies WHERE LOWER(name) LIKE '%" + text + "%'";
            try {
                ResultSet rs = AppHelper.selectQuery(sql);
                Company company;
                int i = 0;
                while(rs.next()){
                    i++;
                    company = new Company(
                        i,
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getInt("id")
                    );
                    list.add(company);
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
        comPanel = new javax.swing.JPanel();
        dynamicComPnl = new javax.swing.JLayeredPane();
        pnlComList = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblCom = new javax.swing.JTable();
        lblSearch = new javax.swing.JLabel();
        txtSearchCom = new javax.swing.JTextField();
        lineSearch = new javax.swing.JPanel();
        pnlComAdd = new javax.swing.JPanel();
        lblAddCom = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtComName = new javax.swing.JTextField();
        LineName = new javax.swing.JPanel();
        cbComUser = new javax.swing.JComboBox<>();
        lblCbUser = new javax.swing.JLabel();
        LineCbUser = new javax.swing.JPanel();
        btnAddCom = new javax.swing.JPanel();
        lblAddCompany = new javax.swing.JLabel();
        LineEmail = new javax.swing.JPanel();
        lbEmail = new javax.swing.JLabel();
        txtComEmail = new javax.swing.JTextField();
        LinePhone = new javax.swing.JPanel();
        lblPhone = new javax.swing.JLabel();
        txtComPhone = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtComAddres = new javax.swing.JTextArea();
        LineTxtAddress = new javax.swing.JPanel();
        lblAddresss = new javax.swing.JLabel();
        pnlComUpdate = new javax.swing.JPanel();
        lblUpdateCom = new javax.swing.JLabel();
        lblName1 = new javax.swing.JLabel();
        txtUpdateName = new javax.swing.JTextField();
        LineName1 = new javax.swing.JPanel();
        cbComUser1 = new javax.swing.JComboBox<>();
        lblCbUser1 = new javax.swing.JLabel();
        LineCbUser1 = new javax.swing.JPanel();
        btnUpdateCom = new javax.swing.JPanel();
        lblUpdateCompany = new javax.swing.JLabel();
        LineEmail1 = new javax.swing.JPanel();
        lbEmail1 = new javax.swing.JLabel();
        txtComEmail1 = new javax.swing.JTextField();
        LinePhone1 = new javax.swing.JPanel();
        lblPhone1 = new javax.swing.JLabel();
        txtComPhone1 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtComAddres1 = new javax.swing.JTextArea();
        LineTxtAddress1 = new javax.swing.JPanel();
        lblAddresss1 = new javax.swing.JLabel();
        btnComAdd = new javax.swing.JPanel();
        btnUserIconComAdd = new javax.swing.JLabel();
        btnUserLblComAdd = new javax.swing.JLabel();
        btnComEdit = new javax.swing.JPanel();
        btnUserIconComEdit = new javax.swing.JLabel();
        btnUserLblComEdit = new javax.swing.JLabel();
        btnComDel = new javax.swing.JPanel();
        btnUserIconDel = new javax.swing.JLabel();
        btnUserLblDel = new javax.swing.JLabel();
        btnComList = new javax.swing.JPanel();
        btnUserIconComList = new javax.swing.JLabel();
        btnUserLblComList = new javax.swing.JLabel();
        branchPanel = new javax.swing.JPanel();
        proPanel = new javax.swing.JPanel();
        staffPanel = new javax.swing.JPanel();
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
        stockPanel = new javax.swing.JPanel();
        btnStockEdit = new javax.swing.JPanel();
        btnUserIcon6 = new javax.swing.JLabel();
        btnUserLbl6 = new javax.swing.JLabel();
        dynamicStockPane = new javax.swing.JLayeredPane();
        pnlStockImport = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtStockName = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        dpStockExpired = new com.toedter.calendar.JDateChooser();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtStockQty = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbStockCate = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtStockAlertQty = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtStockPrice = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        cbStockCompany = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        cbStockBranch = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        cbStockSupplier = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnAddStock = new javax.swing.JPanel();
        lblAdd3 = new javax.swing.JLabel();
        pnlStockList = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearchStock = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        pnlStockCate = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtStockCateName = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        btnAddStock1 = new javax.swing.JPanel();
        lblAdd4 = new javax.swing.JLabel();
        txtStockCateDesc = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStockCate = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtSearchStock1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btnStockImport = new javax.swing.JPanel();
        btnUserIcon4 = new javax.swing.JLabel();
        btnUserLbl4 = new javax.swing.JLabel();
        btnStockDel = new javax.swing.JPanel();
        btnUserIcon7 = new javax.swing.JLabel();
        btnUserLbl7 = new javax.swing.JLabel();
        btnStockCate = new javax.swing.JPanel();
        btnUserIcon9 = new javax.swing.JLabel();
        btnUserLbl9 = new javax.swing.JLabel();
        btnStockList = new javax.swing.JPanel();
        btnUserIcon11 = new javax.swing.JLabel();
        btnUserLbl11 = new javax.swing.JLabel();
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

        comPanel.setBackground(new java.awt.Color(234, 234, 234));
        comPanel.setPreferredSize(new java.awt.Dimension(1217, 884));

        dynamicComPnl.setBackground(new java.awt.Color(255, 255, 255));
        dynamicComPnl.setLayout(new java.awt.CardLayout());

        pnlComList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblCom.setAutoCreateRowSorter(true);
        tblCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblCom.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblCom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Address", "Email", "Phone", "User Id"
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
        tblCom.setEditingColumn(0);
        tblCom.setEditingRow(0);
        tblCom.setGridColor(new java.awt.Color(255, 255, 255));
        tblCom.setOpaque(false);
        tblCom.setPreferredSize(new java.awt.Dimension(1167, 744));
        tblCom.setRowHeight(34);
        tblCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblCom);

        lblSearch.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblSearch.setText("Search:");

        txtSearchCom.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchCom.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchCom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchCom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchComActionPerformed(evt);
            }
        });
        txtSearchCom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchComKeyPressed(evt);
            }
        });

        lineSearch.setBackground(new java.awt.Color(0, 0, 0));
        lineSearch.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout lineSearchLayout = new javax.swing.GroupLayout(lineSearch);
        lineSearch.setLayout(lineSearchLayout);
        lineSearchLayout.setHorizontalGroup(
            lineSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1167, Short.MAX_VALUE)
        );
        lineSearchLayout.setVerticalGroup(
            lineSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlComListLayout = new javax.swing.GroupLayout(pnlComList);
        pnlComList.setLayout(pnlComListLayout);
        pnlComListLayout.setHorizontalGroup(
            pnlComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchCom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
            .addComponent(lineSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 1167, Short.MAX_VALUE)
            .addComponent(lblSearch)
        );
        pnlComListLayout.setVerticalGroup(
            pnlComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComListLayout.createSequentialGroup()
                .addComponent(lblSearch)
                .addGap(0, 0, 0)
                .addComponent(txtSearchCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lineSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicComPnl.add(pnlComList, "card2");

        pnlComAdd.setBackground(new java.awt.Color(255, 255, 255));

        lblAddCom.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblAddCom.setText("ADD COMPANY");

        lblName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName.setText("Name:");

        txtComName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComName.setBorder(null);
        txtComName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComName.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComNameFocusLost(evt);
            }
        });
        txtComName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComNameActionPerformed(evt);
            }
        });

        LineName.setBackground(new java.awt.Color(0, 0, 0));
        LineName.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineNameLayout = new javax.swing.GroupLayout(LineName);
        LineName.setLayout(LineNameLayout);
        LineNameLayout.setHorizontalGroup(
            LineNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineNameLayout.setVerticalGroup(
            LineNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbComUser.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbComUser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbComUser.setPreferredSize(new java.awt.Dimension(300, 42));
        cbComUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbComUserActionPerformed(evt);
            }
        });

        lblCbUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCbUser.setText("User:");

        LineCbUser.setBackground(new java.awt.Color(0, 0, 0));
        LineCbUser.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineCbUserLayout = new javax.swing.GroupLayout(LineCbUser);
        LineCbUser.setLayout(LineCbUserLayout);
        LineCbUserLayout.setHorizontalGroup(
            LineCbUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineCbUserLayout.setVerticalGroup(
            LineCbUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnAddCom.setBackground(new java.awt.Color(144, 202, 249));
        btnAddCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddComMouseClicked(evt);
            }
        });

        lblAddCompany.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAddCompany.setForeground(new java.awt.Color(255, 255, 255));
        lblAddCompany.setText("ADD");

        javax.swing.GroupLayout btnAddComLayout = new javax.swing.GroupLayout(btnAddCom);
        btnAddCom.setLayout(btnAddComLayout);
        btnAddComLayout.setHorizontalGroup(
            btnAddComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddComLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAddCompany)
                .addGap(25, 25, 25))
        );
        btnAddComLayout.setVerticalGroup(
            btnAddComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddComLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAddCompany)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LineEmail.setBackground(new java.awt.Color(0, 0, 0));
        LineEmail.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineEmailLayout = new javax.swing.GroupLayout(LineEmail);
        LineEmail.setLayout(LineEmailLayout);
        LineEmailLayout.setHorizontalGroup(
            LineEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineEmailLayout.setVerticalGroup(
            LineEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lbEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbEmail.setText("Email:");

        txtComEmail.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComEmail.setBorder(null);
        txtComEmail.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComEmail.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComEmailFocusLost(evt);
            }
        });
        txtComEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComEmailActionPerformed(evt);
            }
        });

        LinePhone.setBackground(new java.awt.Color(0, 0, 0));
        LinePhone.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LinePhoneLayout = new javax.swing.GroupLayout(LinePhone);
        LinePhone.setLayout(LinePhoneLayout);
        LinePhoneLayout.setHorizontalGroup(
            LinePhoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LinePhoneLayout.setVerticalGroup(
            LinePhoneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPhone.setText("Phone:");

        txtComPhone.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComPhone.setBorder(null);
        txtComPhone.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComPhone.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComPhoneFocusLost(evt);
            }
        });
        txtComPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComPhoneActionPerformed(evt);
            }
        });

        jScrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        txtComAddres.setColumns(20);
        txtComAddres.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComAddres.setRows(5);
        txtComAddres.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        jScrollPane5.setViewportView(txtComAddres);

        LineTxtAddress.setBackground(new java.awt.Color(0, 0, 0));
        LineTxtAddress.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineTxtAddressLayout = new javax.swing.GroupLayout(LineTxtAddress);
        LineTxtAddress.setLayout(LineTxtAddressLayout);
        LineTxtAddressLayout.setHorizontalGroup(
            LineTxtAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineTxtAddressLayout.setVerticalGroup(
            LineTxtAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblAddresss.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAddresss.setText("Address:");

        javax.swing.GroupLayout pnlComAddLayout = new javax.swing.GroupLayout(pnlComAdd);
        pnlComAdd.setLayout(pnlComAddLayout);
        pnlComAddLayout.setHorizontalGroup(
            pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComAddLayout.createSequentialGroup()
                .addGroup(pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAddCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlComAddLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblAddCom, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlComAddLayout.createSequentialGroup()
                                .addGroup(pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LineName, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(txtComName, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(cbComUser, 0, 549, Short.MAX_VALUE)
                                    .addComponent(LineCbUser, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(lblCbUser, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LineEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(txtComEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(LinePhone, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(txtComPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(lbEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(lblPhone, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)))
                            .addComponent(jScrollPane5)
                            .addComponent(LineTxtAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE)
                            .addComponent(lblAddresss, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        pnlComAddLayout.setVerticalGroup(
            pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAddCom)
                .addGap(18, 18, 18)
                .addGroup(pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlComAddLayout.createSequentialGroup()
                        .addGroup(pnlComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlComAddLayout.createSequentialGroup()
                                .addComponent(lblName)
                                .addGap(0, 0, 0)
                                .addComponent(txtComName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(LineName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlComAddLayout.createSequentialGroup()
                                .addComponent(lbEmail)
                                .addGap(0, 0, 0)
                                .addComponent(txtComEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(LineEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblCbUser)
                        .addGap(0, 0, 0)
                        .addComponent(cbComUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(LineCbUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlComAddLayout.createSequentialGroup()
                        .addComponent(lblPhone)
                        .addGap(0, 0, 0)
                        .addComponent(txtComPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(LinePhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblAddresss)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineTxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        dynamicComPnl.add(pnlComAdd, "card2");

        pnlComUpdate.setBackground(new java.awt.Color(255, 255, 255));

        lblUpdateCom.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        lblUpdateCom.setText("UPDATE COMPANY");

        lblName1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblName1.setText("Name:");

        txtUpdateName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtUpdateName.setBorder(null);
        txtUpdateName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtUpdateName.setPreferredSize(new java.awt.Dimension(300, 36));
        txtUpdateName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUpdateNameFocusLost(evt);
            }
        });
        txtUpdateName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUpdateNameActionPerformed(evt);
            }
        });

        LineName1.setBackground(new java.awt.Color(0, 0, 0));
        LineName1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineName1Layout = new javax.swing.GroupLayout(LineName1);
        LineName1.setLayout(LineName1Layout);
        LineName1Layout.setHorizontalGroup(
            LineName1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineName1Layout.setVerticalGroup(
            LineName1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbComUser1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbComUser1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbComUser1.setPreferredSize(new java.awt.Dimension(300, 42));
        cbComUser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbComUser1ActionPerformed(evt);
            }
        });

        lblCbUser1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCbUser1.setText("User:");

        LineCbUser1.setBackground(new java.awt.Color(0, 0, 0));
        LineCbUser1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineCbUser1Layout = new javax.swing.GroupLayout(LineCbUser1);
        LineCbUser1.setLayout(LineCbUser1Layout);
        LineCbUser1Layout.setHorizontalGroup(
            LineCbUser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineCbUser1Layout.setVerticalGroup(
            LineCbUser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnUpdateCom.setBackground(new java.awt.Color(19, 132, 150));
        btnUpdateCom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateComMouseClicked(evt);
            }
        });

        lblUpdateCompany.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblUpdateCompany.setForeground(new java.awt.Color(255, 255, 255));
        lblUpdateCompany.setText("update");

        javax.swing.GroupLayout btnUpdateComLayout = new javax.swing.GroupLayout(btnUpdateCom);
        btnUpdateCom.setLayout(btnUpdateComLayout);
        btnUpdateComLayout.setHorizontalGroup(
            btnUpdateComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateComLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblUpdateCompany)
                .addGap(25, 25, 25))
        );
        btnUpdateComLayout.setVerticalGroup(
            btnUpdateComLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateComLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblUpdateCompany)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        LineEmail1.setBackground(new java.awt.Color(0, 0, 0));
        LineEmail1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineEmail1Layout = new javax.swing.GroupLayout(LineEmail1);
        LineEmail1.setLayout(LineEmail1Layout);
        LineEmail1Layout.setHorizontalGroup(
            LineEmail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineEmail1Layout.setVerticalGroup(
            LineEmail1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lbEmail1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbEmail1.setText("Email:");

        txtComEmail1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComEmail1.setBorder(null);
        txtComEmail1.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComEmail1.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComEmail1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComEmail1FocusLost(evt);
            }
        });
        txtComEmail1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComEmail1ActionPerformed(evt);
            }
        });

        LinePhone1.setBackground(new java.awt.Color(0, 0, 0));
        LinePhone1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LinePhone1Layout = new javax.swing.GroupLayout(LinePhone1);
        LinePhone1.setLayout(LinePhone1Layout);
        LinePhone1Layout.setHorizontalGroup(
            LinePhone1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LinePhone1Layout.setVerticalGroup(
            LinePhone1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblPhone1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPhone1.setText("Phone:");

        txtComPhone1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComPhone1.setBorder(null);
        txtComPhone1.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtComPhone1.setPreferredSize(new java.awt.Dimension(300, 36));
        txtComPhone1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComPhone1FocusLost(evt);
            }
        });
        txtComPhone1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtComPhone1ActionPerformed(evt);
            }
        });

        jScrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        txtComAddres1.setColumns(20);
        txtComAddres1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtComAddres1.setRows(5);
        txtComAddres1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        jScrollPane6.setViewportView(txtComAddres1);

        LineTxtAddress1.setBackground(new java.awt.Color(0, 0, 0));
        LineTxtAddress1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout LineTxtAddress1Layout = new javax.swing.GroupLayout(LineTxtAddress1);
        LineTxtAddress1.setLayout(LineTxtAddress1Layout);
        LineTxtAddress1Layout.setHorizontalGroup(
            LineTxtAddress1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        LineTxtAddress1Layout.setVerticalGroup(
            LineTxtAddress1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        lblAddresss1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblAddresss1.setText("Address:");

        javax.swing.GroupLayout pnlComUpdateLayout = new javax.swing.GroupLayout(pnlComUpdate);
        pnlComUpdate.setLayout(pnlComUpdateLayout);
        pnlComUpdateLayout.setHorizontalGroup(
            pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlComUpdateLayout.createSequentialGroup()
                .addGroup(pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUpdateCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlComUpdateLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUpdateCom, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlComUpdateLayout.createSequentialGroup()
                                .addGroup(pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LineName1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(txtUpdateName, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(cbComUser1, 0, 549, Short.MAX_VALUE)
                                    .addComponent(LineCbUser1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(lblCbUser1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                                    .addComponent(lblName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LineEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(txtComEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(LinePhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(txtComPhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(lbEmail1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                                    .addComponent(lblPhone1, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)))
                            .addComponent(jScrollPane6)
                            .addComponent(LineTxtAddress1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE)
                            .addComponent(lblAddresss1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1117, Short.MAX_VALUE))))
                .addGap(25, 25, 25))
        );
        pnlComUpdateLayout.setVerticalGroup(
            pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlComUpdateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblUpdateCom)
                .addGap(18, 18, 18)
                .addGroup(pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlComUpdateLayout.createSequentialGroup()
                        .addGroup(pnlComUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlComUpdateLayout.createSequentialGroup()
                                .addComponent(lblName1)
                                .addGap(0, 0, 0)
                                .addComponent(txtUpdateName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(LineName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlComUpdateLayout.createSequentialGroup()
                                .addComponent(lbEmail1)
                                .addGap(0, 0, 0)
                                .addComponent(txtComEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(LineEmail1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblCbUser1)
                        .addGap(0, 0, 0)
                        .addComponent(cbComUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(LineCbUser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlComUpdateLayout.createSequentialGroup()
                        .addComponent(lblPhone1)
                        .addGap(0, 0, 0)
                        .addComponent(txtComPhone1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(LinePhone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(lblAddresss1)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(LineTxtAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnUpdateCom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        dynamicComPnl.add(pnlComUpdate, "card2");

        btnComAdd.setBackground(new java.awt.Color(144, 202, 249));
        btnComAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComAddMouseClicked(evt);
            }
        });

        btnUserIconComAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/plus_40px.png"))); // NOI18N

        btnUserLblComAdd.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComAdd.setText("ADD");

        javax.swing.GroupLayout btnComAddLayout = new javax.swing.GroupLayout(btnComAdd);
        btnComAdd.setLayout(btnComAddLayout);
        btnComAddLayout.setHorizontalGroup(
            btnComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComAdd)
                .addGap(25, 25, 25))
        );
        btnComAddLayout.setVerticalGroup(
            btnComAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComAdd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIconComAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnComEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnComEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComEditMouseClicked(evt);
            }
        });

        btnUserIconComEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLblComEdit.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComEdit.setText("EDIT");

        javax.swing.GroupLayout btnComEditLayout = new javax.swing.GroupLayout(btnComEdit);
        btnComEdit.setLayout(btnComEditLayout);
        btnComEditLayout.setHorizontalGroup(
            btnComEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComEdit)
                .addGap(25, 25, 25))
        );
        btnComEditLayout.setVerticalGroup(
            btnComEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComEditLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconComEdit)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblComEdit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnComDel.setBackground(new java.awt.Color(200, 35, 51));
        btnComDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComDelMouseClicked(evt);
            }
        });

        btnUserIconDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLblDel.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblDel.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblDel.setText("DELETE");

        javax.swing.GroupLayout btnComDelLayout = new javax.swing.GroupLayout(btnComDel);
        btnComDel.setLayout(btnComDelLayout);
        btnComDelLayout.setHorizontalGroup(
            btnComDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconDel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblDel)
                .addGap(25, 25, 25))
        );
        btnComDelLayout.setVerticalGroup(
            btnComDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComDelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIconDel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLblDel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnComList.setBackground(new java.awt.Color(0, 0, 0));
        btnComList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComListMouseClicked(evt);
            }
        });

        btnUserIconComList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLblComList.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLblComList.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLblComList.setText("LIST");

        javax.swing.GroupLayout btnComListLayout = new javax.swing.GroupLayout(btnComList);
        btnComList.setLayout(btnComListLayout);
        btnComListLayout.setHorizontalGroup(
            btnComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnComListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIconComList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLblComList)
                .addGap(25, 25, 25))
        );
        btnComListLayout.setVerticalGroup(
            btnComListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLblComList, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnComListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIconComList, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout comPanelLayout = new javax.swing.GroupLayout(comPanel);
        comPanel.setLayout(comPanelLayout);
        comPanelLayout.setHorizontalGroup(
            comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1217, Short.MAX_VALUE)
            .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(comPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(dynamicComPnl, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(comPanelLayout.createSequentialGroup()
                            .addComponent(btnComAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnComEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnComDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnComList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(25, 25, 25)))
        );
        comPanelLayout.setVerticalGroup(
            comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 884, Short.MAX_VALUE)
            .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(comPanelLayout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addGroup(comPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnComEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnComList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(dynamicComPnl)
                    .addGap(25, 25, 25)))
        );

        dynamicPanel.add(comPanel, "card2");

        branchPanel.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout branchPanelLayout = new javax.swing.GroupLayout(branchPanel);
        branchPanel.setLayout(branchPanelLayout);
        branchPanelLayout.setHorizontalGroup(
            branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1374, Short.MAX_VALUE)
        );
        branchPanelLayout.setVerticalGroup(
            branchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 884, Short.MAX_VALUE)
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
            .addGap(0, 884, Short.MAX_VALUE)
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
            .addGap(0, 884, Short.MAX_VALUE)
        );

        dynamicPanel.add(staffPanel, "card5");

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
        txtSearchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchUserActionPerformed(evt);
            }
        });
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        dynamicPanel.add(userPanel, "card6");

        javax.swing.GroupLayout dashPanelLayout = new javax.swing.GroupLayout(dashPanel);
        dashPanel.setLayout(dashPanelLayout);
        dashPanelLayout.setHorizontalGroup(
            dashPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1374, Short.MAX_VALUE)
        );
        dashPanelLayout.setVerticalGroup(
            dashPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 884, Short.MAX_VALUE)
        );

        dynamicPanel.add(dashPanel, "card6");

        stockPanel.setBackground(new java.awt.Color(234, 234, 234));
        stockPanel.setToolTipText("");

        btnStockEdit.setBackground(new java.awt.Color(19, 132, 150));
        btnStockEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockEditMouseClicked(evt);
            }
        });

        btnUserIcon6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/pencil_40px.png"))); // NOI18N

        btnUserLbl6.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl6.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl6.setText("EDIT");

        javax.swing.GroupLayout btnStockEditLayout = new javax.swing.GroupLayout(btnStockEdit);
        btnStockEdit.setLayout(btnStockEditLayout);
        btnStockEditLayout.setHorizontalGroup(
            btnStockEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockEditLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl6)
                .addGap(25, 25, 25))
        );
        btnStockEditLayout.setVerticalGroup(
            btnStockEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockEditLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        dynamicStockPane.setBackground(new java.awt.Color(255, 255, 255));
        dynamicStockPane.setLayout(new java.awt.CardLayout());

        pnlStockImport.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        jLabel4.setText("IMPORT STOCK");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel9.setText("Name:");

        txtStockName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockName.setBorder(null);
        txtStockName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockName.setPreferredSize(new java.awt.Dimension(100, 36));
        txtStockName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockNameFocusLost(evt);
            }
        });
        txtStockName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockNameActionPerformed(evt);
            }
        });

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));
        jPanel9.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        dpStockExpired.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        dpStockExpired.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel10.setText("Expired date:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Quantity");

        txtStockQty.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockQty.setBorder(null);
        txtStockQty.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockQtyFocusLost(evt);
            }
        });
        txtStockQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockQtyActionPerformed(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));
        jPanel11.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Stock category:");

        cbStockCate.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbStockCate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel12.setBackground(new java.awt.Color(0, 0, 0));
        jPanel12.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Measure:");

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "g", "kg" }));

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));
        jPanel13.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setText("Alert quantity:");

        txtStockAlertQty.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockAlertQty.setBorder(null);
        txtStockAlertQty.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockAlertQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockAlertQtyFocusLost(evt);
            }
        });
        txtStockAlertQty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockAlertQtyActionPerformed(evt);
            }
        });

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));
        jPanel15.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setText("Price:");

        txtStockPrice.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockPrice.setBorder(null);
        txtStockPrice.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockPriceFocusLost(evt);
            }
        });
        txtStockPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockPriceActionPerformed(evt);
            }
        });

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));
        jPanel16.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbStockCompany.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbStockCompany.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setText("Company:");

        jPanel17.setBackground(new java.awt.Color(0, 0, 0));
        jPanel17.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbStockBranch.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbStockBranch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setText("Branch:");

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));
        jPanel18.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbStockSupplier.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        cbStockSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setText("Supplier:");

        jPanel19.setBackground(new java.awt.Color(0, 0, 0));
        jPanel19.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnAddStock.setBackground(new java.awt.Color(144, 202, 249));
        btnAddStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddStockMouseClicked(evt);
            }
        });

        lblAdd3.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd3.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd3.setText("Add");

        javax.swing.GroupLayout btnAddStockLayout = new javax.swing.GroupLayout(btnAddStock);
        btnAddStock.setLayout(btnAddStockLayout);
        btnAddStockLayout.setHorizontalGroup(
            btnAddStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddStockLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd3)
                .addGap(25, 25, 25))
        );
        btnAddStockLayout.setVerticalGroup(
            btnAddStockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddStockLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAdd3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlStockImportLayout = new javax.swing.GroupLayout(pnlStockImport);
        pnlStockImport.setLayout(pnlStockImportLayout);
        pnlStockImportLayout.setHorizontalGroup(
            pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockImportLayout.createSequentialGroup()
                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addContainerGap(1202, Short.MAX_VALUE)
                        .addComponent(btnAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                            .addComponent(txtStockName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtStockQty)
                                                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockImportLayout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                            .addComponent(cbStockCompany, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18))
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(194, 194, 194)))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dpStockExpired, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                                            .addComponent(txtStockAlertQty, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cbStockBranch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
                                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 393, Short.MAX_VALUE)))
                                        .addGap(18, 18, 18))
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(207, 207, 207)))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbStockCate, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addComponent(txtStockPrice, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbStockSupplier, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 222, Short.MAX_VALUE))
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addGap(25, 25, 25))
        );
        pnlStockImportLayout.setVerticalGroup(
            pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockImportLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, 0)
                        .addComponent(cbStockBranch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(25, 25, 25)
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(jLabel10))
                            .addComponent(jLabel12))
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtStockName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dpStockExpired, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtStockQty, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(pnlStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(0, 0, 0)
                                .addComponent(txtStockAlertQty, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlStockImportLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(0, 0, 0)
                                .addComponent(txtStockPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel16)
                        .addGap(0, 0, 0)
                        .addComponent(cbStockCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockImportLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, 0)
                        .addComponent(cbStockSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnAddStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        dynamicStockPane.add(pnlStockImport, "card2");

        pnlStockList.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblStock.setAutoCreateRowSorter(true);
        tblStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblStock.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblStock.setForeground(new java.awt.Color(255, 255, 255));
        tblStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Quantity", "Measure", "Stock Category", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
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
        tblStock.setGridColor(new java.awt.Color(255, 255, 255));
        tblStock.setOpaque(false);
        tblStock.setRowHeight(34);
        jScrollPane2.setViewportView(tblStock);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Search:");

        txtSearchStock.setBackground(new java.awt.Color(234, 234, 234));
        txtSearchStock.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchStock.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchStockActionPerformed(evt);
            }
        });
        txtSearchStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchStockKeyPressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1324, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlStockListLayout = new javax.swing.GroupLayout(pnlStockList);
        pnlStockList.setLayout(pnlStockListLayout);
        pnlStockListLayout.setHorizontalGroup(
            pnlStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSearchStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1324, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1324, Short.MAX_VALUE)
            .addComponent(jLabel5)
        );
        pnlStockListLayout.setVerticalGroup(
            pnlStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockListLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(0, 0, 0)
                .addComponent(txtSearchStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        dynamicStockPane.add(pnlStockList, "card2");

        pnlStockCate.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 42)); // NOI18N
        jLabel6.setText("STOCK CATEGORY");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setText("Name:");

        txtStockCateName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockCateName.setBorder(null);
        txtStockCateName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockCateName.setPreferredSize(new java.awt.Dimension(100, 36));
        txtStockCateName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockCateNameFocusLost(evt);
            }
        });
        txtStockCateName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockCateNameActionPerformed(evt);
            }
        });

        jPanel20.setBackground(new java.awt.Color(0, 0, 0));
        jPanel20.setPreferredSize(new java.awt.Dimension(300, 3));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        btnAddStock1.setBackground(new java.awt.Color(144, 202, 249));
        btnAddStock1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddStock1MouseClicked(evt);
            }
        });

        lblAdd4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd4.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd4.setText("Add");

        javax.swing.GroupLayout btnAddStock1Layout = new javax.swing.GroupLayout(btnAddStock1);
        btnAddStock1.setLayout(btnAddStock1Layout);
        btnAddStock1Layout.setHorizontalGroup(
            btnAddStock1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddStock1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd4)
                .addGap(25, 25, 25))
        );
        btnAddStock1Layout.setVerticalGroup(
            btnAddStock1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAdd4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txtStockCateDesc.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtStockCateDesc.setBorder(null);
        txtStockCateDesc.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtStockCateDesc.setPreferredSize(new java.awt.Dimension(100, 36));
        txtStockCateDesc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStockCateDescFocusLost(evt);
            }
        });
        txtStockCateDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockCateDescActionPerformed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel29.setText("Description:");

        jPanel30.setBackground(new java.awt.Color(0, 0, 0));
        jPanel30.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblStockCate.setAutoCreateRowSorter(true);
        tblStockCate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblStockCate.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblStockCate.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Description", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStockCate.setGridColor(new java.awt.Color(255, 255, 255));
        tblStockCate.setRowHeight(34);
        jScrollPane3.setViewportView(tblStockCate);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Search:");

        txtSearchStock1.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        txtSearchStock1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        txtSearchStock1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchStock1KeyPressed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1274, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jSeparator1.setBackground(new java.awt.Color(234, 234, 234));
        jSeparator1.setForeground(new java.awt.Color(234, 234, 234));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel8.setText("List");

        javax.swing.GroupLayout pnlStockCateLayout = new javax.swing.GroupLayout(pnlStockCate);
        pnlStockCate.setLayout(pnlStockCateLayout);
        pnlStockCateLayout.setHorizontalGroup(
            pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStockCateLayout.createSequentialGroup()
                        .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addGroup(pnlStockCateLayout.createSequentialGroup()
                                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                                        .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(194, 194, 194))
                                    .addComponent(txtStockCateName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                                    .addComponent(txtStockCateDesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(btnAddStock1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtSearchStock1, javax.swing.GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1274, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlStockCateLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(25, 25, 25))
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnlStockCateLayout.setVerticalGroup(
            pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel6)
                .addGap(25, 25, 25)
                .addGroup(pnlStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, 0)
                        .addComponent(txtStockCateName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlStockCateLayout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addGap(0, 0, 0)
                        .addComponent(txtStockCateDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddStock1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(0, 0, 0)
                .addComponent(txtSearchStock1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        dynamicStockPane.add(pnlStockCate, "card2");

        btnStockImport.setBackground(new java.awt.Color(144, 202, 249));
        btnStockImport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockImportMouseClicked(evt);
            }
        });

        btnUserIcon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/plus_40px.png"))); // NOI18N

        btnUserLbl4.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl4.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl4.setText("IMPORT");

        javax.swing.GroupLayout btnStockImportLayout = new javax.swing.GroupLayout(btnStockImport);
        btnStockImport.setLayout(btnStockImportLayout);
        btnStockImportLayout.setHorizontalGroup(
            btnStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockImportLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl4)
                .addGap(25, 25, 25))
        );
        btnStockImportLayout.setVerticalGroup(
            btnStockImportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnUserIcon4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockDel.setBackground(new java.awt.Color(200, 35, 51));
        btnStockDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockDelMouseClicked(evt);
            }
        });

        btnUserIcon7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_40px.png"))); // NOI18N

        btnUserLbl7.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl7.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl7.setText("DELETE");

        javax.swing.GroupLayout btnStockDelLayout = new javax.swing.GroupLayout(btnStockDel);
        btnStockDel.setLayout(btnStockDelLayout);
        btnStockDelLayout.setHorizontalGroup(
            btnStockDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockDelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl7)
                .addGap(25, 25, 25))
        );
        btnStockDelLayout.setVerticalGroup(
            btnStockDelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockDelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockCate.setBackground(new java.awt.Color(0, 0, 0));
        btnStockCate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockCateMouseClicked(evt);
            }
        });

        btnUserIcon9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/category_40px.png"))); // NOI18N

        btnUserLbl9.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl9.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl9.setText("STOCK CATEGORIES");

        javax.swing.GroupLayout btnStockCateLayout = new javax.swing.GroupLayout(btnStockCate);
        btnStockCate.setLayout(btnStockCateLayout);
        btnStockCateLayout.setHorizontalGroup(
            btnStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockCateLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl9)
                .addGap(25, 25, 25))
        );
        btnStockCateLayout.setVerticalGroup(
            btnStockCateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockCateLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btnUserIcon9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(btnUserLbl9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        btnStockList.setBackground(new java.awt.Color(0, 0, 0));
        btnStockList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockListMouseClicked(evt);
            }
        });

        btnUserIcon11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/list_40px.png"))); // NOI18N

        btnUserLbl11.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        btnUserLbl11.setForeground(new java.awt.Color(255, 255, 255));
        btnUserLbl11.setText("LIST");

        javax.swing.GroupLayout btnStockListLayout = new javax.swing.GroupLayout(btnStockList);
        btnStockList.setLayout(btnStockListLayout);
        btnStockListLayout.setHorizontalGroup(
            btnStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnStockListLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnUserIcon11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUserLbl11)
                .addGap(25, 25, 25))
        );
        btnStockListLayout.setVerticalGroup(
            btnStockListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUserLbl11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btnStockListLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnUserIcon11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout stockPanelLayout = new javax.swing.GroupLayout(stockPanel);
        stockPanel.setLayout(stockPanelLayout);
        stockPanelLayout.setHorizontalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dynamicStockPane, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(stockPanelLayout.createSequentialGroup()
                        .addComponent(btnStockImport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockDel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnStockCate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnStockList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        stockPanelLayout.setVerticalGroup(
            stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnStockEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockImport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockDel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockCate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnStockList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(dynamicStockPane)
                .addGap(25, 25, 25))
        );

        dynamicPanel.add(stockPanel, "card6");

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
        showCompanies(getComList());
        JTableHeader header = tblCom.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        
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
        
        dynamicStockPane.removeAll();
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        dynamicStockPane.add(pnlStockList);
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        
        JTableHeader header = tblStock.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        AppHelper.setColWidth(tblStock, 5, 0);
        
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
        dynamicPanel.add(dashPanel);
        dynamicPanel.repaint();
        dynamicPanel.revalidate();
        dynamicLabel.setFont(new java.awt.Font("Segoe UI", 0, 36));
        dynamicLabel.setForeground(new java.awt.Color(255, 255, 255));
        dynamicLabel.setText("SUPPLIER");
        
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
            txtSearchUser.setEditable(true);
        else
            txtSearchUser.setEditable(false);
        showUsers(searchUser(txtSearchUser.getText().trim()));
    }//GEN-LAST:event_txtSearchUserKeyPressed

    private void btnStockImportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockImportMouseClicked
        // TODO add your handling code here:
        dynamicStockPane.removeAll();
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        dynamicStockPane.add(pnlStockImport);
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
    }//GEN-LAST:event_btnStockImportMouseClicked

    private void btnStockEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockEditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStockEditMouseClicked

    private void btnStockDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockDelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnStockDelMouseClicked

    private void btnStockCateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockCateMouseClicked
        // TODO add your handling code here:
        dynamicStockPane.removeAll();
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        dynamicStockPane.add(pnlStockCate);
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        
        JTableHeader header = tblStockCate.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        AppHelper.setColWidth(tblStockCate, 3, 0);
    }//GEN-LAST:event_btnStockCateMouseClicked

    private void txtSearchStockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchStockKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchStockKeyPressed

    private void txtStockNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockNameFocusLost
        // TODO add your handling code here:
        int max = 25;
        if(!txtStockName.getText().trim().equals("") && !AppHelper.isMatchLength("max", max, txtStockName.getText().trim().length())) {
            AppHelper.errorMessage("name", max);
            txtStockName.requestFocus();
        }
    }//GEN-LAST:event_txtStockNameFocusLost

    private void txtStockNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockNameActionPerformed

    private void txtStockQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockQtyFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockQtyFocusLost

    private void txtStockQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockQtyActionPerformed

    private void txtStockAlertQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockAlertQtyFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockAlertQtyFocusLost

    private void txtStockAlertQtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockAlertQtyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockAlertQtyActionPerformed

    private void txtStockPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockPriceFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockPriceFocusLost

    private void txtStockPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockPriceActionPerformed

    private void btnAddStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddStockMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddStockMouseClicked

    private void btnStockListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockListMouseClicked
        // TODO add your handling code here:
        dynamicStockPane.removeAll();
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        dynamicStockPane.add(pnlStockList);
        dynamicStockPane.repaint();
        dynamicStockPane.revalidate();
        AppHelper.setColWidth(tblStock, 5, 0);
    }//GEN-LAST:event_btnStockListMouseClicked

    private void txtStockCateNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockCateNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateNameFocusLost

    private void txtStockCateNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockCateNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateNameActionPerformed

    private void btnAddStock1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddStock1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddStock1MouseClicked

    private void txtStockCateDescFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStockCateDescFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateDescFocusLost

    private void txtStockCateDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockCateDescActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCateDescActionPerformed

    private void txtSearchStock1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchStock1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchStock1KeyPressed

    private void btnComEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComEditMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "update")){
            AppHelper.permissionMessage();
        } else {
            int row = tblCom.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any user first to edit!");
            else {
                dynamicComPnl.removeAll();
                dynamicComPnl.repaint();
                dynamicComPnl.revalidate();
                dynamicComPnl.add(pnlComUpdate);
                dynamicComPnl.repaint();
                dynamicComPnl.revalidate();
                int id = (int)tblCom.getValueAt(row, 6);
                new UserActions("edit", userId).setVisible(true);   
            }
        }   
    }//GEN-LAST:event_btnComEditMouseClicked

    private void txtSearchComKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchComKeyPressed
        // TODO add your handling code here:
        if(AppHelper.alphOnly(evt))
            txtSearchCom.setEditable(true);
        else
            txtSearchCom.setEditable(false);
        showCompanies(searchCom(txtSearchCom.getText().toLowerCase().trim()));
    }//GEN-LAST:event_txtSearchComKeyPressed

    private void btnComAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComAddMouseClicked
        // TODO add your handling code here:
        if(AppHelper.currentUserCan(roleId, "companies", "create")) {
            dynamicComPnl.removeAll();
            dynamicComPnl.repaint();
            dynamicComPnl.revalidate();
            dynamicComPnl.add(pnlComAdd);
            dynamicComPnl.repaint();
            dynamicComPnl.revalidate();
        }
    }//GEN-LAST:event_btnComAddMouseClicked

    private void btnComDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComDelMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "delete")){
            AppHelper.permissionMessage();
        } else {
            int row = tblCom.getSelectedRow();
            if(row < 0)
                JOptionPane.showMessageDialog(null, "Please select any companies first to delete!");
            else {
                int id = (int)tblCom.getValueAt(row, 5);
                int res = JOptionPane.showConfirmDialog(
                    this, 
                    "Are you sure you want to delete this?", 
                    "Confirm message", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.QUESTION_MESSAGE
                );
                if(res == JOptionPane.YES_OPTION) {
                    Company myCom = new Company();
                    myCom.delete(id);
                    showCompanies(getComList());
                }
            }
        }
    }//GEN-LAST:event_btnComDelMouseClicked

    private void btnComListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComListMouseClicked
        // TODO add your handling code here:
        dynamicComPnl.removeAll();
        dynamicComPnl.repaint();
        dynamicComPnl.revalidate();
        dynamicComPnl.add(pnlComList);
        dynamicComPnl.repaint();
        dynamicComPnl.revalidate();
        showCompanies(getComList());
    }//GEN-LAST:event_btnComListMouseClicked

    private void btnAddComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddComMouseClicked
        // TODO add your handling code here:
        if(!AppHelper.currentUserCan(roleId, "companies", "create")) {
            AppHelper.permissionMessage();
        } else {
            String name = txtComName.getText().trim();
            String email = txtComEmail.getText().trim();
            String address = txtComAddres.getText().trim();
            String phone = txtComPhone.getText().trim();

            if(name.equals("") || address.equals("") || phone.equals("") || email.equals("")) {
                AppHelper.fieldRequiredMsg();
                txtComName.requestFocus();
            } else {
                myCompany.setName(name);
                myCompany.setEmail(email);
                myCompany.setAddress(address);
                myCompany.setPhone(phone);
                myCompany.setUserId(userId);
                myCompany.insert();
                txtComName.setText("");
                txtComAddres.setText("");
                txtComEmail.setText("");
                txtComPhone.setText("");
                txtComName.requestFocus();
            }  
        }  
    }//GEN-LAST:event_btnAddComMouseClicked

    private void txtComNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComNameActionPerformed

    private void txtComNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComNameFocusLost

    private void txtSearchComActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchComActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchComActionPerformed

    private void txtComEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComEmailFocusLost
        // TODO add your handling code here:
        if(!txtComEmail.getText().trim().equals("") && !AppHelper.isValid(txtComEmail.getText().trim())){
            AppHelper.errorMessage("email", 0);
            txtComEmail.requestFocus();
        }
    }//GEN-LAST:event_txtComEmailFocusLost

    private void txtComEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComEmailActionPerformed

    private void txtComPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComPhoneFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtComPhoneFocusLost

    private void txtComPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComPhoneActionPerformed

    private void cbComUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbComUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbComUserActionPerformed

    private void txtSearchStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchStockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchStockActionPerformed

    private void txtSearchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchUserActionPerformed

    private void txtUpdateNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUpdateNameFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdateNameFocusLost

    private void txtUpdateNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUpdateNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUpdateNameActionPerformed

    private void cbComUser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbComUser1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbComUser1ActionPerformed

    private void btnUpdateComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateComMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateComMouseClicked

    private void txtComEmail1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComEmail1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComEmail1FocusLost

    private void txtComEmail1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComEmail1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComEmail1ActionPerformed

    private void txtComPhone1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComPhone1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComPhone1FocusLost

    private void txtComPhone1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtComPhone1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtComPhone1ActionPerformed

    private void tblComMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComMouseClicked
        // TODO add your handling code here:
//        int row = tblCom.getSelectedRow();
//        DefaultTableModel model = (DefaultTableModel)tblCom.getModel();
//        txt.setText(model.getValueAt( row, 1).toString());
//        txtPhone.setText(model.getValueAt( row, 2).toString());
//        txtEmail.setText(model.getValueAt( row, 3).toString());
//        txtAddress.setText(model.getValueAt( row, 4).toString());
//        cbCompanies.setSelectedItem(model.getValueAt( row, 5).toString());
//        System.out.println(row);
//        if(row >= 0){
//            btnUpdate.setEnabled(true);
//        }
    }//GEN-LAST:event_tblComMouseClicked

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
    private javax.swing.JPanel LineCbUser;
    private javax.swing.JPanel LineCbUser1;
    private javax.swing.JPanel LineEmail;
    private javax.swing.JPanel LineEmail1;
    private javax.swing.JPanel LineName;
    private javax.swing.JPanel LineName1;
    private javax.swing.JPanel LinePhone;
    private javax.swing.JPanel LinePhone1;
    private javax.swing.JPanel LineTxtAddress;
    private javax.swing.JPanel LineTxtAddress1;
    private javax.swing.JPanel background;
    private javax.swing.JPanel braActive;
    private javax.swing.JPanel braBlink;
    private javax.swing.JLayeredPane braLayer;
    private javax.swing.JLabel branch;
    private javax.swing.JLabel branchIcon;
    private javax.swing.JPanel branchLabel;
    private javax.swing.JPanel branchPanel;
    private javax.swing.JPanel btnAddCom;
    private javax.swing.JPanel btnAddStock;
    private javax.swing.JPanel btnAddStock1;
    private javax.swing.JPanel btnAddUser;
    private javax.swing.JPanel btnChangePass;
    private javax.swing.JPanel btnComAdd;
    private javax.swing.JPanel btnComDel;
    private javax.swing.JPanel btnComEdit;
    private javax.swing.JPanel btnComList;
    private javax.swing.JPanel btnDelUser;
    private javax.swing.JPanel btnEditUser;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPermission;
    private javax.swing.JPanel btnRole;
    private javax.swing.JLabel btnRoleIcon;
    private javax.swing.JLabel btnRoleIcon1;
    private javax.swing.JLabel btnRoleIcon2;
    private javax.swing.JLabel btnRoleLbl;
    private javax.swing.JLabel btnRoleLbl1;
    private javax.swing.JPanel btnStockCate;
    private javax.swing.JPanel btnStockDel;
    private javax.swing.JPanel btnStockEdit;
    private javax.swing.JPanel btnStockImport;
    private javax.swing.JPanel btnStockList;
    private javax.swing.JPanel btnUpdateCom;
    private javax.swing.JLabel btnUserIcon;
    private javax.swing.JLabel btnUserIcon1;
    private javax.swing.JLabel btnUserIcon11;
    private javax.swing.JLabel btnUserIcon2;
    private javax.swing.JLabel btnUserIcon3;
    private javax.swing.JLabel btnUserIcon4;
    private javax.swing.JLabel btnUserIcon6;
    private javax.swing.JLabel btnUserIcon7;
    private javax.swing.JLabel btnUserIcon9;
    private javax.swing.JLabel btnUserIconComAdd;
    private javax.swing.JLabel btnUserIconComEdit;
    private javax.swing.JLabel btnUserIconComList;
    private javax.swing.JLabel btnUserIconDel;
    private javax.swing.JLabel btnUserLbl;
    private javax.swing.JLabel btnUserLbl1;
    private javax.swing.JLabel btnUserLbl11;
    private javax.swing.JLabel btnUserLbl2;
    private javax.swing.JLabel btnUserLbl3;
    private javax.swing.JLabel btnUserLbl4;
    private javax.swing.JLabel btnUserLbl6;
    private javax.swing.JLabel btnUserLbl7;
    private javax.swing.JLabel btnUserLbl9;
    private javax.swing.JLabel btnUserLblComAdd;
    private javax.swing.JLabel btnUserLblComEdit;
    private javax.swing.JLabel btnUserLblComList;
    private javax.swing.JLabel btnUserLblDel;
    private javax.swing.JPanel btnUserRefresh;
    private javax.swing.JComboBox<String> cbComUser;
    private javax.swing.JComboBox<String> cbComUser1;
    private javax.swing.JComboBox<String> cbStockBranch;
    private javax.swing.JComboBox<String> cbStockCate;
    private javax.swing.JComboBox<String> cbStockCompany;
    private javax.swing.JComboBox<String> cbStockSupplier;
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
    private com.toedter.calendar.JDateChooser dpStockExpired;
    private javax.swing.JLayeredPane dynamicComPnl;
    private javax.swing.JLabel dynamicLabel;
    private javax.swing.JLayeredPane dynamicPanel;
    private javax.swing.JLayeredPane dynamicStockPane;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbEmail1;
    private javax.swing.JLabel lblAdd3;
    private javax.swing.JLabel lblAdd4;
    private javax.swing.JLabel lblAddCom;
    private javax.swing.JLabel lblAddCompany;
    private javax.swing.JLabel lblAddresss;
    private javax.swing.JLabel lblAddresss1;
    private javax.swing.JLabel lblCbUser;
    private javax.swing.JLabel lblCbUser1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblName1;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JLabel lblPhone1;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblUpdateCom;
    private javax.swing.JLabel lblUpdateCompany;
    private javax.swing.JPanel lineSearch;
    private javax.swing.JPanel pnlComAdd;
    private javax.swing.JPanel pnlComList;
    private javax.swing.JPanel pnlComUpdate;
    private javax.swing.JPanel pnlStockCate;
    private javax.swing.JPanel pnlStockImport;
    private javax.swing.JPanel pnlStockList;
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
    private javax.swing.JPanel supplierPnl;
    private javax.swing.JTable tblCom;
    private javax.swing.JTable tblStock;
    private javax.swing.JTable tblStockCate;
    private javax.swing.JTextArea txtComAddres;
    private javax.swing.JTextArea txtComAddres1;
    private javax.swing.JTextField txtComEmail;
    private javax.swing.JTextField txtComEmail1;
    private javax.swing.JTextField txtComName;
    private javax.swing.JTextField txtComPhone;
    private javax.swing.JTextField txtComPhone1;
    private javax.swing.JTextField txtSearchCom;
    private javax.swing.JTextField txtSearchStock;
    private javax.swing.JTextField txtSearchStock1;
    private javax.swing.JTextField txtSearchUser;
    private javax.swing.JTextField txtStockAlertQty;
    private javax.swing.JTextField txtStockCateDesc;
    private javax.swing.JTextField txtStockCateName;
    private javax.swing.JTextField txtStockName;
    private javax.swing.JTextField txtStockPrice;
    private javax.swing.JTextField txtStockQty;
    private javax.swing.JTextField txtUpdateName;
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
}
