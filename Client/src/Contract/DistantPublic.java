package Contract;

import java.rmi.RemoteException;
import java.util.List;

public interface DistantPublic extends java.rmi.Remote {
    public List<Candidate> getCandidates() throws RemoteException;
    public VoteMaterial getVoteMaterial(Voter voter) throws RemoteException;
    public boolean checkCanVote(int studentNumber)throws RemoteException;
}
