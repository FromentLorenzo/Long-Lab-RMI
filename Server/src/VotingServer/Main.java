package VotingServer;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class Main {


    public static void main(String[] args) throws RemoteException, MalformedURLException {
        PublicServer publicServer = new PublicServer(10001);
        java.rmi.registry.LocateRegistry.createRegistry(2001);
        java.rmi.Naming.rebind("rmi://localhost:2001/PublicServer", publicServer);
    }
}
