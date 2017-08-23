package lithium1978.m3uMasterCreator.model;

import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import lithium1978.m3uMasterCreator.backendData.*;

public class GroupTitleTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5091550134703370092L;
	private List<GroupTitle> groups;
	private String[] colNames = {"Group Title", "Group Title Replacement"};
	public GroupTitleTableModel() {

	}
	
	public TableModel getTableModel() {
		return this;
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

	public void setData(List<GroupTitle> groups) {
		if(groups == null) {
			return;
		}
		Collections.sort(groups);
		this.groups = groups;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		if(groups == null) {
			return 0;
		}
		return groups.size();
	}
	public List<GroupTitle> getGroupList(){
		if(groups == null) {
			return null;
		}
		return groups;
	}

	@Override
	public Object getValueAt(int row, int col) {
		GroupTitle groupTitle = groups.get(row);

		switch(col) {
		case 0:
			return groupTitle.getGroupTitle();
		case 1:
			return groupTitle.getReplacementTitle();
		}
		return null;
	}

	@Override
	public void setValueAt(Object value, int groupRow, int groupCol)
	{
		GroupTitle groupTitle = groups.get(groupRow);
		if (groupCol==0)
			groupTitle.setGroupTitle((String)value);
		else if (groupCol==1)
			groupTitle.setReplacementTitle((String)value);
//		fireTableCellUpdated(groupRow,groupCol);
	}

	public Class<?> getColumnClass (int column) {
		switch (column) {

		default: return String.class;
		}

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 0: return false;
		case 1: return true;
		default:
			return false;
		}  
	}

}
