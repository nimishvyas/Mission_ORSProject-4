package in.co.rays.proj4.bean;

import java.util.Date;

public class CacheEvictionBean {
	
	private long evictionId;
	private String evictionCode;
	private String keyName;
	private Date evictionTime;
	private String status;
	public long getEvictionId() {
		return evictionId;
	}
	public void setEvictionId(long evictionId) {
		this.evictionId = evictionId;
	}
	public String getEvictionCode() {
		return evictionCode;
	}
	public void setEvictionCode(String evictionCode) {
		this.evictionCode = evictionCode;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public Date getEvictionTime() {
		return evictionTime;
	}
	public void setEvictionTime(Date evictionTime) {
		this.evictionTime = evictionTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
