package classe;

import java.util.ArrayList;
import java.util.List;

public class Kurs {
    private String name;
    private long lehrerID;
    private long kursID;
    private int maxEnrollment;
    private List<Long> studentsEnrolled;
    private int credits;

    /**
     * @param name
     * @param lehrerID
     * @param kursID
     * @param maxEnrollment
     * @param credits
     */
    public Kurs(String name, long lehrerID, long kursID, int maxEnrollment, int credits) {
        this.name = name;
        this.lehrerID = lehrerID;
        this.kursID = kursID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = new ArrayList<>();
        this.credits = credits;
    }

    public Kurs(String name, long lehrerID, long kursID, int maxEnrollment, List<Long> studentsEnrolled, int credits) {
        this.name = name;
        this.lehrerID = lehrerID;
        this.kursID = kursID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }


    public String getName() {
        return name;
    }

    public long getLehrer() {
        return lehrerID;
    }

    public long getKursID() {
        return kursID;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setLehrer(long lehrerID) {
        this.lehrerID = lehrerID;
    }

    public void setKursID(long kursID) { this.kursID = kursID; }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Kurs{" +
                "name='" + name + '\'' +
                ", lehrer=" + lehrerID +
                ", kursID=" + kursID +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }


}