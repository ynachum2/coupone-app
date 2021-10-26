package app.facad;

import java.sql.SQLException;

import app.DAOs.CompaniesDBDAO;
import app.DAOs.CouponsDBDAO;
import app.DAOs.CustomerDBDAO;
import app.exceptions.FacadeException;

abstract public class ClientFacade {
	protected CompaniesDBDAO comdd;
	protected CouponsDBDAO coupdd;
	protected CustomerDBDAO cusdd;
	
	public ClientFacade(CompaniesDBDAO comdd, CouponsDBDAO coupdd, CustomerDBDAO cosdd) {
		super();
		this.comdd = new CompaniesDBDAO();
		this.coupdd = new CouponsDBDAO();
		this.cusdd = new CustomerDBDAO();
	}

	public ClientFacade() {
		
	}
	public abstract boolean login(String email, String password) throws SQLException, FacadeException;
	
	
}
