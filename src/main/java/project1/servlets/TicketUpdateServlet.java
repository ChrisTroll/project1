package project1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import project1.models.Ticket;
import project1.models.TicketUpdateRequest;
import project1.services.TicketService;

public class TicketUpdateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Attempting Ticket Update...");
		
		ObjectMapper om = new ObjectMapper();
		TicketUpdateRequest tur = om.readValue(request.getReader(), TicketUpdateRequest.class);
		
		tserv.closeTicket(tur);
		Ticket ticket = tserv.getTicket(tur.getTicketid());
		

		om.writeValue(response.getWriter(), ticket);
		response.setStatus(201); 
		
		System.out.println("Ticket Update Successful...");
		super.doPost(request, response);
	}
}
