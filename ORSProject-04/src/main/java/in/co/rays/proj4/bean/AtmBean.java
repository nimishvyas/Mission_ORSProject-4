package in.co.rays.proj4.bean;

public class AtmBean extends BaseBean{

	private String bankName;
	private String location;
	private Double cashAvailable;
	private Integer securityCode;
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getCashAvailable() {
		return cashAvailable;
	}

	public void setCashAvailable(Double cashAvailable) {
		this.cashAvailable = cashAvailable;
	}

	public Integer getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(Integer securityCode) {
		this.securityCode = securityCode;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return bankName + " " + location;
	}
	
}
