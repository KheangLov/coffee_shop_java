/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Helper.AppHelper;
import coffee_shop_java.project.Model.DbConn;
import coffee_shop_java.project.Model.Permission;
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
public class PermissionForm extends javax.swing.JFrame {

    /**
     * Creates new form PermissionForm
     */
    public PermissionForm() {
        initComponents();
        showPermission();
        getAllTables();
        cbName.setBackground(new Color(0, 0, 0, 0));
    }
    
    PreparedStatement stmt = null;
    ResultSet rs;
    Permission myPerm = new Permission();
    
    public void getAllTables() {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema ='coffee_shop'";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while(rs.next()){
                cbName.addItem(rs.getString("table_name"));
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public ArrayList<Permission> getAllPermission(){
        ArrayList<Permission> list = new ArrayList<>();
        String sqlGetBranch = "SELECT * FROM `permissions`";
        try {
            stmt = DbConn.getConnection().prepareStatement(sqlGetBranch);
            rs = stmt.executeQuery();
            Permission permission;
            int i = 0;
            while(rs.next()){
                i++;
                permission = new Permission(i, rs.getString("name"), rs.getString("action"), rs.getInt("id"));
                list.add(permission);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return list;
    }
    
    public void showPermission(){
        tblPermission.setModel(new DefaultTableModel(null, new String[]{"#", "Name", "Action", "id"}));
        ArrayList<Permission> list = getAllPermission();
        DefaultTableModel model = (DefaultTableModel) tblPermission.getModel();
        Object[] rows = new Object[4];
        for(int i=0; i<list.size(); i++){
            rows[0] = list.get(i).getTblId();
            rows[1] = list.get(i).getName();
            rows[2] = list.get(i).getAction();
            rows[3] = list.get(i).getId();
            model.addRow(rows);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPermission = new javax.swing.JTable();
        pnlFormAdd = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnAddPer = new javax.swing.JPanel();
        lblPer = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtAction = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cbName = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1080, 376));
        addWindowListener(new java.awt.event.WindowAdapter() {
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
        pnlNavbar.add(exitIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 0, -1, 60));

        lblFormName.setFont(new java.awt.Font("Segoe UI", 0, 32)); // NOI18N
        lblFormName.setForeground(new java.awt.Color(255, 255, 255));
        lblFormName.setText("PERMISSION");
        pnlNavbar.add(lblFormName, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, 60));

        pnlWrapper.setBackground(new java.awt.Color(234, 234, 234));

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 0, true));

        tblPermission.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 0, true));
        tblPermission.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        tblPermission.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
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
                "#", "Name", "Action", "id"
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
        tblPermission.setGridColor(new java.awt.Color(255, 255, 255));
        tblPermission.setOpaque(false);
        tblPermission.setRowHeight(30);
        jScrollPane1.setViewportView(tblPermission);
        tblPermission.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        pnlFormAdd.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Name:");

        btnAddPer.setBackground(new java.awt.Color(144, 202, 249));
        btnAddPer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddPerMouseClicked(evt);
            }
        });

        lblPer.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        lblPer.setForeground(new java.awt.Color(255, 255, 255));
        lblPer.setText("ADD");

        javax.swing.GroupLayout btnAddPerLayout = new javax.swing.GroupLayout(btnAddPer);
        btnAddPer.setLayout(btnAddPerLayout);
        btnAddPerLayout.setHorizontalGroup(
            btnAddPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddPerLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblPer)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        btnAddPerLayout.setVerticalGroup(
            btnAddPerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAddPerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPer)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Action:");

        txtAction.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        txtAction.setBorder(null);
        txtAction.setMargin(new java.awt.Insets(2, 8, 2, 8));
        txtAction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtActionActionPerformed(evt);
            }
        });

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
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 3));

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

        cbName.setBackground(new java.awt.Color(102, 51, 255));
        cbName.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        cbName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 0));
        cbName.setLightWeightPopupEnabled(false);

        javax.swing.GroupLayout pnlFormAddLayout = new javax.swing.GroupLayout(pnlFormAdd);
        pnlFormAdd.setLayout(pnlFormAddLayout);
        pnlFormAddLayout.setHorizontalGroup(
            pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(txtAction)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .addComponent(cbName, 0, 267, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        pnlFormAddLayout.setVerticalGroup(
            pnlFormAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFormAddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(cbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(txtAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddPer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout pnlWrapperLayout = new javax.swing.GroupLayout(pnlWrapper);
        pnlWrapper.setLayout(pnlWrapperLayout);
        pnlWrapperLayout.setHorizontalGroup(
            pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWrapperLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(pnlFormAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1)
                .addGap(25, 25, 25))
        );
        pnlWrapperLayout.setVerticalGroup(
            pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlWrapperLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlWrapperLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlFormAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlNavbar, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
            .addComponent(pnlWrapper, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlNavbar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(pnlWrapper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exitIconMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_exitIconMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        JTableHeader header = tblPermission.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setOpaque(false);
        header.setForeground(Color.WHITE);
        header.setBackground(Color.black);
        AppHelper.setColWidth(tblPermission, 3, 0);
        AppHelper.setColWidth(tblPermission, 0, 50);
    }//GEN-LAST:event_formWindowOpened

    private void txtActionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtActionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtActionActionPerformed

    private void btnAddPerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddPerMouseClicked
        // TODO add your handling code here:
        String name = String.valueOf(cbName.getSelectedItem());
        String action = txtAction.getText().trim();
        
        if(name.equals("") || action.equals("")) {
            JOptionPane.showMessageDialog(null, "Name or Action can't be blank!");
        } else {
            myPerm.setName(name);
            myPerm.setAction(action);
            myPerm.insert();
            showPermission();
            AppHelper.setColWidth(tblPermission, 0, 50);
            AppHelper.setColWidth(tblPermission, 3, 0);
            cbName.setSelectedIndex(0);
            txtAction.setText("");
        }
    }//GEN-LAST:event_btnAddPerMouseClicked

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
            java.util.logging.Logger.getLogger(PermissionForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new PermissionForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAddPer;
    private javax.swing.JComboBox<String> cbName;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFormName;
    private javax.swing.JLabel lblPer;
    private javax.swing.JPanel pnlFormAdd;
    private javax.swing.JPanel pnlNavbar;
    private javax.swing.JPanel pnlWrapper;
    private javax.swing.JTable tblPermission;
    private javax.swing.JTextField txtAction;
    // End of variables declaration//GEN-END:variables
}
