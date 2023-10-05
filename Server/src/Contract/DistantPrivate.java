package Contract;

import java.rmi.RemoteException;
import java.util.Map;

public interface DistantPrivate extends java.rmi.Remote {
    public void echo() throws RemoteException;
    public boolean vote(Map<Integer, Integer> voteMap) throws RemoteException;
}
