/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import static org.itechkenya.leavemanager.gui.OrganizationFrame.addEscapeListener;

/**
 *
 * @author gitahi
 */
public abstract class LeaveManagerFrame extends JInternalFrame {

    public void configureButtons() {
        getRootPane().setDefaultButton(getSaveButton());
        addEscapeListener(this);
    }

    public static void addEscapeListener(final JInternalFrame jif) {

        ActionListener escListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jif.dispose();
            }
        };

        jif.getRootPane().registerKeyboardAction(escListener,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    public abstract void loadData();

    public abstract JButton getSaveButton();
}
