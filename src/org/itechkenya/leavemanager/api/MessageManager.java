/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.api;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author gitahi
 */
public class MessageManager {

    public static void showWarningMessage(Component source, String message, Component toFocus) {
        JOptionPane.showMessageDialog(source, message, "Warning!", JOptionPane.WARNING_MESSAGE, null);
        if (toFocus != null) {
            toFocus.requestFocus();
        }
    }

    public static void showWarningMessage(Component source, String message) {
        showWarningMessage(source, message, null);
    }
}
