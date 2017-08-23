package lithium1978.m3uMasterCreator.backendData;

import java.io.Serializable;

public class GroupTitle implements Serializable, Comparable <GroupTitle> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5081150703297180011L;
	private String groupTitle, replacementTitle;
	
	 public String toString() {
	        return ("(" + groupTitle + ", " + replacementTitle +")");
	    }
	
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

//	 public static Comparator<GroupTitle> groupTitleComparator = new Comparator<GroupTitle>() {
//
//		@Override
//		public int compare(GroupTitle gt1, GroupTitle gt2) {
//			return (int) (gt1.getGroupTitle().compareTo(gt2.getGroupTitle()));   
//		}       
//	};

	@Override
	public int compareTo(GroupTitle o) {
		return this.groupTitle.toLowerCase().compareTo(o.groupTitle.toLowerCase());
		}
}
