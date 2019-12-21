package SupplierManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class UpdateSupplierUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_3;
	private JTextField SupplierName;
	private JSpinner supplierPersonalDay;
	private JSpinner exp_date;
	private JTextField SupplierProduct;
	private JButton confirm;
	private JButton cancles;
	private JLabel label_2;
	private JTextField productPrice;
	private JTextField SupplierTel;

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
	public UpdateSupplierUI(Supplier s, SupplierManager sm) {
		
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
		contentPane.add(getSupplierTel());
		
		String enrDate=s.getSupplierPersonalDay().toString();
		
		int yy=Integer.parseInt(enrDate.substring(24,28));
		String mmm=enrDate.substring(4,7);
		int mm=0;
		int dd=Integer.parseInt(enrDate.substring(8, 10));
		
		String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		
		for(int i=0;i<months.length;i++) {
			if(mmm.equals(months[i])) mm=i;
		}
		
		System.out.println(s.getSupplierName());
		System.out.println(s.getSupplierTel());
		System.out.println(s.getSupplierPersonalDay());
		System.out.println(s.getSupplierProduct());
		System.out.println(s.getProductPrice());
		
		SupplierName.setText(s.getSupplierName());
		SupplierTel.setText(s.getSupplierTel());
		supplierPersonalDay.setValue(new Date(yy-1900,mm,dd));
		SupplierProduct.setText(s.getSupplierProduct());
		productPrice.setText(Integer.toString(s.getProductPrice()));

		cancles.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
			
		});
		
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String enrDate=supplierPersonalDay.getValue().toString();
				System.out.println(enrDate);
				
				int yy=Integer.parseInt(enrDate.substring(24,28));
				String mmm=enrDate.substring(4,7);
				int mm=0;
				int dd=Integer.parseInt(enrDate.substring(8, 10));
				
				String[] months= {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
				
				for(int i=0;i<months.length;i++) {
					if(mmm.equals(months[i])) mm=i;
					if(mm!=0) break;
				}
				Boolean chk=sm.updateSupplier(s.getSupplierName(), SupplierName.getText(),
									SupplierTel.getText(), new Date(yy-1900,mm,dd), SupplierProduct.getText(),
									Integer.parseInt(productPrice.getText()));
				
				if(chk.equals(false)) {
					JOptionPane.showMessageDialog(null, "선택하신 항목의 수정에 실패했습니다.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				dispose();
			}
		});
		
		
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("거래처 수정");
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
		if (SupplierName == null) {
			SupplierName = new JTextField();
			SupplierName.setBounds(81, 47, 139, 21);
			SupplierName.setColumns(10);
		}
		return SupplierName;
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
		if (SupplierProduct == null) {
			SupplierProduct = new JTextField();
			SupplierProduct.setBounds(81, 122, 139, 21);
			SupplierProduct.setColumns(10);
		}
		return SupplierProduct;
	}
	private JButton getConfirm() {
		if (confirm == null) {
			confirm = new JButton("\uC218\uC815");
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
	private JTextField getSupplierTel() {
		if (SupplierTel == null) {
			SupplierTel = new JTextField();
			SupplierTel.setText((String) null);
			SupplierTel.setColumns(10);
			SupplierTel.setBounds(81, 72, 139, 21);
		}
		return SupplierTel;
	}
}