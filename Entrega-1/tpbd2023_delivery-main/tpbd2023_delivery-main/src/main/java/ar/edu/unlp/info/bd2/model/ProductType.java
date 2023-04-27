package ar.edu.unlp.info.bd2.model;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "productType")
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", unique=true)
    private String name;

	@Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_type_product",
            joinColumns = @JoinColumn(name = "product_type_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

	
	public ProductType() {
		
	}
	
	public ProductType(String name, String description) {
		this.name = name;
		this.description = description;
	}

    public Long getId() {
    	return this.id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
