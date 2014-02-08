/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.api;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author gitahi
 */
public class UiManager {

    public static void showWarningMessage(Component source, String message, Component toFocus) {
        JOptionPane.showMessageDialog(source, message, "Stop!", JOptionPane.WARNING_MESSAGE, null);
        if (toFocus != null) {
            toFocus.requestFocus();
        }
    }

    public static void showWarningMessage(Component source, String message) {
        showWarningMessage(source, message, null);
    }

    public static void showErrorMessage(Component source, String message) {
        JOptionPane.showMessageDialog(source, message, "Error!", JOptionPane.ERROR_MESSAGE, null);
    }

    public static boolean showConfirmationMessage(Component source, String message) {
        return JOptionPane.showConfirmDialog(source, message, "Confirm!",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static boolean showDeleteConfirmationMessage(Component source, int recordCount) {
        return showConfirmationMessage(source, "Are you sure you want to delete the " + recordCount + " record(s) selected?");
    }

    public static void showConstraintViolationMessage(Component source, String record) {
        showErrorMessage(source, "Some records depend on the record: '" + record + "'. Please delete those first.");
    }

    public static String formatDate(Date date) {
        if (date != null) {
            return new SimpleDateFormat("MMM dd, yyyy").format(date);
        }
        return "None";
    }
}
