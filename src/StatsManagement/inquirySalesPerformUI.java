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

public class inquirySalesPerformUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JSpinner spinner_startDay;
	private JSpinner spinner_endDay;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					inquirySalesPerformUI frame = new inquirySalesPerformUI();
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
	public inquirySalesPerformUI() {
		statsInquiryManager sIM = new statsInquiryManager();
		setTitle("매출실적 조회");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("조회");
		button.setBounds(490, 406, 97, 23);
		contentPane.add(button);
		
		Date now = new Date();
		SpinnerModel dateModel = new SpinnerDateModel(now, null, now, Calendar.DAY_OF_WEEK);
		spinner_startDay = new JSpinner(dateModel);
		
		spinner_startDay.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.YEAR));
		spinner_startDay.setBounds(272, 25, 116, 22);
		spinner_startDay.setEditor(new JSpinner.DateEditor(spinner_startDay,"yyyy-MM-dd"));
		contentPane.add(spinner_startDay);
		
		spinner_endDay = new JSpinner();
		spinner_endDay.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.YEAR));
		spinner_endDay.setBounds(272, 57, 116, 22);
		spinner_endDay.setEditor(new JSpinner.DateEditor(spinner_endDay,"yyyy-MM-dd"));
		contentPane.add(spinner_endDay);
		
		JLabel lblNewLabel = new JLabel("시작 일");
		lblNewLabel.setBounds(187, 28, 57, 15);
		contentPane.add(lblNewLabel);
		
		JLabel label = new JLabel("종료 일");
		label.setBounds(187, 60, 57, 15);
		contentPane.add(label);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new String[][] {},
			new String[] {
				"일자", "일별 매출액", "잔액"
			}
		));
		table.setBounds(12, 108, 321, 226);
		contentPane.add(table);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 106, 575, 274);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel_1 = new JLabel("기간 내 총 매출액");
		lblNewLabel_1.setBounds(12, 410, 102, 15);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(163, 407, 150, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((Date)spinner_startDay.getValue()).getTime()>((Date)spinner_endDay.getValue()).getTime())
					JOptionPane.showMessageDialog(null, "시작일 보다 종료일이 최근입니다. 날짜를 다시 입력하세요.", "날짜 에러.", JOptionPane.ERROR_MESSAGE);
				int resultsales = 0;
				String[][] tmp = sIM.inquirySalesPerform((Date)spinner_startDay.getValue(), (Date)spinner_endDay.getValue());
				table.setModel(new DefaultTableModel(tmp,
						new String[] {"일자", "일별 매출액", "잔액"}));
				for(int i=0;i<tmp.length;i++)
					resultsales+=Integer.parseInt(tmp[i][1]);
				textField.setText(Integer.toString(resultsales));
			}
		});
	}

}
