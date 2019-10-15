/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project;

import coffee_shop_java.project.Helper.PasswordEncryption;
import coffee_shop_java.project.Model.DbConn;
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
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form LoginForm
     */
    public LoginForm() {
        initComponents();
        jtxtUsername.requestFocus();
        jtxtUsername.setBackground(new Color(0, 0, 0, 0));
        jtxtPass.setBackground(new Color(0, 0, 0, 0));
        jbtnExit.setBackground(new Color(0, 0, 0, 0));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelBackground = new javax.swing.JPanel();
        jlblForm = new javax.swing.JLabel();
        jlblLogo = new javax.swing.JLabel();
        btnRegister = new javax.swing.JLabel();
        btnLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jbtnExit = new javax.swing.JButton();
        jtxtUsername = new javax.swing.JTextField();
        jtxtPass = new javax.swing.JPasswordField();
        jlblPass = new javax.swing.JLabel();
        jlblUser = new javax.swing.JLabel();
        jBackground = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(320, 320));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jpanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblForm.setFont(new java.awt.Font("Agency FB", 1, 56)); // NOI18N
        jlblForm.setForeground(new java.awt.Color(255, 255, 255));
        jlblForm.setText("Login");
        jpanelBackground.add(jlblForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 100, 60));

        jlblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/conference_call_filled_100px.png"))); // NOI18N
        jpanelBackground.add(jlblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        btnRegister.setFont(new java.awt.Font("Agency FB", 0, 24)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("Register Form");
        btnRegister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegisterMouseClicked(evt);
            }
        });
        jpanelBackground.add(btnRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 350, -1, 40));

        btnLogin.setBackground(new java.awt.Color(43, 43, 43));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoginMouseExited(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login");
        jLabel1.setAlignmentX(0.5F);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/enter_2_32px.png"))); // NOI18N

        javax.swing.GroupLayout btnLoginLayout = new javax.swing.GroupLayout(btnLogin);
        btnLogin.setLayout(btnLoginLayout);
        btnLoginLayout.setHorizontalGroup(
            btnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        btnLoginLayout.setVerticalGroup(
            btnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpanelBackground.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 340, 120, 60));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jpanelBackground.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 440, 3));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jpanelBackground.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 440, 3));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 107, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jpanelBackground.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 107, 3));

        jbtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_32px.png"))); // NOI18N
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });
        jpanelBackground.add(jbtnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 40, -1));

        jtxtUsername.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jtxtUsername.setForeground(new java.awt.Color(255, 255, 255));
        jtxtUsername.setText("Username...");
        jtxtUsername.setBorder(null);
        jtxtUsername.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtUsernameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtUsernameFocusLost(evt);
            }
        });
        jtxtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtUsernameActionPerformed(evt);
            }
        });
        jpanelBackground.add(jtxtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 340, 40));

        jtxtPass.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jtxtPass.setForeground(new java.awt.Color(255, 255, 255));
        jtxtPass.setText("Password...");
        jtxtPass.setBorder(null);
        jtxtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jtxtPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtxtPassFocusLost(evt);
            }
        });
        jpanelBackground.add(jtxtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 340, 40));

        jlblPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/lock_48px.png"))); // NOI18N
        jpanelBackground.add(jlblPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, -1, 50));

        jlblUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/user_48px.png"))); // NOI18N
        jpanelBackground.add(jlblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, 50));

        jBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/Artboard 1.png"))); // NOI18N
        jpanelBackground.add(jBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 460));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 440, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jpanelBackground.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 440, 3));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jtxtUsernameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtUsernameFocusGained
        // TODO add your handling code here:
        if(jtxtUsername.getText().equals("Username...")) {
            jtxtUsername.setText("");
        }
    }//GEN-LAST:event_jtxtUsernameFocusGained

    private void jtxtUsernameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtUsernameFocusLost
        // TODO add your handling code here:
        if(jtxtUsername.getText().equals("")) {
            jtxtUsername.setText("Username...");
        }
    }//GEN-LAST:event_jtxtUsernameFocusLost

    private void jtxtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPassFocusGained
        // TODO add your handling code here:
        if(String.valueOf(jtxtPass.getPassword()).equals("Password...")) {
            jtxtPass.setText("");
        }
    }//GEN-LAST:event_jtxtPassFocusGained

    private void jtxtPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtxtPassFocusLost
        // TODO add your handling code here:
        if(String.valueOf(jtxtPass.getPassword()).equals("")) {
            jtxtPass.setText("Password...");
        }
    }//GEN-LAST:event_jtxtPassFocusLost

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        // TODO add your handling code here:
        PreparedStatement st;
        ResultSet rs;
        String username = jtxtUsername.getText().trim();
        String password = String.valueOf(jtxtPass.getPassword()).trim();

        if(username.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(null, "Username and password can't be blank!");
            jtxtUsername.requestFocus();
        } else {
            String sql = "SELECT * FROM `users` WHERE LOWER(fullname) = ? AND password = ?";

            try {
                st = DbConn.getConnection().prepareStatement(sql);
                st.setString(1, username.toLowerCase());
                st.setString(2, PasswordEncryption.MD5(password));
                rs = st.executeQuery();

                if(rs.next()) {
                    int id = rs.getInt("id");
                    int loginCount = rs.getInt("login_count");
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String date = dtf.format(now);
                    loginCount++;
                    String update = "UPDATE users SET login_count = ?, last_logged = ? WHERE id = ?";
                    PreparedStatement stmt = DbConn.getConnection().prepareStatement(update);
                    stmt.setInt(1, loginCount);
                    stmt.setString(2, date);
                    stmt.setInt(3, id);
                    stmt.executeUpdate();
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password!");
                    jtxtPass.setText("");
                    jtxtUsername.setText("");
                    jtxtUsername.requestFocus();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }//GEN-LAST:event_btnLoginMouseClicked

    private void btnRegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegisterMouseClicked
        // TODO add your handling code here:
        RegisterForm reg = new RegisterForm();
        reg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegisterMouseClicked

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered
        // TODO add your handling code here:
        btnLogin.setBackground(new Color(46, 49, 49));
    }//GEN-LAST:event_btnLoginMouseEntered

    private void btnLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseExited
        // TODO add your handling code here:
        btnLogin.setBackground(new Color(43, 43, 43));
    }//GEN-LAST:event_btnLoginMouseExited

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_formWindowOpened

    private void jtxtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtUsernameActionPerformed

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnLogin;
    private javax.swing.JLabel btnRegister;
    private javax.swing.JLabel jBackground;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JLabel jlblForm;
    private javax.swing.JLabel jlblLogo;
    private javax.swing.JLabel jlblPass;
    private javax.swing.JLabel jlblUser;
    private javax.swing.JPanel jpanelBackground;
    private javax.swing.JPasswordField jtxtPass;
    private javax.swing.JTextField jtxtUsername;
    // End of variables declaration//GEN-END:variables
}
