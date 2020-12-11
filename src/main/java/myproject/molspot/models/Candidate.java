package myproject.molspot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Candidate {

    public Candidate() {

    }

    public Candidate(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private boolean isEliminated = false;

    public boolean getIsEliminated() {
        return isEliminated;
    }

    public void setIsEliminated(boolean isEliminated) {
        this.isEliminated = isEliminated;
    }

    @JsonIgnore
    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private Collection<Suspicion> suspicions = new ArrayList<>();
}
