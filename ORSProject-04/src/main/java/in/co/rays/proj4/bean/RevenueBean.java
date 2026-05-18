package in.co.rays.proj4.bean;



public class RevenueBean extends BaseBean{
	
	private long expenseId;
	private String expenseCode;
	private Double amount;
	private String category;
	private String status;
	public long getExpenseId() {
		return expenseId;
	}
	public void setExpenseId(long expenseId) {
		this.expenseId = expenseId;
	}
	public String getExpenseCode() {
		return expenseCode;
	}
	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return getExpenseCode() + " " + getAmount();
	}
	

}
