package RecordManagement;

import java.util.Date;

// 이력 클래스
public class Record {
	public final static int REGISTER = 0;
	public final static int UPDATE = 1;
	public final static int DELETE = 2;
	
	protected int managementID;
	protected Date recordTime;
	
	public Record(int managementID) {
		this.managementID = managementID;
		recordTime = new Date();
	}
	
	public int getManagementID() {
		return managementID;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
}