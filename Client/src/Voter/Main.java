package Voter;

import Contract.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {

        DistantPublic publicServer = (DistantPublic) Naming.lookup("rmi://localhost:2001/PublicServer");
        DistantPrivate privateServer = null;
        VoteMaterial voteMaterial = null;
        Voter voter= null;

        System.out.println("Voici la liste des candidats : ");
        List<Candidate> candidateList = publicServer.getCandidates();
        for (Candidate candidate : candidateList) {
            System.out.println(candidate);
        }
        Scanner scanner = new Scanner(System.in);
        boolean isAuthenticated = false;

        while (!isAuthenticated) {
            System.out.println("Veuillez entrer votre numéro d'étudiant : ");
            int studentNumber = 0;

            try {
                studentNumber = Integer.parseInt(scanner.nextLine());
                if(!(publicServer.checkCanVote(studentNumber))){

                }

            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un numéro d'étudiant valide (entier).");
                continue; // Redemande le numéro d'étudiant s'il n'est pas valide
            }

            System.out.println("Veuillez entrer votre mot de passe : ");
            String password = scanner.nextLine();
            voter = new Voter(studentNumber, password);
            voteMaterial = publicServer.getVoteMaterial(voter);

            if (voteMaterial == null) {
                System.out.println("Erreur : Numéro d'étudiant ou mot de passe incorrect.");
            } else {
                isAuthenticated = true;
                System.out.println("Authentification réussie");
                System.out.println("Voici votre mot de passe temporaire : " + voteMaterial.getOTP());
                privateServer = (DistantPrivate) voteMaterial.getStubPrivate();
                privateServer.echo();
            }
        }

        System.out.println("Authentification réussie");

        Map<Integer, Integer> voteMap = new HashMap<>();
        for (Candidate candidate : candidateList) {
            boolean voteIsValid = false;
            int points = -1;
            while (!voteIsValid) {
                System.out.println("Attribuez une note entre 0 et 3 pour le candidat : " + candidate + "\n");
                points = Integer.parseInt(scanner.nextLine());
                if (points >=0 && points <=3)
                    voteIsValid = true;
            }
            voter.vote(candidate.getRank(), points);
            voteMap.put(candidate.getRank(), points);
        }
        if (privateServer.vote(voteMap,voter.getStudentNumber(),voteMaterial.getOTP())){
            System.out.println("Votre vote a bien été pris en compte");
        }else{
            System.out.println("Erreur lors de l'envoi du vote");
        }

        Map<Candidate, Integer> winners = publicServer.getWinners();
        try {
            Thread.sleep(5000);

        } catch (InterruptedException e) {
        }

        if (winners == null) {
            System.out.println("Les votes ne sont pas encore finis");
            return;
        }
        displayOfWinners(winners);


    }

    private static void displayOfWinners(Map<Candidate, Integer> winners) {
        if (winners.isEmpty()) {
            System.out.println("Aucun gagnant n'a été déterminé.");
        } else {
            System.out.println("Voici les résultats :");
            for (Map.Entry<Candidate, Integer> entry : winners.entrySet()) {
                Candidate candidate = entry.getKey();
                int voteCount = entry.getValue();
                System.out.println(candidate.getFirstName() + " - Nombre de points : " + voteCount);
            }
        }
    }

}
