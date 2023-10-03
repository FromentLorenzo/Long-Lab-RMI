package VotingServer;

import Contract.DistantPrivate;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrivateServer extends UnicastRemoteObject implements DistantPrivate, Serializable {
    protected PrivateServer(int port) throws RemoteException {
        super(port);
    }

    public void echo() {
        System.out.println("patate");
    }
}
