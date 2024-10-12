<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.User" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
<link rel="stylesheet" href="admin-changepro.css" type="text/css">
</head>
<body>
	<%
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	
			User logged = (User) session.getAttribute("logged");
			if(logged==null){
			  	response.sendRedirect("index.jsp");
			}
			else if(!logged.getType().equals("admin")){
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
            <h1>WELCOME ADMIN , <%= logged != null ? logged.getName().toUpperCase() : "" %></h1>
            <br>
            <hr>
            <h2>KEEP UP THE GOOD WORK</h2>
        </div>
    
    </div>
    
    <section class="add">
    <h1>Create Program</h1>
	<div class="form-box">
  	<form action='AdminServlet' method='post'>
  	
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"  required><br>
    
    <label for="password">Password:</label>
    <input type="text" id="password" name="password"  required><br>
    
    <label for="name">Name:</label>
    <input type="text" id="name" name="name"  required><br>
    
    <label for="surname">Surname:</label>
    <input type="text" id="surname" name="surname"  required><br>
    
    <input type='hidden' name='action' value='CreateSeller'>
    <button class="det" type="submit">Create Seller</button>
    
  </form>
 </div> 
</section>

</body>
</html>