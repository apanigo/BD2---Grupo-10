package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DM")
public class DeliveryMan extends User{
	
	@Column(name = "number_of_success_orders", nullable = true, unique = false, updatable = true)
    private int numberOfSuccessOrders;

	@Column(name = "date_of_admission", nullable = true, unique = false, updatable = false)
    private Date dateOfAdmission;

	@Column(name = "free", nullable = true, unique = false, updatable = true)
    private boolean free;


	public DeliveryMan() {
		
	}

    public DeliveryMan(String name, String username, String password, String email, Date dateOfBirth, int score) {
        super(name, username, password, email, dateOfBirth, score);
        this.numberOfSuccessOrders = 0;
        this.dateOfAdmission = new Date();
        this.free = true;
    }
    
    public DeliveryMan(String name, String username, String password, String email, Date dateOfBirth) {
    	super(name, username, password, email, dateOfBirth);
        this.numberOfSuccessOrders = 0;
    	this.dateOfAdmission = new Date();
    	this.free = true;
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
