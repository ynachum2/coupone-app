package app.DAOs;

import java.sql.SQLException;
import java.util.ArrayList;
import app.Models.Company;
import app.exceptions.DBExceptions;
import app.exceptions.DaoException;

public interface CompaniesDAO {
    public boolean isCompanyExists(String email, String password) throws SQLException,DBExceptions ,DaoException;
    public void addCompany (Company company) throws SQLException,DBExceptions ,DaoException ;
    public void updateCompany (Company company) throws SQLException,DBExceptions ,DaoException ;
    public void deleteCompany (int companyID) throws SQLException,DBExceptions ,DaoException;
    public ArrayList<Company> getAllCompanies () throws SQLException,DBExceptions ,DaoException;
    public Company getOneCompany (int companyID) throws SQLException,DBExceptions ,DaoException;


}
