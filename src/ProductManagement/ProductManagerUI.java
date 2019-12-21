package ProductManagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import StockManagement.*;
import RecordManagement.*;

public class ProductManagerUI extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JButton inquiry;
	private JButton register;
	private JButton update;
	private JButton delete;
	private JScrollPane scrollPane;
	private JTable table;
	
	private Product p;
	RecordManager rm = new RecordManager();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductManagerUI frame = new ProductManagerUI();
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
	public ProductManagerUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 615, 478);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextField());
		contentPane.add(getInquiry());
		contentPane.add(getRegister());
		contentPane.add(getUpdate());
		contentPane.add(getDelete());
		contentPane.add(getScrollPane());
			
		ProductManager pm = new ProductManager();
		StockConditionManager scm = new StockConditionManager();
		
							//조회 (별도의 함수구현x)
		inquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//검색조건
				String dp;
				ArrayList<Product> p=new ArrayList<>();
				p = pm.inquiryProduct(textField.getText());
				
				//table에 정보 추가 하는 부분
				DefaultTableModel model = (DefaultTableModel) table.getModel(); // DefaultTableModel클래스로 테이블의 모델을 get하고 
				model.setNumRows(0); //테이블 초기화
				for(int i=0;i<p.size();i++) {
					//열 추가					
					if(p.get(i).getDngrProduct() == 0)dp = "N";
					else dp = "Y";
					model.addRow(new Object[]{false, p.get(i).getProductName(), p.get(i).getProductPrice(), 
							p.get(i).getProperStock(), dp});
				}
			}
		});
							//등록 (별도 함수)
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayRegisterProductUI(pm, scm);			
			}
		});
							//삭제 (별도 함수x)
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int checkCnt = 0;
				int index = 0;
				for(int i = 0; i < table.getRowCount(); i++) {
					if((boolean)table.getValueAt(i, 0)) {
						checkCnt++;
						index = i;
					}
				}
				//새 창 띄우기
				if (checkCnt == 0) {
					JOptionPane.showMessageDialog(null, "항목을 선택하세요.", "항목이 선택되지 않았습니다.", JOptionPane.ERROR_MESSAGE);
				}				
				else if(checkCnt == 1) {
					ArrayList<Product> pList = pm.inquiryProduct((java.lang.String)table.getValueAt(index, 1));
					
					p = pList.get(0);
					
					scm.deleteStockCondition(p.getProductName());
					pm.deleteProduct(p.getProductID());
				}				
				else {
					JOptionPane.showMessageDialog(null, "다중선택", "한가지 항목만 선택해주세요.", JOptionPane.ERROR_MESSAGE);
				}
				
				ProductRecord pr = new ProductRecord(Record.DELETE, p.getProductID(), p.getProductName());
				rm.registerRecord(RecordManager.PRODUCT_RECORD, pr);
			}					
		});
				
							//수정
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "항목을 먼저 선택해주세요.", "수정 실패", JOptionPane.INFORMATION_MESSAGE);
				else {
					try {
						displayUpdateProductUI(pm, scm);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	public void displayRegisterProductUI(ProductManager pm, StockConditionManager scm) {
		//입고 등록 화면 호출
		RegisterProductUI newScr=new RegisterProductUI(pm, scm);
		newScr.setVisible(true);
	}
	
	
	public void displayUpdateProductUI(ProductManager pm, StockConditionManager scm) throws ParseException {
		//입고 수정 화면 호출
		// TODO Auto-generated method stub
		int checkCnt = 0;
		int index = 0;
		
		for(int i = 0; i < table.getRowCount(); i++) {
			if((boolean)table.getValueAt(i, 0)) {
				checkCnt++;
				index = i;
			}
		}
		//새 창 띄우기
		if (checkCnt == 0) {
			JOptionPane.showMessageDialog(null, "항목을 선택하세요.", "항목이 선택되지 않았습니다.", JOptionPane.ERROR_MESSAGE);
		}
		
		else if(checkCnt == 1) {
			ArrayList<Product> pList = pm.inquiryProduct((java.lang.String)table.getValueAt(index, 1));
			
			p = pList.get(0);
				
			UpdateProductUI ep = new UpdateProductUI(p, pm, scm);
			ep.setVisible(true);
		}
		
		else {
			JOptionPane.showMessageDialog(null, "다중선택", "한가지 항목만 선택해주세요.", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	//gui 생성
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setBounds(12, 10, 575, 45);
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getInquiry() {
		if (inquiry == null) {
			inquiry = new JButton("\uC0C1\uD488 \uC870\uD68C");
			inquiry.setBounds(12, 65, 105, 57);
		}
		return inquiry;
	}
	private JButton getRegister() {
		if (register == null) {
			register = new JButton("\uC0C1\uD488 \uB4F1\uB85D");
			register.setBounds(129, 65, 105, 57);
		}
		return register;
	}
	private JButton getUpdate() {
		if (update == null) {
			update = new JButton("\uC0C1\uD488 \uC218\uC815");
			update.setBounds(246, 65, 105, 57);
		}
		return update;
	}
	private JButton getDelete() {
		if (delete == null) {
			delete = new JButton("\uC0C1\uD488 \uC0AD\uC81C");
			delete.setBounds(363, 65, 105, 57);
		}
		return delete;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 132, 575, 297);
			scrollPane.setViewportView(getTable());
		}
		return scrollPane;
	}
	
	private JTable getTable() {
		if (table == null) {
			table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {"", "상품명", "상품가격", "적정재고", "위험약물"}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, String.class, Integer.class, Integer.class ,String.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					true, true, true, true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
		}
		return table;
	}
}