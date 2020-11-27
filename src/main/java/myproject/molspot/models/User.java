package myproject.molspot.models;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {


    public User(){

    }
    public User(String username, String emailAddress, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = encoder.encode(password);
        this.roles = "USER_ROLE";
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    public Integer getId(){ return id; }
    public void setId(Integer id) { this.id = id; }

    private String username;
    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }

    private String emailAddress;
    public String getEmailAddress(){ return emailAddress; }
    public void setEmailAddress(String emailAddress){ this.emailAddress = emailAddress; }

    private String password;
    public String getPassword(){ return password; }
    public void setPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    private int points;
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }

    private String roles;
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }

    @ManyToMany
    @JoinTable(name="pool_members",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "poolId") })
    private Collection<Pool> pools = new ArrayList<>();

}
