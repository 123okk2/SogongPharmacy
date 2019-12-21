package BaselineDataManagement;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChangeAdminPasswordUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField1;
	private JTextField textField2;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ChangeAdminPasswordUI frame = new ChangeAdminPasswordUI();
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
	public ChangeAdminPasswordUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(67, 10, 607, 57);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("\uAD00\uB9AC\uC790 \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(38, 10, 233, 37);
		panel.add(label);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(39, 77, 357, 106);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\uD604\uC7AC \uBE44\uBC00\uBC88\uD638 : ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(27, 22, 126, 33);
		panel_1.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("\uC0C8 \uBE44\uBC00\uBC88\uD638 : ");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(27, 68, 111, 33);
		panel_1.add(label_1);
		
		textField1 = new JTextField();
		textField1.setBounds(165, 24, 151, 30);
		panel_1.add(textField1);
		textField1.setColumns(10);
		
		textField2 = new JTextField();
		textField2.setBounds(165, 70, 151, 30);
		panel_1.add(textField2);
		textField2.setColumns(10);
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(49, 193, 379, 55);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("변경");
		btnNewButton.setBounds(46, 10, 94, 39);
		panel_2.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();
				if(textField1.getText().equals(admin.getPassword())){
					admin.setPassword(textField2.getText());
					JOptionPane.showMessageDialog(null, "관리자 비밀번호가 변경되었습니다.", "비밀번호 변경 성공", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
					JOptionPane.showMessageDialog(null, "현재 비밀번호가 틀렸습니다.", "비밀번호 변경 실패", JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		
		JButton button = new JButton("취소");
		button.setBounds(179, 10, 94, 39);
		panel_2.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
	}

}
