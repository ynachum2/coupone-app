package app.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    int id;
    String Name;
    String email;
    String password;

    List<Coupon> coupons = new ArrayList<Coupon>();

    public Company(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public Company() {
		super();
	}

	public Company( String name, String email, String password) {
        super();

        Name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Companies [id=" + id + ", Name=" + Name + ", email=" + email + ", password=" + password + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return getEmail().equals(company.getEmail()) &&
                getPassword().equals(company.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword());
    }
}
