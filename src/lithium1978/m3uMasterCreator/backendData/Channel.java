package lithium1978.m3uMasterCreator.backendData;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856176905549154671L;
	private String tvgID;
	private String tvgName;
	private String tvgLogo;
	private String groupTitle;
	private Boolean isIncluded;
	private	LocalDateTime dateAdded;
	private String lineOne;
	private String lineTwo;
	private String sourceURL;
	private String tvgCustomName;

	public Channel (String tvgID, String tvgName, String tvgCustomName, String tvgLogo, String groupTitle, 
			Boolean isIncluded, LocalDateTime dateAdded, String lineOne, String lineTwo, String sourceURL) {

		this.tvgID = tvgID;
		this.tvgName = tvgName;
		this.tvgCustomName= tvgCustomName;
		this.tvgLogo = tvgLogo;
		this.groupTitle = groupTitle;
		this.isIncluded = isIncluded;
		this.dateAdded = dateAdded;
		this.lineOne = lineOne;
		this.lineTwo = lineTwo;
		this.sourceURL = sourceURL;

	}

	public Object getTvgCustomName() {
		return tvgCustomName;
	}

	public void setTvgCustomName(String tvgCustomName) {
		this.tvgCustomName = tvgCustomName;
	}

	public String getTvgID() {
		return tvgID;
	}
	public void setTvgID(String tvgID) {
		this.tvgID = tvgID;
	}
	public String getTvgName() {
		return tvgName;
	}
	public void setTvgName(String tvgName) {
		this.tvgName = tvgName;
	}
	public String getTvgLogo() {
		return tvgLogo;
	}
	public void setTvgLogo(String tvgLogo) {
		this.tvgLogo = tvgLogo;
	}
	public String getGroupTitle() {
		return groupTitle;
	}
	public void setGroupTitle(String groupTitle) {
		this.groupTitle = groupTitle;
	}
	public Boolean getIsIncluded() {
		return isIncluded;
	}
	public void setIsIncluded(Boolean isIncluded) {
		this.isIncluded = isIncluded;
	}
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(LocalDateTime localDateTime) {
		this.dateAdded = localDateTime;
	}
	public String getLineOne() {
		return lineOne;
	}
	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}
	public String getLineTwo() {
		return lineTwo;
	}
	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}
	public String getSourceURL() {
		return sourceURL;
	}
	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

}
