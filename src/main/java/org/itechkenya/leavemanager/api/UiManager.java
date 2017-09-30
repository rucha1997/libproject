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

import java.awt.Component;
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
}
