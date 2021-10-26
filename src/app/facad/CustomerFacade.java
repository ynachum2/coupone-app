package app.facad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import app.DAOs.CompaniesDBDAO;
import app.DAOs.CouponsDBDAO;
import app.DAOs.CustomerDBDAO;
import app.Models.Category;
import app.Models.Coupon;
import app.Models.Customer;
import app.exceptions.DBExceptions;
import app.exceptions.FacadeException;

public class CustomerFacade extends ClientFacade {

	private int customerID=0;
	
	
	public CustomerFacade() {
		super();
	}

	
	public int getCustomerID() {
		return customerID;
	}


	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}


	@Override
	public boolean login(String email, String password) throws SQLException, FacadeException {

		try {
			setCustomerID(comdd.checkForID(email, password));
			if(!(getCustomerID()==-1))
			{
				return true;
			}
			
			else {
				System.out.println("customer login failed-details d'ont match");
				return false;
			}
		} catch (DBExceptions e) {
			throw new FacadeException("customer: Customer login failed", e);
		}

	}

	public void couponPurchaseByCustomer(Coupon coupon) throws SQLException, FacadeException {
		Coupon c;
		try {
			
			if(!(coupdd.isCouponInCustomer(customerID,coupon.getId())))
			{
				System.out.println("coupon " +coupon.getTitle()+" allready exists in customer");
			}
			else {
				c=coupdd.getOneCoupon(coupon.getId());
				Date testDate = new Date(System.currentTimeMillis());
				if((c.getAmount()<=0) ||
						(c.getEndDate().before(testDate)))
				{
					System.out.println("coupon " +coupon.getTitle()+" out of inventory or ut of date");
				}
				else {
					coupdd.addCuoponPurchase(customerID, coupon.getId());
					coupon.setAmount(coupon.getAmount()-1);
					coupdd.updateCoupon(coupon);
				}
			}
		} catch (DBExceptions e) {
			throw new FacadeException("customer: coupon Purchase By Customer failed", e);
		}

	}
	public  ArrayList<Coupon> getAllCoupons() throws SQLException, FacadeException {
		try {
			return coupdd.getAllCouponByCustomer(customerID);
			
		} catch (DBExceptions e) {
			throw new FacadeException("customer: get All Coupons failed", e);
		}
		
	}
	
	public  ArrayList<Coupon> getAllCouponsByCategory(Category category) throws SQLException, FacadeException {
		try {
			
			return coupdd.getAllCouponsByCategory(category,customerID);
		} catch (DBExceptions e) {
			throw new FacadeException("customer: get All Coupons By Category failed", e);
		}
	}
	
	public ArrayList<Coupon>  getAllCouponsByMaxPrice(Double maxPrice) throws SQLException, FacadeException {
		try {
			return coupdd.getAllCouponsBypPrice(customerID, maxPrice);

		} catch (DBExceptions e) {
			throw new FacadeException("customer: get All Coupons By max price failed", e);
		}
		
	}
	public Customer getCustomerDetails() throws SQLException, FacadeException {
		try {
			return cusdd.getOneCustomers(customerID);
		} catch (DBExceptions e) {
			throw new FacadeException("customer: get Customer Details failed", e);
		}
	}
	
	
	
	
}