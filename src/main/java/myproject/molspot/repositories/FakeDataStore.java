package myproject.molspot.repositories;

import myproject.molspot.models.Candidate;
import myproject.molspot.models.User;

import java.util.ArrayList;
import java.util.List;

public class FakeDataStore {

    private final List<User> userList = new ArrayList<>();
    private final List<Candidate> candidateList = new ArrayList<>();

    // singleton pattern
    private static final FakeDataStore INSTANCE = new FakeDataStore();
    public static FakeDataStore getInstance() {
        return INSTANCE;
    }

    private FakeDataStore() {
        userList.add(new User("MolPredictor6", "Molpredictor@gmail.com", "ikweetdemolwel"));
        userList.add(new User("Mollootje", "Molloot@hotmail.com", "176389"));
        userList.add(new User("WouterDeMol", "Wouter.youtube@gmail.com", "Klaasfan5"));
        userList.add(new User("BanjoMovies", "BardoEllens@gmail.com", "koudePannenkoeken"));
        userList.add(new User("EllieLustFan", "Dylan.stoer@gmail.com", "Ellie4LIFE"));

        candidateList.add(new Candidate("Ron Boshard"));
        candidateList.add(new Candidate("Nikkie de Jager"));
        candidateList.add(new Candidate("Ellie Lust"));
        candidateList.add(new Candidate("Jeroen kijk in de Vegte"));
        candidateList.add(new Candidate("Patrick Martens"));

    }

    public User getUser(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(User user) {
        if (this.getUser(user.getId()) != null){
            return false;
        }
        userList.add(user);
        return true;
    }

    public boolean deleteUser(int id){
        User user = getUser(id);
        if (user == null){
            return false;
        }
        return userList.remove(user);
    }

    public boolean updateUser(User user){
        User old = this.getUser(user.getId());
        if (old == null){
            return false;
        }
        old.setUsername(user.getUsername());
        old.setEmailAddress(user.getEmailAddress());
        old.setPassword(user.getPassword());
        return true;
    }

    public List<User> getAllUsers(){
        return userList;
    }

    public Candidate getCandidate(int id) {
        for (Candidate candidate : candidateList) {
            if (candidate.getId() == id) {
                return candidate;
            }
        }
        return null;
    }

    public boolean addCandidate(Candidate candidate) {
        if (this.getCandidate(candidate.getId()) != null){
            return false;
        }
        candidateList.add(candidate);
        return true;
    }

    public boolean deleteCandidate(int id){
        Candidate candidate = getCandidate(id);
        if (candidate == null){
            return false;
        }
        return candidateList.remove(candidate);
    }

    public boolean updateCandidate(Candidate candidate){
        Candidate old = this.getCandidate(candidate.getId());
        if (old == null){
            return false;
        }
        old.setName(candidate.getName());
        old.setIsEliminated(candidate.getIsEliminated());
        return true;
    }

    public List<Candidate> getAllCandidates(){
        return candidateList;
    }





}
