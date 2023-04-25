package ar.edu.unlp.info.bd2.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="name")
    private String name;

	@Column(name="cuit", unique=true)
    private String cuit;

	@Column(name="address")
    private String address;

	@Column(name="coordX")
    private float coordX;

	@Column(name="coordY")
    private float coordY;

	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;


	public Supplier() {
		
	}
	
	
	
	public Supplier(String name, String cuit, String address, float coordX, float coordY) {
		super();
		this.name = name;
		this.cuit = cuit;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
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

    public String getCuil() {
        return cuit;
    }

    public void setCuil(String cuil) {
        this.cuit = cuil;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getCoordX() {
        return coordX;
    }

    public void setCoordX(float coordX) {
        this.coordX = coordX;
    }

    public float getCoordY() {
        return coordY;
    }

    public void setCoordY(float coordY) {
        this.coordY = coordY;
    }
    
//    public List<Product> getProducts() {
//    	return this.products();
//    }
//    
//    
//    public void setProducts(List<Product> products) {
//    	this.products = products;
//    }
    
}
