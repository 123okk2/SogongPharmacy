package BaselineDataManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.ScrollPane;

public class CodeManagerUI extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CodeManagerUI frame = new CodeManagerUI();
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
	public CodeManagerUI() {

		CodeManager cm = new CodeManager();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getScrollPane());

		JPanel panel = new JPanel();
		panel.setBounds(54, 10, 522, 57);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("\uCF54\uB4DC \uAD00\uB9AC");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("굴림", Font.BOLD, 25));
		label.setBounds(129, 10, 294, 37);
		panel.add(label);


		JPanel panel_1 = new JPanel();
		panel_1.setBounds(67, 91, 522, 127);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel label_1 = new JLabel("\uCF54\uB4DC \uC774\uB984 : ");
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(35, 13, 109, 35);
		panel_1.add(label_1);

		JTextField textField = new JTextField();
		textField.setBounds(138, 10, 185, 41);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton registerButton = new JButton("등록");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				displayRegisterCodeUI(cm);
			}
		});
		registerButton.setFont(new Font("굴림", Font.PLAIN, 15));
		registerButton.setBounds(138, 74, 94, 41);
		panel_1.add(registerButton);

		JButton updateButton = new JButton("수정");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int checkcnt = 0;
				for (int i = 0; i < table.getRowCount(); i++) {
					if ((boolean) table.getValueAt(i, 0) == true) {
						checkcnt++;
					}
				}
				if (checkcnt == 0) {
					JOptionPane.showMessageDialog(null, "항목을 먼저 선택해주세요.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
				} else if (checkcnt == 1) {
					try {
						displayUpdateCodeUI(cm);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "1개만 선택해 주세요.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		updateButton.setFont(new Font("굴림", Font.PLAIN, 15));
		updateButton.setBounds(256, 74, 94, 41);
		panel_1.add(updateButton);

		JButton inquiryButton = new JButton("조회");
		inquiryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = textField.getText();
				ArrayList<Code> cl = new ArrayList<>();
				cl = (ArrayList<Code>) cm.inquiryCode(str);

				// table에 정보 추가 하는 부분
				DefaultTableModel model = (DefaultTableModel) table.getModel(); // DefaultTableModel클래스로 테이블의 모델을 get하고
				model.setNumRows(0); // 테이블 초기화
				for (int i = 0; i < cl.size(); i++) {
					// 열 추가
					model.addRow(new Object[] { false, cl.get(i).getNumber(), cl.get(i).getName() });
				}
			}
		});
		inquiryButton.setFont(new Font("굴림", Font.PLAIN, 15));
		inquiryButton.setBounds(12, 74, 94, 41);
		panel_1.add(inquiryButton);

		JButton deleteButton = new JButton("삭제");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int checkcnt = 0;
				for (int i = 0; i < table.getRowCount(); i++) {
					if ((boolean) table.getValueAt(i, 0) == true) {
						checkcnt++;
					}
				}
				if (checkcnt == 0) {
					JOptionPane.showMessageDialog(null, "항목을 먼저 선택해주세요.", "삭제 실패", JOptionPane.INFORMATION_MESSAGE);
				}
				else if (checkcnt == 1) {
					boolean chk = true;
					chk = cm.deleteCode((String)table.getValueAt(table.getSelectedColumn(),2));
					if (chk == true)
						JOptionPane.showMessageDialog(null, "선택된 항목이 삭제되었습니다.", "삭제 성공",JOptionPane.INFORMATION_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "선택된 항목이 삭제실패했습니다", "삭제 실패",JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, "1개만 선택해 주세요.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
		});
		deleteButton.setFont(new Font("굴림", Font.PLAIN, 15));
		deleteButton.setBounds(375, 74, 94, 41);
		panel_1.add(deleteButton);

	}

	public void displayRegisterCodeUI(CodeManager cm) {
		RegisterCodeUI newScr = new RegisterCodeUI(cm);
		newScr.setVisible(true);
	}

	public void displayUpdateCodeUI(CodeManager cm) throws ParseException {
		// 입고 수정 화면 호출
		Code c = new Code();

		for (int i = 0; i < table.getRowCount(); i++) {
			if (table.getValueAt(i, 0).equals(true)) {
				int num = (int) table.getValueAt(i, 1);
				String name = (String) table.getValueAt(i, 2);

				c = new Code(num, name);
				break;
			}
		}
		UpdateCodeUI newScr = new UpdateCodeUI(c, cm);
		newScr.setVisible(true);
	}

	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(67, 228, 522, 203);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}

	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "", "코드 번호", "코드 이름" }) {
				Class[] columnTypes = new Class[] { Boolean.class, Integer.class, String.class };

				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}

				boolean[] columnEditables = new boolean[] { true, true, true };

				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(10);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);

		}
		return table;
	}
}
