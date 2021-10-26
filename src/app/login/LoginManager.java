package app.login;

import java.sql.SQLException;


import app.exceptions.DBExceptions;
import app.exceptions.FacadeException;
import app.facad.AdminFacade;
import app.facad.ClientFacade;
import app.facad.CompanyFacade;
import app.facad.CustomerFacade;

public class LoginManager {
	
	private static LoginManager loginInstance ;
	
	private LoginManager() {
		super();
	}

	public static LoginManager  loginInstance() {
		if(loginInstance==null) {
			loginInstance= new LoginManager();
		}
		
		return loginInstance;
	}
	
	 public enum ClientType {ADMINISTRATOR, COMPANY, CUSTOMER}
	 
	 public ClientFacade login(String email, String password,ClientType ct) throws SQLException, FacadeException, DBExceptions {
		 
		 switch (ct) {
		case ADMINISTRATOR:
			AdminFacade a = new AdminFacade();
			if (a.login(email, password))
			return a;
			else
				return null;
		case COMPANY:
			
			CompanyFacade c = new CompanyFacade();
			if(c.login(email, password)) {
				
				return c;
			}
			else
				return null;
			
			
		case CUSTOMER:
			
			CustomerFacade cf = new CustomerFacade();
			if(cf.login(email, password)) {
				return cf;
			}
			else
				return null;
		default:
			return null;
			
		}
	 }
	 
}
