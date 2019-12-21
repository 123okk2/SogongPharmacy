package ClosingManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.CardLayout;

public class registerClosingInformUI extends JFrame {

	private JPanel contentPane;
	private JLabel label_balance;
	private JButton btn_register;
	private JButton btn_register2;
	private JPanel panel;
	private JPanel panel_1;
	private JTable table;
	private closingManager cM;
	private JTextField textField_balancein;
	private JTextField textField_sales;
	private JTextField textField_balanceout;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					registerClosingInformUI frame = new registerClosingInformUI();
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
	public registerClosingInformUI() {
		CardLayout Card = new CardLayout(0, 0);
		cM = new closingManager();
		setTitle("일일마감");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		// 100, 100, 615, 478 100, 100, 450, 300
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(Card);

		panel = new JPanel();
		contentPane.add(panel, "one");
		panel.setLayout(null);

		label_balance = new JLabel("잔액");
		label_balance.setBounds(33, 93, 45, 18);
		panel.add(label_balance);

		btn_register = new JButton("등록");
		btn_register.setBounds(325, 211, 87, 30);
		panel.add(btn_register);

		textField_balancein = new JTextField();
		textField_balancein.setBounds(90, 93, 202, 21);
		panel.add(textField_balancein);
		textField_balancein.setColumns(10);

		JLabel label_date = new JLabel(
				"일자: " + new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date().getTime()));
		label_date.setBounds(12, 9, 142, 15);
		panel.add(label_date);

		panel_1 = new JPanel();
		contentPane.add(panel_1, "two");

		table = new JTable();
		table.setModel(new DefaultTableModel(new String[][] {}, new String[] { "상품명", "판매량", "매출액" }));
		table.setBounds(12, 27, 267, 186);
		panel_1.add(table);

		btn_register2 = new JButton("등록");
		btn_register2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "마감 정보가 등록되었습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		btn_register2.setBounds(490, 406, 97, 23);
		panel_1.add(btn_register2);
		panel_1.setLayout(null);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 33, 500, 307);
		panel_1.add(scrollPane);

		JLabel label_date2 = new JLabel(
				"일자 : " + new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date().getTime()));
		label_date2.setBounds(25, 10, 173, 15);
		panel_1.add(label_date2);

		JLabel label_allsales = new JLabel("총 매출액");
		label_allsales.setBounds(35, 353, 84, 15);
		panel_1.add(label_allsales);

		textField_sales = new JTextField();
		textField_sales.setEditable(false);
		textField_sales.setBounds(131, 350, 155, 21);
		panel_1.add(textField_sales);
		textField_sales.setColumns(10);

		JLabel label_balance2 = new JLabel("잔액");
		label_balance2.setBounds(35, 378, 84, 15);
		panel_1.add(label_balance2);

		textField_balanceout = new JTextField();
		textField_balanceout.setEditable(false);
		textField_balanceout.setBounds(131, 375, 155, 21);
		panel_1.add(textField_balanceout);
		textField_balanceout.setColumns(10);

		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				if (textField_balancein.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "값이 입력되지 않았습니다. 정확한 잔액을 입력후 다시 시도하세요.", "제대로 된 값을 입력하시오.", JOptionPane.ERROR_MESSAGE);
				} 
				else {
					try { 
					    Integer.parseInt(textField_balancein.getText()); 
					} 
					catch (NumberFormatException e1) { 
						JOptionPane.showMessageDialog(null, "값이 숫자가 아닙니다. 정확한 잔액을 입력후 다시 시도하세요.", "제대로 된 값을 입력하시오." , JOptionPane.ERROR_MESSAGE);
						return;
					    //Not an integer 
					}
					if (!cM.registerClosingInform(Integer.parseInt(textField_balancein.getText()))) {
						JOptionPane.showMessageDialog(null, "이미 오늘의 마감정보가 등록되어있습니다.");
					} else {
						int resultsales = 0;
						setBounds(100, 100, 615, 478);
						Card.show(contentPane, "two");
						String[][] iDR = cM.inquiryDailyReport();
						table.setModel(new DefaultTableModel(iDR, new String[] { "상품명", "판매량", "매출액" }));
						textField_balanceout.setText(textField_balancein.getText());
						for (int i = 0; i < iDR.length; i++)
							resultsales += Integer.parseInt(iDR[i][2]);
						textField_sales.setText(Integer.toString(resultsales));
					}
				}
			}
		});

	}
}
