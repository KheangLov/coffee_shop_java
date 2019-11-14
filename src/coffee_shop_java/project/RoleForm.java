/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Helper.AppHelper;
import coffee_shop_java.project.Model.DbConn;
import coffee_shop_java.project.Model.Role;
import coffee_shop_java.project.Model.RolePermission;
import java.awt.Color;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author KHEANG
 */
public class RoleForm extends javax.swing.JFrame {

    /**
     * Creates new form RoleForm
     */
    public RoleForm() {
        initComponents();
        showRole();
        showRolePermission();
        addToRole();
        addToPerName();
        addToAction(String.valueOf(cbPermName.getSelectedItem()));
        cbPermName.setBackground(new Color(0, 0, 0, 0));
        cbPermAction.setBackground(new Color(0, 0, 0, 0));
    }
    
    Role myRole = new Role();
    RolePermission myRolePerm = new RolePermission();

    public ArrayList<Role> getAllRoles() {
        PreparedStatement stmt = null;
        ResultSet rs;
        ArrayList<Role> list = new ArrayList<>();
        String sql = "SELECT * FROM `roles` ORDER BY `name`";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            Role role;
            int i = 0;
            while(rs.next()){
                i++;
                role = new Role(i, rs.getString("name"), rs.getString("description"), rs.getInt("id"));
                list.add(role);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return list;
    }
    
    public void showRole() {
        PreparedStatement stmt = null;
        ResultSet rs;
        tblRole.setModel(new DefaultTableModel(null, new String[]{"#", "Name", "Description", "id"}));
        ArrayList<Role> list = getAllRoles();
        DefaultTableModel model = (DefaultTableModel) tblRole.getModel();
        Object[] rows = new Object[4];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getName();
            rows[2] = list.get(i).getDescription();
            rows[3] = list.get(i).getId();
            model.addRow(rows);
        }
    }
    
