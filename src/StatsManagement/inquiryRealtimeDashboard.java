package StatsManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import javax.swing.JSlider;
import javax.swing.JScrollPane;

public class inquiryRealtimeDashboard extends JFrame {

	private JPanel contentPane;
	private JTextField textField_saleOfDay;
	private JTextField textField_saleOfWeek;
	private JTextField textField_saleOfMonth;
	private JTable table;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inquiryRealtimeDashboard frame = new inquiryRealtimeDashboard();
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
	public inquiryRealtimeDashboard() {
		statsInquiryManager sIM = new statsInquiryManager();
		setTitle("실시간 대시보드");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_inquiryDashboard = new JButton("조회");
		btn_inquiryDashboard.setBounds(325, 378, 97, 23);
		contentPane.add(btn_inquiryDashboard);
		
		JLabel label = new JLabel("오늘의 매출액");
		label.setBounds(12, 29, 91, 15);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("이주의 매출액");
		label_1.setBounds(12, 85, 91, 15);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("이달의 매출액");
		label_2.setBounds(12, 141, 91, 15);
		contentPane.add(label_2);
		
		textField_saleOfDay = new JTextField();
		textField_saleOfDay.setBounds(106, 26, 116, 21);
		contentPane.add(textField_saleOfDay);
		textField_saleOfDay.setColumns(10);
		
		textField_saleOfWeek = new JTextField();
		textField_saleOfWeek.setColumns(10);
		textField_saleOfWeek.setBounds(106, 82, 116, 21);
		contentPane.add(textField_saleOfWeek);
		
		textField_saleOfMonth = new JTextField();
		textField_saleOfMonth.setColumns(10);
		textField_saleOfMonth.setBounds(106, 138, 116, 21);
		contentPane.add(textField_saleOfMonth);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 185, 296, 216);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTop = new JLabel("이달의 최다 판매품목 TOP5");
		lblTop.setBounds(12, 10, 178, 15);
		panel.add(lblTop);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[][] {},
			new String[] {
				"상품명", "판매량"
			}
		));
		table.setBounds(12, 88, 272, 80);
		panel.add(table);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 61, 272, 111);
		panel.add(scrollPane);
		
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String[][] iRD = sIM.inquiryRealtimeDashboard();
				textField_saleOfDay.setText(iRD[0][0]);
				textField_saleOfWeek.setText(iRD[1][0]);
				textField_saleOfMonth.setText(iRD[2][0]);
				String[][] niRD = new String[5][];
				for(int i=3;i<iRD.length;i++)
				{
					niRD[i-3] = iRD[i];
				}
				table.setModel(new DefaultTableModel(niRD ,new String[] { "상품명", "판매량"}));
			}
		};
		ScheduledExecutorService service= Executors.newSingleThreadScheduledExecutor();
		
		btn_inquiryDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				service.scheduleAtFixedRate(runnable, 0, 20, TimeUnit.SECONDS);
			}
		});
	}
}
