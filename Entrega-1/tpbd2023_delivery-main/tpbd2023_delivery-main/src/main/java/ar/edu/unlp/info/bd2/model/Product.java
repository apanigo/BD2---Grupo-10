package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "product")
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
	@JoinColumn(name="supplier")
    private Supplier supplier;

	// CAMBIAR
	/*
	 * LA TABLA TIENE QUE ESTAR DEL LADO DE PRODUCT TYPE OSEA INVERTIR LO QUE HAY EN AMBAS LISTAS DE CADA MODELO - done
	 * PARA LA CONSULTA DE OBTENER PRODUCTOS DEBERIAMOS RECORRER LA LISTA DE TIPOS Y ENCONTRAR LAS DEL TIPO PASADO COMO PARAMETRO
	 * DESPUES HACER UN GETSESULTLIST Y MANDARLE AL RETURN LA LISTA DE PRODUCTOS :====ssdADSA
	 */
	// https://www.adictosaltrabajo.com/2020/04/02/hibernate-onetoone-onetomany-manytoone-y-manytomany/
	@ManyToMany 
    private List<ProductType> types;
	
	
	public Product(String name, float price, float weight, String description, Supplier supplier, List<ProductType> types) {
		this.name = name;
		this.price = price;
		this.weight = weight;
		this.description = description;
		this.supplier = supplier;
		this.types = types;
	}
	
	public Product(String name, float price, Date lastPriceUpdateDate, float weight, String description, Supplier supplier, List<ProductType> types) {
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
