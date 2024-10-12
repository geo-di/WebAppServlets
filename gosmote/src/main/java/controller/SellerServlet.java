package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Client;
import model.PhoneNumber;
import model.Program;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

import database.DatabaseController;

/**
 * Servlet implementation class SellerServlet
 */
public class SellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController db = new DatabaseController();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SellerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		if(action.equals("AddClient")) {
			String username = request.getParameter("username");
			User user = db.searchUser(username);
			session.setAttribute("newclient", user);
			response.sendRedirect("sellerAddClient.jsp");
			
		} else if (action.equals("AddClientComplete")) {			
			User newClient = (User) session.getAttribute("newclient");		
			String phonenumber = request.getParameter("phonenumber");			
			String afm = request.getParameter("afm");
			
			db.registerClient(newClient.getUsername(),phonenumber,afm);
			response.sendRedirect("sellerDashboard.jsp");
			
		} else if (action.equals("AssignProgram")) {
			String username = request.getParameter("username");
			Client client = db.searchClient(username);
			session.setAttribute("client", client);
			ArrayList<Program> programs = db.getAllPrograms();
			session.setAttribute("programs", programs);
			response.sendRedirect("sellerAssignProgram.jsp");
			
		} else if (action.equals("AssignProgramComplete")) {
			String programId = request.getParameter("selectedProgram");
			if(!programId.equals("")) {
				int selectedProgramId = Integer.parseInt(programId);
				Client client = (Client) session.getAttribute("client");		
				db.setProgram(client.getPhoneNumber().getPhoneNumber(),selectedProgramId);
				response.sendRedirect("sellerDashboard.jsp");
			} else {response.sendRedirect("sellerAssignProgram.jsp");}
			 
		
		} else if (action.equals("PrintBill")) {
			String username = request.getParameter("username");
			Client client = db.searchClient(username);
			session.setAttribute("client", client);
			ArrayList<String> billMonths = db.getClientBillMonths(client);
			session.setAttribute("billMonths", billMonths);
			ArrayList<String> callMonths = db.getClientCallMonths(client);
			session.setAttribute("callMonths", callMonths);
			response.sendRedirect("sellerPrintBill.jsp");
		} else if (action.equals("PrintBillComplete")) {
			String billMonth = request.getParameter("SelectedMonth");
			if(!billMonth.equals("")) {
				Client client = (Client) session.getAttribute("client");
				db.calculateBill(client.getPhoneNumber(), billMonth);
				response.sendRedirect("sellerDashboard.jsp");
			} else {response.sendRedirect("sellerPrintBill.jsp");}
		} else {System.out.println("No action");}
		
	}

}
