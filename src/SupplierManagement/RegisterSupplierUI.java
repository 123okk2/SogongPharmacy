package SupplierManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

public class RegisterSupplierUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JTextField supplierName;
	private JSpinner supplierPersonalDay;
	private JSpinner exp_date;
	private JTextField supplierProduct;
	private JButton confirm;
	private JButton cancles;
	private JLabel label_2;
	private JTextField productPrice;
	private JTextField supplierTel;

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
	public RegisterSupplierUI(SupplierManager sm) {
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
		contentPane.add(getLblNewLabel_3());
		contentPane.add(getSupplierName());
		contentPane.add(getSupplierPersonalDay());
		contentPane.add(getSupplierProduct());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		contentPane.add(getLabel_2());
		contentPane.add(getProductPrice());
		
		supplierPersonalDay.setValue(new Date());
		
		supplierTel = new JTextField();
		supplierTel.setColumns(10);
		supplierTel.setBounds(81, 72, 139, 21);
		contentPane.add(supplierTel);

		
		cancles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		confirm.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				/*
				if(new SupplierManager().getSupplierID(supplierName.getText())==0) {
					JOptionPane.showMessageDialog(null, "존재하지 않는 상품입니다.", "등록 실패", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					return;
				}*/
				
				
				String enrDate=supplierPersonalDay.getValue().toString();
				
				int yy=Integer.parseInt(enrDate.substring(24,28));
				String mmm=enrDate.substring(4,7);
				int mm=0;
				int dd=Integer.parseInt(enrDate.substring(8, 10));
				
				String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
				
				for(int i=0;i<months.length;i++) {
					if(mmm.equals(months[i])) mm=i;
					if(mm!=0) break;
				}
				
				Boolean chk=sm.RegisterSupplier(new Supplier(1, supplierName.getText(), supplierTel.getText(),
						new Date(yy-1900,mm,dd),supplierProduct.getText(), Integer.parseInt(productPrice.getText())));
				
				if(chk.equals(false)) {
					JOptionPane.showMessageDialog(null, "선택하신 항목의 등록에 실패했습니다.", "등록 실패", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				dispose();
			}
		});
		
		
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("거래처 등록");
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(12, 10, 410, 30);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("거래처명");
			label.setLabelFor(getSupplierName());
			label.setBounds(12, 50, 57, 15);
		}
		return label;
	}
	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("연락처");
			label_1.setBounds(12, 75, 57, 15);
		}
		return label_1;
	}
	private JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("휴무일");
			lblNewLabel_1.setBounds(12, 100, 57, 15);
		}
		return lblNewLabel_1;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("취급제품");
			lblNewLabel_3.setBounds(12, 125, 57, 15);
		}
		return lblNewLabel_3;
	}
	private JTextField getSupplierName() {
		if (supplierName == null) {
			supplierName = new JTextField();
			supplierName.setBounds(81, 47, 139, 21);
			supplierName.setColumns(10);
		}
		return supplierName;
	}
	private JSpinner getSupplierPersonalDay() {
		if (supplierPersonalDay == null) {
		    Calendar calendar = Calendar.getInstance();//날짜
		    Date value = calendar.getTime(); calendar.add(Calendar.YEAR, -50);
		    Date start = calendar.getTime(); calendar.add(Calendar.YEAR, 100);
		    Date end = calendar.getTime();
		    SpinnerDateModel dateModel = new SpinnerDateModel(value, start, end, Calendar.YEAR);
			supplierPersonalDay = new JSpinner(new SpinnerDateModel(value, start, end, Calendar.YEAR));
		    supplierPersonalDay.setEditor(new JSpinner.DateEditor(supplierPersonalDay, "yyyy-MM-dd"));
			supplierPersonalDay.setBounds(81, 97, 139, 22);
		}
		return supplierPersonalDay;
	}
	
	private JTextField getSupplierProduct() {
		if (supplierProduct == null) {
			supplierProduct = new JTextField();
			supplierProduct.setBounds(81, 122, 139, 21);
			supplierProduct.setColumns(10);
		}
		return supplierProduct;
	}
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uB4F1\uB85D");
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
			label_2 = new JLabel("제품가격");
			label_2.setBounds(12, 150, 57, 15);
		}
		return label_2;
	}
	private JTextField getProductPrice() {
		if (productPrice == null) {
			productPrice = new JTextField();
			productPrice.setBounds(81, 147, 139, 21);
			productPrice.setColumns(10);
		}
		return productPrice;
	}
}