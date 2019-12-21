package CustomerManagement;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JButton;

public class updateCustomerUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel lblNewLabel_3;
	private JTextField nameField;
	private JSpinner birthdateField;
	private JButton confirm;
	private JButton cancles;
	private JLabel label_2;
	private JTextField phoneNumField;

	private Customer c;
	private CustomerManager cm;
	private JLabel label_1;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enr_pro frame = new enr_pro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	
	private boolean isStringInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
	private boolean isProperPhoneNum(String s) {
		String regexp = "^01(?:0|1|[6-9])(\\d{7}|\\d{8})$";
        
        if(s.matches(regexp)){
           return true;
        }
        
        return false;
	}
	
	private boolean isProperDate(Date birthdate) {
		Date now = new Date();
		
		int flag = birthdate.compareTo(now);
		
		// birthdate < now
		if(flag == -1) return true;
		
		return false;
	}
	
	/**
	 * Create the frame.
	 */
	public updateCustomerUI(Customer c, CustomerManager cm) {
		this.c = c;
		this.cm = cm;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getLabel());
		contentPane.add(getLblNewLabel_3());
		contentPane.add(getNameField());
		contentPane.add(getBirthdate());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		contentPane.add(getLabel_2());
		contentPane.add(getPhoneNumField());
		
		nameField.setText(c.getName());
		phoneNumField.setText(c.getPhoneNum());
		contentPane.add(getLabel_1());
		
		// 수정버튼 actionListener
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = nameField.getText();
				String phoneNum = phoneNumField.getText();
				Date birthdate = (Date)birthdateField.getValue();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				
				if(name.equals("") || phoneNum.equals("")) {
					JOptionPane.showMessageDialog(null, "빈칸을 채워 주세요", "등록 실패", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if(!(isStringInteger(phoneNum))) {
					JOptionPane.showMessageDialog(null, "숫자만 입력해주세요", "숫자가 아닌 문자", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!(isProperPhoneNum(phoneNum))) {
					JOptionPane.showMessageDialog(null, "전화체계가 맞지 않습니다", "잘못된 전화번호", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(!(isProperDate(birthdate))) {
					JOptionPane.showMessageDialog(null, "생년월일이 적절하지 않습니다.", "잘못된 생년월일", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Customer updatedCustomer = new Customer(c.getCustomerID(), name, phoneNum, java.sql.Date.valueOf(sdf.format(birthdate)), c.getInterest());
				
				boolean success = false;
				
				success = cm.updateCustomer(updatedCustomer);
				
				if(success) {
					JOptionPane.showMessageDialog(null, "고객 등록 완료", "등록 완료", JOptionPane.WARNING_MESSAGE);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "고객 등록 실패", "등록 실패", JOptionPane.WARNING_MESSAGE);
				}
				
				setVisible(false);
			}
		});
		
		cancles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
		
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("\uACE0\uAC1D \uC218\uC815");
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(12, 10, 410, 30);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("\uC774\uB984");
			label.setLabelFor(getNameField());
			label.setBounds(12, 50, 57, 15);
		}
		return label;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("\uC804\uD654\uBC88\uD638");
			lblNewLabel_3.setBounds(12, 115, 57, 15);
		}
		return lblNewLabel_3;
	}
	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setBounds(81, 47, 139, 21);
			nameField.setColumns(10);
		}
		return nameField;
	}
	private JSpinner getBirthdate() {
		if (birthdateField == null) {
			
		    Calendar calendar = Calendar.getInstance();//날짜
		    Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
		    Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
		    Date end = calendar.getTime();
		    //calendar.setTime(c.getBirthdate());
		    SpinnerDateModel dateModel = new SpinnerDateModel(c.getBirthdate(), start, end, Calendar.YEAR);
		    birthdateField = new JSpinner(dateModel);
		    birthdateField.setEditor(new JSpinner.DateEditor(birthdateField, "yyyy년 MM월 dd일"));
		    //enr_date.setModel(dateModel);
			//enr_date.setModel(new SpinnerDateModel(new Date(1556895600000L), null, null, Calendar.AM_PM));
		    birthdateField.setBounds(81, 171, 139, 22);
		 
		}
		return birthdateField;
	}
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uC218\uC815");
			confirm.setBounds(252, 206, 79, 45);
		}
		return confirm;
	}
	private JButton getCancles() {
		if (cancles == null) {
			cancles = new JButton("\uCDE8\uC18C");
			cancles.setBounds(343, 206, 79, 45);
		}
		return cancles;
	}	
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
			label_2.setBounds(12, 175, 57, 15);
		}
		return label_2;
	}
	private JTextField getPhoneNumField() {
		if (phoneNumField == null) {
			phoneNumField = new JTextField();
			phoneNumField.setBounds(83, 111, 139, 21);
			phoneNumField.setColumns(10);
		}
		return phoneNumField;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("\uD558\uC774\uD3F0\uC5C6\uC774 \uC22B\uC790\uB9CC \uC785\uB825");
			label_1.setBounds(230, 115, 153, 15);
		}
		return label_1;
	}
}
