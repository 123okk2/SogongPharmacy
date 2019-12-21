package ReturnDisposalManagement;

import java.sql.*;
import java.text.*;
import java.time.LocalDate;

import DBManagement.DBManager;
import StockManagement.StockManager;
import StockManagement.StockManagerUI;

public class ReturnDisposalManager {

	DBManager dbm = new DBManager();
	
	// 반품 목록에 등록
	public void registerReturnDisposal(int barcode, int supplierID, int quantity, String state, String returnReason) {
		String query = null;
		int nextRow = 0;
		ReturnDisposal rd = null;

		// id 자동생성 과정
		String nowDate = LocalDate.now().toString().replace("-", "");
		query = "Select * FROM return_disposal where returnDisposalID rlike '" + nowDate +"';";
		ResultSet rs = dbm.doWithResult(query);
		try {
			rs.last();
			// 해당 일의 첫 등록일 시 기본 id 생성
			if(rs.getRow() == 0) {
				nextRow = Integer.parseInt(nowDate + String.format("%02d", 0));
			}
			// 해당 일에 이미 등록한 이력이 있을 경우 id 자동 생성
			else {
				rd = new ReturnDisposal(rs.getInt("returnDisposalID"), rs.getInt("barcode"), rs.getInt("supplierID"),
						rs.getInt("quantity"), rs.getString("state"), rs.getString("returnReason"));
				nextRow = rd.getReturnDisposalID() + 1;
			}
			System.out.print(nextRow);
		} catch (Exception e) {
			System.out.println("기존 행 수 구하기 실패");
		}
		// 등록 과정
		query = "insert into return_disposal(returnDisposalID, barcode, supplierID, quantity, state, returnReason)"
				+ " values('" + nextRow + "','" + barcode + "','" + supplierID + "','" + quantity + "','"
				+ state + "','" + returnReason + "');";
		
		dbm.doWithoutResult(query);
	}
	
	// 반품 목록에서 수정
	public void updateReturnDisposal(int ReturnDisposalID, int barcode, int supplierID, int quantity, String state, String returnReason) {
		String query = null;
		ReturnDisposal rd = new ReturnDisposal(ReturnDisposalID, barcode, supplierID, quantity, state, returnReason);
		
		query = "update return_disposal set barcode='" + rd.getBarcode() + "', supplierID='" + rd.getSupplierID() + "', quantity='" + rd.getQuantity()
				+ "', state='" + rd.getState() + "', returnReason='" + rd.getReason() + "' where returnDisposalID='" + rd.getReturnDisposalID() + "';";
		
		dbm.doWithoutResult(query);
	}
	
	// 반품 목록에서 삭제
	public String deleteReturnDisposal(int ReturnDisposalID) {
		String query = null;
		query = "delete from return_disposal where returnDisposalID='" + ReturnDisposalID + "';";

		dbm.doWithoutResult(query);
		
		return "삭제 완료되었습니다.";
	}
	
	// 반품 목록에서 완료처리
	public String completeReturnDisposal(ReturnDisposal rd) {
		StockManager SM = new StockManager();
		String query = null;
		
		if(rd.getState().equals("반품대기")) {
			rd.setState("반품완료");
		}
		else if(rd.getState().equals("폐기대기")) {
			rd.setState("폐기완료");
		}
		else if(rd.getState().equals("반품완료") || rd.getState().equals("폐기완료")) {
			return "이미 완료처리된 상태입니다.";
		}

		query = "update return_disposal set barcode='" + rd.getBarcode() + "', supplierID='" + rd.getSupplierID() + "', quantity='" + rd.getQuantity()
				+ "', state='" + rd.getState() + "', returnReason='" + rd.getReason() + "' where returnDisposalID='" + rd.getReturnDisposalID() + "';";
		
		dbm.doWithoutResult(query);
		
		
		query = "update stockcondition set quantity = quantity - "+rd.getQuantity()+" where productID = (select productID from stock where barcode = "+rd.getBarcode()+")";
		dbm.doWithoutResult(query);
		
		return "완료처리되었습니다.";
	}
	
	// 반품 목록에서 조회
	public String[][] inquiryReturnDisposal(String NameInput) {
		ResultSet rs = null;
		String query = null;
		String[][] rows = null;
		
		System.out.println(NameInput);

		if(NameInput.equals("")) {
			query = "select b.returnDisposalID, b.productName, b.quantity, supplier.supplierName, b.expirationDate, b.state, b.returnReason "
					+ "from (select a.returnDisposalID, product.productName, a.quantity, a.supplierID, a.expirationDate, a.state, a.returnReason "
					+ "from (select r.returnDisposalID, r.quantity, stock.supplierID, stock.expirationDate, r.state, r.returnReason, stock.productID "
					+ "from return_disposal as r "
					+ "join stock on r.barcode = stock.barcode) as a "
					+ "join product on a.productID = product.productID) as b "
					+ "join supplier on b.supplierID = supplier.supplierID "
					+ "order by b.returnDisposalID;";
		}else {
			query = "select * "
					+ "from (select b.returnDisposalID, b.productName, b.quantity, supplier.supplierName, b.expirationDate, b.state, b.returnReason "
					+ "from (select a.returnDisposalID, product.productName, a.quantity, a.supplierID, a.expirationDate, a.state, a.returnReason "
					+ "from (select r.returnDisposalID, r.quantity, stock.supplierID, stock.expirationDate, r.state, r.returnReason, stock.productID "
					+ "from return_disposal as r "
					+ "join stock on r.barcode = stock.barcode) as a "
					+ "join product on a.productID = product.productID order by a.returnDisposalID) as b "
					+ "join supplier on b.supplierID = supplier.supplierID order by b.returnDisposalID) as c "
					+ "where c.productName rlike '" + NameInput +"';";
		}
		try {
			rs = dbm.doWithResult(query);
			DateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
			rs.last();
			int rowCount;
			rowCount = rs.getRow();
			rs.beforeFirst();
			rows = new String[rowCount][7];
			while (rs.next()) {
				rows[rs.getRow() - 1][0] = Integer.toString(rs.getInt("returnDisposalID"));
				rows[rs.getRow() - 1][1] = rs.getString("productName");
				rows[rs.getRow() - 1][2] = Integer.toString(rs.getInt("quantity"));
				rows[rs.getRow() - 1][3] = rs.getString("supplierName");
				rows[rs.getRow() - 1][4] = sdFormat.format(rs.getDate("expirationDate"));
				rows[rs.getRow() - 1][5] = rs.getString("state");
				rows[rs.getRow() - 1][6] = rs.getString("returnReason");
			}
		} catch (Exception e) {
			System.out.println("목록 배열 생성 실패  : " + e);
		}
		return rows;
	}
	
	// 반품 목록에서 단일 항목 검색	
	public ReturnDisposal searchReturnDisposal(int ReturnDisposalID) {
		ResultSet rs = null;
		String query = null;
		ReturnDisposal rd = null;
		
		query = "select * from return_disposal where returnDisposalID = '" + ReturnDisposalID + "';";

		rs = dbm.doWithResult(query);

		try {
			if (rs.next()) {
				rd = new ReturnDisposal(rs.getInt("returnDisposalID"), rs.getInt("barcode"), rs.getInt("supplierID"),
						rs.getInt("quantity"), rs.getString("state"), rs.getString("returnReason"));
			}
		} catch (Exception e) {
			System.out.println("DB SELECT 오류 : " + e);
		}

		return rd;
	}
}
