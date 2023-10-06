package VotingServer;

import Contract.Candidate;
import Contract.DistantPrivate;
import Contract.Voter;

import java.io.Serializable;
import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrivateServer extends UnicastRemoteObject implements DistantPrivate, Serializable {

    private ArrayList<Candidate> candidateList;
    private Map<Integer, String> temporaryPasswords;
    private Map <Integer, Integer> totalVote;
    private ArrayList<Voter> hasNotVotedList;
    private Boolean isVoteFinished = false;

    protected PrivateServer() throws RemoteException {
        super();
        candidateList = new ArrayList<>();
        temporaryPasswords = new HashMap<>();
        totalVote = new HashMap<>();
        hasNotVotedList = new ArrayList<>();


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
    public boolean vote(Map<Integer, Integer> voteMap,int studentNumber, String otp) throws RemoteException {
        System.out.println("New vote recieved");
        System.out.println("Checking if voter can vote");
        boolean canVote = false;
        for(Voter voter: hasNotVotedList){
            if((voter.getStudentNumber() == studentNumber)){
                canVote = true;
                break;
            }
        }
        if(!canVote){
            System.out.println("Voter cannot vote");
            return false;
        }
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
                for (Voter voter: hasNotVotedList){
                    if(voter.getStudentNumber() == studentNumber){
                        hasNotVotedList.remove(voter);
                        if(hasNotVotedList.size() == 0){
                            isVoteFinished = true;
                            System.out.println("Vote is finished");
                            System.out.println("Total vote : \n" + totalVote);
                            return true;
                        }
                        break;
                    }
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

    public void copyVoterList(ArrayList<Voter> voterList) {
        hasNotVotedList = voterList;
        for(Voter voter: hasNotVotedList){
            System.out.println(voter.getStudentNumber());
        }
    }
    public ArrayList<Voter> getHasNotVotedList() {
        return hasNotVotedList;
    }

    public Map<Candidate, Integer> getWinners() {
        if (isVoteFinished) {
            Map<Candidate, Integer> winners = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : totalVote.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();
                for (Candidate candidate : candidateList) {
                    if (candidate.getRank() == key) {
                        winners.put(candidate, value);
                    }
                }
            }
            return winners;
        }
        return null;
    }
}
