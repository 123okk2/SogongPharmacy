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

public class RegisterSellerUI extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField phoneNumField;

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
	public RegisterSellerUI(SellerManager sm) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(44, 10, 357, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("판매자 관리");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(12, 10, 294, 37);
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 77, 289, 96);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uD310\uB9E4\uC790 \uC774\uB984 : ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(27, 22, 107, 33);
		panel_1.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\uC804\uD654 \uBC88\uD638 :");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(27, 68, 90, 33);
		panel_1.add(label_1);
		
		nameField = new JTextField();
		nameField.setBounds(133, 24, 151, 30);
		panel_1.add(nameField);
		nameField.setColumns(10);
		
		phoneNumField = new JTextField();
		phoneNumField.setColumns(10);
		phoneNumField.setBounds(133, 64, 151, 30);
		panel_1.add(phoneNumField);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 183, 373, 96);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		
	
		
		JButton btnNewButton = new JButton("등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean duplication = true;
				ArrayList<Seller> sl=new ArrayList<>();
				sl=sm.inquirySeller("");
				
				for(int i = 0 ; i < sl.size();i++){
					if(nameField.getText().equals(sl.get(i).getName())){
						duplication = false;
					}
				}
				if(duplication == false){
					JOptionPane.showMessageDialog(null, "판매자 이름이 중복되었습니다","판매자 등록 실패", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				String regexp = "^01(?:0|1|[6-9])(\\d{7}|\\d{8})$";
				
				if(!(phoneNumField.getText().matches(regexp))){
					JOptionPane.showMessageDialog(null, "전화번호 형식이 어긋납니다","판매자 등록 실패", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				else {
					Seller s = new Seller();
					s.setName(nameField.getText());
					s.setPhoneNum(phoneNumField.getText());
					
					Boolean chk = sm.RegisterSeller(s);
					if(chk.equals(true)) {
						JOptionPane.showMessageDialog(null, "판매자가 등록되었습니다","판매자 등록 성공", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "판매자 등록에 실패하였습니다","판매자 등록 실패", JOptionPane.INFORMATION_MESSAGE);
						return;
					}	
				}
				
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.setBounds(69, 24, 101, 39);
		panel_2.add(btnNewButton);
		
		JButton button = new JButton("취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
		button.setBounds(196, 24, 101, 39);
		panel_2.add(button);
		
		JLabel label_2 = new JLabel("\uD558\uC774\uD47C\uC5C6\uC774 \uC22B\uC790\uB9CC \uC785\uB825");
		label_2.setBounds(305, 146, 134, 27);
		contentPane.add(label_2);
	}
}
