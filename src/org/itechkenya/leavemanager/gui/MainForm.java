package org.itechkenya.leavemanager.gui;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import org.itechkenya.leavemanager.domain.Organization;

/**
 *
 * @author gitahi
 */
public class MainForm extends javax.swing.JFrame {

    private final LeaveManagerFrame leaveEventFrame = new LeaveEventFrame(this);
    private final LeaveManagerFrame employeeFrame = new EmployeeFrame(this);
    private final LeaveManagerFrame contractFrame = new ContractFrame(this);
    private final LeaveManagerFrame leaveTypeFrame = new LeaveTypeFrame(this);
    private final LeaveManagerFrame organizationFrame = new OrganizationFrame(this);

    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        showTitle(((OrganizationFrame) organizationFrame).getOrganization());
        setIconImage(new ImageIcon(getClass().getResource("icons/leave-manager.png")).getImage());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        desktopPane = new javax.swing.JDesktopPane();
        navigationPanel = new javax.swing.JPanel();
        leaveEventsButton = new javax.swing.JButton();
        separator1 = new javax.swing.JSeparator();
        employeesButton = new javax.swing.JButton();
        contractsButton = new javax.swing.JButton();
        separator2 = new javax.swing.JSeparator();
        leaveTypesButton = new javax.swing.JButton();
        organizationButton = new javax.swing.JButton();
        separator3 = new javax.swing.JSeparator();
        statusPanel = new javax.swing.JPanel();
        statusLabel = new javax.swing.JLabel();
        toolBar = new javax.swing.JToolBar();
        leaveEventToolButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        employeeToolButton = new javax.swing.JButton();
        contractToolButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        leaveTypeToolButton = new javax.swing.JButton();
        organizationToolButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Leave Manager");

