package VotingServer;

import Contract.Candidate;
import Contract.DistantPublic;
import Contract.VoteMaterial;
import Contract.Voter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicServer extends UnicastRemoteObject implements DistantPublic {

    ArrayList<Candidate> candidateList;
    ArrayList<Voter> voterList;
    Map<Integer, String> temporaryPasswords;

    PrivateServer privateServer;

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

        temporaryPasswords = new HashMap<>(); // Initialisation de la Map

        privateServer = new PrivateServer();
    }

    @Override
    public List<Candidate> getCandidates() throws RemoteException {
        return this.candidateList;
    }

    @Override
    public VoteMaterial getVoteMaterial(Voter voter) throws RemoteException {
        System.out.println("Vote material called by : " + voter.getStudentNumber());
        for (Voter voterIndex : voterList) {
            if (voterIndex.equals(voter)) {
                String OTP = generateOTP(voter.getStudentNumber());

                // Associer le mot de passe temporaire à l'étudiant dans la Map
                temporaryPasswords.put(voter.getStudentNumber(), OTP);

                return new VoteMaterial(this.privateServer, OTP);
            }
        }
        return null;
    }

    private String generateOTP(int studentNumber) {
        return generatePassword();
    }

    public static String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            password.append(randomChar);
        }

        return password.toString();
    }
    @Override
    public String getOTPForStudent(int studentNumber) {
        return temporaryPasswords.get(studentNumber);
    }
}