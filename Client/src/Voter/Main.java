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
        List<Candidate> candidateList = publicServer.getCandidates();

        System.out.println("Voici la liste des candidats : ");
        candidateList = publicServer.getCandidates();
        for (Candidate candidate : candidateList) {
            System.out.println(candidate);
        }


        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        String input;

        while (!stop) {
            System.out.println("Choisissez une action : 'ca' pour afficher les candidats, 'au' pour s'authentifier, 'vo' pour voter, 're' pour afficher les résultats, 'q' pour quitter\n");
            input = scanner.nextLine();
            //impossible de faire des switch cases sur des strings

            if (input.startsWith("ca")) {
                System.out.println("Voici la liste des candidats : ");
                candidateList = publicServer.getCandidates();
                for (Candidate candidate : candidateList) {
                    System.out.println(candidate);
                }
            }
            else if (input.startsWith("au")) {
                Map<Voter, VoteMaterial> data = authentiacte(publicServer);
                if (data == null) {
                    voter = null;
                    continue;   //ramenne au début du while
                }
                for (Voter v : data.keySet()) {
                    voter = v;
                    voteMaterial = data.get(v);
                }
                privateServer = (DistantPrivate) voteMaterial.getStubPrivate();
                privateServer.echo();
            }
            else if (input.startsWith("vo")) {
                if (voter == null) {
                    System.out.println("Veuillez vous authentifier avant de voter");
                    continue;
                }
                if (vote(candidateList, voter, privateServer, voteMaterial)) {
                    voter = null;   //le votant a voté, il est maintenant déconnecté
                    System.out.println("Vous avez voté, vous êtes maintenant déconnecté. Si vous souhaitez modifier votre vote, veuillez vous reconnecter avant de renvoyer votre vote. Attention, cela n'est possible que si les votes sont encore ouverts");
                }
            }
            else if (input.startsWith("re")) {
                Map<Candidate, Integer> winners = publicServer.getWinners();
                if (winners == null) {
                    System.out.println("Les votes ne sont pas encore finis");
                    continue;
                }
                displayWinners(winners);
            }
            else if (input.startsWith("q")) {
                stop = true;
            }
        }



    }

    private static void displayWinners(Map<Candidate, Integer> winners) {
        if (winners.isEmpty()) {
            System.out.println("Aucun gagnant n'a été déterminé.");
        } else {
            System.out.println("Voici les résultats :");
            for (Map.Entry<Candidate, Integer> entry : winners.entrySet()) {
                Candidate candidate = entry.getKey();
                int voteCount = entry.getValue();
                System.out.println(candidate.getFirstName() + " " + candidate.getLastName() + " - Nombre de points : " + voteCount);
            }
        }
    }

    private static Map<Voter, VoteMaterial> authentiacte(DistantPublic publicServer) throws RemoteException {   //la map retournée ne contient qu'une seule paire de données
        Scanner scanner = new Scanner(System.in);
        Voter voter = null;
        VoteMaterial voteMaterial = null;

        boolean isAuthenticated = false;

        while (!isAuthenticated) {
            System.out.println("Veuillez entrer votre numéro d'étudiant : ");
            int studentNumber = 0;

            try {
                studentNumber = Integer.parseInt(scanner.nextLine());
                if(!(publicServer.checkCanVote(studentNumber))){

                }

            } catch (NumberFormatException | RemoteException e) {
                System.out.println("Erreur : Veuillez entrer un numéro d'étudiant valide (entier).");
                continue; // Redemande le numéro d'étudiant s'il n'est pas valide
            }

            System.out.println("Veuillez entrer votre mot de passe : ");
            String password = scanner.nextLine();
            voter = new Voter(studentNumber, password);
            voteMaterial = publicServer.getVoteMaterial(voter);

            if (voteMaterial == null) {
                System.out.println("Erreur : Numéro d'étudiant ou mot de passe incorrect.");
                return null;
            } else {
                isAuthenticated = true;
                System.out.println("Authentification réussie");
                System.out.println("Voici votre mot de passe temporaire : " + voteMaterial.getOTP());
            }
        }

        Map<Voter, VoteMaterial> returnMap = new HashMap<>();
        returnMap.put(voter, voteMaterial);
        return returnMap;
    }

    private static boolean vote(List<Candidate> candidateList, Voter voter, DistantPrivate privateServer, VoteMaterial voteMaterial) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        Map<Integer, Integer> voteMap = new HashMap<>();

        for (Candidate candidate : candidateList) {
            boolean voteIsValid = false;
            int points = -1;
            while (!voteIsValid) {
                System.out.println("Attribuez une note entre 0 et 3 pour le candidat : " + candidate + "\n");
                try {
                    points = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un chiffre");
                }
                if (points >=0 && points <=3)
                    voteIsValid = true;
            }
            voter.vote(candidate.getRank(), points);
            voteMap.put(candidate.getRank(), points);
        }
        if (privateServer.vote(voteMap,voter.getStudentNumber(),voteMaterial.getOTP())){
            System.out.println("Votre vote a bien été pris en compte");
            return true;
        }else{
            System.out.println("Erreur lors de l'envoi du vote");
        }
        return false;
    }

}
