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

public class AdminManagerUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AdminManagerUI frame = new AdminManagerUI();
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
	public AdminManagerUI() {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(67, 10, 522, 57);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\uAD00\uB9AC\uC790 \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(70, 10, 294, 37);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(25, 110, 564, 127);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton btnNewButton = new JButton("\uBE44\uBC00\uBC88\uD638 \uB4F1\uB85D");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayRegisterAdminPasswordUI();
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.setBounds(12, 74, 132, 41);
		panel_1.add(btnNewButton);

		JButton button = new JButton("로그인");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayLoginAdminUI();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
		button.setBounds(156, 74, 118, 41);
		panel_1.add(button);

		JButton button_1 = new JButton("로그아웃");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();
				if (admin.getAdminState() == true) {
					admin.setAdminState(false);
					JOptionPane.showMessageDialog(null, "관리자 로그아웃 하였습니다.", "관리자 로그아웃 성공",JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "관리자 로그인부터 해야합니다.", "관리자 로그아웃 실패", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		button_1.setFont(new Font("굴림", Font.PLAIN, 15));
		button_1.setBounds(286, 74, 119, 41);
		panel_1.add(button_1);

		JButton button_2 = new JButton("\uBE44\uBC00\uBC88\uD638 \uBCC0\uACBD");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();

				if (admin.getAdminState() == true) {
					displayChangeAdminPasswordUI();
				}
				else {
					JOptionPane.showMessageDialog(null, "관리자 로그인부터 해야합니다.", "관리자 비밀번호 병경 실패", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		button_2.setFont(new Font("굴림", Font.PLAIN, 15));
		button_2.setBounds(417, 74, 132, 41);
		panel_1.add(button_2);

	}

	public void displayRegisterAdminPasswordUI() {

		RegisterAdminPasswordUI newScr = new RegisterAdminPasswordUI();
		newScr.setVisible(true);
	}

	public void displayLoginAdminUI() {

		LoginAdminUI newScr = new LoginAdminUI();
		newScr.setVisible(true);
	}

	public void displayChangeAdminPasswordUI() {

		ChangeAdminPasswordUI newScr = new ChangeAdminPasswordUI();
		newScr.setVisible(true);
	}
}
