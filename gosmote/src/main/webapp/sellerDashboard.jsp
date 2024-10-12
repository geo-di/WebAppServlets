<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="model.User" %>
    <%@ page import="model.Client" %>    
    <%@ page import="model.Program" %>
	<%@ page import="java.util.ArrayList" %>

    
   
<!DOCTYPE html>
<html>
<head>
    <title>Salesmen</title>
    <link rel="stylesheet" href="sale.css">
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
              
            </tr>

            <%! private String createProgramTable(Program program) {
			String row = "<tr>";
			row  += "<td>" + program.getName().toUpperCase()+ "</td>";
			row  += "<td>" + program.getMinutes()+ "</td>";
			row  += "<td>" + program.getData() + "</td>";
			row  += "<td>" + program.getPrice() + "</td>";
			row  += "<td>" + program.getExtraMinutesCharge() + "$ per minute</td>";
			row  += "<td>" + program.getExtraDataCharge() +  "$ per mb</td>";
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
    </section>
    

    <section class="packs">
        <h3>AVAILABLE USERS</h3>

        <table id="check" class="tables">
            <tr class="first">

                <th>Username</th>
                <th>Name</th>
                <th>Surname</th>
                <th> </th>
                
              
            </tr>

            <%! private String createUserTable(User user) {
            	
            	String row = "<tr>";
                row += "<td>" + user.getUsername() + "</td>";
                row += "<td>" + user.getName() + "</td>";
                row += "<td>" + user.getSurname() + "</td>";
                row += "<td>" +
                       "<form action='SellerServlet' method='post'>" +
                       "<input type='hidden' name='action' value='AddClient'>" +
                       "<input type='hidden' name='username' value='" + user.getUsername() + "'>" +
                       "<button type='submit' class='det'>ADD CLIENT</button>" +
                       "</form>" +
                       "</td>";
                row += "</tr>";
                return row;
		}
	%>
	<%
	if (session.getAttribute("users") != null) {
		ArrayList<User> users = (ArrayList<User>) session.getAttribute("users");

		for(User user : users){
			out.println(createUserTable(user));
		}
	}

	%>


        </table>
    </section>

  
        
    <section class="packs">
        <div class="change" style="margin:0;">

            <h3> CHECK YOUR CLIENTS</h3>
            
            <table id="check" class="tables">
            <tr class="first">

                <th>Username</th>
                <th>Afm</th>
                <th>Phone Number</th>
                <th>Current Program</th>
                <th></th>
                <th></th>
                
              
            </tr>

            <%! private String createClientTable(Client client) {
            	
            	String row = "<tr>";
                row += "<td>" + client.getUsername() + "</td>";
                row += "<td>" + client.getAfm() + "</td>";
                row += "<td>" + client.getPhoneNumber().getPhoneNumber() + "</td>";
                String program = client.getPhoneNumber().getProgram() != null ? client.getPhoneNumber().getProgram().getName() : "<span>No program assigned yet</span>";
                row += "<td>" + program + "</td>";
                row += "<td>" +
                       "<form action='SellerServlet' method='post'>" +
                       "<input type='hidden' name='action' value='AssignProgram'>" +
                       "<input type='hidden' name='username' value='" + client.getUsername() + "'>" +
                       "<button type='submit' class='det'>ASSIGN PROGRAM</button>" +
                       "</form>" +
                       "</td>";
                row += "<td>" +
                        "<form action='SellerServlet' method='post'>" +
                        "<input type='hidden' name='action' value='PrintBill'>" +
                        "<input type='hidden' name='username' value='" + client.getUsername() + "'>" +
                        "<button type='submit' class='det'>PRINT BILL</button>" +
                        "</form>" +
                        "</td>";
                row += "</tr>";
                return row;
		}
	%>
	<%
	if (session.getAttribute("clients") != null) {
		ArrayList<Client> clients = (ArrayList<Client>) session.getAttribute("clients");

		for(Client client : clients){
			out.println(createClientTable(client));
		}
	} else { }

	%>


        </table>
           
        </div>

    </section>

    <section class="stats">
        <h1>CHECK YOUR PROGRESS</h1>
        
        <div class="text-container">

            <div class="stats-box">
                <h2>CUSTOMERS</h2>
                <p>
                    <i class="fa-solid fa-medal"></i>   <br>
                YOU ALREADY HAVE 47 CUSTOMERS<BR>    
                <h3>VERY GOOD JOB</h3>   

                </p>
            </div>

            <div class="stats-box">
                <h2>TOTAL INCOME</h2>
                <p>
                    <i class="fa-solid fa-sack-dollar"></i><br>
                YOUR CUSTOMERS WORTH 1232$/Mo<BR>    
                <h3>AWSOME!</h3   >    
                </p>
            </div>

            <div class="stats-box">
                <h2>POINTS AWARDER</h2>
                <p>
                    
                    <i class="fa-solid fa-check-to-slot"></i><br>
                YOU HAVE COLLECTED 345 POINTS<BR>    
                <h3>YOU ARE NAILING IT</h3>    
               
                </p>
            </div>
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