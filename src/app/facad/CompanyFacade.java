package app.facad;

import java.sql.SQLException;
import java.util.ArrayList;

import app.DAOs.CompaniesDBDAO;
import app.DAOs.CouponsDBDAO;
import app.DAOs.CustomerDBDAO;
import app.Models.Category;
import app.Models.Company;
import app.Models.Coupon;
import app.exceptions.DBExceptions;
import app.exceptions.FacadeException;

public class CompanyFacade extends ClientFacade {

	private int companyID = 0;

	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public CompanyFacade() {
		super();
	}
	//TODO change getcouponbyprice to sql query 
	@Override
	public boolean login(String email, String password) throws SQLException, FacadeException {

		try {
			setCompanyID(comdd.checkForID(email, password));
			if (!(companyID == -1)) {

				return true;
			} else {
				return false;
			}
		} catch (DBExceptions e) {
			throw new FacadeException("company: login failed", e);
		}

	}

	public void addCouponToCompany(Coupon coupon) throws SQLException, FacadeException {
		
		try {
			ArrayList<Coupon> coupons = coupdd.getAllCompanyCoupons(companyID);
			for (Coupon c : coupons) {
				if (coupon.getTitle().equals(c.getTitle())) {
					break;
				}
			}
				coupdd.addCoupon(coupon);
			
		} catch (DBExceptions e) {
			throw new FacadeException("company: adding coupon failed", e);
		}

	}

	public void updateCouponInCompany(Coupon coupon) throws SQLException, FacadeException {

		try {
			coupdd.updateCoupon(coupon);

		} catch (DBExceptions e) {
			throw new FacadeException("company: updating coupon failed", e);
		}
	}

	public void deleteCopounFromCompany(int couponID) throws SQLException, FacadeException {

		try {
			coupdd.deleteCouponPurchase(couponID);
			coupdd.deleteCuopon(coupdd.getOneCoupon(couponID));

		} catch (DBExceptions e) {
			throw new FacadeException("company: deleting coupon failed", e);
		}

	}

	public ArrayList<Coupon> getAllCouponsInCompany() throws SQLException, FacadeException {
		
		try {
			return coupdd.getAllCompanyCoupons(companyID);
		} catch (DBExceptions e) {
			throw new FacadeException("company: get All Coupons failed", e);
		}
		 
	}

	public ArrayList<Coupon> getAllCompnyCouponsOfOneCategory(Category category) throws SQLException, FacadeException {
		
		try {
			return coupdd.getAllCompnyCouponsOfOneCategory(category, companyID);
		} catch (DBExceptions e) {
			throw new FacadeException("company: get All Coupons Of One Kind failed", e);
		}
		 
	}
	//TODO change getcouponbyprice to sql query 
	public ArrayList<Coupon> getAllCouponsByPrice(double maxPrice) throws SQLException, FacadeException {
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		try { 
			coupons = coupdd.getAllCompanyCoupons(companyID);
			for (Coupon coupon : coupons) {
				if (coupon.getPrice() > maxPrice) {
					coupons.remove(coupon);
				}
			}
			return coupons;

		} catch (DBExceptions e) {
			throw new FacadeException("company: get All Coupons By Price failed", e);
		}
	}

	public Company getCompanyDetails() throws SQLException, FacadeException {
		
		Company company;
		try {
			company = comdd.getOneCompany(companyID);

		} catch (DBExceptions e) {
			throw new FacadeException("company: getting company Details failed", e);
		}
		return company;
	}

}
