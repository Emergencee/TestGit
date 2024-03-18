package com.coffe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.coffe.controller.CoffeController;
import com.coffe.vo.CoffeOrderVO;
import com.coffe.vo.CoffeSaleVO;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		boolean run = true;

		while (run) {
			System.out.println("1. 사용자  2. 운영자");
			String mode = sc.nextLine();
			CoffeController cc = new CoffeController();
			List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
			switch (mode) {
			case "1":
				boolean order_run = true;
				while (order_run) {
					cc.getCoffeList();
					int order = sc.nextInt(); // 메뉴 선택
					System.out.println("몇잔을 주문하시겠습니까? 1~10");
					int cnt = sc.nextInt(); // 잔수 선택
					System.out.println("더 주문하시겠습니까? Y, N");
					String more = sc.next(); // 추가 주문 선택

					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("coffeId", order);
					map.put("orderCnt", cnt);
					map.put("flag", more); // CoffeOrderVO cov = new CoffeOrderVO(order, more, cnt);
					list = cc.setOrderMenu(map);

					int total = 0;
					for (HashMap<String, Object> cv : list) {
						int coffid = (int) cv.get("coffeId");
						HashMap<String, Object> csv = cc.getCoffeNm(coffid);
						String orderNm = (String) csv.get("coffeNm"); // getCoffeNm();
						int orderPrice = (int) csv.get("coffePrice"); // .getPrice();
						int result = orderPrice * (int) cv.get("orderCnt");
						total += result;
						System.out.println("메뉴: " + orderNm + " || 수량: " + (int) cv.get("orderCnt") + "잔 || 합계: "
								+ orderPrice * (int) cv.get("orderCnt") + "원");
					}
					System.out.println("총 합계 : " + total + "원입니다.");
					// System.out.println(coffeNm + "가" + cnt + "잔 주문되었습니다.");
					if (more.equals("N")) {
						order_run = false;
					}
				}
				System.out.println("\n1. 초기 화면\n2. 주문 종료");
				int stop = sc.nextInt();
				if (stop == 2) {
					run = false;
				} else {
					continue;
				}
				break;

			case "2":
				// 1. 일자별 매출 현황
				// 2. 메뉴 추가
				boolean pass = true;
				boolean admin = true;
				while (admin) {
					while (pass) {
						System.out.println("관리자 비밀번호를 입력하세요.");
						String pw = sc.next();
						if (pw.equals("1111")) {
							System.out.println("인증이 완료되었습니다.\n");
							pass = false;
						} else {
							System.out.println("비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
						}
					}
					System.out.println("1. 매출현황  2. 메뉴추가");
					String choo = sc.nextLine();
					if (choo.equals("1")) {
						cc.getDaliySales();
						System.out.println("\n1. 초기 화면\n2. 주문 종료");
						stop = sc.nextInt();
						if (stop == 2) {
							admin = false;
							run = false;
						}if (stop == 1) {
							admin = false;
						} else {
							continue;
						}
					}
					if (choo.equals("2")) {
					}
				}
				break;
			default:
				break;
			}
		}
		
		
	}

}
