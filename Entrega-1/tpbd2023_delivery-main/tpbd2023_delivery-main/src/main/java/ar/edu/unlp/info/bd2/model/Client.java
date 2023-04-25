package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Client extends User{
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "dateOfRegister")
    private Date dateOfRegister;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();
    
    public Client() {
    	
    }
    
    public Client(String name, String username, String password, String email, Date dateOfBirth) {
    	super(name, username, password, email, dateOfBirth);
    	this.setDateOfRegister(new Date());
    }

    public Long getId() {
        return id;
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