package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
public class Item {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;	

	@Column(name = "quantity")
    private int quantity;

	@Column(name = "description")
    private String description;

	@ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;
    
    public Item(int quantity, String description, Order order, Product product) {
		super();
		this.quantity = quantity;
		this.description = description;
		this.order = order;
		this.product = product;
	}
    
    public Long getId() {
    	return this.id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
