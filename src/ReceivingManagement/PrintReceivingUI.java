package ReceivingManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ProductManagement.ProductManager;
import StockManagement.StockConditionManager;
import StockManagement.StockManager;
import SupplierManagement.SupplierManager;

public class PrintReceivingUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JTextField pro_name;
	private JTextField bar_cod;
	private JButton confirm;
	private JButton cancles;
	private JLabel label_2;
	private JTextField from_wh;
	private JTextField cntPro;
	private JTextField enrDate;
	private JTextField expDate;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enr_pro frame = new enr_pro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public PrintReceivingUI(Receiving r) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getLabel());
		contentPane.add(getLabel_1());
		contentPane.add(getLblNewLabel_1());
		contentPane.add(getLblNewLabel_2());
		contentPane.add(getLblNewLabel_3());
		contentPane.add(getPro_name());
		contentPane.add(getBar_cod());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		contentPane.add(getLabel_2());
		contentPane.add(getFrom_wh());
		contentPane.add(getCntPro());
		contentPane.add(getEnrDate());
		contentPane.add(getExpDate());
		
		String enrdate=r.getReceivingDate().toString();
		String expdate=r.getExpirationDate().toString();
		
		int yy=Integer.parseInt(enrdate.substring(24,28));
		int yy2=Integer.parseInt(expdate.substring(24,28));
		String mmm=enrdate.substring(4,7);
		String mmm2=expdate.substring(4,7);
		int mm=0, mm2=0;
		int dd=Integer.parseInt(enrdate.substring(8, 10));
		int dd2=Integer.parseInt(expdate.substring(8,10));
		
		String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		
		for(int i=0;i<months.length;i++) {
			if(mmm.equals(months[i])) mm=i;
			if(mmm2.equals(months[i])) mm2=i;
			if(mm!=0 && mm2!=0) break;
		}
		
		
		pro_name.setText(new ProductManager().getProductName(r.getProductID()));
		cntPro.setText(Integer.toString(r.getQuantity()));
		enrDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(yy-1900,mm,dd)));
		expDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(yy2-1900,mm2,dd2)));
		bar_cod.setText(Integer.toString(r.getBarcode()));
		from_wh.setText(new SupplierManager().getSupplierName(r.getSupplierID()));

		cancles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int yy=Integer.parseInt(enrdate.substring(24,28));
					int yy2=Integer.parseInt(expdate.substring(24,28));
					String mmm=enrdate.substring(4,7);
					String mmm2=expdate.substring(4,7);
					int mm=0, mm2=0;
					int dd=Integer.parseInt(enrdate.substring(8, 10));
					int dd2=Integer.parseInt(expdate.substring(8,10));
					
					String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
					
					for(int i=0;i<months.length;i++) {
						if(mmm.equals(months[i])) mm=i;
						if(mmm2.equals(months[i])) mm2=i;
						if(mm!=0 && mm2!=0) break;
					}
					
					FileWriter fw=new FileWriter("C:\\Users\\magic\\Desktop\\PrintReceivingData.csv");
					String line="";
					line+=new ProductManager().getProductName(r.getProductID())+",";
					line+=Integer.toString(r.getQuantity())+",";
					line+=new SimpleDateFormat("yyyy-MM-dd").format(new Date(yy-1900,mm,dd))+",";
					line+=new SimpleDateFormat("yyyy-MM-dd").format(new Date(yy2-1900,mm2,dd2))+",";
					line+=Integer.toString(r.getBarcode())+",";
					line+=new SupplierManager().getSupplierName(r.getSupplierID());
					fw.write(line);
					fw.close();
					JOptionPane.showMessageDialog(null, "선택하신 항목이 바탕화면에 저장되었습니다.", "출력 완료", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "선택하신 항목의 출력에 실패했습니다.", "출력 실패", JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
				}
				dispose();
			}
		});
		
		
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("\uC785\uACE0 \uD655\uC778\uC99D \uCD9C\uB825");
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(12, 10, 410, 30);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("\uC0C1\uD488\uBA85");
			label.setLabelFor(getPro_name());
			label.setBounds(12, 50, 57, 15);
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("\uC0C1\uD488\uC218\uB7C9");
			label_1.setBounds(12, 75, 57, 15);
		}
		return label_1;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("\uC785\uACE0\uC77C");
			lblNewLabel_1.setBounds(12, 100, 57, 15);
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("\uC720\uD1B5\uAE30\uD55C");
			lblNewLabel_2.setBounds(12, 125, 57, 15);
		}
		return lblNewLabel_2;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("\uBC14\uCF54\uB4DC");
			lblNewLabel_3.setBounds(12, 150, 57, 15);
		}
		return lblNewLabel_3;
	}
	private JTextField getPro_name() {
		if (pro_name == null) {
			pro_name = new JTextField();
			pro_name.setEditable(false);
			pro_name.setBounds(81, 47, 139, 21);
			pro_name.setColumns(10);
		}
		return pro_name;
	}

	private JTextField getBar_cod() {
		if (bar_cod == null) {
			bar_cod = new JTextField();
			bar_cod.setEditable(false);
			bar_cod.setBounds(81, 147, 139, 21);
			bar_cod.setColumns(10);
		}
		return bar_cod;
	}
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uCD9C\uB825");
			confirm.setBounds(252, 206, 79, 45);
		}
		return confirm;
	}
	private JButton getCancles() {
		if (cancles == null) {
			cancles = new JButton("\uCDE8\uC18C");
			cancles.setBounds(343, 206, 79, 45);
		}
		return cancles;
	}
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("\uAC70\uB798\uCC98");
			label_2.setBounds(12, 175, 57, 15);
		}
		return label_2;
	}
	private JTextField getFrom_wh() {
		if (from_wh == null) {
			from_wh = new JTextField();
			from_wh.setEditable(false);
			from_wh.setBounds(81, 172, 139, 21);
			from_wh.setColumns(10);
		}
		return from_wh;
	}
	private JTextField getCntPro() {
		if (cntPro == null) {
			cntPro = new JTextField();
			cntPro.setEditable(false);
			cntPro.setBounds(81, 72, 139, 21);
			cntPro.setColumns(10);
		}
		return cntPro;
	}
	private JTextField getEnrDate() {
		if (enrDate == null) {
			enrDate = new JTextField();
			enrDate.setEditable(false);
			enrDate.setBounds(81, 97, 139, 21);
			enrDate.setColumns(10);
		}
		return enrDate;
	}
	private JTextField getExpDate() {
		if (expDate == null) {
			expDate = new JTextField();
			expDate.setEditable(false);
			expDate.setBounds(81, 122, 139, 21);
			expDate.setColumns(10);
		}
		return expDate;
	}
}