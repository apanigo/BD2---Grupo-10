package ar.edu.unlp.info.bd2.model;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "name", nullable = false, length = 20, unique = false, updatable = true)
    private String name;

    @Column(name = "username", nullable = false, length = 20, unique = false, updatable = true)
    private String username;

    @Column(name = "password", nullable = false, length = 15, unique = false, updatable = true)
    private String password;

    @Column(name = "email", nullable = false, length = 50, unique = true, updatable = true)
    private String email;

    @Column(name = "date_of_birth", nullable = false, unique = false, updatable = true)
    private Date dateOfBirth;

    @Column(name = "score", nullable = false, unique = false, updatable = true)
    private int score;

    public User() {
    }

    public User(String name, String username, String password, String email, Date dateOfBirth, int score) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.score = score;
    }

    public User(String name, String username, String password, String email, Date dateOfBirth) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public void incrementScore() {
        this.score = this.score + 1;
    }
}
