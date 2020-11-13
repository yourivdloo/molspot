package myproject.molspot.models;


import javax.persistence.*;

@Entity
public class Suspicion {
    public Suspicion(){

    }

    public Suspicion(User user, Candidate candidate, Episode episode, int amount){
        setUser(user);
        setCandidate(candidate);
        setEpisode(episode);
        setAmount(amount);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) { this.user = user; }

    public Candidate getCandidate() { return candidate; }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Episode getEpisode() { return episode; }

    public void setEpisode(Episode episode) { this.episode = episode; }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne
    @JoinColumn(name = "episode_id", nullable = false)
    private Episode episode;

    private Integer amount;
}
