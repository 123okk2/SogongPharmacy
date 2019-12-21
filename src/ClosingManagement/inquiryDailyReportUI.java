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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.JScrollBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class inquiryDailyReportUI extends JFrame {

	private JPanel contentPane;
	private JButton button_1;
	private JTable table;
	private closingManager cM;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inquiryDailyReportUI frame = new inquiryDailyReportUI();
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
	public inquiryDailyReportUI() {
		cM = new closingManager();
		setTitle("일보조회");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[][] {},
			new String[] {
				"상품명", "판매량", "매출액"
			}
		));
		table.setBounds(12, 27, 267, 186);
		
		contentPane.add(table);

		button_1 = new JButton("조회");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.setModel(new DefaultTableModel(cM.inquiryDailyReport(), new String[] {"상품명", "판매량", "매출액"}));
			}
		});
		button_1.setBounds(490, 406, 97, 23);
		contentPane.add(button_1);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(25, 33, 500, 363);
		getContentPane().add(scrollPane);
		
		JLabel label = new JLabel("일자 : "+new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date().getTime()));
		label.setBounds(25, 10, 173, 15);
		contentPane.add(label);
		
	}
}
