package ReceivingManagement;

import StockManagement.Stock;
import StockManagement.StockConditionManager;
import StockManagement.StockManager;
import ProductManagement.ProductManager;
import ReturnDisposalManagement.ReturnDisposalUI;
import SupplierManagement.SupplierManager;

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

import BaselineDataManagement.Admin;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.util.ArrayList;
import java.util.Date;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ReceivingManagerUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton inquiry;
	private JButton register;
	private JButton update;
	private JButton delete;
	private JButton printR;
	private JButton printB;
	private JScrollPane scrollPane;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceivingManagerUI frame = new ReceivingManagerUI();
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
	//public ReceivingManagerUI(Admin am) {
	public ReceivingManagerUI() {
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
		contentPane.add(getPrintR());
		contentPane.add(getPrintB());
		contentPane.add(getScrollPane());
		
		
		//�Ŵ����� ����
		ReceivingManager rm=new ReceivingManager();
		StockManager sm=new StockManager();
		StockConditionManager scm=new StockConditionManager();
		

		
							//��ȸ (������ �Լ�����x)
		inquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//�˻�����
				String str=textField.getText();
				ArrayList<Receiving> r=new ArrayList<>();
				r=rm.inquiryReceiving(textField.getText());
				
				SimpleDateFormat t=new SimpleDateFormat("yyyy-MM-dd");
				//table�� ���� �߰� �ϴ� �κ�
				DefaultTableModel model = (DefaultTableModel) table.getModel(); // DefaultTableModelŬ������ ���̺��� ���� get�ϰ� 
				model.setNumRows(0); //���̺� �ʱ�ȭ
				for(int i=0;i<r.size();i++) {
					//�� �߰�
					model.addRow(new Object[]{false, new ProductManager().getProductName(r.get(i).getProductID()), r.get(i).getQuantity(), new SupplierManager().getSupplierName(r.get(i).getSupplierID()), 
							t.format(r.get(i).getReceivingDate()), t.format(r.get(i).getExpirationDate()), r.get(i).getBarcode()});
				}
			}
		});
							//��� (���� �Լ�)
		register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				displayRegisterReceivingUI(rm, sm, scm);
			}
		});
							//���� (���� �Լ�x)
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//�׸� ����/�̼��� ���� �ĺ�
				if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "�׸��� ���� �������ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
				else {
					//�ܼ��� �ʱ�ȭ. �ǹ� ����.
					Receiving r=new Receiving(1,1,1,new Date(2019,05,17),new Date(2019,05,17),1,1);
					//����.���� ���� �Ǻ��� ����
					Boolean chk=false;
					
					for(int i=0;i<table.getRowCount();i++) {
						if(table.getValueAt(i,0).equals(true)) {
							//String to Date
							String enrDate=table.getValueAt(i,4).toString();
							String expDate=table.getValueAt(i,5).toString();
							
							int yy=Integer.parseInt(enrDate.substring(0,4));
							int yy2=Integer.parseInt(expDate.substring(0,4));
							int mm=Integer.parseInt(enrDate.substring(5,7)), mm2=Integer.parseInt(enrDate.substring(5,7));
							int dd=Integer.parseInt(enrDate.substring(8, 10));
							int dd2=Integer.parseInt(expDate.substring(8, 10));

							r=new Receiving(1, Integer.parseInt(table.getValueAt(i, 6).toString()),Integer.parseInt(table.getValueAt(i, 2).toString())
									,new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
									new ProductManager().getProductID(table.getValueAt(i, 1).toString()));
							
							//�԰� ���� ����
							chk=rm.deleteReceiving(new ProductManager().getProductName(r.getProductID()), r.getQuantity(), r.getReceivingDate());
							if(chk.equals(false)) {
								JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ������ �����߽��ϴ�.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
								break;
							}
							else {
								//��� ���� ����
								chk=sm.deleteStock(r.getProductID(), r.getBarcode(), r.getReceivingDate(), r.getExpirationDate());
								if(chk.equals(false)) {
									JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ������ �����߽��ϴ�.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
									//���� ���з� ���� �԰� ���� ����
									rm.RegisterReceiving(new Receiving(1, Integer.parseInt(table.getValueAt(i, 6).toString()),Integer.parseInt(table.getValueAt(i, 2).toString())
											,new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
											new ProductManager().getProductID(table.getValueAt(i, 1).toString())));
									break;
								}
								else {
									//��� ��Ȳ ����
									chk=scm.updateStockCondition(new ProductManager().getProductName(r.getProductID()), -r.getQuantity());
									
									if(chk.equals(true)) JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ���������� �����Ǿ����ϴ�.", "���� �Ϸ�", JOptionPane.INFORMATION_MESSAGE);
									else {
										JOptionPane.showMessageDialog(null, "�����Ͻ� �׸��� ������ �����߽��ϴ�.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
										rm.RegisterReceiving(new Receiving(1, Integer.parseInt(table.getValueAt(i, 6).toString()),Integer.parseInt(table.getValueAt(i, 2).toString())
												,new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
												new ProductManager().getProductID(table.getValueAt(i, 1).toString())));
										sm.registerStock(new Stock(Integer.parseInt(table.getValueAt(i, 6).toString()), Integer.parseInt(table.getValueAt(i, 2).toString()),
												new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
												new ProductManager().getProductID(table.getValueAt(i, 1).toString())));
										break;
									}
								}
							}
							break;
						}
					}
				}
			}
		});
							//����
		update.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "�׸��� ���� �������ּ���.", "���� ����", JOptionPane.INFORMATION_MESSAGE);
				else {
					try {
						displayUpdateReceivingUI(rm, sm, scm);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
							//Ȯ���� ���
		printR.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//if(am.getAdminState()) {
					if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "�׸��� ���� �������ּ���.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
					else {
						Admin admin = new Admin();
						if(admin.getAdminState()==true){
							try {
								displayPrintReceivingUI(rm);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							JOptionPane.showMessageDialog(null, "������ �α����� �ȵǾ��ֽ��ϴ�.", "��ǰ ���� ����", JOptionPane.INFORMATION_MESSAGE);
					
						}
					}
				//}
				//else JOptionPane.showMessageDialog(null, "������ ���� ����Դϴ�.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
			}
		});
							//���ڵ� ���
		printB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(table.getSelectedColumn()==-1) JOptionPane.showMessageDialog(null, "�׸��� ���� �������ּ���.", "��� ����", JOptionPane.INFORMATION_MESSAGE);
				else {
					try {
						displayPrintBarcodeUI(rm);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	public void displayRegisterReceivingUI(ReceivingManager rm, StockManager sm, StockConditionManager scm) {
		//�԰� ��� ȭ�� ȣ��
		RegisterReceivingUI newScr=new RegisterReceivingUI(rm, sm, scm);
		newScr.setVisible(true);
	}
	
	public void displayUpdateReceivingUI(ReceivingManager rm, StockManager sm, StockConditionManager scm) throws ParseException {
		//�԰� ���� ȭ�� ȣ��
		Receiving r=new Receiving(1,1,1,new Date(2019,05,17),new Date(2019,05,17),1,1);
		
		for(int i=0;i<table.getRowCount();i++) {
			if(table.getValueAt(i,0).equals(true)) {
				String enrDate=table.getValueAt(i,4).toString();
				String expDate=table.getValueAt(i,5).toString();
				
				int yy=Integer.parseInt(enrDate.substring(0,4));
				int yy2=Integer.parseInt(expDate.substring(0,4));
				int mm=Integer.parseInt(enrDate.substring(5,7)), mm2=Integer.parseInt(enrDate.substring(5,7));
				int dd=Integer.parseInt(enrDate.substring(8, 10));
				int dd2=Integer.parseInt(expDate.substring(8, 10));

				r=new Receiving(1, Integer.parseInt(table.getValueAt(i, 6).toString()),Integer.parseInt(table.getValueAt(i, 2).toString())
						,new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
						new ProductManager().getProductID(table.getValueAt(i, 1).toString()));
						
				break;
			}
		}
		UpdateReceivingUI newScr=new UpdateReceivingUI(r, rm, sm, scm);
		newScr.setVisible(true);
	}
	
	public void displayPrintReceivingUI(ReceivingManager rm) throws ParseException {
		//�԰� Ȯ���� ��� ȭ�� ȣ��
		Receiving r=new Receiving(1,1,1,new Date(2019,05,17),new Date(2019,05,17),1,1);
		
		for(int i=0;i<table.getRowCount();i++) {
			if(table.getValueAt(i,0).equals(true)) {
				String enrDate=table.getValueAt(i,4).toString();
				String expDate=table.getValueAt(i,5).toString();
				
				int yy=Integer.parseInt(enrDate.substring(0,4));
				int yy2=Integer.parseInt(expDate.substring(0,4));
				int mm=Integer.parseInt(enrDate.substring(5,7)), mm2=Integer.parseInt(enrDate.substring(5,7));
				int dd=Integer.parseInt(enrDate.substring(8, 10));
				int dd2=Integer.parseInt(expDate.substring(8, 10));

				r=new Receiving(1, Integer.parseInt(table.getValueAt(i, 6).toString()),Integer.parseInt(table.getValueAt(i, 2).toString())
						,new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
						new ProductManager().getProductID(table.getValueAt(i, 1).toString()));
						
				break;
			}
		}
		PrintReceivingUI newScr=new PrintReceivingUI(r);
		newScr.setVisible(true);
	}
	public void displayPrintBarcodeUI(ReceivingManager rm) throws ParseException {
		//���ڵ� ��� ȭ�� ȣ��
		Receiving r=new Receiving(1,1,1,new Date(2019,05,17),new Date(2019,05,17),1,1);
		
		for(int i=0;i<table.getRowCount();i++) {
			if(table.getValueAt(i,0).equals(true)) {
				String enrDate=table.getValueAt(i,4).toString();
				String expDate=table.getValueAt(i,5).toString();
				
				int yy=Integer.parseInt(enrDate.substring(0,4));
				int yy2=Integer.parseInt(expDate.substring(0,4));
				int mm=Integer.parseInt(enrDate.substring(5,7)), mm2=Integer.parseInt(enrDate.substring(5,7));
				int dd=Integer.parseInt(enrDate.substring(8, 10));
				int dd2=Integer.parseInt(expDate.substring(8, 10));

				r=new Receiving(1, Integer.parseInt(table.getValueAt(i, 6).toString()),Integer.parseInt(table.getValueAt(i, 2).toString())
						,new Date(yy-1900,mm-1,dd), new Date(yy2-1900,mm2-1,dd2), new SupplierManager().getSupplierID(table.getValueAt(i, 3).toString()),
						new ProductManager().getProductID(table.getValueAt(i, 1).toString()));
						
				break;
			}
		}
		PrintBarcodeUI newScr=new PrintBarcodeUI(r);
		newScr.setVisible(true);
	}
	
	
	

	
	
	
	
	
	
	
	
	
	//gui ����
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
			inquiry = new JButton("\uC785\uACE0 \uC870\uD68C");
			inquiry.setBounds(12, 65, 105, 57);
		}
		return inquiry;
	}
	private JButton getRegister() {
		if (register == null) {
			register = new JButton("\uC785\uACE0 \uB4F1\uB85D");
			register.setBounds(129, 65, 105, 57);
		}
		return register;
	}
	private JButton getUpdate() {
		if (update == null) {
			update = new JButton("\uC785\uACE0 \uC218\uC815");
			update.setBounds(246, 65, 105, 57);
		}
		return update;
	}
	private JButton getDelete() {
		if (delete == null) {
			delete = new JButton("\uC785\uACE0 \uC0AD\uC81C");
			delete.setBounds(363, 65, 105, 57);
		}
		return delete;
	}
	private JButton getPrintR() {
		if (printR == null) {
			printR = new JButton(" \uD655\uC778\uC99D \uCD9C\uB825");
			printR.setBounds(480, 65, 107, 23);
		}
		return printR;
	}
	private JButton getPrintB() {
		if (printB == null) {
			printB = new JButton("\uBC14\uCF54\uB4DC \uCD9C\uB825");
			printB.setBounds(480, 98, 107, 23);
		}
		return printB;
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
				new String[] {
					"", "\uC0C1\uD488\uBA85", "\uC218\uB7C9", "\uAC70\uB798\uCC98", "\uC785\uACE0\uB0A0\uC9DC", "\uC720\uD1B5\uAE30\uD55C", "\uBC14\uCF54\uB4DC"
				}
			) {
				Class[] columnTypes = new Class[] {
					Boolean.class, String.class, Integer.class, String.class, Object.class, Object.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				boolean[] columnEditables = new boolean[] {
					true, false, false, true, true, true, true
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setResizable(false);
			table.getColumnModel().getColumn(0).setPreferredWidth(50);
			table.getColumnModel().getColumn(1).setResizable(false);
			table.getColumnModel().getColumn(2).setResizable(false);
			table.getColumnModel().getColumn(3).setResizable(false);
			table.getColumnModel().getColumn(4).setResizable(false);
			table.getColumnModel().getColumn(5).setResizable(false);
			table.getColumnModel().getColumn(6).setResizable(false);
		}
		return table;
	}
}
