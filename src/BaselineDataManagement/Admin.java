package BaselineDataManagement;

public class Admin {
		private static String password ="default12345"; // �ʱⰪ
		private static boolean adminState = false; // �ʱⰪ�� false �α����ϴ¼��� true �ٽ� �α׾ƿ��ϸ� false

	public Admin() {
		
	}
	public Admin(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean getAdminState() {
		return adminState;
	}
	public void setAdminState(Boolean adminState) {
		this.adminState = adminState;
	}
}
