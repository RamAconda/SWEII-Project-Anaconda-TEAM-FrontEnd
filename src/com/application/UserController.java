package com.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Path("/")
public class UserController {

	@Context
	//static
	HttpServletRequest request;

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public Response loginPage() {
		return Response.ok(new Viewable("/Login.jsp")).build();
	}

	@GET
	@Path("/signUp")
	@Produces(MediaType.TEXT_HTML)
	public Response signUpPage() {
		return Response.ok(new Viewable("/Signup.jsp")).build();
	}
	@GET
	@Path("/saveplace")
	@Produces(MediaType.TEXT_HTML)
	public Response saveplacesPage() {
		return Response.ok(new Viewable("/saveplace.jsp")).build();
	}
	
	@GET
	@Path("/doshowplace")
	@Produces(MediaType.TEXT_HTML)
	public Response showplace() {
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/PlaceService/Showplcaes";
		//ystem.out.println("email : " + email);
		String parameters ="userid"+id;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String , ArrayList<String>> map = new HashMap<>();
		ArrayList<String> list=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson);
			for(int i = 0 ; i<obj.size();i++){
				list.add((String) obj.get("place #"+(i+1)));
				System.out.println((String) obj.get("place #"+(i+1)));
			}
			map.put("places", list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return Response.ok(new Viewable("/showplaces.jsp")).build();
	}
	
	@GET
	@Path("/showLocation")
	@Produces(MediaType.TEXT_HTML)
	public Response showLocationPage(){
		return Response.ok(new Viewable("/ShowLocation.jsp")).build();
	}
	@GET
	@Path("/addplace")
	@Produces(MediaType.TEXT_HTML)
	public Response addplacePage(){
		return Response.ok(new Viewable("/addplace.jsp")).build();
	}
	
	@GET
	@Path("/AddCheckinpag")
	@Produces(MediaType.TEXT_HTML)
	public Response checkinPage(){
		return Response.ok(new Viewable("/addCheckin.jsp")).build();
	}

