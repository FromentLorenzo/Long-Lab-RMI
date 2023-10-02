package VotingServer;

import Contract.Candidate;
import Contract.DistantPublic;
import Contract.VoteMaterial;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PublicServer extends UnicastRemoteObject implements DistantPublic {

    ArrayList<Candidate> candidateList;





    protected PublicServer(int port) throws RemoteException {
        super(port);
        candidateList = new ArrayList<>();
        candidateList.add(new Candidate(1, "Jean", "Patate"));
        candidateList.add(new Candidate(2, "Jean2", "Patate"));
        candidateList.add(new Candidate(3, "Jean3", "Patate"));


    }

    @Override
    public List<Candidate> getCandidates() throws RemoteException {
        return this.candidateList;
    }

    @Override
    public VoteMaterial getVoteMaterial(int studentNumber) throws RemoteException {
        return null;
    }
}
