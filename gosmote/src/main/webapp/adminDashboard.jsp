<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="model.User" %>
    <%@ page import="model.Seller" %>    
    <%@ page import="model.Program" %>
	<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin</title>
<link rel="stylesheet" href="dashbadminpro.css" type="text/css">
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
    
    <section class="lists">

        <h3>AVAILABLE PROGRAMS</h3>

        <table id="check" class="tables">
            <tr class="first">

                <th>Name</th>
                <th>Minutes</th>
                <th>Data</th>
                <th>Price</th>
                <th>Extra Minutes Charge</th>
                <th>Extra Data Charge</th>
                <th></th>
              
            </tr>

            <%! private String createProgramTable(Program program) {
			String row = "<tr>";
			row  += "<td>" + program.getName().toUpperCase()+ "</td>";
			row  += "<td>" + program.getMinutes()+ "</td>";
			row  += "<td>" + program.getData() + "</td>";
			row  += "<td>" + program.getPrice() + "</td>";
			row  += "<td>" + program.getExtraMinutesCharge() + "$ per minute</td>";
			row  += "<td>" + program.getExtraDataCharge() +  "$ per mb</td>";
			row += "<td>" +
                    "<form action='AdminServlet' method='post'>" +
                    "<input type='hidden' name='action' value='ChangeProgram'>" +
                    "<input type='hidden' name='selectedProgram' value='" + program.getId() + "'>" +
                    "<button type='submit' class='det'>CHANGE PROGRAM</button>" +
                    "</form>" +
                    "</td>";
			row +="</tr>";
			return row;
		}
	%>
	<%
	if (session.getAttribute("programs") != null) {
		ArrayList<Program> programs = (ArrayList<Program>) session.getAttribute("programs");

		for(Program program : programs){
			out.println(createProgramTable(program));
		}
	}

	%>
		

        </table>
        <a href="adminCreateProgram.jsp"><button>Add Program</button></a>
    </section>
    
    
    <section class="packs">
        <h3>AVAILABLE SELLERS</h3>

        <table id="check" class="tables">
            <tr class="first">

                <th>Username</th>
                <th>Name</th>
                <th>Surname</th>
                <th> </th>
                
              
            </tr>

            <%! private String createSellerTable(Seller seller) {
            	
            	String row = "<tr>";
                row += "<td>" + seller.getUsername() + "</td>";
                row += "<td>" + seller.getName() + "</td>";
                row += "<td>" + seller.getSurname() + "</td>";
                
                row += "</tr>";
                return row;
		}
	%>
	<%
	if (session.getAttribute("sellers") != null) {
		ArrayList<Seller> sellers = (ArrayList<Seller>) session.getAttribute("sellers");

		for(Seller seller : sellers){
			out.println(createSellerTable(seller));
		}
	}

	%>


        </table>
        
        <a href="adminCreateSeller.jsp"><button>Add Seller</button></a>
        
    </section>
    
    
    
</body>
</html>