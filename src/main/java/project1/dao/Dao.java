package project1.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import project1.models.Credentials;
import project1.models.Ticket;
import project1.models.TicketUpdateRequest;
import project1.models.User;
import project1.util.ConnectionUtil;

public class Dao {
	Date date = new Date();
	
	public User login(Credentials cred){
//		query database for SALT
//		generate hash
//		query database for username + hash combination
		//TODO Extract SALT Value, generate HASH
		try (Connection connection = ConnectionUtil.getConnection()) {
			//fetch salt 
			byte [] hash = getHash(cred);
			String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setString(1, cred.getUsername());
			statement.setBytes(2, hash);

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			User user = extractUser(resultSet);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] getHash(Credentials cred) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT ers_salt FROM ers_users WHERE ers_username = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1,  cred.getUsername());
			ResultSet resultSet = statement.executeQuery();
		
			resultSet.next();
			byte[] salt = resultSet.getBytes(1);
		
			KeySpec spec = new PBEKeySpec(cred.getPassword().toCharArray(), salt, 65536, 128);
			SecretKeyFactory factory;
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			return hash;
			
			} catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
				e.printStackTrace();
				return null;
			}
		}
	
	public void pushHash(int id, Credentials cred) {
		byte[] hash = getHash(cred);
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_users SET ers_password = ? WHERE ers_username = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBytes(1, hash);
			statement.setString(2,  cred.getUsername());
			
			statement.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	
	private User extractUser(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("user_role_id");
		int userid = resultSet.getInt("ers_users_id");
		String username = resultSet.getString("ers_username");
		
		User user = new User(username, id, userid);
		
		return user;
	}
	
	public ArrayList<Ticket> getPastTickets(User userin){
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ? AND reimb_status_id IN (2,3)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, userin.getUserid());

			ResultSet resultSet = statement.executeQuery();
			
			ArrayList<Ticket> tickets = getTicketList(resultSet);
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public ArrayList<Ticket> getReviewTickets(User userin){
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status_id = 1 AND reimb_author != ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, userin.getUserid());

			ResultSet resultSet = statement.executeQuery();
			
			ArrayList<Ticket> tickets = getTicketList(resultSet);
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static Ticket extractTicket(ResultSet resultSet) throws SQLException {	
		int id = resultSet.getInt("reimb_id");
		double amount = resultSet.getDouble("reimb_amount");
		Timestamp datesubmitted = resultSet.getTimestamp("reimb_submitted");
		Timestamp dateresolved = resultSet.getTimestamp("reimb_resolved");
		String description = resultSet.getString("reimb_description");
		String imgaddr = resultSet.getString("reimb_receipt");
		int author = resultSet.getInt("reimb_author");
		int resolver = resultSet.getInt("reimb_resolver");
		int status = resultSet.getInt("reimb_status_id");
		int type = resultSet.getInt("reimb_type_id");
		
		Ticket ticket = new Ticket(id, amount, datesubmitted, dateresolved, description,
				imgaddr, author, resolver, status, type);
		
		return ticket;
	}

	private ArrayList<Ticket> getTicketList(ResultSet resultset){
		try {
			ArrayList<Ticket> tickets = new ArrayList<Ticket>();
			while(resultset.next()) {
				tickets.add(extractTicket(resultset));
			}
			return tickets;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Ticket pushTicket(Ticket ticket) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = " INSERT INTO ers_reimbursement (reimb_id, reimb_amount, reimb_submitted, " + 
				"reimb_description, reimb_receipt, " + 
				"reimb_author, reimb_status_id, reimb_type_id) " +
				"values (?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, genTicketID());
			statement.setDouble(2, ticket.getAmount());
			statement.setTimestamp(3, ticket.getDatesubmitted());
			statement.setString(4, ticket.getDescription());
			statement.setString(5, ticket.getImgaddr());
			statement.setInt(6, ticket.getAuthor());
			statement.setInt(7, ticket.getStatus());
			statement.setInt(8, ticket.getType());

			ResultSet results = statement.executeQuery();
			
			if (results.next()) {
				ticket.setId(results.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ticket;
	}
	
	public void closeTicket(TicketUpdateRequest tur) {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE ers_reimbursement SET reimb_status_id = ?, "
					+ "reimb_resolver = ?, reimb_resolved = ? WHERE reimb_id = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.setInt(1, tur.getTicketstatus());
			statement.setInt(2, tur.getUserid());
			statement.setTimestamp(3, new Timestamp(date.getTime()));
			statement.setInt(4, tur.getTicketid());
			
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int genTicketID(){
		String sql = "SELECT MAX(reimb_id) FROM ers_reimbursement";
		try (Connection connection = ConnectionUtil.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet resultset = statement.executeQuery();

			resultset.next();

			int maxint = resultset.getInt(1);
			return maxint + 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public Ticket pullTicket(int ticketid) {
		try (Connection connection = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, ticketid);
			ResultSet resultset = statement.executeQuery();
			
			resultset.next();
			
			Ticket ticket = extractTicket(resultset);
			return ticket;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void pushSalt(int id) {
		try (Connection connection = ConnectionUtil.getConnection()){
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[20];
			random.nextBytes(salt);
			
			String sql = "UPDATE ers_users SET ers_salt = ? WHERE ers_users_id = ? ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setBytes(1, salt);
			statement.setInt(2, id);
			statement.executeUpdate();
		} catch (SQLException e){
			e.printStackTrace();
		}
	}

}
