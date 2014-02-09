/**
 * LeaveManager, a basic leave management program for small organizations
 * 
 * This file is part of LeaveManager.
 * 
 * LeaveManager is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * LeaveManager is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with LeaveManager. If not, see <http://www.gnu.org/licenses/>.
 */

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
