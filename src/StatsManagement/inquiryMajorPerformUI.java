package StatsManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class inquiryMajorPerformUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField_allVisitor;
	private statsInquiryManager sIM;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inquiryMajorPerformUI frame = new inquiryMajorPerformUI();
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
	public inquiryMajorPerformUI() {
		sIM = new statsInquiryManager();
		setTitle("주요실적 조회");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_inquiry = new JButton("조회");
		btn_inquiry.setBounds(490, 406, 97, 23);
		contentPane.add(		btn_inquiry);
		
		Date now = new Date();
		SpinnerModel dateModel = new SpinnerDateModel(now, null, now, Calendar.DAY_OF_WEEK);
		JSpinner spinner_startDay = new JSpinner(dateModel);
		
		spinner_startDay.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.YEAR));
		spinner_startDay.setBounds(272, 25, 116, 22);
		spinner_startDay.setEditor(new JSpinner.DateEditor(spinner_startDay,"yyyy-MM-dd"));
		contentPane.add(spinner_startDay);
		
		JSpinner spinner_endDay = new JSpinner();
		spinner_endDay.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.YEAR));
		spinner_endDay.setBounds(272, 57, 116, 22);
		spinner_endDay.setEditor(new JSpinner.DateEditor(spinner_endDay,"yyyy-MM-dd"));
		contentPane.add(spinner_endDay);
		
		JLabel lbl_startDay = new JLabel("시작 일");
		lbl_startDay.setBounds(187, 28, 57, 15);
		contentPane.add(lbl_startDay);
		
		JLabel lbl_endDay = new JLabel("종료 일");
		lbl_endDay.setBounds(187, 60, 57, 15);
		contentPane.add(lbl_endDay);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[][] {},
			new String[] {
				"상품명", "판매량", "매출액"
			}
		));
		table.setBounds(12, 108, 321, 226);
		contentPane.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 106, 575, 274);
		contentPane.add(scrollPane);
		
		JLabel lbl_visitor = new JLabel("기간 내 총 방문자");
		lbl_visitor.setBounds(12, 410, 102, 15);
		contentPane.add(lbl_visitor);
		
		textField_allVisitor = new JTextField();
		textField_allVisitor.setBounds(163, 407, 150, 21);
		contentPane.add(textField_allVisitor);
		textField_allVisitor.setColumns(10);
		
		btn_inquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((Date)spinner_startDay.getValue()).getTime()>((Date)spinner_endDay.getValue()).getTime())
					JOptionPane.showMessageDialog(null, "시작일 보다 종료일이 최근입니다. 날짜를 다시 입력하세요.", "날짜 에러.", JOptionPane.ERROR_MESSAGE);
				String[][] iMP = sIM.inquiryMajorPerform((Date)spinner_startDay.getValue(), (Date)spinner_endDay.getValue());
				String[][] niMP = new String[iMP.length-1][];
				String count = iMP[iMP.length-1][0];
				for(int i=0; i<iMP.length-1;i++)
				{
					niMP[i] = iMP[i];
				}
				table.setModel(new DefaultTableModel(niMP,new String[] {"상품명", "판매량", "매출액"}));
				textField_allVisitor.setText(count);
			}
		});
	}

}
