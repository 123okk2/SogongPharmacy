package StatsManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class statsInquiryManagerUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					statsInquiryManagerUI frame = new statsInquiryManagerUI();
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
	public statsInquiryManagerUI() {
		setTitle("통계 관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("통계 관리");
		label.setFont(new Font("굴림", Font.PLAIN, 30));
		label.setBounds(144, 10, 136, 52);
		contentPane.add(label);
		
		JButton btn_inquirySalesPerform = new JButton("매출 실적");
		btn_inquirySalesPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new inquirySalesPerformUI().setVisible(true);
			}
		});
		btn_inquirySalesPerform.setBounds(116, 94, 189, 38);
		contentPane.add(btn_inquirySalesPerform);
		
		JButton btn_inquiryMajorPerform = new JButton("주요 실적");
		btn_inquiryMajorPerform.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new inquiryMajorPerformUI().setVisible(true);
			}
		});
		btn_inquiryMajorPerform.setBounds(116, 141, 189, 38);
		contentPane.add(btn_inquiryMajorPerform);
		
		JButton btn_inquiryDashboard = new JButton("실시간 대시보드");
		btn_inquiryDashboard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new inquiryRealtimeDashboard().setVisible(true);
			}
		});
		btn_inquiryDashboard.setBounds(116, 189, 189, 38);
		contentPane.add(btn_inquiryDashboard);
	}
}
