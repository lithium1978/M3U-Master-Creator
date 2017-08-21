package lithium1978.m3uMasterCreator.backendData;

import java.io.Serializable;

public class GroupTitle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5081150703297180011L;
	private String groupTitle;
	private String replacementTitle;
	
	public GroupTitle(String groupTitle, String replacementTitle) {
		this.groupTitle = groupTitle;
		this.replacementTitle = replacementTitle;
	}
	
	public String getGroupTitle() {
		return groupTitle;
	}
	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}
	public String getReplacementTitle() {
		return replacementTitle;
	}
	public void setReplacementTitle(String replacementTitle) {
		this.replacementTitle = replacementTitle;
	}
	
	

}
