package Contract;

import java.io.Serializable;
import java.util.Objects;

public class Voter implements Serializable {

    private int studentNumber;
    private String password;


    public Voter(int studentNumber, String password) {
        this.studentNumber = studentNumber;
        this.password = password;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public boolean matchPassword(String password) {
        return Objects.equals(this.password, password);
    }
}
