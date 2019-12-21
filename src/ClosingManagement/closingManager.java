package ClosingManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DBManagement.DBManager;
import ProductManagement.ProductManager;
import RecordManagement.ClosingRecord;
import RecordManagement.RecordManager;

public class closingManager {
	private DBManager DB;
	private Date date;

	public closingManager() {
		DB = new DBManager();
	}
	// 마감정보를 DB에 등록하는 함수
	public boolean registerClosingInform(int balance) {
		boolean re = true;
		date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet check = DB.doWithResult("select EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')"); // 당일 등록된 마감정보가 있는지 체크
		try {
			if(check.next())
			{
				if(check.getBoolean("EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')"))return false; // 존재하면 false return 후 함수 종료
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = DB.doWithResult(
				"select productID, salesdate, SUM(productquantity)productquantity from sale join salesproduct on sale.saleId = salesproduct.saleID where sale.salesdate = '"+ format1.format(date.getTime()) +"' group by productID");
		// DB의 sale과 salesproduct 테이블을 join해 당일 판매된 상품의 ID, 판매일, 판매량을 받는데 group by ID를 통해 상품ID가 같은 판매기록은 통합해 품목별 통합 판매량을 알수있게함
		ArrayList<salesPerform> list = new ArrayList<salesPerform>();
		try {
			while (rs.next()) {
				int pI = rs.getInt("productID");
				int pq = rs.getInt("productquantity");
				list.add(new salesPerform (date, pI, pq));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < list.size(); i++) {
			re = DB.doWithoutResult(
					"insert into salesPerform(salesDate,productID,salesvolumePerDay) values('"
							+ format1.format(list.get(i).getSalesDate().getTime()) + "'," + list.get(i).getProductID()+ "," + list.get(i).getSalesVolumePerDay() + ")");
			// 위에서 얻어온 일별 품목별 판매량을 판매실적(salesPerform) 테이블에 저장함
			if (!re)
				return false;
		}
		rs = DB.doWithResult("select SUM(productPrice*salesvolumePerDay) as sale from product join salesperform on product.productId = salesperform.productID where salesDate = '"+format1.format(date.getTime())+"'");
		// 품목별 일별 판매량을 총합하여 일별 (총)매출액을 산출해냄
		try {
			rs.next();
			DB.doWithoutResult("insert into closingInform(closingDate,salesPerDay,balance) values('"+ format1.format(date.getTime()) + "'," + rs.getInt("sale") + "," + balance + ")");
			// 마감등록을 요청한 날짜, 산출해낸 일별 매출액, parameter로 받은 잔액을 마감정보 테이블에 등록함
			new RecordManager().registerRecord(5, new ClosingRecord(0, date, rs.getInt("sale"), balance)); // 마감이력 생성
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
	
// 일보(일일보고)조회 한 내용을 String 2차원 배열 ( 테이블에 적용하기 위해 ) 로 반환
	public String[][] inquiryDailyReport() {
		date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = DB.doWithResult("select salesperform.productID, salesvolumePerDay, productPrice*salesvolumePerDay as sale from product join salesperform on product.productId = salesperform.productID where salesDate ='" + format1.format(date.getTime()) + "' order by sale desc");
		// salesPerform 테이블과 product 테이블 조인을 통해 sale 테이블에 존재한 판매된 상품의 가격을 가져와 품목별 ID, 판매량, 판매량*가격을 반환받음 ( 총 판매액 내림차순 )
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			while (rs.next()) {
				String pID = Integer.toString(rs.getInt("productID"));
				String svPD = Integer.toString(rs.getInt("salesvolumePerDay"));
				String s = Integer.toString(rs.getInt("sale"));
				list.add(new String[] {pID, svPD, s});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] relist = new String[list.size()][];
		ProductManager PM = new ProductManager();
		for (int i = 0; i < list.size(); i++) {
				relist[i] = new String[] { PM.getProductName(Integer.parseInt(list.get(i)[0])), list.get(i)[1], list.get(i)[2] };
		// String 2차원 배열의 행별로 ' 상품명, 판매량, 매출액 ' 데이터를 넣음
		}
		try {
			rs.close();
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}

	public String[][] inquiryMonthlyReport() {
		date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); // 당월의 첫날
		Date startDay = calendar.getTime(); 
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(calendar.DAY_OF_MONTH)); // 당월의 마지막날
		Date endDay = calendar.getTime(); 
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs = DB.doWithResult("select salesperform.productID, SUM(salesvolumePerDay)salesvolumePerDay, SUM(productPrice*salesvolumePerDay)sale from product join salesperform on product.productId = salesperform.productID where salesDate >= '" + format1.format(startDay) + "' and salesDate <= '"+format1.format(endDay)+"' group by productID order by sale desc");
		// salesPerform 테이블과 product 테이블을 join 하여 월의 첫날과 마지막날 사이의 등록된 매출실적 데이터들을 productID를 기준으로 판매량을 통합해 product 테이블의 가격과 곱해 월별 매출액을 내림차순으로 산출함
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			while (rs.next()) {
				String pID = Integer.toString(rs.getInt("productID"));
				String svPD = Integer.toString(rs.getInt("salesvolumePerDay"));
				String s = Integer.toString(rs.getInt("sale"));
				list.add(new String[] {pID, svPD, s});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] relist = new String[list.size()][];
		ProductManager PM = new ProductManager();
		for (int i = 0; i < list.size(); i++) {
				relist[i] = new String[] { PM.getProductName(Integer.parseInt(list.get(i)[0])), list.get(i)[1], list.get(i)[2] };
				// 일보조회때와 동일하게 ' 상품명, (당월)판매량, (당월)매출액 ' 을 String 2차원 배열의 행마다 넣음
		}
		try {
			rs.close();
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}
	public boolean isClosed()
	{		
		date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet check = DB.doWithResult("select EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')");
		try {
			if(check.next())
			{
				if(check.getBoolean("EXISTS (select * from closingInform where closingDate = '"+format1.format(date.getTime())+"')"))return true; // 존재하면 false return 후 함수 종료
			}
			else return false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}
}
