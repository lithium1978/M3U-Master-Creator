package lithium1978.m3uMasterCreator.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import lithium1978.m3uMasterCreator.backendData.*;



public class ChannelTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Channel> cd;
	private String[] colNames = {"Enabled", "Date Added", "Tvg-ID", "Tvg-Name", "Tvg-Logo", "Group Title", "Source"};
	public ChannelTableModel() {

	}

	public TableModel getTableModel() {
		return this;
	}

	public String getColumnName(int column) {
		return colNames[column];
	}

	public void setData(List<Channel> cd) {
		if(cd == null) {
			return;
		}
		this.cd = cd;
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		if(cd == null) {
			return 0;
		}
		return cd.size();
	}
	public List<Channel> getChanList(){
		if(cd == null) {
			return null;
		}
		return cd;
	}

	@Override
	public Object getValueAt(int row, int col) {
		Channel channel = cd.get(row);

		switch(col) {
		case 0:
			return channel.getIsIncluded();
		case 1:
			return channel.getDateAdded();
		case 2:
			return channel.getTvgID();
		case 3:
			return channel.getTvgCustomName();
		case 4:
			return channel.getTvgLogo();
		case 5:
			return channel.getGroupTitle();
		case 6:
			return channel.getSourceURL();
		}

		return null;
	}

	@Override
	public void setValueAt(Object value, int row, int col)
	{
		Channel channel = cd.get(row);
		if (col==0)
			channel.setIsIncluded((Boolean)value);
		else if (col==2)
			channel.setTvgID((String)value);
		else if (col==3)
			channel.setTvgCustomName((String)value);
		else if (col==4)
			channel.setTvgLogo((String)value);
		else if (col==5)
			channel.setGroupTitle((String)value);
		fireTableCellUpdated(row,col);
	}

	public Class<?> getColumnClass (int column) {
		switch (column) {
		case 0: return Boolean.class;

		case 1: return LocalDateTime.class;

		case 2: return String.class;

		case 3:  return String.class;

		case 4: return String.class;

		case 5: return String.class;

		case 6: return String.class;

		default: return String.class;
		}

	}

	@Override
	public boolean isCellEditable(int row, int col) {
		switch (col) {
		case 0: return true;
		case 1: return false;
		case 2: return true;
		case 3: return true;
		case 4: return true;
		case 5: return true;
		default:
			return false;
		}  
	}

	//	public void getSelectedChannels() {
	//		
	//		ChannelTableModel ct = new ChannelTableModel();
	//		JTable table =	ct.getTable();
	//		
	//		String i = (String) table.getValueAt(1, 1);
	//		System.out.println(i);
	//		
	//		int row = this.getRowCount();
	//		int h = 0;
	//		String val = null;
	//		
	//		while(h<row) {
	//			Boolean chk = (Boolean)this.getValueAt(h,0);
	//			if(chk) {
	//			val=	(String) this.getValueAt(h,3);
	//			System.out.println(val);
	//			h++;
	//			
	//		
	//			}
	//		}
	//
	//	}
}
