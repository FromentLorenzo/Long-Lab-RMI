package Contract;

import java.io.Serializable;

public class Voter implements Serializable {

    private int studentNumber;
    private String password;


    public Voter(int studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }


}
