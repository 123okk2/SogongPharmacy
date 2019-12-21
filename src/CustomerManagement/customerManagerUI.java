package CustomerManagement;
import java.awt.BorderLayout; 
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;

import CustomerManagement.CustomerManager;
import DBManagement.DBManager;
import javax.swing.JComboBox;

public class customerManagerUI extends JFrame {
	CustomerManager cm;
	
	private JPanel contentPane;
	private JButton customerInquiryBtn;
	private JButton customerRegisterBtn;
	private JButton customerUpdateBtn;
	private JButton customerDeleteBtn;
	private JLabel lblNewLabel;
	private JScrollPane scrollPane;
	
	private JTextField nameField;
	private JTextField phoneNumField;
	private JSpinner birthdateSpinner;
	private JTextField yearTextField;
	private JLabel yearLabel;
	private JTextField monthTextField;
	private JLabel lblNewLabel_2;
	private JTextField dayTextField;
	private JLabel dayLabel;

	//private JTable customerTable;
	private JTable table;
	
	private DefaultTableModel model;
	
	//private SpinnerDateModel dateModel;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mshow_enrolls frame = new Mshow_enrolls();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

//	private boolean isStringInteger(String s) {
//		try {
//			Integer.parseInt(s);
//			return true;
//		}
//		catch(NumberFormatException e) {
//			return false;
//		}
//	}
//	
//	private boolean isProperPhoneNum(String s) {
//		String regexp = "^01(?:0|1|[6-9])(\\d{7}|\\d{8})$";
//        
//        if(s.matches(regexp)){
//           return true;
//        }
//        
//        return false;
//	}
//	
//	private boolean isProperDate(Date birthdate) {
//		Date now = new Date();
//		
//		int flag = birthdate.compareTo(now);
//		
//		// birthdate < now
//		if(flag == -1) return true;
//		
//		return false;
//	}
	
	/**
	 * Create the frame.
	 */
	public customerManagerUI() {
		cm = new CustomerManager();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		contentPane.add(getCustomerInquiryBtn());
		contentPane.add(getCustomerRegisterBtn());
		contentPane.add(getCustomerUpdateBtn());
		contentPane.add(getCustomerDeleteBtn());
		contentPane.add(getLblNewLabel());
		contentPane.add(getScrollPane());
		
		//contentPane.add(getBirthdate());
		
		JLabel label = new JLabel("\uACE0\uAC1D \uBA85 : ");
		label.setBounds(12, 64, 50, 15);
		contentPane.add(label);
		
		nameField = new JTextField();
		nameField.setBounds(74, 61, 96, 21);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		JLabel label_1 = new JLabel("\uC804\uD654\uBC88\uD638 : ");
		label_1.setBounds(182, 64, 62, 15);
		contentPane.add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel("\uC0DD\uB144\uC6D4\uC77C : ");
		lblNewLabel_1.setBounds(355, 64, 67, 15);
		contentPane.add(lblNewLabel_1);
		
		phoneNumField = new JTextField();
		phoneNumField.setBounds(247, 61, 96, 21);
		contentPane.add(phoneNumField);
		phoneNumField.setColumns(10);
		contentPane.add(getYearTextField());
		contentPane.add(getYearLabel());
		contentPane.add(getMonthTextField());
		contentPane.add(getLblNewLabel_2());
		contentPane.add(getDayTextField());
		contentPane.add(getDayLabel());
		
		/*JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InquiryInterestCustomerUI iic = new InquiryInterestCustomerUI(cm);
				iic.setVisible(true);
			}
		});
		
		btnNewButton.setBounds(252, 119, 91, 23);
		contentPane.add(btnNewButton);*/
		
		customerInquiryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//검색조건
				String year = yearTextField.getText();
				String month = monthTextField.getText();
				String day = dayTextField.getText();
				//Date birthdate = (Date)dateModel.getValue();
				
				if(year.equals("")) year = "1900";
				if(month.equals("")) month = "01";
				if(day.equals("")) day = "01";
				
				String name = nameField.getText();
				String phoneNum = phoneNumField.getText();
				String birthdate = year + "-" + month + "-" + day;
				
				
//				if(!(isStringInteger(phoneNum))) {
//					JOptionPane.showMessageDialog(null, "숫자만 입력해주세요", "숫자가 아닌 문자", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				
//				if(!(isProperPhoneNum(phoneNum))) {
//					JOptionPane.showMessageDialog(null, "전화체계가 맞지 않습니다", "잘못된 전화번호", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				
//				if(!(isProperDate(new Date(birthdate)))) {
//					JOptionPane.showMessageDialog(null, "생년월일이 적절하지 않습니다.", "잘못된 생년월일", JOptionPane.ERROR_MESSAGE);
//					return;
//				}
//				
				
				ArrayList<Customer> r = new ArrayList<>();
				r = cm.inquiryCustomer(name, phoneNum, java.sql.Date.valueOf(birthdate));
				
				SimpleDateFormat t=new SimpleDateFormat("yyyy-MM-dd");
				//table에 정보 추가 하는 부분
				model = (DefaultTableModel)table.getModel(); // DefaultTableModel클래스로 테이블의 모델을 get하고 
				model.setNumRows(0); //테이블 초기화
				
				int count = r.size();
				
				if(count <= 0) {
					JOptionPane.showMessageDialog(null, "조회된 고객이 없습니다.", "조회 결과", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				for(int i=0;i<r.size();i++) {
					//열 추가
					Customer c = r.get(i);
					
					model.addRow(new Object[]{false, c.getName(), c.getPhoneNum(), c.getBirthdate(), c.getInterest(), 1});
				}
			}
		});
		
