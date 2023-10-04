package Voter;

import Contract.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        Voter voter = new Voter(1, "A");

        DistantPublic publicServer = (DistantPublic) Naming.lookup("rmi://localhost:2001/PublicServer");

        System.out.println("Voici la liste des candidats : ");
        List<Candidate> candidateList = publicServer.getCandidates();
        for (Candidate candidate : candidateList) {
            System.out.println(candidate);
        }

        VoteMaterial voteMaterial = publicServer.getVoteMaterial(voter);
        if (voteMaterial == null) {
            System.out.println("Erreur, le voteMaterial reçu est null");
            return;
        }
        System.out.println("Authentification réussie");
        System.out.println("Voici votre mot de passe temporaire : " + voteMaterial.getOTP());
        DistantPrivate privateServer = (DistantPrivate) voteMaterial.getStubPrivate();
        privateServer.echo();
    }
}
