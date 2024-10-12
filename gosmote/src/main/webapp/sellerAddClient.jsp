<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.User" %>
    <%@ page import="model.Program" %>
	<%@ page import="java.util.ArrayList" %>

    
   
<!DOCTYPE html>
<html>
<head>
    <title>Add Client</title>
    <link rel="stylesheet" href="addclient.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://kit.fontawesome.com/c5f1eac028.js" crossorigin="anonymous"></script>
</head>
<body>
	<%
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	
			User logged = (User) session.getAttribute("logged");
			if(logged==null){
			  	response.sendRedirect("index.jsp");
			}
			else if(!logged.getType().equals("seller")){
				response.sendRedirect("index.jsp");
			}
			User newClient = (User) session.getAttribute("newclient");
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
            <h1>WELCOME SALESMAN , <%= logged != null ? logged.getName().toUpperCase() : "" %></h1>
            <br>
            <hr>
            <h2>KEEP UP THE GOOD WORK</h2>
        </div>
    
    </div>
	<section class="add">
	<h1>Add new Client</h1>
	<div class="form-box">
  	<form action='SellerServlet' method='post'>
  	
  	<p name="username" id="username">Username: <%= logged != null ? newClient.getUsername().toUpperCase() : "" %></p>
    <p>Name: <%= logged != null ? newClient.getName().toUpperCase() : "" %></p>
    <p>Surname: <%= logged != null ? newClient.getSurname().toUpperCase() : "" %></p>
    <label for="afm">Afm:</label>
    <input type="number" id="afm" name="afm" placeholder="123456789" required><br>

    <label for="phonenumber">Phone:</label>
    <input type="number" id="phonenumber" name="phonenumber" placeholder="123456789" required><br>
    
    <input type='hidden' name='action' value='AddClientComplete'>
    <button class="det" type="submit">Create New Client</button>
    
  </form>
 </div> 
</section>
    <section class="footer">
            
        <h1 class="footerH1">UNIPI TELECOM</h1>
        <div class="footerAM">
            <ul>
                <li><a href="#" >P20105</a></li>
                <li><a href="#" >P20040</a></li>
                <li><a href="#" >P20207</a></li>
            </ul>

        </div>
       

    </section>
    
    


    
</body>
</html>