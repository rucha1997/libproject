package org.itechkenya.leavemanager.api;

import javax.swing.JFrame;
import org.itechkenya.leavemanager.gui.MainForm;

/**
 *
 * @author gitahi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the MainForm and start the LeaveManager thread to auto-create leave events */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm mainForm = new MainForm();
                mainForm.setExtendedState(JFrame.MAXIMIZED_BOTH);
                mainForm.setVisible(true);
                
                Thread leaveManagerThread = new Thread(new LeaveManager(mainForm));
                leaveManagerThread.start();
            }
        });
    }
}
