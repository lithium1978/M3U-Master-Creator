package lithium1978.m3uMasterCreator.backendData;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Channel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856176905549154671L;
	private String tvgID, tvgName, tvgLogo, groupTitle, lineOne, lineTwo, sourceURL, tvgCustomName, provider;
	private boolean isIncluded;
	private	LocalDateTime dateAdded;


	public Channel (String tvgID, String tvgName, String tvgCustomName, String tvgLogo, String groupTitle, 
			boolean isIncluded, LocalDateTime dateAdded, String lineOne, String lineTwo, String sourceURL, String provider) {

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
		this.provider = provider;

	}


	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((tvgName == null) ? 0 : tvgName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Channel other = (Channel) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (tvgName == null) {
			if (other.tvgName != null)
				return false;
		} else if (!tvgName.equals(other.tvgName))
			return false;
		return true;
	}

}
