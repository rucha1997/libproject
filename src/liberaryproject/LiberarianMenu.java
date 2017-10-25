 
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liberaryproject;

/**
 *
 * @author amans
 */
public class LiberarianMenu extends javax.swing.JFrame {

    /**
     * Creates new form LiberarianMenu
     */
    public LiberarianMenu() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JButton manageBooksButton = new javax.swing.JButton();
        manageStudentButton = new javax.swing.JButton();
        searchBooksButton = new javax.swing.JButton();
        manageBorrowerButton = new javax.swing.JButton();
        adminSettingButton = new javax.swing.JButton();
        generatereportButton = new javax.swing.JButton();
        generateBackupButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        manageBooksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Borrow_Book_50px.png"))); // NOI18N
        manageBooksButton.setText("Manage Books");
        manageBooksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageBooksButtonActionPerformed(evt);
            }
        });

        manageStudentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Student_Male_50px.png"))); // NOI18N
        manageStudentButton.setText("Manage Students");
        manageStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageStudentButtonActionPerformed(evt);
            }
        });

        searchBooksButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Search_50px_1.png"))); // NOI18N
        searchBooksButton.setText("Search Books");
        searchBooksButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBooksButtonActionPerformed(evt);
            }
        });

        manageBorrowerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_People_50px.png"))); // NOI18N
        manageBorrowerButton.setText("Manage Borrower");
        manageBorrowerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageBorrowerButtonActionPerformed(evt);
            }
        });

        adminSettingButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Settings_50px.png"))); // NOI18N
        adminSettingButton.setText("Admin Setting");
        adminSettingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminSettingButtonActionPerformed(evt);
            }
        });

        generatereportButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Report_Card_50px.png"))); // NOI18N
        generatereportButton.setText("Generate Report");
        generatereportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatereportButtonActionPerformed(evt);
            }
        });

        generateBackupButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Data_Backup_50px.png"))); // NOI18N
        generateBackupButton.setText("Generate Backup");
        generateBackupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateBackupButtonActionPerformed(evt);
            }
        });

        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Exit_50px.png"))); // NOI18N
        logoutButton.setText("Logout");

        jPanel1.setBackground(new java.awt.Color(18, 49, 113));
        jPanel1.setBorder(new javax.swing.border.MatteBorder(null));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logo.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(69, 69, 69))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(adminSettingButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(manageBooksButton)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(generatereportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageStudentButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchBooksButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateBackupButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manageBorrowerButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logoutButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageBorrowerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBooksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageStudentButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(manageBooksButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generateBackupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(generatereportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(adminSettingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(64, 64, 64))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generatereportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatereportButtonActionPerformed
        // TODO add your handling code here:
        GenerateReport generateReport=new  GenerateReport();
        generateReport.setVisible(true);
        
        //To close thew current window 
        LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
                
            
    }//GEN-LAST:event_generatereportButtonActionPerformed

    private void generateBackupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateBackupButtonActionPerformed
        // TODO add your handling code here:
        GenerateBackup generateBackup=new GenerateBackup();
        generateBackup.setVisible(true);
        
        //To close thew current window 
        LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
    }//GEN-LAST:event_generateBackupButtonActionPerformed

    private void manageBorrowerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageBorrowerButtonActionPerformed
        // TODO add your handling code here:
        ManageBorrower manageBorrower=new ManageBorrower();
        manageBorrower.setVisible(true);
        
        //To close thew current window 
        LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
        
    }//GEN-LAST:event_manageBorrowerButtonActionPerformed

    private void searchBooksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBooksButtonActionPerformed
        // TODO add your handling code here:
        SearchBooks searchBooks=new SearchBooks();
        searchBooks.setVisible(true);
        
        //To close thew current window 
        LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
    }//GEN-LAST:event_searchBooksButtonActionPerformed

    private void manageBooksButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageBooksButtonActionPerformed
        // TODO add your handling code here:
         ManageBooks manageBooks=new ManageBooks();
        manageBooks.setVisible(true);
        
        //To close thew current window 
         LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
    }//GEN-LAST:event_manageBooksButtonActionPerformed

    private void manageStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageStudentButtonActionPerformed
        // TODO add your handling code here:
        ManageStudents manageStudents=new ManageStudents();
        manageStudents.setVisible(true);
        
        //To close thew current window 
        LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
    }//GEN-LAST:event_manageStudentButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void adminSettingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminSettingButtonActionPerformed
        // TODO add your handling code here:
        AdminSettings adminSettings=new AdminSettings();
        adminSettings.setVisible(true);
        
        //To close thew current window 
        LiberarianMenu liberarianMenu= new LiberarianMenu();
        liberarianMenu.setVisible(false);
        dispose();
    }//GEN-LAST:event_adminSettingButtonActionPerformed

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
            java.util.logging.Logger.getLogger(LiberarianMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiberarianMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiberarianMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiberarianMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

         /*Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

@Override
            public void run() {
                new LiberarianMenu().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton adminSettingButton;
    private javax.swing.JButton generateBackupButton;
    private javax.swing.JButton generatereportButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton manageBorrowerButton;
    private javax.swing.JButton manageStudentButton;
    private javax.swing.JButton searchBooksButton;
    // End of variables declaration//GEN-END:variables
}
