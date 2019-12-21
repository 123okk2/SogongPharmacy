package ClosingManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import MainUI.MainUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class closingManagerUI extends JFrame {

	private JPanel contentPane;
	private static closingManagerUI frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new closingManagerUI();
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
	public closingManagerUI() {
		setTitle("마감관리");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_registerCI = new JButton("마감 등록");
		btn_registerCI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new registerClosingInformUI().setVisible(true);
			}
		});
		btn_registerCI.setBounds(125, 59, 189, 38);
		contentPane.add(btn_registerCI);
		
		JButton btn_inquiryDR = new JButton("일보 조회");
		btn_inquiryDR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new inquiryDailyReportUI().setVisible(true);
			}
		});
		btn_inquiryDR.setBounds(125, 107, 189, 38);
		contentPane.add(btn_inquiryDR);
		
		JButton btn_inquiryMR = new JButton("월보 조회");
		btn_inquiryMR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new inquiryMonthlyReportUI().setVisible(true);
			}
		});
		btn_inquiryMR.setBounds(125, 155, 189, 38);
		contentPane.add(btn_inquiryMR);
		
		JLabel label = new JLabel("마감 관리");
		label.setFont(new Font("굴림", Font.PLAIN, 30));
		label.setBounds(153, 0, 136, 52);
		contentPane.add(label);
	}
//	public void dispose() {
//		new MainUI().setVisible(true);
//		super.dispose();
//	}
}
