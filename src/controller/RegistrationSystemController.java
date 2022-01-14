package controller;

import exception.DeleteKursFromLehrerException;
import exception.ExistException;
import exception.RegisterException;
import classe.Lehrer;
import classe.Person;
import classe.Student;
import classe.Kurs;
import repository.RegistrationSystem;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationSystemController {
    private final RegistrationSystem registrationSystem;

    public RegistrationSystemController() throws SQLException {
        this.registrationSystem = new RegistrationSystem();
    }

    /**
     * @param KusrId
     * @param
     * @throws RegisterException
     * @throws SQLException
     */
    public void controller_register(long KusrId, long StudentID) throws RegisterException, SQLException {
        registrationSystem.register(KusrId, StudentID);
    }

    /**
     * @param KursID
     * @param StudentID
     * @throws RegisterException
     * @throws SQLException
     */
    public void controller_unregister(long KursID, long StudentID) throws RegisterException, SQLException {
        registrationSystem.unregister(KursID, StudentID);
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Kurs> controller_getAllCourses() throws SQLException {
        return registrationSystem.getAllKurse();
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Person> controller_getAllPersons() throws SQLException {
        return registrationSystem.getAllPersons();
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Student> controller_getAllStudents() throws SQLException {
        return registrationSystem.getAllStudents();
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Lehrer> controller_getAllLehrer() throws SQLException {
        return registrationSystem.getAllLehrer();
    }

    /**
     * @return
     * @throws SQLException
     */
    public HashMap<Integer, Long> controller_retrieveCoursesWithFreePlaces() throws SQLException {
        return registrationSystem.retrieveKursWithFreePlaces();
    }

    /**
     * @param kursID
     * @return
     * @throws ExistException
     * @throws SQLException
     */
    public List<Long> controller_retrieveStudentsEnrolledForACourse(long kursID) throws ExistException, SQLException {
        return registrationSystem.retrieveStudentsEnrolledForKurs(kursID);
    }

    /**
     * @param KursID
     * @param newCredit
     * @throws RegisterException
     * @throws ExistException
     * @throws SQLException
     */
    public void controller_changeCreditFromACourse(long KursID, int newCredit) throws RegisterException, ExistException, SQLException {
        registrationSystem.changeCreditFromKurs(KursID, newCredit);
    }

    /**
     * @param LehrerID
     * @param kursID
     * @throws DeleteKursFromLehrerException
     * @throws RegisterException
     * @throws SQLException
     */
    public void controller_deleteKursFromLehrer(long LehrerID, long kursID) throws DeleteKursFromLehrerException, RegisterException, SQLException {
        registrationSystem.deleteKursFromLehrer(LehrerID, kursID);
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Student> controller_sortListeStudenten() throws SQLException {
        Collections.sort(registrationSystem.getAllStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getPersonID().compareTo(o2.getPersonID());
            }
        });

        return registrationSystem.getAllStudents();
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Kurs> controller_sortListeKurs() throws SQLException {
        Collections.sort(registrationSystem.getAllKurse(), new Comparator<Kurs>() {
            @Override
            public int compare(Kurs o1, Kurs o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return registrationSystem.getAllKurse();
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Student> controller_filterListeStudenten() throws SQLException {
        List<Student> studentList = this.controller_getAllStudents().stream()
                .filter(student -> student.getEnrolledCourses().size() >= 2).collect(Collectors.toList());

        return studentList;
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Kurs> controller_filterListeKurse() throws SQLException {
        List<Kurs> kursList = this.controller_getAllCourses().stream()
                .filter(kurs -> kurs.getMaxEnrollment() > 30).collect(Collectors.toList());

        return kursList;
    }

    /**
     * @param personID
     * @param firstName
     * @param lastName
     * @throws SQLException
     */
    public void controller_addPerson(Long personID, String firstName, String lastName) throws SQLException {
        this.registrationSystem.getPersonRepository()
                .create(new Person(personID, firstName, lastName));
    }

    /**
     * @param PersonID
     * @param StudentID
     * @throws SQLException
     */
    public void controller_addStudent(Long PersonID, Long StudentID) throws SQLException {
        if (registrationSystem.getPersonRepository().getOne(PersonID) != null) {
            registrationSystem.getStudentRepository()
                    .create(new Student(registrationSystem.getPersonRepository().getOne(PersonID), StudentID));
        }
    }

    /**
     * @param PersonID
     * @param LehrerID
     * @throws SQLException
     */
    public void controller_addLehrer(Long PersonID, Long LehrerID) throws SQLException {
        if (registrationSystem.getLehrerRepository().getOne(PersonID) != null) {
            registrationSystem.getLehrerRepository()
                    .create(new Lehrer(registrationSystem.getPersonRepository().getOne(PersonID), LehrerID));
        }

    }

    /**
     * @param Name
     * @param IdLehrer
     * @param IdKurs
     * @param MaxEnrollment
     * @param Credits
     * @throws SQLException
     */
    public void controller_addVorlesung(String Name, Long IdLehrer, Long IdKurs, int MaxEnrollment, int Credits) throws SQLException {
        if (registrationSystem.getLehrerRepository().getOne(IdLehrer) != null) {
            registrationSystem.getKursRepository()
                    .create(new Kurs(Name, IdLehrer, IdKurs, MaxEnrollment, Credits));
        }
    }
}