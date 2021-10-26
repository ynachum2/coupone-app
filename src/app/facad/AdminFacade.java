package app.facad;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.Models.Company;
import app.Models.Coupon;
import app.Models.Customer;
import app.exceptions.DBExceptions;
import app.exceptions.FacadeException;

public class AdminFacade extends ClientFacade {

	private static final String EMAIL = "admin@admin.com";
	private static final String PASSWORD = "admin";

	public AdminFacade() {
		super();

	}
	@Override
	public boolean login(String email, String password) {

		if ((email.equals(EMAIL)) && (password.equals(PASSWORD))) {
			return true;
		}

		return false;
	}

	public boolean addNewCompany(Company company) throws SQLException, DBExceptions {
		if (comdd.isCompanyExists(company.getEmail(), company.getPassword())) {

			return false;
		} else {
			comdd.addCompany(company);
			return true;
		}
	}

	public boolean updateCompany(Company company, String email, String password) throws SQLException, DBExceptions {
		if (comdd.isCompanyExists(company.getEmail(), company.getPassword())) {
			company.setEmail(email);
			company.setPassword(password);
			comdd.updateCompany(company);
			return true;
		} else {

			return false;
		}
	}

	public void deleteCompany(Company company) throws SQLException, DBExceptions {

		ArrayList<Coupon> allcoupons = coupdd.getAllCoupons();
		for (Coupon c : allcoupons) {
			if (c.getCompanyId() == company.getId())
				coupdd.deleteCuopon(c);
			coupdd.deleteCouponPurchase(c.getId());
		}
		comdd.deleteCompany(company.getId());
	}

	public ArrayList<Company> getAllCompany() throws FacadeException, SQLException {
		ArrayList<Company> companies = null;
		try {
			companies = comdd.getAllCompanies();
		} catch (DBExceptions e) {
			throw new FacadeException("admin: get All Companies failed", e);
		}

		return companies;
	}

	public Company getOneCompany(int companyID) throws SQLException, DBExceptions, FacadeException {

		Company company = null;
		try {
			company = comdd.getOneCompany(companyID);
		} catch (DBExceptions e) {
			throw new FacadeException("admin:get One Company failed", e);
		}
		return company;
	}

	public boolean addCustomer(Customer customer) throws FacadeException, SQLException {

		try {
			if (cusdd.isCustomerExists(customer.getEmail(), customer.getPassword())) {
				return false;
			} else {
				cusdd.addCustomer(customer);
			}
		} catch (DBExceptions e) {
			throw new FacadeException("admin: adding Customer failed", e);
		}
		return true;
	}

	public boolean updateCustomer(Customer customer) throws SQLException, FacadeException {
		try {
			if (!cusdd.isCustomerExists(customer.getEmail(), customer.getPassword())) {
				return false;

			} else {
				cusdd.updateCustomer(customer);
				return true;
			}
		} catch (DBExceptions e) {
			throw new FacadeException("admin: updatting Customer failed", e);
		}
		// return false;
	}

	public boolean deleteCustomer(Customer customer) throws SQLException, DBExceptions, FacadeException {

		try {
			if (!cusdd.isCustomerExists(customer.getEmail(), customer.getPassword())) {
				return false;

			} else {
				coupdd.deleteCouponPurchaseByCustomerId(customer.getId());
				cusdd.deleteCustomer(customer.getId());
				return true;
			}
		} catch (DBExceptions e) {
			throw new FacadeException("admin: delete Customer failed", e);
		}
	}

	public ArrayList<Customer> getAllCustomers() throws FacadeException, SQLException {

		ArrayList<Customer> customers = null;
		try {
			customers = cusdd.getAllCustomers();

		} catch (DBExceptions e) {
			throw new FacadeException("admin: get all Customers failed", e);
		}
		return customers;
	}
	
	public Customer getOneCustomer(int customerID) throws FacadeException, SQLException {
		
		Customer customer= null;
		try {
			customer=cusdd.getOneCustomers(customerID);
		}catch(DBExceptions e) {
			throw new FacadeException("admin: get one customer failed", e);
		}
		return customer;
		
	}
}
