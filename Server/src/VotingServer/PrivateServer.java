package VotingServer;

import Contract.DistantPrivate;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrivateServer extends UnicastRemoteObject implements DistantPrivate, Serializable {
    protected PrivateServer() throws RemoteException {
        super();
    }

    public void echo() {
        System.out.println("patate");
    }
}
