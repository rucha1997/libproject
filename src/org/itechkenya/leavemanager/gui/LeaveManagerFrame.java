/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.itechkenya.leavemanager.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import static org.itechkenya.leavemanager.gui.OrganizationFrame.addEscapeListener;

/**
 *
 * @author gitahi
 */
public abstract class LeaveManagerFrame extends JInternalFrame {

    public enum UpdateType {

        CREATE,
        EDIT,
        DESTROY
    }

    private boolean hasSelectedItem = false;

    public void configureComponents() {
        if (getOkButton() != null) {
            getRootPane().setDefaultButton(getOkButton());
        }
        if (getDeleteButton() != null) {
            getDeleteButton().setEnabled(false);
        }
        if (getTable() != null) {
            getTable().getSelectionModel().addListSelectionListener(new TableListSelectionListener());
        }
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

    private class TableListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (getDeleteButton() != null && getTable() != null) {
                hasSelectedItem = getTable().getSelectedRows().length > 0;
                getDeleteButton().setEnabled(hasSelectedItem);
                if (hasSelectedItem) {
                    showSelectedItem(getSelectedItems().get(0));
                }
            }
        }
    }

    public List<Object> getSelectedItems() {
        List<Object> objList = new ArrayList<>();
        if (getTable() != null) {
            for (int index : getTable().getSelectedRows()) {
                Object item = ((LeaveManagerTableModel) getTable().getModel()).getRow(index);
                objList.add(item);
            }
        }
        return objList;
    }

    public Object getSelectedItem() {
        if (hasSelectedItem) {
            return getSelectedItems().get(0);
        }
        return null;
    }

    public void clear() {
        clearFields();
        if (getTable() != null) {
            getTable().clearSelection();
        }
    }

    public void updateTable(Object item, UpdateType updateType) {
        if (getTable() != null) {
            LeaveManagerTableModel model = (LeaveManagerTableModel) getTable().getModel();
            if (updateType == UpdateType.CREATE) {
                model.createRow(item);
            } else if (updateType == UpdateType.EDIT) {
                model.editRow(item);
            } else if (updateType == UpdateType.DESTROY) {
                model.destroyRow(item);
            }
            clear();
        }
    }

    public abstract void loadData();

    public abstract void flesh(Object item);

    public abstract JButton getOkButton();

    public abstract JButton getDeleteButton();

    public abstract JTable getTable();

    public abstract void clearFields();

    public abstract void showSelectedItem(Object item);
}