        splitPane.setDividerLocation(200);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 668, Short.MAX_VALUE)
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 455, Short.MAX_VALUE)
        );

        splitPane.setRightComponent(desktopPane);

        leaveEventsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/leave-event.png"))); // NOI18N
        leaveEventsButton.setText("Leave Events");
        leaveEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveEventsButtonActionPerformed(evt);
            }
        });

        employeesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/employee.png"))); // NOI18N
        employeesButton.setText("Employees");
        employeesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeesButtonActionPerformed(evt);
            }
        });

        contractsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/contract.png"))); // NOI18N
        contractsButton.setText("Contracts");
        contractsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractsButtonActionPerformed(evt);
            }
        });

        leaveTypesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/leave-type.png"))); // NOI18N
        leaveTypesButton.setText("Leave Types");
        leaveTypesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveTypesButtonActionPerformed(evt);
            }
        });

        organizationButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/organization.png"))); // NOI18N
        organizationButton.setText("Organization");
        organizationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigationPanelLayout = new javax.swing.GroupLayout(navigationPanel);
        navigationPanel.setLayout(navigationPanelLayout);
        navigationPanelLayout.setHorizontalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separator1)
                    .addComponent(employeesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(contractsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(separator2)
                    .addComponent(organizationButton, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                    .addComponent(separator3)
                    .addComponent(leaveTypesButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(leaveEventsButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        navigationPanelLayout.setVerticalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(leaveEventsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(contractsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leaveTypesButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(organizationButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(241, Short.MAX_VALUE))
        );

        splitPane.setLeftComponent(navigationPanel);

        statusPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(statusLabel)
                .addContainerGap())
        );

        toolBar.setRollover(true);

        leaveEventToolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/leave-event.png"))); // NOI18N
        leaveEventToolButton.setToolTipText("Leave events");
        leaveEventToolButton.setFocusable(false);
        leaveEventToolButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        leaveEventToolButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        leaveEventToolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveEventToolButtonActionPerformed(evt);
            }
        });
        toolBar.add(leaveEventToolButton);
        toolBar.add(jSeparator1);

        employeeToolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/employee.png"))); // NOI18N
        employeeToolButton.setToolTipText("Employees");
        employeeToolButton.setFocusable(false);
        employeeToolButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        employeeToolButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        employeeToolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employeeToolButtonActionPerformed(evt);
            }
        });
        toolBar.add(employeeToolButton);

        contractToolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/contract.png"))); // NOI18N
        contractToolButton.setToolTipText("Contracts");
        contractToolButton.setFocusable(false);
        contractToolButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        contractToolButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        contractToolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contractToolButtonActionPerformed(evt);
            }
        });
        toolBar.add(contractToolButton);
        toolBar.add(jSeparator2);

        leaveTypeToolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/leave-type.png"))); // NOI18N
        leaveTypeToolButton.setToolTipText("Leave type");
        leaveTypeToolButton.setFocusable(false);
        leaveTypeToolButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        leaveTypeToolButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        leaveTypeToolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveTypeToolButtonActionPerformed(evt);
            }
        });
        toolBar.add(leaveTypeToolButton);

        organizationToolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/itechkenya/leavemanager/gui/icons/organization.png"))); // NOI18N
        organizationToolButton.setToolTipText("Organization");
        organizationToolButton.setFocusable(false);
        organizationToolButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        organizationToolButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        organizationToolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationToolButtonActionPerformed(evt);
            }
        });
        toolBar.add(organizationToolButton);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane)
            .addComponent(statusPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(toolBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(toolBar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitPane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void employeesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeesButtonActionPerformed
        showLeaveManagerFrame(employeeFrame);
    }//GEN-LAST:event_employeesButtonActionPerformed

    private void contractsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractsButtonActionPerformed
        showLeaveManagerFrame(contractFrame);
    }//GEN-LAST:event_contractsButtonActionPerformed

    private void organizationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationButtonActionPerformed
        showLeaveManagerFrame(organizationFrame, false);
    }//GEN-LAST:event_organizationButtonActionPerformed

    private void leaveEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveEventsButtonActionPerformed
        showLeaveManagerFrame(leaveEventFrame);
    }//GEN-LAST:event_leaveEventsButtonActionPerformed

    private void leaveTypesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveTypesButtonActionPerformed
        showLeaveManagerFrame(leaveTypeFrame);
    }//GEN-LAST:event_leaveTypesButtonActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        AboutDialog ad = new AboutDialog(this, true);
        ad.setLocationRelativeTo(this);
        ad.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void leaveEventToolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveEventToolButtonActionPerformed
        showLeaveManagerFrame(leaveEventFrame);
    }//GEN-LAST:event_leaveEventToolButtonActionPerformed

    private void employeeToolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeToolButtonActionPerformed
        showLeaveManagerFrame(employeeFrame);
    }//GEN-LAST:event_employeeToolButtonActionPerformed

    private void contractToolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contractToolButtonActionPerformed
        showLeaveManagerFrame(contractFrame);
    }//GEN-LAST:event_contractToolButtonActionPerformed

    private void leaveTypeToolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveTypeToolButtonActionPerformed
        showLeaveManagerFrame(leaveTypeFrame);
    }//GEN-LAST:event_leaveTypeToolButtonActionPerformed

    private void organizationToolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationToolButtonActionPerformed
        showLeaveManagerFrame(organizationFrame, false);
    }//GEN-LAST:event_organizationToolButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton contractToolButton;
    private javax.swing.JButton contractsButton;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton employeeToolButton;
    private javax.swing.JButton employeesButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JButton leaveEventToolButton;
    private javax.swing.JButton leaveEventsButton;
    private javax.swing.JButton leaveTypeToolButton;
    private javax.swing.JButton leaveTypesButton;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JButton organizationButton;
    private javax.swing.JButton organizationToolButton;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;
    private javax.swing.JSeparator separator3;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

    public LeaveManagerFrame getContractFrame() {
        return contractFrame;
    }

    public LeaveManagerFrame getEmployeeFrame() {
        return employeeFrame;
    }

    public LeaveManagerFrame getLeaveEventFrame() {
        return leaveEventFrame;
    }

    public LeaveManagerFrame getLeaveTypeFrame() {
        return leaveTypeFrame;
    }

    public LeaveManagerFrame getOrganizationFrame() {
        return organizationFrame;
    }

    public void dataChanged(LeaveManagerFrame source) {
        for (LeaveManagerFrame frame : getLeaveManagaerFrames()) {
            if (!frame.equals(source)) {
                frame.dataChanged(source);
            }
        }
        if (source instanceof OrganizationFrame) {
            OrganizationFrame orgFrame = (OrganizationFrame) source;
            showTitle(orgFrame.getOrganization());
        }
    }

    public void showAutoCreatedLeaveEventMessage(String message) {
        statusLabel.setText(message);
    }

    public final void showTitle(Organization organization) {
        String title = "Leave Manager";
        if (organization != null) {
            title = title + " - " + organization.getName();
        }
        this.setTitle(title);
    }

    private List<LeaveManagerFrame> getLeaveManagaerFrames() {
        List<LeaveManagerFrame> leaveManagerFrames = new ArrayList<>();
        leaveManagerFrames.add(contractFrame);
        leaveManagerFrames.add(employeeFrame);
        leaveManagerFrames.add(leaveEventFrame);
        leaveManagerFrames.add(leaveTypeFrame);
        leaveManagerFrames.add(organizationFrame);
        return leaveManagerFrames;
    }

    private void showLeaveManagerFrame(LeaveManagerFrame frame, boolean maximize) {
        try {
            if (!frameAlreadyLoaded(desktopPane.getAllFrames(), frame)) {
                desktopPane.add(frame);
                frame.setMaximum(maximize);
                centerFrame(frame);
                frame.setVisible(true);
            }
            frame.setSelected(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showLeaveManagerFrame(LeaveManagerFrame frame) {
        showLeaveManagerFrame(frame, true);
    }

    private boolean frameAlreadyLoaded(JInternalFrame[] frames, JInternalFrame frame) {
        for (JInternalFrame jInternalFrame : frames) {
            if (jInternalFrame.equals(frame)) {
                return true;
            }
        }
        return false;
    }

    public void centerFrame(JInternalFrame jif) {
        Dimension desktopSize = desktopPane.getSize();
        Dimension jInternalFrameSize = jif.getSize();
        int width = (desktopSize.width - jInternalFrameSize.width) / 2;
        int height = (desktopSize.height - jInternalFrameSize.height) / 2;
        jif.setLocation(width, height);
    }
}
