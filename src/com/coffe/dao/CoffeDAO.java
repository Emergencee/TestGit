package com.coffe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.coffe.vo.CoffeOrderVO;
import com.coffe.vo.CoffeSaleVO;

public class CoffeDAO {

	public List<HashMap<String, Object>> getCoffeList() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		// List <CoffeSaleVO>list = new ArrayList<CoffeSaleVO>();

		try {
			String url = "jdbc:mysql://localhost:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String id = "root";
			String pass = "1111";
			String driver = "com.mysql.jdbc.Driver";
			Connection con = DriverManager.getConnection(url, id, pass);

			Statement st = con.createStatement();

			ResultSet rs = null;

			String sql = "SELECT tcm.COFFE_ID, tcm.COFFE_NM, tcn.PRICE " + "   FROM TB_COFFE_001MT tcm, "
					+ "  	    TB_COFFE_002NT tcn " + "  WHERE tcm.USEYN ='Y' " + "    AND tcn.PRICE_DATE ='202403' "
					+ "    AND tcm.COFFE_ID = tcn.COFFE_ID ";

			rs = st.executeQuery(sql);

			while (rs.next()) {
				int coffeId = rs.getInt("COFFE_ID");
				String coffeNm = rs.getString("COFFE_NM");
				int price = rs.getInt("PRICE");

				HashMap<String, Object> map = new HashMap<>();

				map.put("coffeId", coffeId);
				map.put("coffeNm", coffeNm);
				map.put("price", price);


				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<HashMap<String, Object>> getDaliySales() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		try {
			String url = "jdbc:mysql://localhost:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String id = "root";
			String pass = "1111";
			String driver = "com.mysql.jdbc.Driver";
			Connection con = DriverManager.getConnection(url, id, pass);

			Statement st = con.createStatement();

			ResultSet rs = null;
			String mdate = "202403";
			String sql = "SELECT DATE_FORMAT(tsn.SALE_DATE, '%Y-%m-%d')as SALE_DATE ,tcm.COFFE_ID, tcm.COFFE_NM, "
					+ "	   tcn.PRICE, SUM(tsn.SALE_CNT) AS SALE_CNT " + "		FROM TB_COFFE_001MT tcm, "
					+ "  				 TB_COFFE_002NT tcn, " + "  				 TB_SALES_001NT tsn "
					+ " 	 WHERE tcm.USEYN ='Y' " + "   AND tcn.PRICE_DATE = " + mdate
					+ "   AND tcm.COFFE_ID = tcn.COFFE_ID " + "   AND tcm.COFFE_ID = tsn.COFFE_ID "
					+ "   AND DATE_FORMAT(tsn.SALE_DATE, '%Y-%m-%d') = DATE_FORMAT(now(), '%Y-%m-%d') "
					+ "  GROUP BY DATE_FORMAT(tsn.SALE_DATE, '%Y-%m-%d'),tcm.COFFE_ID, tcm.COFFE_NM, tcn.PRICE; ";

			rs = st.executeQuery(sql);

			while (rs.next()) {
				Date saleDate = rs.getDate("SALE_DATE");
				int coffeId = rs.getInt("COFFE_ID");
				String coffeNm = rs.getString("COFFE_NM");
				int price = rs.getInt("PRICE");
				int saleCnt = rs.getInt("SALE_CNT");

				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("coffeId", coffeId);
				map.put("coffeNm", coffeNm);
				map.put("price", price);
				map.put("saleCnt", saleCnt);
				map.put("saleDate", saleDate);

//				CoffeSaleVO csv = new CoffeSaleVO(coffeId, coffeNm, price, saleCnt, saleDate, "202403");
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 주문서 입력
	public void setOrderMenu(List<HashMap<String, Object>> orderList) {
		try {
			String url = "jdbc:mysql://localhost:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String id = "root";
			String pass = "1111";
			String driver = "com.mysql.jdbc.Driver";
			Connection con = DriverManager.getConnection(url, id, pass);
			PreparedStatement psmt = null;

			String sql = "insert into TB_SALES_001NT (COFFE_ID, SALE_CNT) " + "values(?, ?)";

			for (HashMap<String, Object> map : orderList) {
				int coffeId = (int) map.get("coffeId");
				int cnt = (int) map.get("orderCnt");

				psmt = con.prepareStatement(sql);

				psmt.setInt(1, coffeId);
				psmt.setInt(2, cnt);

				psmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Object> getCoffeInfo(int coffeId) {
		HashMap<String, Object> csv = new HashMap<String, Object>();
		try {
			String url = "jdbc:mysql://localhost:3306/testDB?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String id = "root";
			String pass = "1111";
			String driver = "com.mysql.jdbc.Driver";
			Connection con = DriverManager.getConnection(url, id, pass);

			Statement st = con.createStatement();

			ResultSet rs = null;
			String sql = "  SELECT tcm.COFFE_NM, tcn.PRICE  " + "    FROM TB_COFFE_001MT tcm,  "
					+ "         TB_COFFE_002NT tcn " + "   WHERE tcm.COFFE_ID = " + coffeId
					+ "     AND tcm.COFFE_ID = tcn.COFFE_ID " + "     AND tcn.PRICE_DATE = '202403' ";

			rs = st.executeQuery(sql);

			if (rs.next()) {
				String coffeNm = rs.getString("COFFE_NM");
				int coffePrice = rs.getInt("PRICE");
				csv.put("coffeNm", coffeNm); // setCoffeNm(coffeNm);
				csv.put("coffePrice", coffePrice); // .setPrice(coffePrice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return csv;
	}
}