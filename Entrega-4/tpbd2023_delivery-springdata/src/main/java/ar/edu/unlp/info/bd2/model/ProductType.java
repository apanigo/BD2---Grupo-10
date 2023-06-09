package ar.edu.unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "product_type")
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product_type")
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true, updatable = true)
    private String name;

	@Column(name = "description", nullable = false, length = 100, unique = false, updatable = true)
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_types",
            joinColumns = @JoinColumn(name="type"),
            inverseJoinColumns = @JoinColumn(name="product"))
    private List<Product> products;

	
	public ProductType() {
		
	}
	
	public ProductType(String name, String description) {
		this.name = name;
		this.description = description;
        this.products = new ArrayList<>();
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

    public void addProducts(Product newProduct) {
        if (!this.products.contains(newProduct)) {
            this.products.add(newProduct);
        }
    }

}
