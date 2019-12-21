package MainUI;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BaselineDataManagement.Admin;
import BaselineDataManagement.BaselineDataManagerUI;
import BaselineDataManagement.CodeManagerUI;
import ClosingManagement.closingManagerUI;
import CustomerManagement.customerManagerUI;
import ProductManagement.ProductManagerUI;
import ReceivingManagement.ReceivingManagerUI;
import RecordManagement.RecordManagerUI;
import ReturnDisposalManagement.ReturnDisposalUI;
import SaleManagement.SaleManager;
import SaleManagement.SalesManagementUI;
import StatsManagement.statsInquiryManager;
import StatsManagement.statsInquiryManagerUI;
import StockManagement.StockManagerUI;
import SupplierManagement.SupplierManagerUI;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class MainUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI();
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
	public MainUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_basement = new JButton("기초자료 관리");
		btn_basement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BaselineDataManagerUI().setVisible(true);
			}
		});
		btn_basement.setBounds(12, 114, 122, 60);
		contentPane.add(btn_basement);
		
		JButton btn_product = new JButton("상품 관리");
		btn_product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ProductManagerUI().setVisible(true);
			}
		});
		btn_product.setBounds(178, 114, 112, 60);
		contentPane.add(btn_product);
		
		
		JButton btn_receiving = new JButton("입고 관리");
		btn_receiving.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ReceivingManagerUI().setVisible(true);
			}
		});
		btn_receiving.setBounds(328, 114, 112, 60);
		contentPane.add(btn_receiving);
		
		JButton btn_sales = new JButton("판매 관리");
		btn_sales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SalesManagementUI().setVisible(true);
			}
		});
		btn_sales.setBounds(475, 114, 112, 60);
		contentPane.add(btn_sales);
		
		JButton btn_stock = new JButton("재고 관리");
		btn_stock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new StockManagerUI().setVisible(true);
			}
		});
		btn_stock.setBounds(12, 206, 122, 60);
		contentPane.add(btn_stock);
		
		JButton btn_return = new JButton("반품 관리");
		btn_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					Admin admin = new Admin();
					if(admin.getAdminState()==true){
						new ReturnDisposalUI().setVisible(true);
					}
					else {
						JOptionPane.showMessageDialog(null, "관리자 로그인이 안되어있습니다.", "반품 관리 실패", JOptionPane.INFORMATION_MESSAGE);
				}
		}});
	
		btn_return.setBounds(178, 206, 112, 60);
		contentPane.add(btn_return);
		
		JButton btn_closing = new JButton("마감 관리");
		btn_closing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new closingManagerUI().setVisible(true);
			}
		});
		btn_closing.setBounds(328, 206, 112, 60);
		contentPane.add(btn_closing);
		
		JButton btn_supplier = new JButton("거래처 관리");
		btn_supplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Admin admin = new Admin();
				if(admin.getAdminState()==true){
					new SupplierManagerUI().setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "관리자 로그인이 안되어있습니다.", "거래처 관리 실패", JOptionPane.INFORMATION_MESSAGE);
			}
		}});
		btn_supplier.setBounds(475, 206, 112, 60);
		contentPane.add(btn_supplier);
		
		JButton btn_customer = new JButton("고객 관리");
		btn_customer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new customerManagerUI().setVisible(true);
			}
		});
		btn_customer.setBounds(12, 307, 122, 60);
		contentPane.add(btn_customer);
		
		JButton btn_record = new JButton("이력 관리");
		btn_record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new RecordManagerUI().setVisible(true);
			}
		});
		btn_record.setBounds(178, 307, 112, 60);
		contentPane.add(btn_record);
		
		JButton btn_stats = new JButton("통계 관리");
		btn_stats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new statsInquiryManagerUI().setVisible(true);
			}
		});
		btn_stats.setBounds(328, 307, 112, 60);
		contentPane.add(btn_stats);
		
		JLabel label = new JLabel("소공약국 관리 시스템");
		label.setFont(new Font("굴림", Font.PLAIN, 30));
		label.setBounds(165, 10, 331, 69);
		contentPane.add(label);
	}
}
