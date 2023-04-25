package ar.edu.unlp.info.bd2.model;


import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "number")
    private int number;

	@Column(name = "date_of_order")
    private Date dateOfOrder;

	@Column(name = "comments")
    private String comments;

	@Column(name = "total_price")
    private float totalPrice;

	@Column(name = "delivered")
    private boolean delivered;

//    @OneToOne
//    @Column(name = "delivery_man")
//    private DeliveryMan deliveryMan;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

//    private Qualification qualification;

    @ManyToOne()
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "items")
    private List<Item> items;

    public Order(int number, Date dateOfOrder, String comments, Client client, Address address) {
        this.number = number;
        this.dateOfOrder = dateOfOrder;
        this.comments = comments;
        this.client = client;
        this.client.setNewOrder(this);
        this.address = address;
    }

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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
//
//    public Qualification getQualification() {
//        return qualification;
//    }
//
//    public void setQualification(Qualification qualification) {
//        this.qualification = qualification;
//    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
}
