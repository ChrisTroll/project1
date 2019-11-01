package project1.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import project1.models.Credentials;
import project1.models.User;
import project1.services.LoginService;

public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 12;
	LoginService lserv = new LoginService();
	
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
		System.out.println("Attempting Login...");
		ObjectMapper om = new ObjectMapper();
		
		Credentials cred = om.readValue(request.getReader(), Credentials.class);

		User user = lserv.tryLogin(cred);
		response.setStatus(201); 
		om.writeValue(response.getWriter(), user);
		
		System.out.println("Login Attempt Finished...");
	}
	
}
