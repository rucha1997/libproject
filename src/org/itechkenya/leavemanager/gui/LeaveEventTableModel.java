package org.itechkenya.leavemanager.gui;

import org.itechkenya.leavemanager.domain.LeaveEvent;

/**
 *
 * @author gitahi
 */
public class LeaveEventTableModel extends LeaveManagerTableModel {

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= getRows().size()) {
            return null;
        }
        LeaveEvent leaveEvent = (LeaveEvent) getRow(rowIndex);
        switch (columnIndex) {
            case 0:
                return leaveEvent.getContract().getEmployee();
            case 1:
                return leaveEvent.getContract();
            case 2:
                return leaveEvent.getContractYear();
            case 3:
                return leaveEvent.getStartDate();
            case 4:
                return leaveEvent.getEndDate();
            case 5:
                return leaveEvent.getLeaveType();
            case 6:
                return leaveEvent.getDaysEarned();
            case 7:
                return leaveEvent.getDaysSpent();
            case 8:
                return leaveEvent.getComment();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        Object value = getValueAt(0, column);
        if (value != null) {
            return value.getClass();
        }
        return Object.class;
    }

    @Override
    public String[] getColumns() {
        String[] columns = {"Employee", "Contract", "Contract Year", "Start Date", "End Date", "Leave Type", "Days Earned", "Days Spent", "Comment"};
        return columns;
    }
}
