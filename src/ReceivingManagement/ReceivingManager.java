package ReceivingManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import DBManagement.DBManager;
import ProductManagement.ProductManager;
import RecordManagement.*;

//�԰� ���� ��Ʈ�� Ŭ����

public class ReceivingManager {
	DBManager dm=new DBManager();
	
	RecordManager rmr = new RecordManager();
	
	public Boolean RegisterReceiving(Receiving receivingInfo) {
		//�԰� ���� �߰�
		//�̷°����� ���
		ReceivingRecord receivingRecord=new ReceivingRecord(Record.REGISTER, new ProductManager().getProductName(receivingInfo.getProductID()), receivingInfo.getQuantity(), receivingInfo.getReceivingDate());
		boolean chk = rmr.registerRecord(RecordManager.RECEIVING_RECORD, receivingRecord);
		if(chk==false) return chk;
		
		return dm.doWithoutResult("INSERT INTO receiving(quantity, receivingdate, expirationdate, barcode, productid, supplierid) "
				+"VALUES('"+receivingInfo.getQuantity()+"','"+new SimpleDateFormat("yyyy-MM-dd").format(receivingInfo.getReceivingDate())+"','"+new SimpleDateFormat("yyyy-MM-dd").format(receivingInfo.getExpirationDate())+"','"
				+receivingInfo.getBarcode()+"','"+receivingInfo.getProductID()+"','"+receivingInfo.getSupplierID()+"');");
	}
	
	public Boolean updateReceiving(String productName, Date receivingDate, Receiving receivingInfo) {
		//�԰� ���� ����
		ReceivingRecord receivingRecord=new ReceivingRecord(Record.UPDATE, new ProductManager().getProductName(receivingInfo.getProductID()), receivingInfo.getQuantity(), receivingInfo.getReceivingDate());
		boolean chk = rmr.registerRecord(RecordManager.RECEIVING_RECORD, receivingRecord);
		if(chk==false) return chk;
		
		Receiving r=new Receiving(-1,1,1,new Date(),new Date(),1,1);
		try {
			ResultSet rs=dm.doWithResult("SELECT * From Receiving where productID='"+receivingInfo.getProductID()+"' and receivingDate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingInfo.getReceivingDate())+"';");
			while(rs.next()) {
				r=new Receiving(rs.getInt("receivingID"), rs.getInt("barcode"), rs.getInt("quantity")
						,rs.getDate("receivingDate"), rs.getDate("expirationdate")
						,rs.getInt("supplierID"), rs.getInt("productID"));
			}
			dm.closeDB();
			if(r.getReceivingID()!=-1) {
				return false;
			}
		}
		catch(SQLException e) {	
				e.printStackTrace();
		}
		
		return dm.doWithoutResult("UPDATE receiving set quantity='"+receivingInfo.getQuantity()
			+"', receivingdate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingInfo.getReceivingDate())+"', expirationDate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingInfo.getExpirationDate())
			+"', barcode='"+receivingInfo.getBarcode()+"', productID='"+receivingInfo.getProductID()+"', supplierID='"
			+receivingInfo.getSupplierID()+"' where productID='"+new ProductManager().getProductID(productName)+"' AND receivingdate='"
			+new SimpleDateFormat("yyyy-MM-dd").format(receivingDate)+"';");
	}
	
	public ArrayList<Receiving> inquiryReceiving(String nameInput) {
		//�԰� ���� ��ȸ
		//"SELECT * From Receiving where productID='"+inputName+"';"
		//
		ResultSet rs=null;
		ArrayList<Receiving> r=new ArrayList<>();
		
		if(nameInput.equals("")) {
			try {
				rs=dm.doWithResult("SELECT * From Receiving;");
				while(rs.next()) {
					r.add(new Receiving(rs.getInt("receivingID"), rs.getInt("barcode"), rs.getInt("quantity")
							,rs.getDate("receivingDate"), rs.getDate("expirationdate")
							,rs.getInt("supplierID"), rs.getInt("productID")));

				}
				dm.closeDB();
			}
			catch(SQLException e) {	
					e.printStackTrace();
			}
		}
		else {
			try {
				rs=dm.doWithResult("SELECT * From Receiving where productID='"+nameInput+"';");
				while(rs.next()) {
					r.add(new Receiving(rs.getInt("receivingID"), rs.getInt("barcode"), rs.getInt("quantity")
							,rs.getDate("receivingDate"), rs.getDate("expirationdate")
							,rs.getInt("supplierID"), rs.getInt("productID")));

				}
				dm.closeDB();
			}
			catch(SQLException e) {	
					e.printStackTrace();
			}
			if(r.size()==0) JOptionPane.showMessageDialog(null, "�˻� ���ǿ� ��ġ�ϴ� �׸��� �������� �ʽ��ϴ�.", "�׸� ������", JOptionPane.INFORMATION_MESSAGE);
			
		}
		return r;
	}
	
	public Boolean deleteReceiving(String productName, int quantity, Date receivingDate){
		//�԰� ���� ����
		//�Ķ���Ϳ� ��ƼƼ �߰�
		ReceivingRecord receivingRecord = new ReceivingRecord(Record.DELETE, productName, quantity, receivingDate);
		boolean chk = rmr.registerRecord(RecordManager.RECEIVING_RECORD, receivingRecord);
		if(chk==false) return chk;
		
		return dm.doWithoutResult("delete from receiving where productid='"+new ProductManager().getProductID(productName)
					+"' AND receivingdate='"+new SimpleDateFormat("yyyy-MM-dd").format(receivingDate)+"';");
	}
	/*
	public Receiving printReceiving(String productName, Date receivingDate) {
		//�԰� ���� ���
		return new Receiving(1,1,1,new Date(), new Date(),1,1);
	}
	
	public Receiving printBarcode(String productName, Date receivingDate) {
		//���ڵ� ���
		return new Receiving(1,1,1,new Date(), new Date(),1,1);
	}
	*/
}