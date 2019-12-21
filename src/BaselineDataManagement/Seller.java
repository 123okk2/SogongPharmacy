package BaselineDataManagement;

public class Seller{
	private String name;
	private String phoneNum;

	public Seller() {
		
	}
	public Seller(String name, String phoneNum) {
		this.name = name;
		this.phoneNum = phoneNum;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String toString() {
		return name + phoneNum;
	}
}