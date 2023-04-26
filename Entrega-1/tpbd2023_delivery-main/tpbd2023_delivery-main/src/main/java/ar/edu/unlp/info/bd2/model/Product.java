package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="name")
    private String name;

	@Column(name="price")
    private float price;

	@Column(name="lastPriceUpdateDate")
    private Date lastPriceUpdateDate;

	@Column(name="weight")
    private float weight;

	@Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

	@ManyToMany(mappedBy = "products")
    private List<ProductType> types;
	
	
	public Product(String name, float price, Date lastPriceUpdateDate, float weight, String description,
			Supplier supplier, List<ProductType> types) {
		this.name = name;
		this.price = price;
		this.lastPriceUpdateDate = lastPriceUpdateDate;
		this.weight = weight;
		this.description = description;
		this.supplier = supplier;
		this.types = types;
	}

	public Product() {
		
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
    
    public String getName() {
    	return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<ProductType> getTypes() {
        return types;
    }

    public void setTypes(List<ProductType> types) {
        this.types = types;
    }

    public Date getLastPriceUpdateDate() {
        return lastPriceUpdateDate;
    }

    public void setLastPriceUpdateDate(Date lastPriceUpdateDate) {
        this.lastPriceUpdateDate = lastPriceUpdateDate;
    }

}
