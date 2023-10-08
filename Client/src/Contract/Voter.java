package Contract;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Voter implements Serializable {

    private int studentNumber;
    private String password;
    Map<Integer, Integer> voteMap;


    public Map<Integer, Integer> getVoteMap() {
        return voteMap;
    }

    public void setVoteMap(Map<Integer, Integer> voteMap) {
        this.voteMap = voteMap;
    }

    public Voter(int studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
        voteMap= new HashMap<>();
        for (int i = 0; i <= 3; i++) {
            voteMap.put(i, 0);
        }
    }

    public void vote(int rank, int voteValue) {
        if (voteMap.containsKey(rank) && (voteValue >= 0 && voteValue <= 3)) {
            voteMap.put(rank, voteValue);
        } else {
            System.out.println("Invalid vote value");
        }
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public boolean matchPassword(String password) {
        return Objects.equals(this.password, password);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return studentNumber == voter.studentNumber && Objects.equals(password, voter.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, password);
    }
}
