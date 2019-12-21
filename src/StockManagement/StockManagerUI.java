package StockManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ProductManagement.ProductManager;
import SupplierManagement.SupplierManager;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class StockManagerUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton showStocks;
	private JButton showExpireSoon;
	private JButton showLessStocks;
	private JScrollPane scrollPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockManagerUI frame = new StockManagerUI();
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
	public StockManagerUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextField());
		contentPane.add(getShowStocks());
		contentPane.add(getShowExpireSoon());
		contentPane.add(getShowLessStocks());
		contentPane.add(getScrollPane());

		StockManager sm=new StockManager();
		StockConditionManager scm=new StockConditionManager();
		
		//전체 재고 조회
		showStocks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DefaultTableModel model=new DefaultTableModel(null,new Object[] {"상품명", "상품수량", "적정재고"});
				table.setModel(model);
				
				ArrayList<StockCondition> sc=scm.StockinquiryStockConditionList(textField.getText());
				for(int i=0;i<sc.size();i++) {
					model.addRow(new Object[]{new ProductManager().getProductName(sc.get(i).getProductID()), sc.get(i).getQuantity(), sc.get(i).getProperStock()});
				}
			}
		});
		//유통기한 임박 상품 조회 (테이블 변경)
		showExpireSoon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DefaultTableModel model=new DefaultTableModel(null,new Object[] {"상품명", "수량", "거래처", "입고일", "유통기한", "바코드"});
				table.setModel(model);

				SimpleDateFormat t=new SimpleDateFormat("yyyy-MM-dd");
				ArrayList<Stock> sc=sm.inquiryExpireSoonStockList(textField.getText());
				
				for(int i=0;i<sc.size();i++) {
					model.addRow(new Object[]{new ProductManager().getProductName(sc.get(i).getProductID()), 
							sc.get(i).getQuantity(), new SupplierManager().getSupplierName(sc.get(i).getSupplierID())
							,t.format(sc.get(i).getReceivingDate()), t.format(sc.get(i).getExpirationDate()),
							sc.get(i).getBarcode()});
				}
			}
		});
		//적정 재고 미달 재고 조회
		showLessStocks.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DefaultTableModel model=new DefaultTableModel(null,new Object[] {"상품명", "상품수량", "적정재고"});
				table.setModel(model);
				
				ArrayList<StockCondition> sc=scm.inquiryLessStockConditionList(textField.getText());
				for(int i=0;i<sc.size();i++) {
					model.addRow(new Object[]{new ProductManager().getProductName(sc.get(i).getProductID()), sc.get(i).getQuantity(), sc.get(i).getProperStock()});
				}
			}
		});
		
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(12, 10, 575, 45);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getShowStocks() {
		if (showStocks == null) {
			showStocks = new JButton("\uC7AC\uACE0 \uC870\uD68C");
			showStocks.setBounds(12, 65, 170, 59);
		}
		return showStocks;
	}
	private JButton getShowExpireSoon() {
		if (showExpireSoon == null) {
			showExpireSoon = new JButton("\uC720\uD1B5\uAE30\uD55C \uC784\uBC15\uC0C1\uD488 \uC870\uD68C");
			showExpireSoon.setBounds(417, 65, 170, 59);
		}
		return showExpireSoon;
	}
	private JButton getShowLessStocks() {
		if (showLessStocks == null) {
			showLessStocks = new JButton("\uC801\uC815 \uC7AC\uACE0 \uBBF8\uB2EC \uC0C1\uD488 \uC870\uD68C");
			showLessStocks.setBounds(212, 65, 170, 59);
		}
		return showLessStocks;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 134, 575, 295);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
		}
		return table;
	}
}
