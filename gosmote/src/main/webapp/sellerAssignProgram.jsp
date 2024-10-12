<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="model.User" %>
<%@ page import="model.Client" %>
<%@ page import="model.Program" %>
<%@ page import="java.util.ArrayList" %>
	
<!DOCTYPE html>
<html>
<head>
 	<link rel="stylesheet" href="assignprogram.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://kit.fontawesome.com/c5f1eac028.js" crossorigin="anonymous"></script>
<meta charset="ISO-8859-1">
<title>Assign Program</title>
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
    <section  class="content">
    
    <% 
    if (session.getAttribute("client") != null) {
				Client client = (Client) session.getAttribute("client");		
				if (session.getAttribute("programs") != null) {
					ArrayList<Program> programs = (ArrayList<Program>) session.getAttribute("programs");
	%>
	<h3>Assign Program to Client: <%= client != null ? client.getUsername().toUpperCase() : "" %></h3>
	<form action="SellerServlet" method="post" class="form-box">  
		<select name="selectedProgram">
            <option value="">Select a Program</option>  <%
                for (Program program : programs) {
                    out.println("<option value='" + program.getId() + "'>" + program.getName() + "</option>");
                }
            %>
        </select>
         <%
        }
    }
	%>
        <input type='hidden' name='action' value='AssignProgramComplete'>
        <button type="submit">Assign Program</button>
    </form>
    
   
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