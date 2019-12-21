package BaselineDataManagement;

public class Admin {
		private static String password ="default12345"; // 초기값
		private static boolean adminState = false; // 초기값은 false 로그인하는순간 true 다시 로그아웃하면 false

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
