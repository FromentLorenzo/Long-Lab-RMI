package Contract;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface DistantPrivate extends java.rmi.Remote {
    public void echo() throws RemoteException;
    public boolean vote(Map<Integer, Integer> voteMap, int studentNumber, String Otp) throws RemoteException;
    public ArrayList<Voter> getHasNotVotedList() throws RemoteException;
}
