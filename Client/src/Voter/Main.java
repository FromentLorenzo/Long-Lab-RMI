package Voter;

import Contract.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {

        DistantPublic publicServer = (DistantPublic) Naming.lookup("rmi://localhost:2001/PublicServer");

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
            } catch (NumberFormatException e) {
                System.out.println("Erreur : Veuillez entrer un numéro d'étudiant valide (entier).");
                continue; // Redemande le numéro d'étudiant s'il n'est pas valide
            }

            System.out.println("Veuillez entrer votre mot de passe : ");
            String password = scanner.nextLine();
            Voter voter = new Voter(studentNumber, password);
            VoteMaterial voteMaterial = publicServer.getVoteMaterial(voter);

            if (voteMaterial == null) {
                System.out.println("Erreur : Numéro d'étudiant ou mot de passe incorrect.");
            } else {
                isAuthenticated = true;
                System.out.println("Authentification réussie");
                System.out.println("Voici votre mot de passe temporaire : " + voteMaterial.getOTP());
                DistantPrivate privateServer = (DistantPrivate) voteMaterial.getStubPrivate();
                privateServer.echo();
            }
        }

        System.out.println("Authentification réussie");
    }
}
