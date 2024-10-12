<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
     <%@ page import="model.User" %>
    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="thank.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://kit.fontawesome.com/c5f1eac028.js" crossorigin="anonymous"></script>
<meta charset="ISO-8859-1">
<title>User</title>
</head>
<body>
<%
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	
			User logged = (User) session.getAttribute("logged");
			if(logged==null){
			  	response.sendRedirect("index.jsp");
			}
			else if(!logged.getType().equals("user")){
				response.sendRedirect("index.jsp");
			}
	%>
	<div class="banner">
        <div class=" navbar">
            <img src="media/logopro.png" class="logo">
            <ul>
                <li><a href="#" >Home</a></li>
                <li><a href="#" >About Us</a></li>
                <li><a href="#" >Users</a></li>
                <li><a href="#" >Packages</a></li>
                    <li><a href="contact.jsp" >Contact</a></li>
                <li>
                	<form action="LoginServlet" method="post">
                	<input type='hidden' name='action' value='Logout'>
                	<button type="submit" class="det">Logout</button></form> </li>

            </ul>
        </div>

        
        <div class="Wel">
            <h1>YOU ARE OFFICIALLY SIGNED UP</h1>
            <br>
            <hr>
            <h2>PLEASE CONTACT A SALESMAN TO ACTIVATE YOUR ACCOUNT</h2>
        </div>
    
    </div>
</body>
</html>