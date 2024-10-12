<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.Client" %>
    <%@ page import="model.Call" %>
    <%@ page import="model.Bill" %>
    <%@ page import="java.util.ArrayList" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Client</title>
<link rel="stylesheet" href="dashbadminpro.css">
</head>
<body>
	<%
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	
			Client logged = (Client) session.getAttribute("clientLogged");
			if(logged==null){
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
            <h1>WELCOME CLIENT , <%= logged != null ? logged.getName().toUpperCase() : "" %></h1>
            <br>
            <hr>
            <h2>AFM: <%= logged != null ? logged.getAfm() : "" %>, PHONENUMBER: <%= logged != null ? logged.getPhoneNumber().getPhoneNumber() : "" %> </h2>
        </div>
    
    </div>
    
    <section class="lists">
    	<h3>BILLS FOR PHONENUMBER: <%= logged != null ? logged.getPhoneNumber().getPhoneNumber() : "" %></h3>

        <table id="check" class="tables">
            <tr class="first">

                <th>Month</th>
                <th>Total Minutes</th>
                <th>Total Data</th>
                <th>Total Amount</th>
                <th>Is Payed</th>
                
                
              
            </tr>

            <%! 
            
            private String createBillTable(Bill bill) {
            	
            	String row = "<tr>";
                row += "<td>" + bill.getMonth() + "</td>";
                row += "<td>" + bill.getCalls() + "</td>";
                row += "<td>" + bill.getDataUsed() + "</td>";
                row += "<td>" + bill.getTotal() + "</td>";
                if (bill.isPayed()) {
                    row += "<td>" + bill.isPayed() + "</td>";
                } else {
                	row += "<td>" +
                            "<form action='ClientServlet' method='post'>" +
                            "<input type='hidden' name='action' value='PayBill'>" +
                            "<input type='hidden' name='billId' value='" + bill.getId() + "'>" +
                            "<button type='submit' class='det'>PAY BILL</button>" +
                            "</form>" +
                            "</td>";
                }
                
                row += "</tr>";
                return row;
		}
	%>
	<%
	if (session.getAttribute("bills") != null) {
		ArrayList<Bill> bills = (ArrayList<Bill>) session.getAttribute("bills");

		for(Bill bill : bills){
			out.println(createBillTable(bill));
		}
	}

	%>


        </table>
    </section>
    
    <section class="packs">
        <h3>CALL HISTORY</h3>

        <table id="check" class="tables">
            <tr class="first">

                <th>Call</th>
                <th>Minutes</th>
                <th>Month</th>
                
                
              
            </tr>

            <%! 
            
            
            private String createCallTable(Call call, int counter) {
            	
            	String row = "<tr>";
                row += "<td>" + (counter) + "</td>";
                row += "<td>" + call.getMinutes() + "</td>";
                row += "<td>" + call.getMonth() + "</td>";
                
                row += "</tr>";
                return row;
		}
	%>
	<%
	if (session.getAttribute("calls") != null) {
		ArrayList<Call> calls = (ArrayList<Call>) session.getAttribute("calls");
		int counter = 0;

		for(Call call : calls){
			counter++;
			out.println(createCallTable(call,counter));
		}
	}

	%>


        </table>
        
       
    </section>

</body>
</html>