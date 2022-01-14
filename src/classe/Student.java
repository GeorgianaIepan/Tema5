package classe;

import java.util.ArrayList;
import java.util.List;

public class Student extends classe.Person {
    private long studentID;
    private int totalCredits;
    private List<Long> enrolledCourses;

    /**
     * @param person
     * @param studentID
     */
    public Student(Person person, long studentID) {
        super(person.getPersonID(), person.getFirstName(), person.getLastName());
        this.studentID = studentID;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
    }

    public Student(Person person, long studentID, int totalCredits, List<Long> enrolledCourses) {
        super(person.getPersonID(), person.getFirstName(), person.getLastName());
        this.studentID = studentID;
        this.totalCredits = totalCredits;
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public Long getPersonID() {
        return super.getPersonID();
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    public long getStudentID() {
        return studentID;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }


    @Override
    public void setPersonID(Long personID) {
        super.setPersonID(personID);
    }

    @Override
    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void setEnrolledCourses(List<Long> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentID=" + studentID +
                ", totalCredits=" + totalCredits +
                ", enrolledCourses=" + enrolledCourses +
                "} " + super.toString();
    }

}