		/*customerInquiryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String name = nameField.getText();
				String phoneNum = phoneNumField.getText();
				
				String year = yearTextField.getText();
				String month = monthTextField.getText();
				String day = dayTextField.getText();
				//Date birthdate = (Date)dateModel.getValue();
				
				if(year.equals("")) year = "1900";
				if(month.equals("")) month = "01";
				if(day.equals("")) day = "01";
				
				String birthdate = year + "-" + month + "-" + day;
				
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        //birthdate = new Date(sdf.format(birthdate));
				
				
				ArrayList<Customer> cList = cm.inquiryCustomer(name, phoneNum, java.sql.Date.valueOf(birthdate));
				
				Vector data = new Vector<>();
		        for (int i = 0; i < cList.size(); i++) {
		            Vector row = new Vector<>();
		           
		            Customer c = cList.get(i);
		            
		            row.add(Boolean.class);
		            row.add(c.getName());
		            row.add(c.getPhoneNum());
		            row.add(c.getBirthdate());
		            row.add(c.getInterest());
		           
		            data.add(row);
		        }
		        
		        customerTable = null;
		        scrollPane.setViewportView(getCustomerTable(data));
			}
		});*/
		
		customerRegisterBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//새 창 띄우기
				registerCustomerUI ep=new registerCustomerUI(cm);
				ep.setVisible(true);
			}
		});
		
		customerUpdateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int checkCnt = 0;
				int index = 0;
				for(int i = 0; i < table.getRowCount(); i++) {
					if((boolean)table.getValueAt(i, 0)) {
						checkCnt++;
						index = i;
					}
				}
				//새 창 띄우기
				if (checkCnt == 0) {
					JOptionPane.showMessageDialog(null, "항목을 선택하세요.", "항목이 선택되지 않았습니다.", JOptionPane.ERROR_MESSAGE);
				}
				
				else if(checkCnt == 1) {
					ArrayList<Customer> cList = cm.inquiryCustomer((java.lang.String)table.getValueAt(index, 1), (java.lang.String)table.getValueAt(index, 2), (java.sql.Date)table.getValueAt(index,3));
					
					Customer c = cList.get(0);
						
					updateCustomerUI ep=new updateCustomerUI(c, cm);
					ep.setVisible(true);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "한가지 항목만 선택해주세요.", "다중선택", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		
		customerDeleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int checkCnt = 0;
				int index = 0;
				for(int i = 0; i < table.getRowCount(); i++) {
					if((boolean)table.getValueAt(i, 0)) {
						checkCnt++;
						index = i;
					}
				}
				//새 창 띄우기
				if (checkCnt == 0) {
					JOptionPane.showMessageDialog(null, "항목을 선택하세요.", "항목이 선택되지 않았습니다.", JOptionPane.ERROR_MESSAGE);
				}
				
				else if(checkCnt == 1) {
					ArrayList<Customer> cList = cm.inquiryCustomer((java.lang.String)table.getValueAt(index, 1), (java.lang.String)table.getValueAt(index, 2), (java.sql.Date)table.getValueAt(index,3));
					
					Customer c = cList.get(0);
					
					cm.deleteCustomer(c.getCustomerID());
					
					JOptionPane.showMessageDialog(null, "선택한 항목이 삭제되었습니다.", "삭제완료", JOptionPane.YES_OPTION);
					/*
					updateCustomerUI ep=new updateCustomerUI(c, cm);
					ep.setVisible(true);*/
				}
				
				else {
					JOptionPane.showMessageDialog(null, "한가지 항목만 선택해주세요.", "다중선택", JOptionPane.ERROR_MESSAGE);
				}
				/*
				if(checkCnt == 0) {
					JOptionPane.showMessageDialog(null, "항목을 선택하세요.", "항목이 선택되지 않았습니다.", JOptionPane.ERROR_MESSAGE);
				}
				
				else {
					int result = 0;
					result=JOptionPane.showConfirmDialog(null, "선택하신 항목을 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					JOptionPane.showMessageDialog(null, result);
					//0-yes 1-no
					JOptionPane.showMessageDialog(null, "선택하신 항목이 정상적으로 삭제되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
				}*/
			}
			
		});
		
		/*print_enroll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				prt_enr ep=new prt_enr();
				ep.setVisible(true);
			}
			
		});
		
		print_barcodes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				prt_bcd ep=new prt_bcd();
				ep.setVisible(true);
				
			}
			
		});*/	
	}
	
	private JButton getCustomerInquiryBtn() {
		if (customerInquiryBtn == null) {
			customerInquiryBtn = new JButton("\uACE0\uAC1D \uC870\uD68C");
			customerInquiryBtn.setBounds(12, 102, 103, 56);
		}
		return customerInquiryBtn;
	}
	
	private JButton getCustomerRegisterBtn() {
		if (customerRegisterBtn == null) {
			customerRegisterBtn = new JButton("\uACE0\uAC1D \uB4F1\uB85D");
			customerRegisterBtn.setBounds(172, 102, 103, 56);
		}
		return customerRegisterBtn;
	}
	
	private JButton getCustomerUpdateBtn() {
		if (customerUpdateBtn == null) {
			customerUpdateBtn = new JButton("\uACE0\uAC1D \uC218\uC815");
			customerUpdateBtn.setBounds(328, 102, 109, 56);
		}
		return customerUpdateBtn;
	}
	
	private JButton getCustomerDeleteBtn() {
		if (customerDeleteBtn == null) {
			customerDeleteBtn = new JButton("\uACE0\uAC1D \uC0AD\uC81C");
			customerDeleteBtn.setBounds(480, 102, 109, 56);
		}
		return customerDeleteBtn;
	}
	
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("\uACE0\uAC1D \uAD00\uB9AC");
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setForeground(new Color(0, 0, 0));
			lblNewLabel.setBounds(12, 10, 577, 28);
		}
		return lblNewLabel;
	}
	
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 168, 577, 263);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	/*private JTable getCustomerTable(Vector datas) {
		if (customerTable == null) {
			//String[] title= {"고객명", "전화번호", "생년월일", "관심고객여부"};
			/*
			table.getColumnModel().getColumn(0).setPreferredWidth(70);
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
			table.getColumnModel().getColumn(2).setPreferredWidth(80);
			table.getColumnModel().getColumn(3).setPreferredWidth(80);
			table.getColumnModel().getColumn(4).setPreferredWidth(50);
			
			Vector title = new Vector<>();
			
			title.add("선택");
			title.add("고객명");
			title.add("전화번호");
			title.add("생년월일");
			title.add("관심고객여부");
			
			getContentPane().add(scrollPane);
			
			DefaultTableModel dtm = new DefaultTableModel(datas, title);

			customerTable = new JTable(datas,title);
		}
		return customerTable;
	}*/
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"", "이름", "전화번호", "생년월일", "관심고객여부"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, String.class, String.class, String.class, String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					true, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(4).setResizable(false);
		}
		return table;
	}
	/*
	private JSpinner getBirthdate() {
		if (birthdateSpinner == null) {
		    Calendar calendar = Calendar.getInstance();//날짜
		    Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
		    Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
		    Date end = calendar.getTime();
		    
		    dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
		    
		    birthdateSpinner = new JSpinner(dateModel);
		    birthdateSpinner.setEditor(new JSpinner.DateEditor(birthdateSpinner, "yyyy년 MM월 dd일"));
		    //enr_date.setModel(dateModel);
			//enr_date.setModel(new SpinnerDateModel(new Date(1556895600000L), null, null, Calendar.AM_PM));
		    birthdateSpinner.setBounds(450, 30, 139, 22);
		 
		}
		return birthdateSpinner;
	}*/
	private JTextField getYearTextField() {
		if (yearTextField == null) {
			yearTextField = new JTextField();
			yearTextField.setBounds(419, 61, 50, 21);
			yearTextField.setColumns(10);
		}
		return yearTextField;
	}
	private JLabel getYearLabel() {
		if (yearLabel == null) {
			yearLabel = new JLabel("\uB144");
			yearLabel.setBounds(469, 64, 21, 15);
		}
		return yearLabel;
	}
	private JTextField getMonthTextField() {
		if (monthTextField == null) {
			monthTextField = new JTextField();
			monthTextField.setBounds(481, 61, 35, 21);
			monthTextField.setColumns(10);
		}
		return monthTextField;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("\uC6D4");
			lblNewLabel_2.setBounds(516, 64, 21, 15);
		}
		return lblNewLabel_2;
	}
	private JTextField getDayTextField() {
		if (dayTextField == null) {
			dayTextField = new JTextField();
			dayTextField.setBounds(528, 61, 35, 21);
			dayTextField.setColumns(10);
		}
		return dayTextField;
	}
	private JLabel getDayLabel() {
		if (dayLabel == null) {
			dayLabel = new JLabel("\uC77C");
			dayLabel.setBounds(568, 64, 21, 15);
		}
		return dayLabel;
	}
}
