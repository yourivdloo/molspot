package Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    public User(){

    }
    public User(String username, String emailAddress, String password){
        Username = username;
        EmailAddress = emailAddress;
        setPassword(password);
    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    public Integer getId(){
        return Id;
    }

    private String Username;
    public String getUsername(){
        return Username;
    }
    public void setUsername(String username){
        Username = username;
    }

    private String EmailAddress;
    public String getEmailAddress(){
        return EmailAddress;
    }
    public void setEmailAddress(String emailAddress){
        EmailAddress = emailAddress;
    }

    private int PasswordHash;
    public int getPasswordHash(){ return PasswordHash; }
    public void setPassword(String password){
        PasswordHash = password.hashCode();
    }
    public void setPasswordHash(int passwordHash){
        PasswordHash = passwordHash;
    }
}
