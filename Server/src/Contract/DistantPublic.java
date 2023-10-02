package Contract;

import java.util.List;

public interface DistantPublic {
    public List<Candidates> getCandidates();
    public VoteMaterial getVoteMaterial(int studentNumber);

}
