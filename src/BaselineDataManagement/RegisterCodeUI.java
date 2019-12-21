package BaselineDataManagement;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class RegisterCodeUI extends JFrame {

	private JPanel contentPane;
	private JTextField numbertextField;
	private JTextField nametextField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegisterCodeUI frame = new RegisterCodeUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public RegisterCodeUI(CodeManager cm) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(55, 10, 369, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("코드 관리");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(12, 10, 294, 37);
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(65, 66, 321, 105);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uCF54\uB4DC \uBC88\uD638 : ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(27, 22, 90, 33);
		panel_1.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\uCF54\uB4DC \uC774\uB984 : ");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(27, 68, 90, 33);
		panel_1.add(label_1);
		
		numbertextField = new JTextField();
		numbertextField.setBounds(113, 24, 151, 30);
		panel_1.add(numbertextField);
		numbertextField.setColumns(10);
		
		nametextField = new JTextField();
		nametextField.setColumns(10);
		nametextField.setBounds(113, 65, 151, 30);
		panel_1.add(nametextField);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(26, 181, 428, 82);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		
	
		
		JButton btnNewButton = new JButton("등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isStringInteger(numbertextField.getText())){
					JOptionPane.showMessageDialog(null, "코드번호를 정수로 입력해주세요","코드 번호 타입 에러", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				boolean duplication = true;
				ArrayList<Code> cl=new ArrayList<>();
				cl=cm.inquiryCode("");
				for(int i = 0 ; i < cl.size();i++){
					if(Integer.parseInt(numbertextField.getText())==cl.get(i).getNumber()){
						duplication = false;
					}
				}
				
				if(duplication == false){
					JOptionPane.showMessageDialog(null, "코드번호가 중복되었습니다","코드 번호 중복", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					return;
				}
				else {
				Code c = new Code();
				c.setNumber(Integer.parseInt(numbertextField.getText()));
				c.setName(nametextField.getText());
				
				Boolean chk = cm.RegisterCode(c);
				if(chk.equals(true)) {
					JOptionPane.showMessageDialog(null, "코드가 등록되었습니다","코드 등록 성공", JOptionPane.INFORMATION_MESSAGE);
				}
					dispose();
					
				}
				
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.setBounds(113, 22, 98, 39);
		panel_2.add(btnNewButton);
		
		JButton button = new JButton("취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
		button.setBounds(249, 22, 98, 39);
		panel_2.add(button);
	}
	private boolean isStringInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}

}
