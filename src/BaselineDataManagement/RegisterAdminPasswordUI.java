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

public class RegisterAdminPasswordUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterAdminPasswordUI frame = new RegisterAdminPasswordUI();
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
	public RegisterAdminPasswordUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(67, 10, 357, 57);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\uAD00\uB9AC\uC790 \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(12, 10, 294, 37);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(67, 77, 346, 65);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("\uAD00\uB9AC\uC790 \uBE44\uBC00\uBC88\uD638 : ");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(27, 22, 140, 33);
		panel_1.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(167, 24, 151, 30);
		panel_1.add(textField);
		textField.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(22, 164, 451, 72);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JButton btnNewButton = new JButton("등록");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();
				if (admin.getPassword().equals("default12345")) {
					if (textField.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "공백은 비밀번호가 될수 없습니다.", "비밀번호 등록 실패",	JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						admin.setPassword(textField.getText());
						JOptionPane.showMessageDialog(null, "관리자 비밀번호가 등록되었습니다.", "비밀번호 등록 성공",JOptionPane.INFORMATION_MESSAGE);
					}
				} 
				else {
					JOptionPane.showMessageDialog(null, "이미 비밀번호가 등록되어있습니다", "비밀번호 등록 실패",JOptionPane.INFORMATION_MESSAGE);
				}
				dispose();
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.setBounds(118, 23, 103, 39);
		panel_2.add(btnNewButton);

		JButton button = new JButton("취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
		button.setBounds(274, 23, 103, 39);
		panel_2.add(button);
	}

}
