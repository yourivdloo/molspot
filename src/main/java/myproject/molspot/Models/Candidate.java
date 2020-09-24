package myproject.molspot.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Candidate {

    public Candidate(){

    }
    public Candidate(String name){
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    public Integer getId(){ return id; }
    public void setId(Integer id) { this.id = id; }

    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    private boolean isEliminated = false;
    public boolean getIsEliminated(){
        return isEliminated;
    }
    public void setIsEliminated(boolean isEliminated){
        this.isEliminated = isEliminated;
    }
}