	@POST
	@Path("/updateMyLocation")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateLocation(@FormParam("lat") String lat, @FormParam("long") String lon){
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/updatePosition";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/locationServices/updatePosition";

		String urlParameters = "id=" + id + "&lat=" + lat + "&long="+ lon;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		JSONObject obj;
		try {
			obj = (JSONObject)parser.parse(retJson);
			Long status = (Long) obj.get("status");
			if(status == 1)
				return "Your location is updated";
			else
				return "A problem occured";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "A problem occured";
		
	}
	
	@POST
	@Path("/doLogin")
	@Produces(MediaType.TEXT_HTML)
	public Response showHomePage(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/login";
		System.out.println("In do log in function");
		String serviceUrl = "http://localhost:8080/FCISquare/rest/startingServices/login";

		String urlParameters = "email=" + email + "&pass=" + pass;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		System.out.println("back from the server");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			System.out.println("going to parse the return values");
			obj = (JSONObject) parser.parse(retJson);
			session.setAttribute("email", obj.get("email"));
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("long", obj.get("long"));
			session.setAttribute("pass", obj.get("pass"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));
			//HttpSession session = request.getSession();
			int userid= (Integer) session.getAttribute("id");
			String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/followersCheckIns";
			//System.out.println("useris: " + userid);
			String parameters = "userid="+userid;
			String retJson1 = Connection.connect(ServerUrl, parameters, "POST", 
					"application/x-www-form-urlencoded;charset=UTF-8");
			JSONObject obj1 = new JSONObject();
			JSONParser parser1 = new JSONParser();
			Map<String , ArrayList<String>> map1 = new HashMap<>();
			ArrayList<String> list1=new ArrayList<>();
			try {
				obj1 = (JSONObject)parser.parse(retJson1);
				for(int i = 0 ; i<obj1.size();i++){
					list1.add((String) obj1.get("followercheck #"+(i+1)));
					System.out.println((String) obj1.get("followercheckin#"+(i+1)));
				}
				map1.put("followercheckin", list1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String ServerUrl2 = "http://localhost:8080/FCISquare/rest/checkInServices/myCheckIns";
			//System.out.println("useris: " + userid);
				parameters = "userid="+userid;
			String retJson2 = Connection.connect(ServerUrl2, parameters, "POST", 
					"application/x-www-form-urlencoded;charset=UTF-8");
			JSONObject obj2 = new JSONObject();
			JSONParser parser2 = new JSONParser();
			Map<String , ArrayList<String>> map2 = new HashMap<>();
			ArrayList<String> list2=new ArrayList<>();
			try {
				obj = (JSONObject)parser.parse(retJson1);
				for(int i = 0 ; i<obj2.size();i++){
					list2.add((String) obj2.get("mycheckin #"+(i+1)));
					System.out.println((String) obj2.get("mycheckin#"+(i+1)));
				}
				map2.put("mycheckin", list2);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			return Response.ok(new Viewable("/home.jsp", map2)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@POST
	@Path("/doSignUp")
	@Produces(MediaType.TEXT_HTML)
	public Response showHomePage(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/signup";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/startingServices/signup";

		String urlParameters = "name=" + name + "&email=" + email + "&pass="
				+ pass;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			session.setAttribute("email", obj.get("email"));
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("long", obj.get("long"));
			session.setAttribute("pass", obj.get("pass"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));
			
			

			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	@POST
	@Path("/doGetFollowers")
	@Produces(MediaType.TEXT_HTML)
	public Response getFollowers(){
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/followingServices/followers";
		System.out.println("email : " + email);
		String parameters = "email="+email;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String , ArrayList<String>> map = new HashMap<>();
		ArrayList<String> list=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson);
			for(int i = 0 ; i<obj.size();i++){
				list.add((String) obj.get("follower #"+(i+1)));
				System.out.println((String) obj.get("follower #"+(i+1)));
			}
			map.put("followers", list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/getFollowers.jsp", map)).build();
	}
	
	@POST
	@Path("/follow")
	@Produces(MediaType.TEXT_HTML)
	public Response call_follow(){
		return Response.ok(new Viewable("/follow.jsp")).build();
	}
	
	
	@POST
	@Path("/doFollow")
	@Produces(MediaType.TEXT_HTML)
	public String follow(@FormParam("tofollow") String tofollow){
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String pass = (String) session.getAttribute("Pass");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/followingServices/follow";
		String parameters = "Follower_email="+email+"&pass="+pass+"&Followed_email="+tofollow;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			Long status = (Long) obj.get("status");
			if(status == 1)
				return "now you follow -> "+tofollow;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "there is a problem";
	}
	
	@POST
	@Path("/unfollow")
	@Produces(MediaType.TEXT_HTML)
	public Response call_unfollow(){
		return Response.ok(new Viewable("/unfollow.jsp")).build();
	}
	
	
	@POST
	@Path("/dounFollow")
	@Produces(MediaType.TEXT_HTML)
	public String unfollow(@FormParam("tounfollow") String tounfollow){
		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("email");
		String pass = (String) session.getAttribute("Pass");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/followingServices/unfollow";
		String parameters = "Follower_email="+email+"&pass="+pass+"&Followed_email="+tounfollow;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			Long status = (Long) obj.get("status");
			if(status == 1)
				return "unfollow is done";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "there is a problem";
	}
	
	@POST
	@Path("/AddNewPlace")
	@Produces(MediaType.TEXT_HTML)
	public Response showhomePage(@FormParam("name") String name,
			@FormParam("decription") String decription, @FormParam("lat") double lat, @FormParam("lng") double lng){
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/signup";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/PlaceService/addnewplace";

		String urlParameters = "name=" + name + "&decription=" + decription + "&lat="
				+ lat+"&lng="+lng; 
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		HttpSession session = request.getSession();
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			
			session.setAttribute("name", obj.get("name"));
			session.setAttribute("id", obj.get("id"));
			session.setAttribute("lat", obj.get("lat"));
			session.setAttribute("lng", obj.get("lng"));
			session.setAttribute("decription", obj.get("decription"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));

			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	@POST
	@Path("/SavePlace")
	@Produces(MediaType.TEXT_HTML)
	public Response showhomePage(@FormParam("placeid") int id){
		//String serviceUrl = "http://se2firstapp-softwareeng2.rhcloud.com/FCISquare/rest/signup";
		String serviceUrl = "http://localhost:8080/FCISquare/rest/PlaceService/SavePlacee";
		HttpSession session = request.getSession();
		
	Integer userid =(Integer) session.getAttribute("id");
		String urlParameters = "&placeid=" + id + "&userid="+userid ;
		// System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			
			session.setAttribute("usermail", obj.get("usermail"));
			session.setAttribute("id", obj.get("id"));
			Map<String, String> map = new HashMap<String, String>();

			map.put("name", (String) obj.get("name"));
			map.put("email", (String) obj.get("email"));

			return Response.ok(new Viewable("/home.jsp", map)).build();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	@POST
	@Path("/ShowPlaces")
	@Produces(MediaType.TEXT_HTML)
	public Response getPlaces(){
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/PlaceService/Showplcaes";
		//ystem.out.println("email : " + email);
		String parameters ="userid"+id;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String , ArrayList<String>> map = new HashMap<>();
		ArrayList<String> list=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson);
			for(int i = 0 ; i<obj.size();i++){
				list.add((String) obj.get("place #"+(i+1)));
				System.out.println((String) obj.get("place #"+(i+1)));
			}
			map.put("places", list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/showplaces.jsp", map)).build();
		}
	@POST
	@Path("/GetCheckin")
	@Produces(MediaType.TEXT_HTML)
	public Response getCheckin(@FormParam("user_Id") int user_Id){
		HttpSession session = request.getSession();
		//String email = (String) session.getAttribute("email");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/followersCheckIns";
		String parameters = "user_Id="+user_Id;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String , ArrayList<String>> map = new HashMap<>();
		ArrayList<String> list=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson);
			for(int i = 0 ; i<obj.size();i++){
				list.add((String) obj.get("checkin#"+(i+1)));
				System.out.println((String) obj.get("checkin #"+(i+1)));
			}
			map.put("checkin", list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/checkin.jsp", map)).build();
	}
	@POST
	@Path("/ShowCheckin")
	@Produces(MediaType.TEXT_HTML)
	public Response showCheckin(@FormParam("checkin_Id") int checkin_Id){
		HttpSession session = request.getSession();
		try {
			
		
			session.setAttribute("checkin", checkin_Id);

		} catch (Exception e) {
			// TODO: handle exception
		}
			
		//String email = (String) session.getAttribute("email");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/followersCheckIns";
		String parameters = "checkin_Id="+checkin_Id;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String , ArrayList<String>> map = new HashMap<>();
		ArrayList<String> list=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson);
			for(int i = 0 ; i<obj.size();i++){
				list.add((String) obj.get("checkin#"+(i+1)));
				System.out.println((String) obj.get("checkin #"+(i+1)));
			}
			map.put("checkin", list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/showcheckin.jsp", map)).build();
	}

	@POST
	@Path("/doComment")
	@Produces(MediaType.TEXT_HTML)
	public String comment(@FormParam("Comment") String Comment,@FormParam("checkinid") int checkinid){
		HttpSession session = request.getSession();
		int userid= (Integer) session.getAttribute("id");
	//	int checkinid= (Integer) session.getAttribute("checkin");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/comment";
		String parameters = "userid="+userid+"&checkinid="+checkinid+"&comment="+Comment;
		 Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
//		JSONObject obj = new JSONObject();
//		JSONParser parser = new JSONParser();
//		try {
//			obj = (JSONObject) parser.parse(retJson);
//			Long status = (Long) obj.get("status");
//			if(status == 1)
//				return "successful comment";
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "there is a problem";
		return "successful comment";
	}
	@POST
	@Path("/doLike")
	@Produces(MediaType.TEXT_HTML)
	public String Like(@FormParam("checkinid") int checkinid){
		HttpSession session = request.getSession();
		int userid= (Integer) session.getAttribute("id");
		//int checkinid= (Integer) session.getAttribute("checkin");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/like";
		String parameters = "userid="+userid+"&checkinid="+checkinid;
		 Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
//		JSONObject obj = new JSONObject();
//		JSONParser parser = new JSONParser();
//		try {
//			obj = (JSONObject) parser.parse(retJson);
//			Long status = (Long) obj.get("status");
//			if(status == 1)
//				return "successful comment";
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return "there is a problem";
		return "successful Like";
	}
	@POST
	@Path("/addCheckin")
	@Produces(MediaType.TEXT_HTML)
	public String follow(@FormParam("Placeid") int place_id,@FormParam("description") String description){
		HttpSession session = request.getSession();
		int userid  = (Integer) session.getAttribute("id");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/checkIn";
		String parameters = "userId="+userid+"&placeId="+place_id+"&description="+description;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		try {
			obj = (JSONObject) parser.parse(retJson);
			
			
				return  "successful checkin";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "there is a problem";
	}
	@POST
	@Path("/HomePage")
	@Produces(MediaType.TEXT_HTML)
	public Response Home(){
		HttpSession session = request.getSession();
		Integer userid= (Integer) session.getAttribute("id");
		String ServerUrl = "http://localhost:8080/FCISquare/rest/checkInServices/followersCheckIns";
		//System.out.println("useris: " + userid);
		String parameters = "userid="+userid;
		String retJson = Connection.connect(ServerUrl, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj = new JSONObject();
		JSONParser parser = new JSONParser();
		Map<String , ArrayList<String>> map = new HashMap<>();
		ArrayList<String> list=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson);
			for(int i = 0 ; i<obj.size();i++){
				list.add((String) obj.get("followercheck #"+(i+1)));
				System.out.println((String) obj.get("followercheckin#"+(i+1)));
			}
			map.put("followercheckin", list);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String ServerUrl2 = "http://localhost:8080/FCISquare/rest/checkInServices/myCheckIns";
		//System.out.println("useris: " + userid);
			parameters = "userid="+userid;
		String retJson1 = Connection.connect(ServerUrl2, parameters, "POST", 
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONObject obj2 = new JSONObject();
		JSONParser parser2 = new JSONParser();
		Map<String , ArrayList<String>> map2 = new HashMap<>();
		ArrayList<String> list2=new ArrayList<>();
		try {
			obj = (JSONObject)parser.parse(retJson1);
			for(int i = 0 ; i<obj2.size();i++){
				list2.add((String) obj2.get("mycheckin #"+(i+1)));
				System.out.println((String) obj2.get("mycheckin#"+(i+1)));
			}
			map2.put("mycheckin", list2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Response.ok(new Viewable("/home.jsp", map)).build();
	}
	

}


















