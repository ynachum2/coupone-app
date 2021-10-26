package app.Infastucture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import app.exceptions.DBExceptions;

public class ConnectionPool {
	// for singleton
	private static ConnectionPool ourInstance;
	
	private ConnectionPool() throws DBExceptions  {
		super();
		
		try {
			for (int i = 0; i < CONNECTION_AMOUNT; i++) {
				Connection connection = DriverManager.getConnection(connectionString);
				this.connections.add(connection);
			}
		} catch (SQLException e) {
			
			throw new DBExceptions("connection poll not fail", e);
		}
		
	}
	
	public static ConnectionPool getInstance() throws DBExceptions {
		if (ourInstance==null) 
		{
			ourInstance =new ConnectionPool();
		}
		
		return ourInstance;
	}
	private static boolean conOpen = true;
	
    private static final int CONNECTION_AMOUNT =5;
    private static String dbName = "sql11437103";
    private static String userName = "sql11437103";
    private static String password = "NLxRdvgRp9";
    private static String connectionString = "jdbc:mysql://sql11.freemysqlhosting.net/"
            + dbName +
            "?user=" + userName +
            "&password=" + password;
    
    private  Set <Connection> connections = new HashSet<>(CONNECTION_AMOUNT) ;
   
    


    public synchronized Connection getConnection ()throws SQLException{
    	
        
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                System.out.println("DB " + dbName+"connection attempt established");
                
            }
        });
        
        thread.start();
        while(this.connections.isEmpty()) {
    		
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	
	}
	if (conOpen) {
		Iterator<Connection> it = this.connections.iterator();
		Connection con = it.next();
		it.remove();
		return con;
	} else {
		System.out.println("you can't connect to DB, becuse the program close");
		return null;	
	}
    }
    
    public synchronized void restoreConnection (Connection con) throws DBExceptions {
		try {
			this.connections.add(con);
			notify();
		} catch (Exception e) {
			throw new DBExceptions("add operation is not supported by this set", e);
		}

	}
    
    
    
    public void closeAllConnection() throws DBExceptions {
		// close the pool - no more con go out
		conOpen = false;
		// wait for all con back
		while(this.connections.size() != CONNECTION_AMOUNT) {
			try {
				// wait in order not to overload the system
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//close all connection
		Iterator<Connection> it = this.connections.iterator();
		while (it.hasNext()) {
			Connection con = it.next();
			try {
				con.close();
			} catch (SQLException e) {
				throw new DBExceptions("close all connection fail", e);
			}
			
		}
	
    
   

}
}