package Contract;

import java.rmi.RemoteException;

public interface DistantPrivate extends java.rmi.Remote {
    public void echo() throws RemoteException;
}
