package app.DAOs;

import app.Infastucture.ConnectionPool;
import app.Models.Category;
import app.Models.Company;
import app.Models.Coupon;
import app.Models.Customer;
import app.exceptions.DBExceptions;
import app.exceptions.DaoException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CouponsDBDAO implements CouponsDAO {

	ConnectionPool connectionPool;

	@Override

	public void addCoupon(Coupon coupon) throws SQLException, DBExceptions, DaoException {

		Connection con = ConnectionPool.getInstance().getConnection();

		System.out.println("Inserting data to coupon=" + coupon.getTitle());
		try {
			String sql = "INSERT INTO sql11437103.coupons +"
					+ "(company_id, category_id, title, discription, start_date, end_date, amount, price, image) +"
					+ "VALUES ('" + coupon.getCompanyId() + "','" + coupon.getCategory().ordinal() + "', '" + coupon.getTitle()
					+ "'," + " '" + coupon.getDescription() + "', '" + coupon.getStartDate() + "', '"
					+ coupon.getEndDate() + "'," + " '" + coupon.getAmount() + "', '" + coupon.getPrice() + "', '"
					+ coupon.getImage() + "')";

			PreparedStatement preparedStatement = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.execute();
			// get back the id
			ResultSet rsKey = preparedStatement.getGeneratedKeys();
			rsKey.next();
			coupon.setId(rsKey.getInt("id"));

		} catch (SQLException e) {
			throw new DaoException("adding coupon failed ", e);
		} finally { // TODO: handle exception
			ConnectionPool.getInstance().restoreConnection(con);
			System.out.println("new coupon added to DB");
		}
	}

	@Override

	public void updateCoupon(Coupon coupon) throws SQLException, DBExceptions {

		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "update customer set sql11437103.coupons"
				+ " ( category_id, title, discription, start_date, end_date, amount, price, image)"
				+ "  VALUES ('" + coupon.getCategory() + "', '" + coupon.getTitle()
				+ "', " + "'" + coupon.getDescription() + "', '" + coupon.getStartDate() + "', '" + coupon.getEndDate()
				+ "'," + "  '" + coupon.getAmount() + "', '" + coupon.getPrice() + "', '" + coupon.getImage() + "')";
		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Record with description " + coupon.getDescription() + " was updated");
		} catch (Exception e) {
			throw new DaoException("updatting Coupon  failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override

	public void deleteCuopon(Coupon coupon) throws SQLException, DBExceptions {

		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete from coupons where id = " + coupon.getId();
		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Record with id " + coupon.getId() + " was deleted");

		} catch (Exception e) {
			throw new DaoException("delete Cuopon failed", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override

	public ArrayList<Coupon> getAllCoupons() throws SQLException, DBExceptions {

		Connection con = ConnectionPool.getInstance().getConnection();
		ArrayList<Coupon> coupons = new ArrayList<>();

		String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ " from coupons";
		// open connection
		Statement statement = connectionPool.getConnection().createStatement();

		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{

			Coupon coupon = new Coupon();

			coupon.setCategory(Category.valueOf(results.getString("category_id")));
			coupon.setId(results.getInt("id"));
			coupon.setTitle(results.getString("title"));
			coupon.setDescription(results.getString("Description"));
			coupon.setStartDate(results.getDate("Start_Date"));
			coupon.setEndDate(results.getDate("end_Date"));
			coupon.setAmount(results.getInt("amount"));
			coupon.setPrice(results.getDouble("price"));
			coupon.setImage(results.getString("image"));
			coupon.setCompanyId(results.getInt("company_id"));
			coupons.add(coupon);
		}

		ConnectionPool.getInstance().restoreConnection(con);
		return coupons;

	}

	@Override

	public Coupon getOneCoupon(int couponID) throws SQLException, DBExceptions {

		Coupon coupon = new Coupon();
		Connection con = ConnectionPool.getInstance().getConnection();

		try {
			if (getAllCoupons().contains(couponID)) {
				System.out.println("Query executed successfully");
				Statement statement = connectionPool.getConnection().createStatement();
				String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
						+ " from coupons where id= " + couponID;

				ResultSet results = statement.executeQuery(sql);

				coupon.setCategory(Category.valueOf(results.getString("category_id")));
				coupon.setId(results.getInt("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setDescription(results.getString("Description"));
				coupon.setStartDate(results.getDate("Start_Date"));
				coupon.setEndDate(results.getDate("end_Date"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				coupon.setCompanyId(results.getInt("company_id"));

				System.out.println("coupon found");
			} else {
				System.out.println("no such coupon in DB");

			}
		} catch (SQLException e) {
			throw new DaoException("getOneCoupon failed" + e);
		} finally {

			ConnectionPool.getInstance().restoreConnection(con);
		}

		return coupon;
	}

	@Override

	public void addCuoponPurchase(int customerID, int couponID) throws SQLException, DBExceptions {

		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "INSERT INTO sql11437103.customers_vs_coupons +" + "(customer_id, coupon_id) +" + "VALUES (	"
				+ customerID + "," + couponID + ")";

		PreparedStatement preparedStatement = con.prepareStatement(sql);
		preparedStatement.execute();
		// return connection to connectionPol
		ConnectionPool.getInstance().restoreConnection(con);

	}

	@Override
	public void deleteCouponPurchase(int customerID, int couponID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete  from sql11437103.customers_Vs_coupons where customer_id" + " = " + customerID
				+ " and coupon_id = " + couponID + "";
		try {

			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.execute();
			System.out.println("coupon purchase by customer deleted");

		} catch (SQLException e) {
			throw new DaoException("delete CuponParchase failed" + e);

		} finally {

			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCouponPurchase(int couponID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete  from sql11437103.customers_Vs_coupons where coupon_id = " + couponID + "";
		try {

			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.execute();
			System.out.println("coupon purchase by customer deleted");

		} catch (SQLException e) {
			throw new DaoException("delete CuponParchase failed" + e);

		} finally {

			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	@Override
	public void deleteCouponPurchaseByCustomerId(int customerID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete  from sql11437103.customers_Vs_coupons where customer_id  = " + customerID + "";
		try {

			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.execute();
			System.out.println("coupon purchase by customer deleted");

		} catch (SQLException e) {
			throw new DaoException("delete CuponParchase failed" + e);

		} finally {

			ConnectionPool.getInstance().restoreConnection(con);
		}
	}

	
	@Override
	public ArrayList<Coupon> getAllCompanyCoupons(int companyID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		ArrayList<Coupon> coupons = new ArrayList<>();
		String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ " from coupons where company_id = " + companyID + "";
		try {
		// open connection
		Statement statement = connectionPool.getConnection().createStatement();

		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{
			Coupon coupon = new Coupon();

				coupon.setCategory(Category.valueOf(results.getString("category_id")));
				coupon.setId(results.getInt("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setDescription(results.getString("Description"));
				coupon.setStartDate(results.getDate("Start_Date"));
				coupon.setEndDate(results.getDate("end_Date"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				coupon.setCompanyId(results.getInt("company_id"));
				coupons.add(coupon);
			}

		} catch (Exception e) {
			throw new DaoException("getting all coupons of a company failed" + e);
	} finally {

		ConnectionPool.getInstance().restoreConnection(con);
	}
		return coupons;
		
	}
	
	@Override
	//TODO-if sql wont work need to add "*from" to the query
	public ArrayList<Coupon> getAllCompnyCouponsOfOneCategory(Category category, int companyID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		ArrayList<Coupon> coupons = new ArrayList<>();
		String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ " from coupons where category_id = " + category.ordinal() + " and company_id = "+companyID+"";
		try {
		// open connection
		Statement statement = connectionPool.getConnection().createStatement();

		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{
			Coupon coupon = new Coupon();

				coupon.setCategory(Category.valueOf(results.getString("category_id")));
				coupon.setId(results.getInt("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setDescription(results.getString("Description"));
				coupon.setStartDate(results.getDate("Start_Date"));
				coupon.setEndDate(results.getDate("end_Date"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				coupon.setCompanyId(results.getInt("company_id"));
				coupons.add(coupon);
			}

		} catch (Exception e) {
			throw new DaoException("getting all coupons of a company failed" + e);
	} finally {

		ConnectionPool.getInstance().restoreConnection(con);
	}
		return coupons;
		
	}
	@Override
	public boolean isCouponInCustomer(int customerID, int couponID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "select * from sql11437103.customers_Vs_coupons where customer_id" + " = " + customerID
				+ " and coupon_id = " + couponID + ";";
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.execute();

			ResultSet results = preparedStatement.executeQuery(sql);
			if(results!=null) {
				return true;
			}
			else
			{
				return false;
			}
		} catch (SQLException e) {
			throw new DaoException("delete CuponParchase failed" + e);

		} finally {

			ConnectionPool.getInstance().restoreConnection(con);
		}
	}
	
	
	@Override
	public ArrayList<Coupon> getAllCouponByCustomer(int customerID) throws SQLException, DBExceptions {
		
		Connection con = ConnectionPool.getInstance().getConnection();
		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
		String sql = "select * from sql11437103.customers_Vs_coupons where customer_id  = " + customerID + "";
		try {

			Statement statement = connectionPool.getConnection().createStatement();
			ResultSet results = statement.executeQuery(sql);
			
			while (results.next()) // Iteration over each row / record
			{
				Coupon coupon = new Coupon();

					coupon.setCategory(Category.valueOf(results.getString("category_id")));
					coupon.setId(results.getInt("id"));
					coupon.setTitle(results.getString("title"));
					coupon.setDescription(results.getString("Description"));
					coupon.setStartDate(results.getDate("Start_Date"));
					coupon.setEndDate(results.getDate("end_Date"));
					coupon.setAmount(results.getInt("amount"));
					coupon.setPrice(results.getDouble("price"));
					coupon.setImage(results.getString("image"));
					coupon.setCompanyId(results.getInt("company_id"));
					coupons.add(coupon);
				}

		} catch (SQLException e) {
			throw new DaoException("delete CuponParchase failed" + e);

		} finally {

			ConnectionPool.getInstance().restoreConnection(con);
		}
		return coupons;
	}
	@Override
	public ArrayList<Coupon> getAllCouponsByCategory(Category category, int customeID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		ArrayList<Coupon> coupons = new ArrayList<>();
		String sql = "select company_id, category_id, title, discription, start_date, end_date, amount, price, image)"
				+ " from coupons where category_id = " + category.ordinal() + " and customer_id = "+customeID+"";
		try {
		
		Statement statement = connectionPool.getConnection().createStatement();

		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{
			Coupon coupon = new Coupon();

				coupon.setCategory(Category.valueOf(results.getString("category_id")));
				coupon.setId(results.getInt("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setDescription(results.getString("Description"));
				coupon.setStartDate(results.getDate("Start_Date"));
				coupon.setEndDate(results.getDate("end_Date"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				coupon.setCompanyId(results.getInt("company_id"));
				coupons.add(coupon);
			}

		} catch (Exception e) {
			throw new DaoException("getting all customers coupons of a category failed" + e);
	} finally {

		ConnectionPool.getInstance().restoreConnection(con);
	}
		return coupons;
		
	}
	
	@Override
	public ArrayList<Coupon> getAllCouponsBypPrice(int customerID,double maxPrice) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		ArrayList<Coupon> coupons = new ArrayList<>();
		String sql = "SELECT * FROM sql11437103.coupons join "
				+ "sql11437103.customers_Vs_coupons on "
				+ "(sql11437103.coupons.id = sql11437103.customers_Vs_coupons.coupon_id)"
				+ "  where sql11437103.coupons.price > "+ maxPrice+"  and "
				+ "sql11437103.customers_Vs_coupons.customer_id = "+ customerID+" ";
		try {
		
		Statement statement = connectionPool.getConnection().createStatement();

		ResultSet results = statement.executeQuery(sql);
		while (results.next()) // Iteration over each row / record
		{
			Coupon coupon = new Coupon();

				coupon.setCategory(Category.valueOf(results.getString("category_id")));
				coupon.setId(results.getInt("id"));
				coupon.setTitle(results.getString("title"));
				coupon.setDescription(results.getString("Description"));
				coupon.setStartDate(results.getDate("Start_Date"));
				coupon.setEndDate(results.getDate("end_Date"));
				coupon.setAmount(results.getInt("amount"));
				coupon.setPrice(results.getDouble("price"));
				coupon.setImage(results.getString("image"));
				coupon.setCompanyId(results.getInt("company_id"));
				coupons.add(coupon);
			}

		} catch (Exception e) {
			throw new DaoException("getting all customers coupons of a category failed" + e);
	} finally {

		ConnectionPool.getInstance().restoreConnection(con);
	}
		return coupons;
		
	}
	
	public void deleteExpiredCoupons() throws SQLException, DBExceptions {
		
		
		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "delete from sql11437103.coupons where end_date < NOW()";
		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("daily job deleting expired coupons");
		} catch (Exception e) {
			throw new DaoException("delete Cuopon failed", e);

		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}
	
//	public void dailyDelet() {
//		
//		ArrayList<Coupon> coupons = new ArrayList<Coupon>();
//		
//		try {
//			coupons = getAllCoupons();
//			
//				deleteExpiredCoupons(coupons);
//			
//		} catch (Exception e) {
//		
//		}
//	}
//	
//	
	
	
}
