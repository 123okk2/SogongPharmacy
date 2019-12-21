package SupplierManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SupplierManagerUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton inquiry;
	private JButton register;
	private JButton update;
	private JButton delete;
	private JScrollPane scrollPane;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierManagerUI frame = new SupplierManagerUI();
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
	public SupplierManagerUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextField());
		contentPane.add(getInquiry());
		contentPane.add(getRegister());
		contentPane.add(getUpdate());
		contentPane.add(getDelete());
		contentPane.add(getScrollPane());
		
		
		//매니저들 선언
		SupplierManager sm=new SupplierManager();

		//나중에 여기에리코드 매니저 추가

		
							//조회 (별도의 함수구현x)
		inquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//검색조건
				String str=textField.getText();
				ArrayList<Supplier> r=new ArrayList<>();
				r=sm.inquirySupplier(textField.getText());
				
				SimpleDateFormat t=new SimpleDateFormat("yyyy-MM-dd");
				//table에 정보 추가 하는 부분
				DefaultTableModel model = (DefaultTableModel) table.getModel(); // DefaultTableModel클래스로 테이블의 모델을 get하고 
				model.setNumRows(0); //테이블 초기화
				for(int i=0;i<r.size();i++) {
					//열 추가
					model.addRow(new Object[]{false, r.get(i).getSupplierName(),
							r.get(i).getSupplierTel(), t.format(r.get(i).getSupplierPersonalDay()),
							r.get(i).getSupplierProduct(), r.get(i).getProductPrice()});
				}
			}
		});
							//등록 (별도 함수)
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayRegisterSupplierUI(sm);
			}
		});
							//삭제 (별도 함수x)
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//항목 선택/미선택 오류 식별
				if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "항목을 먼저 선택해주세요.", "삭제 실패", JOptionPane.INFORMATION_MESSAGE);
				else {
					//단순한 초기화. 의미 없음.
					Supplier s=new Supplier(1,"","",new Date(2019,05,17),"",1);
					//성공.실패 유무 판별용 변수
					Boolean chk=false;
					
					for(int i=0;i<table.getRowCount();i++) {
						if(table.getValueAt(i,0).equals(true)) {
							//String to Date
							String enrDate=table.getValueAt(i,3).toString();
							
							int yy=Integer.parseInt(enrDate.substring(0,4));
							int mm=Integer.parseInt(enrDate.substring(5,7));
							int dd=Integer.parseInt(enrDate.substring(8,10));

							/*
							s=new Supplier(1, table.getValueAt(i, 1).toString(),table.getValueAt(i, 2).toString()
									,new Date(yy-1900,mm-1,dd), table.getValueAt(i, 4).toString(),
									Integer.parseInt(table.getValueAt(i, 5).toString()));
							*/
							//입고 내역 삭제
							chk=sm.deleteSupplier(table.getValueAt(i, 1).toString());
							System.out.println(1);
							if(chk.equals(false)) {
								JOptionPane.showMessageDialog(null, "선택하신 항목의 삭제에 실패했습니다.", "삭제 실패", JOptionPane.INFORMATION_MESSAGE);
								break;
							}
						}
					}
				}
			}
		});
							//수정
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "항목을 먼저 선택해주세요.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
				else {
					try {
						displayUpdateReceivingUI(sm);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	public void displayRegisterSupplierUI(SupplierManager sm) {
		//입고 등록 화면 호출
		RegisterSupplierUI newScr=new RegisterSupplierUI(sm);
		newScr.setVisible(true);
	}
	
	public void displayUpdateReceivingUI(SupplierManager sm) throws ParseException {
		//입고 수정 화면 호출
		Supplier s=new Supplier(1,"","",new Date(2019,05,17),"",1);
		
		for(int i=0;i<table.getRowCount();i++) {
			if(table.getValueAt(i,0).equals(true)) {
				String enrDate=table.getValueAt(i,3).toString();
				
				int yy=Integer.parseInt(enrDate.substring(0,4));
				int mm=Integer.parseInt(enrDate.substring(5,7));
				int dd=Integer.parseInt(enrDate.substring(8, 10));

				s=new Supplier(1, table.getValueAt(i, 1).toString(),table.getValueAt(i, 2).toString()
						,new Date(yy-1900,mm-1,dd), table.getValueAt(i, 4).toString(),
						Integer.parseInt(table.getValueAt(i, 5).toString()));
						
				break;
			}
		}
		UpdateSupplierUI newScr=new UpdateSupplierUI(s,sm);
		newScr.setVisible(true);
	}
	
	
	

	
	
	
	
	
	
	
	
	
	//gui 생성
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(12, 10, 575, 45);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getInquiry() {
		if (inquiry == null) {
			inquiry = new JButton("거래처 조회");
			inquiry.setBounds(12, 65, 105, 57);
		}
		return inquiry;
	}
	private JButton getRegister() {
		if (register == null) {
			register = new JButton("거래처 등록");
			register.setBounds(166, 65, 105, 57);
		}
		return register;
	}
	private JButton getUpdate() {
		if (update == null) {
			update = new JButton("거래처 수정");
			update.setBounds(324, 65, 105, 57);
		}
		return update;
	}
	private JButton getDelete() {
		if (delete == null) {
			delete = new JButton("거래처 삭제");
			delete.setBounds(482, 65, 105, 57);
		}
		return delete;
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
					"", "\uAC70\uB798\uCC98\uBA85", "\uC5F0\uB77D\uCC98", "\uD734\uBB34\uC77C", "\uCDE8\uAE09\uC81C\uD488", "\uC81C\uD488\uAC00\uACA9"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, String.class, Integer.class, Object.class, String.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(5).setResizable(false);
		}
		return table;
	}
}
