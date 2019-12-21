package BaselineDataManagement;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BaselineDataManagerUI extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaselineDataManagerUI frame = new BaselineDataManagerUI();
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
	public BaselineDataManagerUI() {
		
		
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615,478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("코드 관리");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();
				if(admin.getAdminState()==true){
					CodeManagerUI code = new CodeManagerUI();
					code.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "관리자 로그인이 안되어있습니다.", "코드 관리 실패", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
		button.setBounds(47, 145, 141, 53);
		contentPane.add(button);
		
		JButton button_1 = new JButton("관리자 관리");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminManagerUI admin = new AdminManagerUI();
				admin.setVisible(true);
			}
		});
		button_1.setFont(new Font("굴림", Font.PLAIN, 15));
		button_1.setBounds(241, 145, 141, 53);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("판매자 관리");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();
				if(admin.getAdminState()==true){
					SellerManagerUI seller = new SellerManagerUI();
					seller.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "관리자 로그인이 안되어있습니다.", "판매자 관리 실패", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		button_2.setFont(new Font("굴림", Font.PLAIN, 15));
		button_2.setBounds(437, 145, 141, 53);
		contentPane.add(button_2);
		
		JLabel label = new JLabel("\uAE30\uCD08 \uC815\uBCF4 \uAD00\uB9AC");
		label.setFont(new Font("굴림", Font.BOLD, 30));
		label.setBounds(176, 36, 242, 53);
		contentPane.add(label);
		
		
	}
}
