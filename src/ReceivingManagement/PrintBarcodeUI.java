package ReceivingManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ProductManagement.ProductManager;
import SupplierManagement.SupplierManager;

public class PrintBarcodeUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel lblNewLabel_3;
	private JTextField pro_name;
	private JTextField bar_cod;
	private JButton confirm;
	private JButton cancles;

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
	public PrintBarcodeUI(Receiving r) {
		
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
		contentPane.add(getBar_cod());
		contentPane.add(getConfirm());
		contentPane.add(getCancles());
		
		
		pro_name.setText(new ProductManager().getProductName(r.getProductID()));
		bar_cod.setText(Integer.toString(r.getBarcode()));

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
					FileWriter fw=new FileWriter("C:\\Users\\magic\\Desktop\\PrintBarcodeData.csv");
					String line="";
					line+=new ProductManager().getProductName(r.getProductID())+",";
					line+=Integer.toString(r.getBarcode())+",";
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
			lblNewLabel = new JLabel("\uBC14\uCF54\uB4DC \uCD9C\uB825");
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
	private JLabel getLblNewLabel_3() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("\uBC14\uCF54\uB4DC");
			lblNewLabel_3.setBounds(12, 75, 57, 15);
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
			bar_cod.setBounds(81, 72, 139, 21);
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
}