package StatsManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import ClosingManagement.closingInform;
import ClosingManagement.salesPerform;
import DBManagement.DBManager;
import ProductManagement.ProductManager;

public class statsInquiryManager {
	private DBManager DB;
	private SimpleDateFormat format1;

	public statsInquiryManager() {
		DB = new DBManager();
		format1 = new SimpleDateFormat("yyyy-MM-dd");
	}

	// startDay 부터 endDay까지 매출실적 ( 사실상 일별 마감실적 조회 )을 조회함.
	public String[][] inquirySalesPerform(Date startDay, Date endDay)
	{
		ResultSet rs = DB.doWithResult("select * from closingInform where closingDate >= '"+format1.format(startDay.getTime())+"' and closingDate <= '"+format1.format(endDay.getTime())+"'");
		ArrayList<closingInform> list = new ArrayList<closingInform>();
		try {
			while(rs.next())
			{
				Date cD = rs.getDate("closingDate");
				int sPD = rs.getInt("salesPerDay");
				int b = rs.getInt("balance");
				list.add(new closingInform(cD,sPD,b));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] relist = new String[list.size()][];
		for(int i=0;i<list.size();i++)
		{
			relist[i] = new String[] { format1.format(list.get(i).getClosingDate()), Integer.toString(list.get(i).getSalesPerDay()), Integer.toString(list.get(i).getBalance()) };
		// '마감일, 매출액, 잔액'을 String 2차원 배열에 넣음 
		}
		return relist;
	}

	public String[][] inquiryMajorPerform(Date startDay, Date endDay) {
		ProductManager PM = new ProductManager();
		ResultSet rs = DB.doWithResult("select salesperform.productID, SUM(salesvolumePerDay)salesvolumePerDay, SUM(productPrice*salesvolumePerDay)sale from product join salesperform on product.productId = salesperform.productID where salesDate >= '"+format1.format(startDay.getTime())+"' and salesDate <= '"+format1.format(endDay.getTime())+"' group by productID order by sale desc");
		// 판매량*상품금액을 곱한 값을 얻어내기 위해 product 테이블과 salesPerform 테이블을 조인하여 startDay 부터 endDay까지의 salesPerform의 값들을 받아와 기간 내 상품들의 판매량, 판매금액을 산출해내 내림차순 정렬
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			while(rs.next())
			{
				String pI = Integer.toString(rs.getInt("productID"));
				String svPD = Integer.toString(rs.getInt("salesvolumePerDay"));
				String s = Integer.toString(rs.getInt("sale"));
				list.add(new String[] {pI, svPD, s});
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rs = DB.doWithResult("select COUNT(saleID) from sale where salesDate >= '"+format1.format(startDay.getTime())+"' and salesDate <= '"+format1.format(endDay.getTime())+"'");
		// startDay와 endDay의 기간 사이의 saleID가 몇개인지, 즉 판매가 몇번 일어났는지 = 기간내 방문자 수가 몇명인지 count함.
		int count = 0;
		try {
			rs.next();
			count = rs.getInt("COUNT(saleID)");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String[][] relist = new String[list.size()+1][]; // 배열의 마지막에는 총 방문자수를 넣기위해 실제 사이즈보다 +1함
		for (int i = 0; i < list.size(); i++) {
				relist[i] = new String[] { PM.getProductName(Integer.parseInt(list.get(i)[0])) , list.get(i)[1], list.get(i)[2] };
		// ' 상품명, 판매량, 판매금액 '을 String 2차원 행렬에 행별로 기입
		}
		relist[list.size()] = new String[] { Integer.toString(count) };
		// String 2차원 배열의 마지막 행에 총방문자수 count를 기입
		try {
			rs.close();
			DB.closeDB();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}

	public String[][] inquiryRealtimeDashboard() {
		ProductManager PM = new ProductManager();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)+1); // 이주의 첫날 (월요일) 세팅
		Date WstartDay = calendar.getTime();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)+8); // 이주의 마지막날 (일요일) 세팅
		Date WendDay = calendar.getTime();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMinimum(Calendar.DAY_OF_MONTH)); // 이달의 첫날 세팅
		Date MstartDay = calendar.getTime();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(calendar.DAY_OF_MONTH)); // 이달의 마지막날 세팅
		Date MendDay = calendar.getTime();
		
		String querySOD = "select SUM(productPrice*productquantity) as sale from ( select productID, salesdate, SUM(productquantity)productquantity from sale join salesproduct on sale.saleId = salesproduct.saleID where sale.salesdate = '"+format1.format(date.getTime())+"' group by productID )result join product on result.productID = product.productID";
		// 서브쿼리를 통해 sale과 salesproduct 테이블을 join하여 (productID, salesDate, productquantity) 테이블을 생성해 이를 product 테이블과 join 시켜 productquantity와 productPrice를 곱한 값들을 모두 SUM한 값, 즉 오늘의 실시간 매출액을 조회함
		String querySOW = "select SUM(productPrice*salesvolumePerDay) as sale from salesPerform join product on salesperform.productID = product.productID where salesDate >= '"+format1.format(WstartDay)+"' and salesDate <= '"+format1.format(WendDay)+"' and not salesDate in ('"+format1.format(date.getTime())+"')";
		// 위에서 세팅해둔 주의 시작일과 주의 마지막일 사이의 salesPerform와 product 테이블을 join하여 기간내의 salesPerform을 productID를 통해 grouping 하고 상품별 price와 productquantity를 곱한 값들을 모두 SUM 한값, 즉 이주의 판매액을 조회해옴
		// 이때 where문에서 not in 을 통해 당일의 salesperform column들은 가져오지 않는데, 이는 당일 마감후 실시간 대시보드를 이용할경우 sale 테이블 내의 값과 중복되어 당일 매출액이 중복으로 더해질수 있기 때문 ( 이는 이달의 매출액 조회시에도 동일 )
		String querySOM = "select SUM(productPrice*salesvolumePerDay) as sale from salesPerform join product on salesperform.productID = product.productID where salesDate >= '"+format1.format(MstartDay)+"' and salesDate <= '"+format1.format(MendDay)+"' and not salesDate in ('"+format1.format(date.getTime())+"')";
		// 위에서 세팅해둔 월의 시작일과 월의 마지막일 사이의 sales.... 이뒤의 내용은 이주의 매출액과 동일
		String queryTop5 = "select productID, SUM(productquantity)productquantity from (select productID ,SUM(salesvolumePerDay)productquantity from salesPerform where salesDate >= '"+format1.format(MstartDay)+"' and salesDate <= '"+format1.format(MendDay)+"' and not salesDate in ('"+format1.format(date.getTime())+"') group by productID union select productID, SUM(productquantity)productquantity from sale join salesproduct on sale.saleId = salesproduct.saleID where sale.salesdate = '"+format1.format(date.getTime())+"' group by productID)result group by productID order by productquantity desc limit 5";
		// 이달의 최다 판매품목 Top5를 조회함, 1:salesPerform 테이블에서 당월 첫 날~ 당월 마지막 날까지의 ( 단 당일 데이터는 not in으로 제외 ) 데이터들 productID가 같은것들 grouping 후 productquantity 통합한 테이블 생성, 2:sale 테이블과 salesproduct 테이블을 join하고, salesdate가 당일인 column중 productID가 같은것들 grouping 후 productquantity 합침 , 3:이 두테이블을 union 하여 당일(실시간) + 이달의 품목별 판매량을 조회하고 판매량 내림차순으로 가장 상위 5개만 조회함
		int saleOfDay, saleOfWeek, saleOfMonth = 0;
		ResultSet rs = DB.doWithResult(querySOD);
		String[][] relist = new String[8][]; // 오늘의 매출액, 이주의 매출액, 이달의 매출액  + 최다판매품목 Top5를 남은 5칸에 저장
		try {
			rs.next();
			saleOfDay = rs.getInt("sale");
			rs = DB.doWithResult(querySOW);
			rs.next();
			saleOfWeek = rs.getInt("sale")+saleOfDay; // 이주의 매출액 ( 당일 제외 ) + 오늘의 매출액 ( 실시간 )
			rs = DB.doWithResult(querySOM);
			rs.next();
			saleOfMonth = rs.getInt("sale")+saleOfDay; // 이달의 매출액 ( 당일 제외 ) + 오늘의 매출액  ( 실시간 )
			relist[0] = new String[] { Integer.toString(saleOfDay) };
			relist[1] = new String[] { Integer.toString(saleOfWeek) };
			relist[2] = new String[] { Integer.toString(saleOfMonth) };
			rs = DB.doWithResult(queryTop5);
			int count = 3;
			while(rs.next()){
					relist[count++] = new String[] { PM.getProductName(rs.getInt("productID")), Integer.toString(rs.getInt("productquantity"))};
			}
			if(count!=7)
			{
				for(;count<relist.length;count++)
					relist[count] = new String[] {" ", " "};
			}
			DB.closeDB();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return relist;
	}
}