    public ArrayList<RolePermission> getAllRolePer() {
        ArrayList<RolePermission> list = new ArrayList<>();
        String sql = "SELECT `role_permissions`.`id` AS role_per_id, `roles`.`name` AS role_name, "
                + "CONCAT(`permissions`.`name`, '_', `permissions`.`action`) AS permission_name "
                + "FROM `role_permissions` "
                + "INNER JOIN `roles` ON `role_permissions`.`role_id` = `roles`.`id` "
                + "INNER JOIN `permissions` ON `role_permissions`.`permission_id` = `permissions`.`id`";
        try {
            ResultSet rs = AppHelper.selectQuery(sql);
            RolePermission rolePer;
            int i = 0;
            while(rs.next()){
                i++;
                rolePer = new RolePermission(
                    i, 
                    rs.getString("role_name"), 
                    rs.getString("permission_name"), 
                    rs.getInt("role_per_id")
                );
                list.add(rolePer);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return list;
    }
    
    public void showRolePermission() {
        tblRolePer.setModel(new DefaultTableModel(null, new String[]{"#", "Role", "Permission", "id"}));
        ArrayList<RolePermission> list = getAllRolePer();
        DefaultTableModel model = (DefaultTableModel) tblRolePer.getModel();
        Object[] rows = new Object[4];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getRole_name();
            rows[2] = list.get(i).getPermission_name();
            rows[3] = list.get(i).getId();
            model.addRow(rows);
        }
    }
    
    public void addToRole() {
        String sql = "SELECT * FROM `roles` ORDER BY `name`";
        try {
            ResultSet rs = AppHelper.selectQuery(sql);
            while(rs.next())
                cbRole.addItem(rs.getString("name"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void addToPerName() {
        PreparedStatement stmt = null;
        ResultSet rs;
        cbPermName.removeAllItems();
        String sql = "SELECT DISTINCT `name` FROM permissions ORDER BY `name`";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()) {
                cbPermName.addItem(AppHelper.toCapitalize(rs.getString("name")));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void addToAction(String perName) {
        PreparedStatement stmt = null;
        ResultSet rs;
        cbPermAction.removeAllItems();
        String sql = "SELECT `action` FROM `permissions` WHERE LOWER(`name`) = ?";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            stmt.setString(1, perName.toLowerCase());
            rs = stmt.executeQuery();
            while(rs.next()) {
                cbPermAction.addItem(rs.getString("action"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void getRoleId(String combo) {
        PreparedStatement stmt = null;
        ResultSet rs;
        String sql = "SELECT * FROM `roles` "
            + "WHERE LOWER(`name`) = '" + combo + "'";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()) {
                myRolePerm.setRole_id(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void getPermissionId(String name, String action) {
        PreparedStatement stmt = null;
        ResultSet rs;
        String sql = "SELECT * FROM `permissions` "
            + "WHERE LOWER(`name`) = '" + name + "' "
            + "AND LOWER(`action`) = '" + action + "'";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            if(rs.next()) {
                myRolePerm.setPermission_id(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlNavbar = new javax.swing.JPanel();
        exitIcon = new javax.swing.JLabel();
        lblFormName = new javax.swing.JLabel();
        pnlWrapper = new javax.swing.JPanel();
        pnlFormAdd = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnUpdateRole = new javax.swing.JPanel();
        lblAdd = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        txtDesc = new javax.swing.JTextArea();
        btnAddRole = new javax.swing.JPanel();
        lblAdd2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRole = new javax.swing.JTable();
        pnlAddRolePer = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnAddRolePer = new javax.swing.JPanel();
        lblAdd1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cbRole = new javax.swing.JComboBox<>();
        cbPermName = new javax.swing.JComboBox<>();
        cbPermAction = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnUpdateRolePer = new javax.swing.JPanel();
        lblAdd3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRolePer = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowIconified(java.awt.event.WindowEvent evt) {
                formWindowIconified(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        pnlNavbar.setBackground(new java.awt.Color(102, 51, 0));
        pnlNavbar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        exitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/icons8_delete_sign_50px.png"))); // NOI18N
        exitIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitIconMouseClicked(evt);
            }
        });
        pnlNavbar.add(exitIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 0, -1, 60));

        lblFormName.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        lblFormName.setForeground(new java.awt.Color(255, 255, 255));
        lblFormName.setText("ROLE");
        pnlNavbar.add(lblFormName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 60));

        pnlWrapper.setBackground(new java.awt.Color(234, 234, 234));
        pnlWrapper.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 51, 0), 5));
        pnlWrapper.setPreferredSize(new java.awt.Dimension(1083, 900));

        pnlFormAdd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Name:");

        btnUpdateRole.setBackground(new java.awt.Color(19, 132, 150));
        btnUpdateRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateRoleMouseClicked(evt);
            }
        });

        lblAdd.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd.setText("UPDATE");

        javax.swing.GroupLayout btnUpdateRoleLayout = new javax.swing.GroupLayout(btnUpdateRole);
        btnUpdateRole.setLayout(btnUpdateRoleLayout);
        btnUpdateRoleLayout.setHorizontalGroup(
            btnUpdateRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateRoleLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd)
                .addGap(25, 25, 25))
        );
        btnUpdateRoleLayout.setVerticalGroup(
            btnUpdateRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateRoleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAdd)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Description:");

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 3));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        txtName.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtName.setBorder(null);
        txtName.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        txtDesc.setColumns(20);
        txtDesc.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtDesc.setRows(5);
        txtDesc.setBorder(null);

        btnAddRole.setBackground(new java.awt.Color(144, 202, 249));
        btnAddRole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddRoleMouseClicked(evt);
            }
        });

        lblAdd2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd2.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd2.setText("ADD");

        javax.swing.GroupLayout btnAddRoleLayout = new javax.swing.GroupLayout(btnAddRole);
        btnAddRole.setLayout(btnAddRoleLayout);
        btnAddRoleLayout.setHorizontalGroup(
            btnAddRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddRoleLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd2)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        btnAddRoleLayout.setVerticalGroup(
            btnAddRoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddRoleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAdd2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlFormAddLayout = new javax.swing.GroupLayout(pnlFormAdd);
        pnlFormAdd.setLayout(pnlFormAddLayout);
        pnlFormAddLayout.setHorizontalGroup(
            pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(txtName)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFormAddLayout.createSequentialGroup()
                        .addComponent(btnAddRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdateRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDesc, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        pnlFormAddLayout.setVerticalGroup(
            pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txtDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdateRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblRole.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        tblRole.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblRole.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
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
        tblRole.setGridColor(new java.awt.Color(255, 255, 255));
        tblRole.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblRole.setOpaque(false);
        tblRole.setRowHeight(30);
        jScrollPane1.setViewportView(tblRole);
        tblRole.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnlAddRolePer.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(250, 3));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Roles:");

        btnAddRolePer.setBackground(new java.awt.Color(144, 202, 249));
        btnAddRolePer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddRolePerMouseClicked(evt);
            }
        });

        lblAdd1.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd1.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd1.setText("ADD");

        javax.swing.GroupLayout btnAddRolePerLayout = new javax.swing.GroupLayout(btnAddRolePer);
        btnAddRolePer.setLayout(btnAddRolePerLayout);
        btnAddRolePerLayout.setHorizontalGroup(
            btnAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddRolePerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd1)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        btnAddRolePerLayout.setVerticalGroup(
            btnAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddRolePerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAdd1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Permission Name:");

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setPreferredSize(new java.awt.Dimension(250, 3));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        cbRole.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbRole.setLightWeightPopupEnabled(false);
        cbRole.setOpaque(false);
        cbRole.setBackground(new Color(0, 0, 0, 0));
        cbRole.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbRoleItemStateChanged(evt);
            }
        });

        cbPermName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbPermName.setLightWeightPopupEnabled(false);
        cbPermName.setOpaque(false);
        cbPermName.setBackground(new Color(0, 0, 0, 0));
        cbPermName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbPermNameItemStateChanged(evt);
            }
        });

        cbPermAction.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbPermAction.setLightWeightPopupEnabled(false);
        cbPermAction.setOpaque(false);
        cbPermName.setBackground(new Color(0, 0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Permission Action:");

        btnUpdateRolePer.setBackground(new java.awt.Color(19, 132, 150));
        btnUpdateRolePer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnUpdateRolePerMouseClicked(evt);
            }
        });

        lblAdd3.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblAdd3.setForeground(new java.awt.Color(255, 255, 255));
        lblAdd3.setText("UPDATE");

        javax.swing.GroupLayout btnUpdateRolePerLayout = new javax.swing.GroupLayout(btnUpdateRolePer);
        btnUpdateRolePer.setLayout(btnUpdateRolePerLayout);
        btnUpdateRolePerLayout.setHorizontalGroup(
            btnUpdateRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateRolePerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblAdd3)
                .addGap(25, 25, 25))
        );
        btnUpdateRolePerLayout.setVerticalGroup(
            btnUpdateRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUpdateRolePerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAdd3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlAddRolePerLayout = new javax.swing.GroupLayout(pnlAddRolePer);
        pnlAddRolePer.setLayout(pnlAddRolePerLayout);
        pnlAddRolePerLayout.setHorizontalGroup(
            pnlAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddRolePerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(pnlAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                        .addComponent(cbRole, 0, 258, Short.MAX_VALUE)
                        .addComponent(cbPermAction, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                    .addComponent(cbPermName, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAddRolePerLayout.createSequentialGroup()
                        .addComponent(btnAddRolePer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnUpdateRolePer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );
        pnlAddRolePerLayout.setVerticalGroup(
            pnlAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddRolePerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlAddRolePerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnUpdateRolePer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlAddRolePerLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, 0)
                        .addComponent(cbRole, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(0, 0, 0)
                        .addComponent(cbPermName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addGap(0, 0, 0)
                        .addComponent(cbPermAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAddRolePer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));

        tblRolePer.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblRolePer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#", "Role", "Permission", "id"
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
        tblRolePer.setGridColor(new java.awt.Color(255, 255, 255));
        tblRolePer.setOpaque(false);
        tblRolePer.setRowHeight(30);
        jScrollPane2.setViewportView(tblRolePer);
        tblRolePer.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout pnlWrapperLayout = new javax.swing.GroupLayout(pnlWrapper);
        pnlWrapper.setLayout(pnlWrapperLayout);
        pnlWrapperLayout.setHorizontalGroup(
            pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWrapperLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlAddRolePer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFormAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );
        pnlWrapperLayout.setVerticalGroup(
            pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlWrapperLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlFormAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(25, 25, 25)
                .addGroup(pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlAddRolePer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlNavbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, 1101, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlNavbar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, 767, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitIconMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_exitIconMouseClicked

    private void btnUpdateRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateRoleMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateRoleMouseClicked

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void btnAddRolePerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddRolePerMouseClicked
        // TODO add your handling code here:
        PreparedStatement stmt = null;
        ResultSet rs;
        String role = String.valueOf(cbRole.getSelectedItem());
        String permName = String.valueOf(cbPermName.getSelectedItem());
        String permAction = String.valueOf(cbPermAction.getSelectedItem());
        if(role.equals("") || permName.equals("") || permAction.equals("")) {
            JOptionPane.showMessageDialog(null, "You can't add a blank role or permission name or permission action!");
        } else {
            getRoleId(role);
            getPermissionId(permName, permAction);
            Boolean checkExist = false;
            String sql = "SELECT * FROM `role_permissions` "
                    + "WHERE `role_id` = ? AND `permission_id` = ?";
            try {
                stmt = DbConn.getConnection().prepareStatement(sql);
                stmt.setInt(1, myRolePerm.getRole_id());
                stmt.setInt(2, myRolePerm.getPermission_id());
                rs = stmt.executeQuery();
                if(rs.next())
                    checkExist = true;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            if(checkExist.equals(true)) {
                JOptionPane.showMessageDialog(null, "Role permission already exist!");
                checkExist = false;
                cbRole.setSelectedIndex(0);
                cbPermName.setSelectedIndex(0);
                cbPermAction.setSelectedIndex(0);
            } else {
                myRolePerm.insert();
                showRolePermission();
                AppHelper.setColWidth(tblRolePer, 0, 50);
                AppHelper.setColWidth(tblRolePer, 3, 0);
                cbRole.setSelectedIndex(0);
                cbPermName.setSelectedIndex(0);
                cbPermAction.setSelectedIndex(0);
            }
        }
    }//GEN-LAST:event_btnAddRolePerMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        JTableHeader roleHeader = tblRole.getTableHeader();
        roleHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
        roleHeader.setOpaque(false);
        roleHeader.setForeground(Color.WHITE);
        roleHeader.setBackground(Color.black);
        AppHelper.setColWidth(tblRole, 3, 0);
        AppHelper.setColWidth(tblRole, 0, 50);
        
        JTableHeader rolePermHeader = tblRolePer.getTableHeader();
        rolePermHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
        rolePermHeader.setOpaque(false);
        rolePermHeader.setForeground(Color.WHITE);
        rolePermHeader.setBackground(Color.black);        
        AppHelper.setColWidth(tblRolePer, 3, 0);
        AppHelper.setColWidth(tblRolePer, 0, 50);
    }//GEN-LAST:event_formWindowOpened

    private void formWindowIconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowIconified
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowIconified

    private void cbRoleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbRoleItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbRoleItemStateChanged

    private void btnAddRoleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddRoleMouseClicked
        // TODO add your handling code here:
        PreparedStatement stmt = null;
        ResultSet rs;
        if(txtName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Name can't be blank!");
        } else {
            Boolean checkExist = false;
            String name = txtName.getText().trim();
            String description = txtDesc.getText().trim();
            String sql = "SELECT * FROM `roles` WHERE LOWER(`name`) = ?";
            try {
                stmt = DbConn.getConnection().prepareStatement(sql);
                stmt.setString(1, name.toLowerCase());
                rs = stmt.executeQuery();
                if(rs.next()) {
                    checkExist = true;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            if(checkExist.equals(true)) {
                JOptionPane.showMessageDialog(null, "Role already exist!");
                checkExist = false;
                txtName.setText("");
                txtDesc.setText("");
                txtName.requestFocus(true);
            } else {
                myRole.setName(name);
                myRole.setDescription(description);
                myRole.insert();
                showRole();
                AppHelper.setColWidth(tblRole, 0, 50);
                AppHelper.setColWidth(tblRole, 3, 0);
                txtName.setText("");
                txtDesc.setText("");
            }
        }
    }//GEN-LAST:event_btnAddRoleMouseClicked

    private void btnUpdateRolePerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUpdateRolePerMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateRolePerMouseClicked

    private void cbPermNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbPermNameItemStateChanged
        // TODO add your handling code here:
        addToAction(String.valueOf(cbPermName.getSelectedItem()));
    }//GEN-LAST:event_cbPermNameItemStateChanged

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RoleForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RoleForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAddRole;
    private javax.swing.JPanel btnAddRolePer;
    private javax.swing.JPanel btnUpdateRole;
    private javax.swing.JPanel btnUpdateRolePer;
    private javax.swing.JComboBox<String> cbPermAction;
    private javax.swing.JComboBox<String> cbPermName;
    private javax.swing.JComboBox<String> cbRole;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblAdd1;
    private javax.swing.JLabel lblAdd2;
    private javax.swing.JLabel lblAdd3;
    private javax.swing.JLabel lblFormName;
    private javax.swing.JPanel pnlAddRolePer;
    private javax.swing.JPanel pnlFormAdd;
    private javax.swing.JPanel pnlNavbar;
    private javax.swing.JPanel pnlWrapper;
    private javax.swing.JTable tblRole;
    private javax.swing.JTable tblRolePer;
    private javax.swing.JTextArea txtDesc;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
