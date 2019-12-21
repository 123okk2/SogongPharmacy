package RecordManagement;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class InquiryClosingRecordUI extends JFrame {

	private JPanel contentPane;
	private JLabel label_1, label_2;
	private JSpinner term1, term2;
	private JButton button;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InquiryClosingRecordUI frame = new InquiryClosingRecordUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InquiryClosingRecordUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // x버튼을 누르면 해당 창만 종료
		setTitle("마감 이력 조회");
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLabel_1());
		contentPane.add(getLabel_2());
		contentPane.add(getTerm1());
		contentPane.add(getTerm2());
		contentPane.add(getButton());
		contentPane.add(getScrollPane());
		
		RecordManager rm = new RecordManager();
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Date t1 = (Date)(term1.getValue());
				Date t2 = (Date)(term2.getValue());
				
				if(t1.getTime() > t2.getTime()) {
					JOptionPane.showMessageDialog(null, "올바른 기간을 입력하십시오", "기간 선택 알림", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				ClosingRecord[] csrList = null;
				
				try {
					csrList = (ClosingRecord[])(rm.inquiryRecordList(RecordManager.CLOSING_RECORD, t1, t2));
					
					if(csrList.length == 0) {
						JOptionPane.showMessageDialog(null, "조회할 데이터가 없습니다", "기간 선택 알림", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setNumRows(0); //테이블 초기화
					
					for(int i=0; i<csrList.length; i++) {
						String mngID = null;
						if(csrList[i].getManagementID() == 0) mngID = "등록";
						if(csrList[i].getManagementID() == 1) mngID = "수정";
						if(csrList[i].getManagementID() == 2) mngID = "삭제";
						
						model.addRow(new Object[]{
							mngID,
							new SimpleDateFormat("yyyy-MM-dd").format(csrList[i].getDate()),
							csrList[i].getSalesPerDay(),
							csrList[i].getBalance(),
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(csrList[i].getRecordTime())});
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	private JLabel getLabel_1() {
		label_1 = new JLabel("\uCC98\uC74C \uB0A0\uC9DC");
		label_1.setBounds(58, 31, 61, 22);
		
		return label_1;
	}
	private JLabel getLabel_2() {
		label_2 = new JLabel("\uB9C8\uC9C0\uB9C9 \uB0A0\uC9DC");
		label_2.setBounds(318, 31, 75, 22);
		
		return label_2;
	}
	private JSpinner getTerm1() {
		if (term1 == null) {
			Calendar calendar = Calendar.getInstance();//날짜
			Date end = calendar.getTime(); calendar.add(Calendar.YEAR, -5);
			Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 5);
			calendar.set(Calendar.MONTH, 0); calendar.set(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0); calendar.set(Calendar.MINUTE, 0); calendar.set(Calendar.SECOND, 0);
			Date value = calendar.getTime();
			SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
			term1 = new JSpinner(dateModel);
			term1.setEditor(new JSpinner.DateEditor(term1, "yyyy-MM-dd HH:mm:ss"));
			term1.setBounds(133, 31, 139, 22);
		}
		
		return term1;
	}
	private JSpinner getTerm2() {
		if (term2 == null) {
			Calendar calendar = Calendar.getInstance();//날짜
			Date value = calendar.getTime();
			Date end = calendar.getTime();
			calendar.set(Calendar.MONTH, 0); calendar.set(Calendar.DATE, 1);
			Date start = (Date)(term1.getValue());
			SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
			term2 = new JSpinner(dateModel);
			term2.setEditor(new JSpinner.DateEditor(term2, "yyyy-MM-dd HH:mm:ss"));
			term2.setBounds(407, 31, 139, 22);
		}
		
		return term2;
	}
	private JButton getButton() {
		button = new JButton("\uC870\uD68C");
		button.setBounds(480, 65, 105, 57);
		
		return button;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 132, 575, 297);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"관리명칭", "일자", "매출액", "잔액", "기록시간"
				}
			) {
				Class[] columnTypes = new Class[] {
					String.class, String.class, Integer.class, Integer.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					true, false, false, true, true, true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		}
		return table;
	}
}
