package project1.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import project1.models.Ticket;
import project1.models.User;
import project1.services.TicketService;

public class ViewTicketServlet extends HttpServlet{
	private static final long serialVersionUID = 2L;
	TicketService tserv = new TicketService();
	
	@Override
	public void init() throws ServletException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Attempting Ticket Array Read...");
		
		ObjectMapper om = new ObjectMapper();
		User user = om.readValue(request.getReader(), User.class);
		
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets = tserv.viewPastTickets(user);
		
		response.setStatus(201); 
		om.writeValue(response.getWriter(), tickets);
		super.doGet(request, response);
		
		System.out.println("Finished Ticket Array Read...");
	}
}
