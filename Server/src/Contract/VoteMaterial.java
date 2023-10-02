package Contract;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class VoteMaterial implements Serializable {
    DistantPrivate stubPrivate;
    String OTP;

    public VoteMaterial(DistantPrivate stubPrivate, String OTP) {
        this.stubPrivate = stubPrivate;
        this.OTP = OTP;
    }

}
