package myproject.molspot.models;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    public User(){

    }
    public User(String username, String emailAddress, String password){
        this.username = username;
        this.emailAddress = emailAddress;
        hashPassword(password);
    }
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    public Integer getId(){
        return id;
    }
    public void setId(Integer id) { this.id = id; }

    @Column(name = "username")
    private String username;
    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    @Column(name = "email_address")
    private String emailAddress;
    public String getEmailAddress(){
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    @Column(name = "password_hash")
    private int passwordHash;
    public int getPasswordHash(){ return passwordHash; }
    public void hashPassword(String password){
        passwordHash = password.hashCode();
    }
    public void setPasswordHash(int passwordHash){
        this.passwordHash = passwordHash;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Column(name = "points")
    private int points;

}
