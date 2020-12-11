package myproject.molspot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Episode {

    public Episode() {

    }

    public Episode(LocalDateTime startDate) {
        this.startDate = startDate;
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

    private LocalDateTime startDate;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @JsonIgnore
    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "episode")
    private Collection<Suspicion> suspicions = new ArrayList<>();
}
