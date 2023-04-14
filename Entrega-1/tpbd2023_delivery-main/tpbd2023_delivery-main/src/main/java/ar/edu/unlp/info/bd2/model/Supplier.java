package ar.edu.unlp.info.bd2.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name="name")
    private String name;

	@Column(name="cuit")
    private String cuit;

	@Column(name="address")
    private String address;

	@Column(name="coordX")
    private float coordX;

	@Column(name="coordY")
    private float coordY;

//	@OneToMany
//    private List<Product> products;


	public Supplier() {
		
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
