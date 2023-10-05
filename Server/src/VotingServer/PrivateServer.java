package VotingServer;

import Contract.Candidate;
import Contract.DistantPrivate;

import java.io.Serializable;
import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrivateServer extends UnicastRemoteObject implements DistantPrivate, Serializable {

    private ArrayList<Candidate> candidateList;
    private Map<Integer, String> temporaryPasswords;
    private Map <Integer, Integer> totalVote;

    protected PrivateServer() throws RemoteException {
        super();
        candidateList = new ArrayList<>();
        temporaryPasswords = new HashMap<>();
        totalVote = new HashMap<>();


        candidateList.add(new Candidate(1, "Jean", "Patate"));
        candidateList.add(new Candidate(2, "Jean2", "Patate"));
        candidateList.add(new Candidate(3, "Jean3", "Patate"));

        for (Candidate candidate : candidateList) {
            totalVote.put(candidate.getRank(), 0);
        }

    }

    public void echo() {
        System.out.println("patate");
        System.out.println(temporaryPasswords);
    }

    @Override
    public boolean vote(Map<Integer, Integer> voteMap) throws RemoteException {
        System.out.println("New vote recieved");
        if (voteMap.size() == totalVote.size()) {   //vérifie que la map envoyée a autant d'entrées que de candidats
            System.out.println("Checking if it is correct");
            for (Map.Entry<Integer, Integer> entry : voteMap.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();

                if (!(value >= 0 && value <= 3)) {
                    System.out.println("Vote recieved is not correct \n" + voteMap);
                    return false;
                }
            }
            System.out.println("Adding \n" + totalVote + "\n" + voteMap);
            for (Map.Entry<Integer, Integer> entry : voteMap.entrySet()) {
                int key = entry.getKey();

                totalVote.put(key, totalVote.get(key) + voteMap.get(key));
            }
            System.out.println("total = \n" + totalVote);
            return true;
        }
        return false;
    }

    public Map<Integer, String> getTemporaryPasswords() {
        return temporaryPasswords;
    }

    public void setTemporaryPasswords(Map<Integer, String> temporaryPasswords) {
        this.temporaryPasswords = temporaryPasswords;
    }

    public ArrayList<Candidate> getCandidateList() {
        return candidateList;
    }

    public void setCandidateList(ArrayList<Candidate> candidateList) {
        this.candidateList = candidateList;
    }
}
