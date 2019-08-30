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
        jbtnReg.setBackground(new Color(0, 0, 0, 0));
        jbtnLogin.setBounds(250, 250, 100, 50);
        jbtnLogin.setBackground(new Color(100, 100, 100));
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
        jbtnReg = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jbtnExit = new javax.swing.JButton();
        jtxtUsername = new javax.swing.JTextField();
        jtxtPass = new javax.swing.JPasswordField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jbtnLogin = new javax.swing.JButton();
        jlblPass = new javax.swing.JLabel();
        jlblUser = new javax.swing.JLabel();
        jBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jpanelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jlblForm.setFont(new java.awt.Font("Agency FB", 1, 56)); // NOI18N
        jlblForm.setForeground(new java.awt.Color(255, 255, 255));
        jlblForm.setText("Login");
        jpanelBackground.add(jlblForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 100, 60));

        jlblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/conference_call_filled_100px.png"))); // NOI18N
        jpanelBackground.add(jlblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        jbtnReg.setFont(new java.awt.Font("Agency FB", 0, 24)); // NOI18N
        jbtnReg.setForeground(new java.awt.Color(255, 255, 255));
        jbtnReg.setText("Register");
        jbtnReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRegActionPerformed(evt);
            }
        });
        jpanelBackground.add(jbtnReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, -1, -1));
        jpanelBackground.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 390, 70, 10));

        jbtnExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/delete_sign_32px.png"))); // NOI18N
        jbtnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExitActionPerformed(evt);
            }
        });
        jpanelBackground.add(jbtnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, 40, -1));

        jtxtUsername.setFont(new java.awt.Font("Agency FB", 1, 18)); // NOI18N
        jtxtUsername.setForeground(new java.awt.Color(255, 255, 255));
        jtxtUsername.setBorder(null);
        jpanelBackground.add(jtxtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 340, 40));

        jtxtPass.setFont(new java.awt.Font("Agency FB", 0, 18)); // NOI18N
        jtxtPass.setForeground(new java.awt.Color(255, 255, 255));
        jtxtPass.setBorder(null);
        jpanelBackground.add(jtxtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 340, 40));

        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 20));
        jpanelBackground.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 320, 440, -1));

        jSeparator2.setMinimumSize(new java.awt.Dimension(2, 2));
        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 20));
        jpanelBackground.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 440, 20));

        jbtnLogin.setBackground(new java.awt.Color(50, 50, 50));
        jbtnLogin.setFont(new java.awt.Font("Agency FB", 1, 24)); // NOI18N
        jbtnLogin.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/enter_2_32px.png"))); // NOI18N
        jbtnLogin.setText("  Login");
        jbtnLogin.setBorder(null);
        jbtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoginActionPerformed(evt);
            }
        });
        jpanelBackground.add(jbtnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 120, 50));

        jlblPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/lock_48px.png"))); // NOI18N
        jpanelBackground.add(jlblPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 260, -1, 50));

        jlblUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/user_48px.png"))); // NOI18N
        jpanelBackground.add(jlblUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 170, -1, 50));

        jBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/coffee_shop_java/icons/Artboard 1.png"))); // NOI18N
        jpanelBackground.add(jBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 460));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRegActionPerformed
        // TODO add your handling code here:
        RegisterForm reg = new RegisterForm();
        reg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jbtnRegActionPerformed

    private void jbtnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jbtnExitActionPerformed

    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
        // TODO add your handling code here:
        PreparedStatement st;
        ResultSet rs;
        String username = jtxtUsername.getText().trim();
        String password = String.valueOf(jtxtPass.getPassword()).trim();

        String sql = "SELECT * FROM `users` WHERE LOWER(fullname) = ? AND password = ?";

        try {
            st = DbConn.getConnection().prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            rs = st.executeQuery();

            if(rs.next()) {
                MainForm main = new MainForm();
                main.setVisible(true);
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong username or password!");
                jtxtPass.setText("");
                jtxtUsername.setText("");
                jtxtUsername.requestFocus();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_jbtnLoginActionPerformed

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
    private javax.swing.JLabel jBackground;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JButton jbtnExit;
    private javax.swing.JButton jbtnLogin;
    private javax.swing.JButton jbtnReg;
    private javax.swing.JLabel jlblForm;
    private javax.swing.JLabel jlblLogo;
    private javax.swing.JLabel jlblPass;
    private javax.swing.JLabel jlblUser;
    private javax.swing.JPanel jpanelBackground;
    private javax.swing.JPasswordField jtxtPass;
    private javax.swing.JTextField jtxtUsername;
    // End of variables declaration//GEN-END:variables
}
