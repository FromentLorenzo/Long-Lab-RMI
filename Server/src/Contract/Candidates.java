package Contract;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Candidates extends UnicastRemoteObject {
    int rank;
    String firstName;
    String lastName;

    public Candidates(int rank, String firstName, String lastName) throws RemoteException {
        super();
        this.rank = rank;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
