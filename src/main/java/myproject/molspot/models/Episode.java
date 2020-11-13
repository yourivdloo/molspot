package myproject.molspot.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Episode {

    public Episode(){

    }
    public Episode(LocalDateTime startDate){
        this.startDate = startDate;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    public Integer getId(){ return id; }
    public void setId(Integer id) { this.id = id; }

    private LocalDateTime startDate;
    public LocalDateTime getStartDate(){
        return startDate;
    }
    public void setStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }
}
