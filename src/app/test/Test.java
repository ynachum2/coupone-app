package app.test;

import java.sql.SQLException;

import app.exceptions.DBExceptions;
import app.exceptions.FacadeException;
import app.login.LoginManager;
import app.login.LoginManager.ClientType;

public class Test {
 static public void testAll() throws SQLException, FacadeException, DBExceptions {
	 LoginManager.loginInstance().login("admin","admin@admin.com" , ClientType.ADMINISTRATOR);
	 
 }
}
