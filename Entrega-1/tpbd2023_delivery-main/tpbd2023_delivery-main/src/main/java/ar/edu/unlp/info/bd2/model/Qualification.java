package ar.edu.unlp.info.bd2.model;

import javax.persistence.*;

@Entity
@Table(name = "qualification")
public class Qualification {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_qualification")
	private Long id;	
	
	@Column(name = "score", nullable = false, unique = false, updatable = false)
    private float score; //De 1 a 5 estrellas

	@Column(name = "commentary", nullable = false, length = 30, unique = false, updatable = false)
    private String commentary;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false, unique = false, updatable = false)
    private Order order;

    public Qualification() {
    	
    }

    public Qualification(float score, String commentary, Order order) {
        this.score = score;
        this.commentary = commentary;
        this.order = order;
    }

    public Long getId() {
    	return this.id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
