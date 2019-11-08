package project1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import project1.models.Ticket;
import project1.services.TicketService;

public class TicketCollectorServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TicketService tserv = new TicketService();
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Add CORS headers
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "content-type");
		super.service(request, response);
		
	}
	
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		System.out.println("Attempting Ticket Write...");
		ObjectMapper om = new ObjectMapper();
		
		Ticket ticket = om.readValue(request.getReader(), Ticket.class);

		ticket = tserv.submit(ticket);
		response.setStatus(201); 
		om.writeValue(response.getWriter(), ticket);
		
		System.out.println("End Ticket Write Attempt...");
	}
}
