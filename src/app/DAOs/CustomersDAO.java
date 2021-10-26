package app.DAOs;

import app.Models.Customer;
import app.exceptions.DBExceptions;
import app.exceptions.DaoException;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomersDAO {
    public boolean isCustomerExists (String email, String password) throws SQLException,DaoException, DBExceptions, SQLException ;
    public void addCustomer (Customer customer) throws SQLException, DaoException ,DBExceptions;
    public void updateCustomer(Customer customer) throws SQLException, DaoException, DBExceptions ;
    public void deleteCustomer (int customerID) throws SQLException, DaoException, DBExceptions ;
    public ArrayList<Customer> getAllCustomers () throws SQLException, DaoException, DBExceptions ;
    public Customer getOneCustomers (int customerID) throws SQLException, DaoException, DBExceptions;
}
