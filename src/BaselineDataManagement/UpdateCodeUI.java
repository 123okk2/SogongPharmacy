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

public class UpdateCodeUI extends JFrame {

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
	public UpdateCodeUI(Code c, CodeManager cm) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(67, 10, 369, 57);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\uCF54\uB4DC \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(28, 10, 294, 37);
		panel.add(label);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(77, 69, 357, 102);
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
		panel_2.setBounds(-62, 102, 435, 89);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		JButton btnNewButton = new JButton("수정");
		btnNewButton.setBounds(100, 192, 105, 39);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!isStringInteger(numbertextField.getText())){
					JOptionPane.showMessageDialog(null, "코드번호를 정수로 입력해주세요","코드 번호 타입 에러", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				boolean duplication = true;

				ArrayList<Code> cl = new ArrayList<>();
				cl = cm.inquiryCode("");

				Code updatec = new Code();
				updatec.setNumber(Integer.parseInt(numbertextField.getText()));
				updatec.setName(nametextField.getText());

				for (int i = 0; i < cl.size(); i++) {
					if (cl.get(i).getNumber() == updatec.getNumber()) {
						JOptionPane.showMessageDialog(null, "코드 번호가 중복되어서 수정할 수 없습니다.", "수정 실패",JOptionPane.INFORMATION_MESSAGE);
						duplication = false;
						dispose();
						break;
					}
				}
				if (duplication == true) {
					boolean chk = cm.updateCode(c, updatec);
					if (chk == true)
						JOptionPane.showMessageDialog(null, "코드 수정 성공했습니다.", "수정 성공", JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "수정 실패했습니다.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
			}
		});
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 15));

		JButton button = new JButton("취소");
		button.setBounds(258, 192, 105, 39);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("굴림", Font.PLAIN, 15));
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
