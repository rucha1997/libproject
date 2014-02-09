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
import javax.swing.table.AbstractTableModel;
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

    final MainForm mainForm;

    private boolean hasSelectedItem = false;

    public LeaveManagerFrame(MainForm mainForm) {
        this.mainForm = mainForm;
    }

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

    public void dataChanged(LeaveManagerFrame source) {
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

    public abstract class LeaveManagerTableModel extends AbstractTableModel {

        private List<Object> rows;

        public List<Object> getRows() {
            if (rows == null) {
                rows = new ArrayList<>();
            }
            return rows;
        }

        public void createRow(Object row) {
            if (!getRows().contains(row)) {
                getRows().add(row);
                int rowIndex = getRows().indexOf(row);
                fireTableRowsInserted(rowIndex, rowIndex);
            }
        }

        public void editRow(Object row) {
            if (getRows().contains(row)) {
                int rowIndex = getRows().indexOf(row);
                getRows().set(rowIndex, row);
                fireTableRowsUpdated(rowIndex, rowIndex);
            }
        }

        public void destroyRow(Object row) {
            if (getRows().contains(row)) {
                int rowIndex = getRows().indexOf(row);
                getRows().remove(row);
                fireTableRowsDeleted(rowIndex, rowIndex);
            }
        }

        @Override
        public int getRowCount() {
            return getRows().size();
        }

        @Override
        public int getColumnCount() {
            return getColumns().length;
        }

        @Override
        public String getColumnName(int column) {
            return getColumns()[column];
        }

        public abstract String[] getColumns();

        @Override
        public Class getColumnClass(int column) {
            for (int i = 0; i < getRows().size(); i++) {
                Object value = getValueAt(i, column);
                if (value != null) {
                    return value.getClass();
                }
            }
            return Object.class;
        }

        public Object getRow(int rowIndex) {
            return rows.get(rowIndex);
        }

        public void clear() {
            int firstRow = 0;
            int lastRow = getRows().isEmpty() ? 0 : getRows().size() - 1;
            getRows().clear();
            fireTableRowsDeleted(firstRow, lastRow);
        }
    }
}
