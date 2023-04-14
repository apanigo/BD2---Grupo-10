package ar.edu.unlp.info.bd2.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "number")
    private int number;

	@Column(name = "dateOfOrder")
    private Date dateOfOrder;

	@Column(name = "comments")
    private String comments;

	@Column(name = "totalPrice")
    private float totalPrice;

	@Column(name = "delivered")
    private boolean delivered;

//    private DeliveryMan deliveryMan;
//
//    private Client client;
//
//    private Qualification qualification;
//
//    private Address address;
//
//    private List<Item> items;
    
    public Long getId() {
    	return this.id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

//    public DeliveryMan getDeliveryMan() {
//        return deliveryMan;
//    }
//
//    public void setDeliveryMan(DeliveryMan deliveryMan) {
//        this.deliveryMan = deliveryMan;
//    }
//
//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }
//
//    public Qualification getQualification() {
//        return qualification;
//    }
//
//    public void setQualification(Qualification qualification) {
//        this.qualification = qualification;
//    }
//
//    public Address getAddress() {
//        return address;
//    }
//
//    public void setAddress(Address address) {
//        this.address = address;
//    }
//
//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
