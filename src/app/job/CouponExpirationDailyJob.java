package app.job;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

import app.DAOs.CouponsDBDAO;
import app.exceptions.DBExceptions;

public class CouponExpirationDailyJob implements Runnable {

	
	private CouponsDBDAO couponDBDAO;
	static private boolean quit = false;
	
	private LocalDateTime now = LocalDateTime.now();   
	static private Thread t = new Thread(); 
	
	public CouponExpirationDailyJob() {
		super();
	}
	
	//t.start();		
	
	@Override
	public void run() {
		
		
		  while(!quit) {
			  
		         System.out.println("The job thread is running");
		         timeloop();
		      }
		      System.out.println("The job thread is now stopped");
		   }
	
	
	
	static public void start() {
		t.setDaemon(true);
		t.start();
		}
	
	
	static public void stop() {
		quit = true;
	}
	
	public void timeloop() {
		while(now!=(LocalDateTime.MIN)) {
			
			try {
				couponDBDAO.deleteExpiredCoupons();
			} catch (SQLException e) {
				
				e.printStackTrace();
			} catch (DBExceptions e) {
				
				e.printStackTrace();
			}
		}
	}
	
	
}
