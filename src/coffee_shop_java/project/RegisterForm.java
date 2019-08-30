/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author KHEANG
 */
public class RegisterForm extends javax.swing.JFrame {

    /**
     * Creates new form RegisterForm
     */
    public RegisterForm() {
        initComponents();
        jtxtFirstname.requestFocus();
        jbtnExit.setBackground(new Color(0, 0, 0, 0));
        jbtnLogin.setBackground(new Color(0, 0, 0, 0));
        jtxtPass.setBackground(new Color(0, 0, 0, 0));
        jtxtEmail.setBackground(new Color(0, 0, 0, 0));
        jtxtFirstname.setBackground(new Color(0, 0, 0, 0));
        jtxtLastname.setBackground(new Color(0, 0, 0, 0));
        jtxtConPass.setBackground(new Color(0, 0, 0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jbtnLogin = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jbtnExit = new javax.swing.JButton();
        jlblReg = new javax.swing.JLabel();
        jlblLogo = new javax.swing.JLabel();
        jlblFirstname = new javax.swing.JLabel();
        jtxtFirstname = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jlblPass = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jlblConPass = new javax.swing.JLabel();
        jlblLastname = new javax.swing.JLabel();
        jtxtLastname = new javax.swing.JTextField();
        jlblGender = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jlblUserType = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jlblStatus = new javax.swing.JLabel();
        jlblEmail = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jSeparator9 = new javax.swing.JSeparator();
        jtxtConPass = new javax.swing.JPasswordField();
        jtxtPass = new javax.swing.JPasswordField();
        jcomboStatus = new javax.swing.JComboBox<>();
        jcomboGender = new javax.swing.JComboBox<>();
        jcomboAdmin = new javax.swing.JComboBox<>();
        jbtnRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 106, 83));
        jPanel1.setPreferredSize(new java.awt.Dimension(530, 690));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtnLogin.setFont(new java.awt.Font("Agency FB", 0, 24)); // NOI18N
        jbtnLogin.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLogin.setText("Login");
        jbtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 500, -1, -1));
        jPanel1.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 540, 50, 10));

        jbtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_32px.png"))); // NOI18N
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 10, 40, -1));

        jlblReg.setFont(new java.awt.Font("Agency FB", 1, 48)); // NOI18N
        jlblReg.setForeground(new java.awt.Color(255, 255, 255));
        jlblReg.setText("Register");
        jPanel1.add(jlblReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 140, 70));

        jlblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/why_us_male_64px.png"))); // NOI18N
        jPanel1.add(jlblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, -1, 60));

        jlblFirstname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/user_48px.png"))); // NOI18N
        jPanel1.add(jlblFirstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, -1, 50));

        jtxtFirstname.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jtxtFirstname.setForeground(new java.awt.Color(255, 255, 255));
        jtxtFirstname.setBorder(null);
        jPanel1.add(jtxtFirstname, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 340, 40));

        jSeparator2.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 200, 440, 20));

        jSeparator3.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator3.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, 440, 20));

        jlblPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/lock_48px.png"))); // NOI18N
        jPanel1.add(jlblPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 220, -1, 50));

        jSeparator4.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator4.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 440, 20));

        jSeparator5.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator5.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 280, 440, 20));

        jlblConPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/lock_48px.png"))); // NOI18N
        jPanel1.add(jlblConPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, -1, 50));

        jlblLastname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/user_48px.png"))); // NOI18N
        jPanel1.add(jlblLastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, -1, 50));

        jtxtLastname.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jtxtLastname.setForeground(new java.awt.Color(255, 255, 255));
        jtxtLastname.setBorder(null);
        jPanel1.add(jtxtLastname, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 150, 340, 40));

        jlblGender.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/gender_48px.png"))); // NOI18N
        jPanel1.add(jlblGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 300, -1, 50));

        jSeparator6.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator6.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 440, 20));

        jlblUserType.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/administrator_male_48px.png"))); // NOI18N
        jPanel1.add(jlblUserType, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 380, -1, 50));

        jSeparator7.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator7.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 440, 440, 20));

        jlblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/connection_status_on_48px.png"))); // NOI18N
        jPanel1.add(jlblStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 380, -1, 50));

        jlblEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/message_48px.png"))); // NOI18N
        jPanel1.add(jlblEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, -1, 50));

        jtxtEmail.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jtxtEmail.setForeground(new java.awt.Color(255, 255, 255));
        jtxtEmail.setBorder(null);
        jPanel1.add(jtxtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 310, 340, 40));

        jSeparator8.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator8.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 360, 440, 20));

        jSeparator9.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator9.setPreferredSize(new java.awt.Dimension(50, 20));
        jPanel1.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 440, 440, 20));

        jtxtConPass.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jtxtConPass.setForeground(new java.awt.Color(255, 255, 255));
        jtxtConPass.setBorder(null);
        jPanel1.add(jtxtConPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 340, 40));

        jtxtPass.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jtxtPass.setForeground(new java.awt.Color(255, 255, 255));
        jtxtPass.setBorder(null);
        jPanel1.add(jtxtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 340, 40));

        jcomboStatus.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jcomboStatus.setForeground(new java.awt.Color(255, 255, 255));
        jcomboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive" }));
        jPanel1.add(jcomboStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 390, 340, 40));

        jcomboGender.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jcomboGender.setForeground(new java.awt.Color(255, 255, 255));
        jcomboGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));
        jPanel1.add(jcomboGender, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 340, 40));

        jcomboAdmin.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jcomboAdmin.setForeground(new java.awt.Color(255, 255, 255));
        jcomboAdmin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Superadmin", "Non-Superadmin" }));
        jPanel1.add(jcomboAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 340, 40));

        jbtnRegister.setBackground(new java.awt.Color(50, 50, 50));
        jbtnRegister.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jbtnRegister.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/add_user_male_26px.png"))); // NOI18N
        jbtnRegister.setText(" Register");
        jbtnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRegisterActionPerformed(evt);
            }
        });
        jPanel1.add(jbtnRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(889, 490, 140, 60));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    User myUser = new User();
    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
        // TODO add your handling code here:
        LoginForm login = new LoginForm();
        login.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jbtnLoginActionPerformed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jbtnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegisterActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        ResultSet rs;
        String firstname = jtxtFirstname.getText().trim();
        String lastname = jtxtLastname.getText().trim();
        String fullname = firstname + lastname;
        String email = jtxtEmail.getText().trim();
        String password = String.valueOf(jtxtPass.getPassword()).trim();
        String conPassword = String.valueOf(jtxtConPass.getPassword()).trim();
        String status = String.valueOf(jcomboStatus.getSelectedItem()).trim();
        String gender = String.valueOf(jcomboGender.getSelectedItem()).trim();
        int userType = 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        if(firstname.equals("") || lastname.equals("") || email.equals("") || password.equals("") || conPassword.equals("")) {
            JOptionPane.showMessageDialog(null, "Please input the required fields!");
            jtxtFirstname.requestFocus();
        } else {
            Boolean checkExist = false;
            String sql = "SELECT * FROM `users` WHERE LOWER(fullname) = ?";
            try {
                st = DbConn.getConnection().prepareStatement(sql);
                st.setString(1, fullname.toLowerCase());
                rs = st.executeQuery();
                if(rs.next()) {
                    checkExist = true;
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
            if(checkExist.equals(true)) {
                JOptionPane.showMessageDialog(null, "User already exist!");
                checkExist = false;
                jtxtPass.setText("");
                jtxtEmail.setText("");
                jtxtFirstname.setText("");
                jtxtLastname.setText("");
                jtxtConPass.setText("");
                jcomboAdmin.setSelectedIndex(0);
                jcomboGender.setSelectedIndex(0);
                jcomboStatus.setSelectedIndex(0);
                jtxtFirstname.requestFocus();
            } else {
                if(!password.equals(conPassword)) {
                    JOptionPane.showMessageDialog(null, "Password and Confirm Password does not match!");
                    jtxtConPass.setText("");
                    jtxtPass.setText("");
                    jtxtConPass.requestFocus();
                } else {
                    String hashedPass = PasswordEncryption.MD5(password);
                    myUser.setFirstname(firstname);
                    myUser.setLastname(lastname);
                    myUser.setFullname(fullname);
                    myUser.setEmail(email);
                    myUser.setPassword(hashedPass);
                    myUser.setStatus(status);
                    myUser.setGender(gender);
                    myUser.setUserType(userType);
                    myUser.setCreatedDate(date);
                    myUser.setUpdatedDate(date);
                    myUser.insert();
                    jtxtPass.setText("");
                    jtxtEmail.setText("");
                    jtxtFirstname.setText("");
                    jtxtLastname.setText("");
                    jtxtConPass.setText("");
                    jcomboAdmin.setSelectedIndex(0);
                    jcomboGender.setSelectedIndex(0);
                    jcomboStatus.setSelectedIndex(0);
                    jtxtFirstname.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_jbtnRegisterActionPerformed

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
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegisterForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new RegisterForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnLogin;
    private javax.swing.JButton jbtnRegister;
    private javax.swing.JComboBox<String> jcomboAdmin;
    private javax.swing.JComboBox<String> jcomboGender;
    private javax.swing.JComboBox<String> jcomboStatus;
    private javax.swing.JLabel jlblConPass;
    private javax.swing.JLabel jlblEmail;
    private javax.swing.JLabel jlblFirstname;
    private javax.swing.JLabel jlblGender;
    private javax.swing.JLabel jlblLastname;
    private javax.swing.JLabel jlblLogo;
    private javax.swing.JLabel jlblPass;
    private javax.swing.JLabel jlblReg;
    private javax.swing.JLabel jlblStatus;
    private javax.swing.JLabel jlblUserType;
    private javax.swing.JPasswordField jtxtConPass;
    private javax.swing.JTextField jtxtEmail;
    private javax.swing.JTextField jtxtFirstname;
    private javax.swing.JTextField jtxtLastname;
    private javax.swing.JPasswordField jtxtPass;
    // End of variables declaration//GEN-END:variables
}
