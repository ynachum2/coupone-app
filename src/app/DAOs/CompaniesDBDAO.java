package app.DAOs;

import app.Infastucture.ConnectionPool;
import app.Models.Company;
import app.exceptions.DBExceptions;
import app.exceptions.DaoException;

import java.sql.*;
import java.util.ArrayList;

import java.sql.*;
import java.util.ArrayList;

public class CompaniesDBDAO implements CompaniesDAO {

	private ConnectionPool connectionPool;

	@Override
	public boolean isCompanyExists(String email, String password) throws SQLException, DBExceptions {
		Company company = new Company(email, password);
		if (getAllCompanies().contains(company)) {
			return true;
		} else {
			return false;
		}
	}

	public int checkForID(String email, String password) throws SQLException, DBExceptions {
		Company company = new Company(email, password);
		int id= -1;
		for (Company c : getAllCompanies()) {

			if (c.equals(company)) {
				id=c.getId();
				return id;
			}	
		}
		return id;
	}

	@Override
	public void addCompany(Company company) throws SQLException, DBExceptions {

		Connection con = ConnectionPool.getInstance().getConnection();
		if (isCompanyExists(company.getEmail(), company.getPassword())) {
			System.out.println("company already exists");
		} else {
			// to send the info to the DB

			System.out.println("Inserting data id=" + company.getId());
			String sql = "insert into companies (  name,email,password ) values (" + company.getName() + "," + " "
					+ company.getEmail() + "" + ", " + company.getPassword() + ")";
			try {
				PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
				preparedStatement.execute();

				ResultSet rsKey = preparedStatement.getGeneratedKeys();
				rsKey.next();
				company.setId(rsKey.getInt("id"));

				System.out.println("new company added to DB");

			} catch (Exception e) {
				throw new DaoException("adding company failed", e);
			} finally {
				ConnectionPool.getInstance().restoreConnection(con);
			}
		}

	}

	@Override
	public void updateCompany(Company company) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		String sql = "update customer set (name, email, password) values (" + company.getName() + ", name='"
				+ company.getName() + "' where id=" + company.getId() + ")";

		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Record with id " + company.getId() + " was updated");
		} catch (Exception e) {
			throw new DaoException("updating company failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);

		}
	}

	@Override
	public void deleteCompany(int companyID) throws SQLException, DBExceptions {

		Connection con = ConnectionPool.getInstance().getConnection();
		String sql = "delete from company where id = " + companyID;
		try {
			PreparedStatement preparedStatement = connectionPool.getConnection().prepareStatement(sql);
			preparedStatement.executeUpdate();
			System.out.println("Record with id " + companyID + " was deleted");

		} catch (Exception e) {
			throw new DaoException("deleting company failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);

		}
	}

	@Override
	public ArrayList<Company> getAllCompanies() throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();

		ArrayList<Company> companies = new ArrayList<>();

		String sql = "select id, name, email, password from companies";

		try {
			Statement statement = connectionPool.getConnection().createStatement();
			ResultSet results = statement.executeQuery(sql);
			while (results.next()) // Iteration over each row / record
			{
				Company comapny = new Company();
				comapny.setId(results.getInt("id"));
				comapny.setName(results.getString("name"));
				comapny.setEmail(results.getString("email"));
				comapny.setPassword(results.getString("password"));
				companies.add(comapny);
			}
		} catch (Exception e) {
			throw new DaoException("getAllCompanies failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);

		}
		return companies;
	}

	@Override
	public Company getOneCompany(int companyID) throws SQLException, DBExceptions {
		Connection con = ConnectionPool.getInstance().getConnection();
		Company company = new Company();

		try {
			Statement statement = connectionPool.getConnection().createStatement();
			String sql = "select id, name, email, password from companies where id=" + companyID;
			ResultSet results = statement.executeQuery(sql);

			company.setId(results.getInt("id"));
			company.setName(results.getString("name"));
			company.setEmail(results.getString("email"));
			company.setPassword(results.getString("password"));

			if (isCompanyExists(company.getEmail(), company.getPassword())) {
				System.out.println("Query executed successfully");

			} else {
				System.out.println("no such company in DB");
			}
		} catch (Exception e) {
			throw new DaoException("get All Companies failed", e);
		} finally {
			ConnectionPool.getInstance().restoreConnection(con);

		}
		return company;

	}

}
