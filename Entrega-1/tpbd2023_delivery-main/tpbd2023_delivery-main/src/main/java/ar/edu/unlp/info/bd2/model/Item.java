package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name = "item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item", nullable = false, unique = true, updatable = false)
	private Long id;

	@Column(name = "quantity", nullable = false, unique = false, updatable = true)
    private int quantity;

	@Column(name = "description", nullable = false, length = 100, unique = false, updatable = true)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false, unique = false, updatable = false)
    private Order order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", nullable = false, unique = false, updatable = false)
    private Product product;
    
    public Item(int quantity, String description, Order order, Product product) {
		super();
		this.quantity = quantity;
		this.description = description;
		this.order = order;
		this.product = product;
	}

    public Item () {}
    
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
