<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List" %>
	<%@ page import="java.util.ArrayList" %>
	<%@ page import="model.User" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Salesman</title>
<link rel="stylesheet" href="print.css" type="text/css">

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
    
    
    <%
	    ArrayList<String> billMonth = (ArrayList<String>) session.getAttribute("billMonths");
	    ArrayList<String> callMonth = (ArrayList<String>) session.getAttribute("callMonths");
	    
	    if (billMonth == null) {
	        billMonth = new ArrayList<>();
	    }
	
	    if (callMonth == null) {
	        callMonth = new ArrayList<>();
	    }
	
	   
	
	    ArrayList<String> availableToPrint = new ArrayList<>();
	
	    for (String item : callMonth) {
	        if (!billMonth.contains(item) && !availableToPrint.contains(item)) {
	            availableToPrint.add(item);
	        }
	    }
	%>
	
	<section class="add">
		<h1>Select Month to Print Bill</h1>

		<form action="SellerServlet" method="post" class="form-box"> 
		    <select name="SelectedMonth" >
		    	<option value="">Select a Month</option>
		        <%
		            for (String item : availableToPrint) {
		        %>
		            <option value="<%= item %>"><%= item %></option>
		        <%
		            }
		        %>
		    </select>
		
			<input type='hidden' name='action' value='PrintBillComplete'>
	        <button type="submit">Print Bill</button>
	        
	    </form>

		</section>
</body>
</html>