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

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voter voter = (Voter) o;
        return studentNumber == voter.studentNumber && Objects.equals(password, voter.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNumber, password);
    }
}
