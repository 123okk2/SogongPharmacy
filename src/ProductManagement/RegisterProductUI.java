package ProductManagement;

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

import StockManagement.*;
import RecordManagement.*;

public class RegisterProductUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel lblNewLabel_3;
	private JTextField pro_name;
	private JTextField pro_price;
	private JButton confirm;
	private JButton cancles;
	private JLabel label_2;
	private JTextField properStock;
	private JLabel label_1;
	private JTextField dngrProduct;

	RecordManager rm = new RecordManager();
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
	public RegisterProductUI(ProductManager pm, StockConditionManager scm) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getLabel());
		contentPane.add(getLblNewLabel_3());
		contentPane.add(getPro_name());
		contentPane.add(getPro_price());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		contentPane.add(getLabel_2());
		contentPane.add(getProperStock());
		contentPane.add(getLabel_1());
		contentPane.add(getDngrProduct());


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
				int dp = 0;
				if((dngrProduct.getText().equals("Y"))) dp = 1; 

				Boolean chk=pm.registerProduct(new Product(pro_name.getText(), Integer.parseInt(pro_price.getText()), 
						Integer.parseInt(properStock.getText()), dp));

				if(chk.equals(false)) {
					JOptionPane.showMessageDialog(null, "선택하신 항목의 등록에 실패했습니다.", "등록 실패", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				
				chk=scm.registerStockCondition(new StockCondition(pm.getProductID(pro_name.getText()), 0, 						
						Integer.parseInt(properStock.getText())));
				if(chk.equals(false)) {
					JOptionPane.showMessageDialog(null, "선택하신 항목의 등록에 실패했습니다.", "등록 실패", JOptionPane.ERROR_MESSAGE);
					dispose();
				}
				
				ProductRecord pr = new ProductRecord(Record.REGISTER, pm.getProductID(pro_name.getText()), pro_name.getText());
				rm.registerRecord(RecordManager.PRODUCT_RECORD, pr);
				JOptionPane.showMessageDialog(null, "선택하신 항목의 등록에 성공했습니다.", "등록 성공", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("상품 등록");
			lblNewLabel.setFont(new Font("굴림", Font.BOLD, 20));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(12, 10, 410, 30);
		}
		return lblNewLabel;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("상품명");
			label.setLabelFor(getPro_name());
			label.setBounds(12, 71, 57, 15);
		}
		return label;
	}
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("상품가격");
			lblNewLabel_3.setBounds(12, 96, 57, 15);
		}
		return lblNewLabel_3;
	}
	private JTextField getPro_name() {
		if (pro_name == null) {
			pro_name = new JTextField();
			pro_name.setBounds(96, 68, 182, 21);
			pro_name.setColumns(10);
		}
		return pro_name;
	}

	private JTextField getPro_price() {
		if (pro_price == null) {
			pro_price = new JTextField();
			pro_price.setBounds(96, 93, 182, 21);
			pro_price.setColumns(10);
		}
		return pro_price;
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
			label_2 = new JLabel("적정재고");
			label_2.setBounds(12, 121, 57, 15);
		}
		return label_2;
	}
	private JTextField getProperStock() {
		if (properStock == null) {
			properStock = new JTextField();
			properStock.setBounds(96, 118, 182, 21);
			properStock.setColumns(10);
		}
		return properStock;
	}

	private JLabel getLabel_1() {
		if (label_1 == null) {
			label_1 = new JLabel("위험약물");
			label_1.setBounds(12, 143, 57, 15);
		}
		return label_1;
	}

	private JTextField getDngrProduct() {
		if (dngrProduct == null) {
			dngrProduct = new JTextField();
			dngrProduct.setBounds(96, 143, 182, 21);
			dngrProduct.setColumns(10);
		}
		return dngrProduct;
	}
}