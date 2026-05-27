package in.co.rays.proj4.bean;

import java.util.Date;

public class MeetingBean extends BaseBean{
	
	
	private String hostName;
	private String platform;
	private Integer duration;
	private Integer participants;
	
	
	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}


	public Integer getDuration() {
		return duration;
	}


	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	public Integer getParticipants() {
		return participants;
	}


	public void setParticipants(Integer participants) {
		this.participants = participants;
	}


	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return hostName + " " + platform;
	}

}
