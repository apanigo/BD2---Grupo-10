package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 20, unique = false, updatable = true)
    private String name;

    @Column(name = "address", nullable = false, length = 20, unique = false, updatable = true)
    private String address;

    @Column(name = "apartment", nullable = true, length = 20, unique = false, updatable = true)
    private String apartment;

    @Column(name = "coord_x", nullable = false, unique = false, updatable = true)
    private float coordX;

    @Column(name = "coord_y", nullable = false, unique = false, updatable = true)
    private float coordY;

    @Column(name = "description", nullable = false, length = 100, unique = false, updatable = true)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false, unique = false, updatable = true)
    private Client client;

    public Address () {
    	
    }
    
    public Address(String name, String address, String apartment, float coordX, float coordY, String description, Client client) {
		this.name = name;
		this.address = address;
		this.apartment = apartment;
		this.coordX = coordX;
		this.coordY = coordY;
		this.description = description;
		this.client = client;
		this.client.setNewAddress(this);
	}
    
    public Address(String name, String address, float coordX, float coordY, String description, Client client) {
    	this.name = name;
		this.address = address;
		this.coordX = coordX;
		this.coordY = coordY;
		this.description = description;
		this.client = client;
		this.client.setNewAddress(this);
    }

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
    
    public Long getId() {
    	return this.id;
    }
    
    public void setId(Long id) { 
    	this.id = id;
    }
    
}