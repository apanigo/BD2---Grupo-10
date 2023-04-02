package ar.edu.unlp.info.bd2.model;

import java.util.Date;

public class DeliveryMan extends User{

    private int numberOfSuccessOrders;

    private Date dateOfAdmission;

    private boolean free;


    
    
    public DeliveryMan(String name, String username, String password, String email, Date dateOfBirth) {
    	super(name, username, password, email, dateOfBirth);
    	this.setDateOfAdmission(new Date());
    }

    
    public int getNumberOfSuccessOrders() {
        return numberOfSuccessOrders;
    }

    public void setNumberOfSuccessOrders(int numberOfSuccessOrders) {
        this.numberOfSuccessOrders = numberOfSuccessOrders;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
