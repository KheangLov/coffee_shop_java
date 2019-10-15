/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Model.DbConn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class CompanyForm extends javax.swing.JFrame {

    /**
     * Creates new form CompanyForm
     */
    public CompanyForm() {
        initComponents();
        String sql = "SELECT * FROM users";
        try {
            stmt = DbConn.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            int i = 0;
            while(rs.next()) {
                cbUsers.addItem(rs.getString("fullname"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }  
    }
    PreparedStatement stmt = null;
    ResultSet rs; 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabelcompanies = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jpanel2 = new javax.swing.JPanel();
        jLabelregistercompany = new javax.swing.JLabel();
        jLabelcname = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelcphonenumber = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabelcemail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        btnAdd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabelcaddress = new javax.swing.JLabel();
        cbUsers = new javax.swing.JComboBox<>();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setForeground(new java.awt.Color(0, 153, 204));

        jLabelcompanies.setBackground(new java.awt.Color(255, 255, 255));
        jLabelcompanies.setFont(new java.awt.Font("Agency FB", 1, 48)); // NOI18N
        jLabelcompanies.setForeground(new java.awt.Color(255, 255, 255));
        jLabelcompanies.setText("Companies...");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/Company_100px.png"))); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelcompanies))
                .addGap(0, 112, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabelcompanies)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(366, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 491));

        jpanel2.setBackground(new java.awt.Color(51, 51, 51));

        jLabelregistercompany.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabelregistercompany.setForeground(new java.awt.Color(255, 255, 255));
        jLabelregistercompany.setText("Register Companies");

        jLabelcname.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabelcname.setForeground(new java.awt.Color(255, 255, 255));
        jLabelcname.setText("Company Name :");

        txtName.setBackground(new java.awt.Color(51, 51, 51));
        txtName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtName.setForeground(new java.awt.Color(255, 255, 255));
        txtName.setText("Name");
        txtName.setBorder(null);
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });
        txtName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNameMouseClicked(evt);
            }
        });
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        jSeparator2.setMaximumSize(new java.awt.Dimension(33767, 33767));

        jLabelcphonenumber.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabelcphonenumber.setForeground(new java.awt.Color(255, 255, 255));
        jLabelcphonenumber.setText("Phone Number :");

        txtPhone.setBackground(new java.awt.Color(51, 51, 51));
        txtPhone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtPhone.setForeground(new java.awt.Color(255, 255, 255));
        txtPhone.setText("Phone");
        txtPhone.setBorder(null);
        txtPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPhoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPhoneFocusLost(evt);
            }
        });
        txtPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhoneActionPerformed(evt);
            }
        });

        jSeparator4.setMaximumSize(new java.awt.Dimension(33767, 33767));

        jLabelcemail.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabelcemail.setForeground(new java.awt.Color(255, 255, 255));
        jLabelcemail.setText("Email :");

        txtEmail.setBackground(new java.awt.Color(51, 51, 51));
        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(255, 255, 255));
        txtEmail.setText("Email");
        txtEmail.setBorder(null);
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });

        jSeparator5.setMaximumSize(new java.awt.Dimension(33767, 33767));

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        txtAddress.setText("Address");
        txtAddress.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAddressFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAddressFocusLost(evt);
            }
        });
        jScrollPane2.setViewportView(txtAddress);

        jLabelcaddress.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jLabelcaddress.setForeground(new java.awt.Color(255, 255, 255));
        jLabelcaddress.setText("Address :");

        cbUsers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbUsersItemStateChanged(evt);
            }
        });
        cbUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbUsersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanel2Layout = new javax.swing.GroupLayout(jpanel2);
        jpanel2.setLayout(jpanel2Layout);
        jpanel2Layout.setHorizontalGroup(
            jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel2Layout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(jLabelregistercompany)
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanel2Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(cbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jpanel2Layout.createSequentialGroup()
                            .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelcname)
                                .addComponent(jLabelcphonenumber)
                                .addComponent(jLabelcemail)
                                .addComponent(jLabelcaddress))
                            .addGap(22, 22, 22)
                            .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtEmail)
                                .addComponent(txtPhone)
                                .addComponent(txtName)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                .addComponent(jScrollPane2)))))
                .addGap(55, 55, 55))
        );
        jpanel2Layout.setVerticalGroup(
            jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabelregistercompany)
                .addGap(26, 26, 26)
                .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanel2Layout.createSequentialGroup()
                        .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelcname)
                            .addGroup(jpanel2Layout.createSequentialGroup()
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelcphonenumber)
                            .addGroup(jpanel2Layout.createSequentialGroup()
                                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabelcemail))
                .addGap(18, 18, 18)
                .addGroup(jpanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelcaddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbUsers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        getContentPane().add(jpanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 390, 490));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    Company myCompany = new Company();
    private void txtNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNameMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameMouseClicked

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusGained
        // TODO add your handling code here:
        if(txtName.getText().toLowerCase().equals("name")) {
            txtName.setText("");
        }
    }//GEN-LAST:event_txtNameFocusGained

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        // TODO add your handling code here:
        if(txtName.getText().toLowerCase().equals("")) {
            txtName.setText("Name");
        }
    }//GEN-LAST:event_txtNameFocusLost

    private void txtPhoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusGained
        // TODO add your handling code here:
        if(txtPhone.getText().toLowerCase().equals("phone")) {
            txtPhone.setText("");
        }
    }//GEN-LAST:event_txtPhoneFocusGained

    private void txtPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhoneFocusLost
        // TODO add your handling code here:
        if(txtPhone.getText().toLowerCase().equals("")) {
            txtPhone.setText("Phone");
        }
    }//GEN-LAST:event_txtPhoneFocusLost

    private void txtEmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusGained
        // TODO add your handling code here:
        if(txtEmail.getText().toLowerCase().equals("email")) {
            txtEmail.setText("");
        }
    }//GEN-LAST:event_txtEmailFocusGained

    private void txtPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPhoneActionPerformed

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        // TODO add your handling code here:
        if(txtEmail.getText().toLowerCase().equals("")) {
            txtEmail.setText("Email");
        }
    }//GEN-LAST:event_txtEmailFocusLost

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();
        if(name.equals("") || phone.equals("") || email.equals("")){
            JOptionPane.showMessageDialog(null, "Please fill out the required fields");
        } else{
            String sqlGetId = "SELECT id FROM users WHERE LOWER(fullname) = ?";
            try {
                stmt = DbConn.getConnection().prepareStatement(sqlGetId);
                stmt.setString(1, String.valueOf(cbUsers.getSelectedItem()));
                rs = stmt.executeQuery();
                if(rs.next()){
                    myCompany.setUserId(rs.getInt("id"));
                } else{
                    JOptionPane.showMessageDialog(null, "User ID not Found");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            myCompany.setName(name);
            myCompany.setEmail(email);
            myCompany.setPhone(phone);
            myCompany.setAddress(address);
            myCompany.insert();
            txtName.setText("Name");
            txtPhone.setText("Phone");
            txtEmail.setText("Email");
            txtAddress.setText("Address");
            cbUsers.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtAddressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddressFocusGained
        // TODO add your handling code here:
        if(txtAddress.getText().toLowerCase().equals("address")) {
            txtAddress.setText("");
        }
    }//GEN-LAST:event_txtAddressFocusGained

    private void txtAddressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddressFocusLost
        // TODO add your handling code here:
        if(txtAddress.getText().toLowerCase().equals("")) {
            txtAddress.setText("Address");
        }
    }//GEN-LAST:event_txtAddressFocusLost

    private void cbUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbUsersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUsersActionPerformed

    private void cbUsersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbUsersItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cbUsersItemStateChanged

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CompanyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompanyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompanyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompanyForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new CompanyForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JComboBox<String> cbUsers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelcaddress;
    private javax.swing.JLabel jLabelcemail;
    private javax.swing.JLabel jLabelcname;
    private javax.swing.JLabel jLabelcompanies;
    private javax.swing.JLabel jLabelcphonenumber;
    private javax.swing.JLabel jLabelregistercompany;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel jpanel2;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    // End of variables declaration//GEN-END:variables
}
