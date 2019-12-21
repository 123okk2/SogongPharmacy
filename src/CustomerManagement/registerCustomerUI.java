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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import javax.swing.JButton;

public class registerCustomerUI extends JFrame {
	private CustomerManager cm;
	
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel_1;
	private JTextField nameField;
	private JSpinner birthdateField;
	private JButton confirm;
	private JButton cancles;
	private JTextField phoneNumField;

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
	public registerCustomerUI(CustomerManager cm) {
		this.cm = cm;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getLabel());
		contentPane.add(getLabel_1());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getNameField());
		contentPane.add(getBirthdateField());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		contentPane.add(getPhoneNumField());
		
		JLabel lblNewLabel_2 = new JLabel("\uD558\uC774\uD3F0\uC5C6\uC774 \uC22B\uC790\uB9CC \uC785\uB825");
		lblNewLabel_2.setBounds(234, 114, 153, 15);
		contentPane.add(lblNewLabel_2);
		

		cancles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				
				
				
				Customer c = new Customer(name, phoneNum, java.sql.Date.valueOf(sdf.format(birthdate)));				
				
				boolean success = false;
				
				success = cm.registerCustomer(c);				
				
				if(success) {
					JOptionPane.showMessageDialog(null, "고객 등록 완료", "등록 완료", JOptionPane.WARNING_MESSAGE);
				}
				
				else {
					JOptionPane.showMessageDialog(null, "고객 등록 실패", "등록 실패", JOptionPane.WARNING_MESSAGE);
				}
				setVisible(false);
			}
		});
		
		
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("\uACE0\uAC1D \uB4F1\uB85D");
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
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("\uC804\uD654\uBC88\uD638");
			label_1.setBounds(12, 114, 57, 15);
		}
		return label_1;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
			lblNewLabel_1.setBounds(12, 176, 57, 15);
		}
		return lblNewLabel_1;
	}
	private JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
			nameField.setBounds(81, 47, 139, 21);
			nameField.setColumns(10);
		}
		return nameField;
	}
	private JSpinner getBirthdateField() {
		if (birthdateField == null) {
			
		    Calendar calendar = Calendar.getInstance();//날짜
		    Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
		    Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
		    Date end = calendar.getTime();
		    SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
			birthdateField = new JSpinner(dateModel);
		    birthdateField.setEditor(new JSpinner.DateEditor(birthdateField, "yyyy 년MM월 dd일"));
			birthdateField.setBounds(81, 172, 139, 22);
		}
		return birthdateField;
	}
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uB4F1\uB85D");
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
	private JTextField getPhoneNumField() {
		if (phoneNumField == null) {
			phoneNumField = new JTextField();
			phoneNumField.setColumns(10);
			phoneNumField.setBounds(83, 110, 139, 21);
		}
		return phoneNumField;
	}
}
