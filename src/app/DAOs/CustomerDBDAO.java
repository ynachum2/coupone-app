package app.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.Infastucture.ConnectionPool;
import app.Models.Company;
import app.Models.Customer;
import app.exceptions.DBExceptions;
import app.exceptions.DaoException;

public class CustomerDBDAO implements CustomersDAO {
	
	ConnectionPool connectionPool;

	@Override
	public boolean isCustomerExists(String email, String password) throws SQLException, DBExceptions {

		Customer customer = new Customer(email, password);
		if (getAllCustomers().contains(customer)) {
			return true;
		} else {
			return false;
		}

	}
	public int checkForID(String email, String password) throws SQLException, DBExceptions {
		Customer customer = new Customer(email, password);
		int id= -1;
		for (Customer c : getAllCustomers()) {

			if (c.equals(customer)) {
				id=c.getId();
				return id;
			}	
		}
		return id;
	}
	
	
	
	

	@Override
	public void addCustomer(Customer customer) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		if (isCustomerExists(customer.getEmail(), customer.getPassword())) {

			System.out.println("company already exists");

		} else {
			// to send the info to the DB
		

			// System.out.println("Inserting data id=" + customer.getId());
			System.out.println("Inserting data to customer=" + customer.getFirstName() + " " + customer.getLastName());
			String sql = "insert into customers (  firsName,lastName,email,password ) values ("
					+ customer.getFirstName() + "," + customer.getLastName() + "," + " " + customer.getEmail() + ""
					+ ", " + customer.getPassword() + ")";
			try {
				PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
				preparedStatement.execute();

				ResultSet rsKey = preparedStatement.getGeneratedKeys();
				rsKey.next();
				customer.setId(rsKey.getInt("id"));

				System.out.println("new customer added to DB");

			} catch (Exception e) {
				throw new DaoException("adding custome failed", e);
			} finally {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		
		String sql = "update customer set (  firsName,lastName,email,password ) values (" + customer.getFirstName()
				+ "," + customer.getLastName() + "," + " " + customer.getEmail() + "" + ", " + customer.getPassword()
				+ ")";
		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Record with id " + customer.getId() + " was updated");
		} catch (Exception e) {
			throw new DaoException("adding custome failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}

	}

	@Override
	public void deleteCustomer(int customerID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		
		String sql = "delete from customers where id = " + customerID;
		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Record with id " + customerID + " was deleted");

		} catch (Exception e) {
			throw new DaoException("adding custome failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
	}


	@Override
	public ArrayList<Customer> getAllCustomers() throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		ArrayList<Customer> customers = new ArrayList<>();

	

		String sql = "select id, name, email, password from customers";
		try {
			Statement statement = connectionPool.getConnection().createStatement();
			ResultSet results = statement.executeQuery(sql);
			while (results.next()) // Iteration over each row / record
			{
				Customer customer = new Customer();

				customer.setId(results.getInt("id"));
				customer.setFirstName(results.getString("firstName"));
				customer.setLastName(results.getString("lastName"));
				customer.setEmail(results.getString("email"));
				customer.setPassword(results.getString("password"));
				customers.add(customer);
			}
		} catch (Exception e) {
			throw new DaoException("getting customers failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return customers;
	}

	@Override
	public Customer getOneCustomers(int customerID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		
		String sql = "select id, firstName,lastName, email, password from customers where id=" + customerID;
		Customer customer = new Customer();
		try {
			Statement statement = connectionPool.getConnection().createStatement();
			ResultSet results = statement.executeQuery(sql);

			customer.setId(results.getInt("id"));
			customer.setFirstName(results.getString("firstName"));
			customer.setLastName(results.getString("lastName"));
			customer.setEmail(results.getString("email"));
			customer.setPassword(results.getString("password"));

			if (isCustomerExists(customer.getEmail(), customer.getPassword())) {
				System.out.println("Query executed successfully");
				System.out.println(customer);

			} else {
				System.out.println("no such customer in DB");

			}
		} catch (Exception e) {
			throw new DaoException("getting customer failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);
		}
		return customer;
	}

}
