package app.DAOs;

import app.Models.Category;
import app.Models.Coupon;
import app.exceptions.DBExceptions;
import app.exceptions.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CouponsDAO {
    public void addCoupon (Coupon coupon) throws SQLException, DBExceptions,DaoException;
    public void updateCoupon (Coupon coupon) throws SQLException ,DBExceptions,DaoException;
    public void deleteCuopon (Coupon coupon) throws SQLException, DBExceptions;
    public ArrayList<Coupon> getAllCoupons() throws SQLException, DBExceptions;
    public Coupon getOneCoupon (int couponID) throws SQLException, DBExceptions;
    public void addCuoponPurchase (int customerID, int couponID) throws SQLException, DBExceptions;
    public void deleteCouponPurchase (int customerID, int couponID) throws SQLException, DBExceptions;
	void deleteCouponPurchase(int couponID) throws SQLException, DBExceptions;
	void deleteCouponPurchaseByCustomerId(int customerID) throws SQLException, DBExceptions;
	ArrayList<Coupon> getAllCompanyCoupons(int companyID) throws SQLException, DBExceptions;
	ArrayList<Coupon> getAllCompnyCouponsOfOneCategory(Category category, int companyID)
			throws SQLException, DBExceptions;
	boolean isCouponInCustomer(int customerID, int couponID) throws SQLException, DBExceptions;
	ArrayList<Coupon> getAllCouponByCustomer(int customerID) throws SQLException, DBExceptions;
	ArrayList<Coupon> getAllCouponsByCategory(Category category, int customeID) throws SQLException, DBExceptions;
	ArrayList<Coupon> getAllCouponsBypPrice(int customerID, double maxPrice) throws SQLException, DBExceptions;

}
