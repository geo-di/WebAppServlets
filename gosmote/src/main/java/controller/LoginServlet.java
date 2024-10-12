package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Bill;
import model.Call;
import model.Client;
import model.Program;
import model.Seller;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

import database.DatabaseController;


public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController db = new DatabaseController();
	public void init() throws ServletException{
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String action = request.getParameter("action");
		
		if (action.equals("signin")) {
			User user = db.findUser(username, password);
			if(user == null) {
				response.sendRedirect("login.jsp");
				session.setAttribute("error","Wrong login!");
			} else {
				session.removeAttribute("error");
				String userType = user.getType();
				if ("client".equals(userType)) {

				    Client client = db.findClient(user);
				    System.out.println(client.getName());
			        session.setAttribute("clientLogged", client);
			        ArrayList<Call> calls = db.getClientCalls(client.getPhoneNumber());
	   			 	session.setAttribute("calls", calls);
	   			 	ArrayList<Bill> bills = db.getClientBills(client.getPhoneNumber());
	   			 	session.setAttribute("bills", bills);
	            	response.sendRedirect("clientDashboard.jsp");		        

				} else {
		        session.setAttribute("logged", user);
	
		        
		        switch (userType) {
		            case "admin":
		            	ArrayList<Program> programs = db.getAllPrograms();
		   			 	session.setAttribute("programs", programs);
		   			 	ArrayList<Seller> sellers = db.getAllSellers();
		   			 	session.setAttribute("sellers", sellers);
		                response.sendRedirect("adminDashboard.jsp");
		                break;
		            case "seller":
		            	ArrayList<Program> programs1 = db.getAllPrograms();
		   			 	session.setAttribute("programs", programs1);
		   			 	ArrayList<User> users = db.getAllUsers();
		   			 	session.setAttribute("users", users);
		   			 	ArrayList<Client> clients = db.getAllClients();
		   			 	session.setAttribute("clients", clients);
		                response.sendRedirect("sellerDashboard.jsp");
		                break;
		            case "user":
		            	response.sendRedirect("userDashboard.jsp");
		                break;
		            default:
		                session.setAttribute("error", "Unknown user type!");
		                response.sendRedirect("login.jsp");
		                break;
		        }
				}
			}
			
		} else if (action.equals("signup")) {
			String name = request.getParameter("firstname");
			String surname = request.getParameter("lastname");
			User user = db.registerUser(username,password,name,surname);
			session.removeAttribute("error");
	        session.setAttribute("logged", user);
        	response.sendRedirect("userDashboard.jsp");


		} else if(action.equals("Logout")) {
			session.invalidate();
			response.sendRedirect("index.jsp");
		}
	}



}
