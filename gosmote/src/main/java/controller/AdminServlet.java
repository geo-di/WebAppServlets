package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Program;
import model.User;

import java.io.IOException;

import database.DatabaseController;

/**
 * Servlet implementation class AdminServlet
 */
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DatabaseController db = new DatabaseController();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		
		if(action.equals("CreateProgram")) {
			String name = request.getParameter("name");
			int minutes = Integer.parseInt(request.getParameter("minutes"));
			int data = Integer.parseInt(request.getParameter("data"));
			float price = Float.parseFloat(request.getParameter("price"));
			float extraMinutesCharge = Float.parseFloat(request.getParameter("extraMinutesCharge"));
			float extraDataCharge = Float.parseFloat(request.getParameter("extraDataCharge"));
			
			db.createProgram(name, minutes, data, price, extraMinutesCharge, extraDataCharge);
			response.sendRedirect("adminDashboard.jsp");
			
		} else if (action.equals("CreateSeller")) {
			
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			db.createSeller(username, password, name, surname);
			response.sendRedirect("adminDashboard.jsp");

			
		} else if (action.equals("ChangeProgram")) {
			int selectedProgramId = Integer.parseInt(request.getParameter("selectedProgram"));
			Program program = db.searchProgram(selectedProgramId);
			session.setAttribute("newprogram", program);
			response.sendRedirect("adminChangeProgram.jsp");
			
		} else if (action.equals("ChangeProgramComplete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int minutes = Integer.parseInt(request.getParameter("minutes"));
			int data = Integer.parseInt(request.getParameter("data"));
			float price = Float.parseFloat(request.getParameter("price"));
			float extraMinutesCharge = Float.parseFloat(request.getParameter("extraMinutesCharge"));
			float extraDataCharge = Float.parseFloat(request.getParameter("extraDataCharge"));
			
			db.changeProgram(id ,name, minutes, data, price, extraMinutesCharge, extraDataCharge);
			response.sendRedirect("adminDashboard.jsp");
		}
	}

}
