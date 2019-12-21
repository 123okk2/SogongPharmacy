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
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class UpdateSellerUI extends JFrame {

	private JPanel contentPane;
	private JTextField nametextField;
	private JTextField phoneNumtextField;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UpdateCodeUI frame = new UpdateCodeUI();
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
	public UpdateSellerUI(Seller s, SellerManager sm) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(12, 17, 357, 57);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\uD310\uB9E4\uC790 \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(12, 10, 294, 37);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(12, 73, 289, 107);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("\uD310\uB9E4\uC790 \uC774\uB984 :");
		lblNewLabel.setFont(new Font("굴림", Font.PLAIN, 15));
		lblNewLabel.setBounds(27, 22, 90, 33);
		panel_1.add(lblNewLabel);

		JLabel label_1 = new JLabel("\uC804\uD654 \uBC88\uD638 :");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(27, 68, 90, 33);
		panel_1.add(label_1);

		nametextField = new JTextField();
		nametextField.setBounds(133, 24, 151, 30);
		panel_1.add(nametextField);
		nametextField.setColumns(10);

		phoneNumtextField = new JTextField();
		phoneNumtextField.setColumns(10);
		phoneNumtextField.setBounds(133, 70, 151, 30);
		panel_1.add(phoneNumtextField);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(12, 190, 303, 63);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JButton btnNewButton = new JButton("수정");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				boolean duplication = true;

				ArrayList<Seller> sl = new ArrayList<>();
				sl = sm.inquirySeller("");

				Seller updates = new Seller();
				updates.setName(nametextField.getText());
				updates.setPhoneNum(phoneNumtextField.getText());

				for (int i = 0; i < sl.size(); i++) {
					if (sl.get(i).getName().equals(updates.getName())) {
						JOptionPane.showMessageDialog(null, "판매자 이름이 중복되어서 수정할 수 없습니다.", "수정 실패",JOptionPane.INFORMATION_MESSAGE);
						duplication = false;
						return;
					}
				}
				String regexp = "^01(?:0|1|[6-9])(\\d{7}|\\d{8})$";
				
				if(!(phoneNumtextField.getText().matches(regexp))){
					JOptionPane.showMessageDialog(null, "전화번호 형식이 어긋납니다","판매자 등록 실패", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				if (duplication == true) {
					boolean chk = sm.updateSeller(s, updates);
					if (chk == true) { 
						JOptionPane.showMessageDialog(null, "판매자 수정 성공했습니다.", "수정 성공", JOptionPane.INFORMATION_MESSAGE);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "수정 실패했습니다.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));
		btnNewButton.setBounds(50, 10, 97, 39);
		panel_2.add(btnNewButton);

		JButton button = new JButton("취소");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
		button.setBounds(170, 10, 97, 39);
		panel_2.add(button);
		
		JLabel label_2 = new JLabel("\uD558\uC774\uD47C\uC5C6\uC774 \uC22B\uC790\uB9CC \uC785\uB825");
		label_2.setBounds(302, 138, 134, 27);
		contentPane.add(label_2);
	}

}
