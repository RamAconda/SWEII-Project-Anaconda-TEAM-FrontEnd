<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import= "java.util.ArrayList" %>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	
    	<form action="follow" method="post">
		<input type="submit" value="Follow"/>
	</form>
    <form action="unfollow" method="post">
		<input type="submit" value="UnFollow"/>
	</form>
	<form action="doGetFollowers" method="post">
		<input type="submit" value="Followed"/>
	</form> 
    </form>
	<form action="AddCheckinpage" method="get">
		<input type="submit" value="Checkin"/>
	</form> 
    <form action="addplace" method="get">
		<input type="submit" value="Add Place"/>
	</form> 
        <form action="saveplace" method="get">
		<input type="submit" value="Save Place"/>
	</form> 
     <c:forEach items= "${it.mycheckin }" var="mycheckin" > 
 		<c:out value="${ mycheckin}"/><br> 
 	</c:forEach> 
     <c:forEach items= "${it.followercheckin }" var="followercheckin" > 
 		<c:out value="${ followercheckin}"/><br> 
	</c:forEach> 
    
       <form action="doComment" method="post"> 
       <input type ="text"  name ="checkinid"> 
      <input type="text" name="comment"/>
      <input type="submit"  value="comment" />
	</form> 
     <form action="doLike" method="post"> 
      <br>
       <input type ="text"  name ="checkinid"> 
      <input type="submit"  value="like"/>
	</form> 
    
    
</body>
</html>