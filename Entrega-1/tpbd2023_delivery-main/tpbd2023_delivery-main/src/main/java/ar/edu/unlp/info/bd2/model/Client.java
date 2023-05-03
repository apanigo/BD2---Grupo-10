package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@DiscriminatorValue("C")
public class Client extends User{
	
    @Column(name = "date_of_register", nullable = true, unique = false, updatable = false)
    private Date dateOfRegister;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    
    public Client() {
    	
    }

    public Client(String name, String username, String password, String email, Date dateOfBirth, int score) {
        super(name, username, password, email, dateOfBirth, score);
        Calendar calendar = Calendar.getInstance();
        this.dateOfRegister = calendar.getTime();
    }
    
    public Client(String name, String username, String password, String email, Date dateOfBirth) {
    	super(name, username, password, email, dateOfBirth);
        Calendar calendar = Calendar.getInstance();
        this.dateOfRegister = calendar.getTime();
    }

    public Date getDateOfRegister() {
        return dateOfRegister;
    }

    public void setDateOfRegister(Date dateOfRegister) {
        this.dateOfRegister = dateOfRegister;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    public void setNewAddress(Address newAddress) {
    	this.addresses.add(newAddress);
    }

    public void setNewOrder(Order newOrder) {
        this.orders.add(newOrder);
    }


}