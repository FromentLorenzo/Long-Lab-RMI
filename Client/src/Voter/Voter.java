package Voter;

import Contract.Candidate;
import Contract.DistantPublic;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public class Voter {


    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        DistantPublic publicServer = (DistantPublic) Naming.lookup("rmi://localhost:2001/PublicServer");
        List<Candidate> candidateList = (List<Candidate>) publicServer.getCandidates();
        for (int i = 0; i < candidateList.size(); i++) {
            System.out.println(candidateList.get(i).toString());
        }
    }
}
