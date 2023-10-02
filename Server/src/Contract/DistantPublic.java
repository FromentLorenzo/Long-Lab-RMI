package Contract;

import java.rmi.RemoteException;
import java.util.List;

public interface DistantPublic extends java.rmi.Remote {
    public List<Candidate> getCandidates() throws RemoteException;
    public VoteMaterial getVoteMaterial(int studentNumber) throws RemoteException;

}