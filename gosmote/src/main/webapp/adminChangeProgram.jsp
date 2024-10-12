<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ page import="model.User" %>
       <%@ page import="model.Program" %>
    
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
			Program newProgram = (Program) session.getAttribute("newprogram");

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
  	
    <label for="name">Name:</label>
	<input type="text" id="name" name="name" required value="<%= logged != null ? newProgram.getName() : "" %>"><br>

    <label for="minutes">Minutes:</label>
    <input type="number" id="minutes" name="minutes"  required value="<%= logged != null ? newProgram.getMinutes() : "" %>"><br>
    
    <label for="data">Data:</label>
    <input type="number" id="data" name="data"  required value="<%= logged != null ? newProgram.getData() : "" %>"><br>
    
    <label for="price">Price:</label>
    <input type="number" id="price" name="price"  required value="<%= logged != null ? newProgram.getPrice() : "" %>"><br>
    
    <label for="extraMinutesCharge">Extra Minutes Charge:</label>
    <input type="number" id="extraMinutesCharge" name="extraMinutesCharge"  required value="<%= logged != null ? newProgram.getExtraMinutesCharge() : "" %>"><br>
    
    <label for="extraDataCharge">Extra Data Charge:</label>
    <input type="number" id="extraDataCharge" name="extraDataCharge"  required value="<%= logged != null ? newProgram.getExtraDataCharge() : "" %>"><br>
    
    <input type='hidden' name='action' value='ChangeProgramComplete'>
    <input type='hidden' name='id' value='<%= logged != null ? newProgram.getId() : "" %>'>
    <button class="det" type="submit">Change Program</button>
    
  </form>
 </div> 
</section>

</body>
</html>