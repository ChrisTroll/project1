package project1.models;

public class User {

	private String username;
	private String password;
	private int userrole;
	private int userid;
	
	public User(String username, String password, int userrole, int userid) {
		super();
		this.username = username;
		this.password = password;
		this.userrole = userrole;
		this.userid = userid;
	}

	public User() {
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getUserrole() {
		return userrole;
	}
	public void setUserrole(int userrole) {
		this.userrole = userrole;
	}
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
}
