//package com.application;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//@WebServlet(urlPatterns = { "/doGetFollowers" } )
//public class getFollowers extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    public getFollowers() {
//        super();
//    }
//
//    
//    
//	protected void doPost(HttpServletRequest request1, HttpServletResponse response1) throws ServletException, IOException {
//		System.out.println("In doPost");
//		String ServerUrl = "http://localhost:8080/FCISquare/rest/followers";
//		
//		HttpSession session = UserController.request.getSession();
//		String email = (String) 
//		System.out.println("email : " + email);
//		String parameters = "email="+email;
//		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
//				"application/x-www-form-urlencoded;charset=UTF-8");
//		PrintWriter writer = response1.getWriter();
//		JSONObject obj = new JSONObject();
//		JSONParser parser = new JSONParser();
//		try {
//			obj = (JSONObject)parser.parse(retJson);
//			for(int i = 0 ; i<obj.size();i++){
//				writer.println(obj.get("follower #"+(i+1)));
//			}
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//
//}
