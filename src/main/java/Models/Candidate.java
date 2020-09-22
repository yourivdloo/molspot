package Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Candidate {

    public Candidate(){

    }
    public Candidate(String name, int id){
        Name = name;
        Id = id;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    public Integer getId(){ return Id; }

    private String Name;
    public String getName(){
        return Name;
    }
    public void setName(String name){
        Name = name;
    }

    private boolean IsEliminated = false;
    public boolean getIsEliminated(){
        return IsEliminated;
    }
    public void setIsEliminated(boolean isEliminated){
        IsEliminated = isEliminated;
    }
}
