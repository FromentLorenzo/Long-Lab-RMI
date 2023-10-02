package VotingServer;

import Contract.Candidate;
import Contract.DistantPublic;
import Contract.VoteMaterial;
import Contract.Voter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PublicServer extends UnicastRemoteObject implements DistantPublic {

    ArrayList<Candidate> candidateList;
    ArrayList<Voter> voterList;



    protected PublicServer(int port) throws RemoteException {
        super(port);
        candidateList = new ArrayList<>();
        candidateList.add(new Candidate(1, "Jean", "Patate"));
        candidateList.add(new Candidate(2, "Jean2", "Patate"));
        candidateList.add(new Candidate(3, "Jean3", "Patate"));

        voterList = new ArrayList<>();
        voterList.add(new Voter(1, "A"));
        voterList.add(new Voter(2, "B"));
        voterList.add(new Voter(3, "C"));
        voterList.add(new Voter(4, "D"));
        voterList.add(new Voter(5, "E"));
        voterList.add(new Voter(6, "F"));
        voterList.add(new Voter(7, "G"));
    }

    @Override
    public List<Candidate> getCandidates() throws RemoteException {
        return this.candidateList;
    }

    @Override
    public VoteMaterial getVoteMaterial(int studentNumber) throws RemoteException {
        //return new VoteMaterial();
        //TODO: check if studentNumber is in voterList and PASSWORD
    }


}
