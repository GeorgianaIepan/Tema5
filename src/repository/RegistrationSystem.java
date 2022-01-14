package repository;

import exception.DeleteKursFromLehrerException;
import exception.ExistException;
import exception.RegisterException;
import classe.Lehrer;
import classe.Person;
import classe.Student;
import classe.Kurs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class RegistrationSystem {
    private final KursRepository kursRepository;
    private final PersonRepository personRepository;
    private final StudentRepository studentRepository;
    private final LehrerRepository lehrerRepository;
    private final EnrolledRepository newRepository;

    /**
     * @throws SQLException
     */
    public RegistrationSystem() throws SQLException {
        this.kursRepository = new KursRepository();
        this.personRepository = new PersonRepository();
        this.studentRepository = new StudentRepository();
        this.lehrerRepository = new LehrerRepository();
        this.newRepository = new EnrolledRepository();
    }


    public KursRepository getKursRepository(){
        return this.kursRepository;
    }


    public PersonRepository getPersonRepository() {
        return personRepository;
    }


    public StudentRepository getStudentRepository(){
        return this.studentRepository;
    }


    public LehrerRepository getLehrerRepository(){
        return this.lehrerRepository;
    }


    public EnrolledRepository getNewRepository() {
        return newRepository;
    }

    /**
     *
     * @param KursID
     * @param StudentID
     * @return true, daca toate conditiile sunt respectate
     * @throws RegisterException
     * @throws SQLException
     *
     */
    public boolean register(long KursID, long StudentID) throws RegisterException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Kurs kurs = this.kursRepository.getOne(KursID);
        Student student = this.studentRepository.getOne(StudentID);

        if (kurs == null || student == null)
            throw new RegisterException(message + "Der Student und/oder die Kurs sind/ist nicht in der Liste.");

        if (this.kursRepository.enrolledStudents(kurs).size() > kurs.getMaxEnrollment())
            throw new RegisterException(message + "Keine freie Platze.");
        else if (this.studentRepository.credits(student) + kurs.getCredits() > 30)
            throw new RegisterException(message + "Anzahl von Credits ubersprungen.");
        else if (this.newRepository.getOne(kurs.getKursID(), student.getStudentID()))
            throw new RegisterException(message + "Der Student nimmt an dieser Kurs teil.");
        else
            this.newRepository.create(KursID, StudentID);

        return true;
    }

    /**
     *
     * @param KursId
     * @param StudentID
     *
     * @throws RegisterException
     * @throws SQLException
     */
    public void unregister(long KursId, long StudentID) throws RegisterException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Kurs kurs = this.kursRepository.getOne(KursId);
        Student student = this.studentRepository.getOne(StudentID);

        if (kurs == null || student == null)
            throw new RegisterException(message + "Der Student und/oder die Kurs sind/ist nicht in der Liste.");

        this.newRepository.delete(KursId, StudentID);
    }

    /**
     * @return locurile disponibile la un curs
     * @throws SQLException
     */
    public HashMap<Integer, Long> retrieveKursWithFreePlaces() throws SQLException {
        HashMap<Integer, Long> map = new HashMap<Integer, Long>();
        for (Kurs kurs: this.kursRepository.getAll()){
            map.put(kurs.getMaxEnrollment() - this.kursRepository.enrolledStudents(kurs).size(), kurs.getKursID());
        }

        return map;
    }

    /**
     * @param KursID
     * @return studentii inscrisi la un curs
     * @throws ExistException
     * @throws SQLException
     */
    public List<Long> retrieveStudentsEnrolledForKurs(long KursID) throws ExistException, SQLException {
        Kurs kurs = this.kursRepository.getOne(KursID);
        if (kurs == null){
            throw new ExistException("Die Kurs ist nicht in der Liste.");
        }

        return this.kursRepository.enrolledStudents(kurs);
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Kurs> getAllKurse() throws SQLException {
        List<Kurs> vorlesungList = new ArrayList<>();
        kursRepository.getAll()
                .forEach(kurs -> {
                    try {
                        vorlesungList
                                .add(new Kurs(kurs.getName(),
                                        kurs.getLehrer(),
                                        kurs.getKursID(),
                                        kurs.getMaxEnrollment(),
                                        kursRepository.enrolledStudents(kurs),
                                        kursRepository.getOne(kurs.getKursID()).getCredits()
                                ));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

        return vorlesungList;
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Person> getAllPersons() throws SQLException {
        List<Person> personList = new ArrayList<>();
        this.personRepository.getAll()
                .forEach(person -> personList
                        .add(new Person(person.getPersonID(),
                                person.getFirstName(),
                                person.getLastName())));
        return personList;
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        this.studentRepository.getAll()
                .forEach(student ->
                {
                    try {
                        studentList
                                .add(new Student(new Person(student.getPersonID(), student.getFirstName(), student.getLastName()),
                                        student.getStudentID(),
                                        this.studentRepository.credits(student),
                                        this.studentRepository.enrolledCourses(student)));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });

        return studentList;
    }

    /**
     * @return
     * @throws SQLException
     */
    public List<Lehrer> getAllLehrer() throws SQLException {
        List<Lehrer> lehrerList = new ArrayList<>();
        this.lehrerRepository.getAll()
                .forEach(lehrer ->
                {
                    try {
                        lehrerList
                                .add(new Lehrer(new Person(lehrer.getPersonID(), lehrer.getFirstName(), lehrer.getLastName()),
                                        lehrer.getLehrerID(),
                                        this.lehrerRepository.enrolledCourses(lehrer)));
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                });
        return lehrerList;
    }

    /**
     *
     * @param KursID
     * @param newCredit
     * @return
     * @throws RegisterException
     * @throws ExistException
     * @throws SQLException
     */
    public boolean changeCreditFromKurs(long KursID, int newCredit) throws RegisterException, ExistException, SQLException {
        String message = "Unerfüllte Bedingungen: ";
        Kurs kurs = this.kursRepository.getOne(KursID);
        if (kurs == null)
            throw new RegisterException(message + "Die Kurs ist nicht in der Liste.");

        Kurs newKurs = new Kurs(kurs.getName(),
                kurs.getLehrer(),
                kurs.getKursID(),
                kurs.getMaxEnrollment(),
                newCredit);
        this.kursRepository.update(newKurs);

        Student student;
        for (Long studentID: this.kursRepository.enrolledStudents(kurs)) {
            student = this.studentRepository.getOne(studentID);
            if (this.studentRepository.credits(student) > 30)
                unregister(KursID, studentID);

        }

        throw new ExistException("Die Anzahl der Credits wurden geandert.");
    }

    /**
     * @param LehrerID
     * @param KursID
     * @throws DeleteKursFromLehrerException
     * @throws RegisterException
     * @throws SQLException
     */
    public void deleteKursFromLehrer(long LehrerID, long KursID) throws DeleteKursFromLehrerException, RegisterException, SQLException {
        String message = "Unerfullte Bedingungen: ";
        Lehrer lehrer = this.lehrerRepository.getOne(LehrerID);
        Kurs kurs = this.kursRepository.getOne(KursID);

        if (lehrer == null || kurs == null)
            throw new DeleteKursFromLehrerException(message + "Der Lehrer und/oder die Kurs sind/ist nicht in der Liste.");

        if (kurs.getLehrer() != lehrer.getLehrerID())
            throw new DeleteKursFromLehrerException(message + "Der Lehrer unterrichtet nicht diese Kurs.");

        this.kursRepository.enrolledStudents(kurs)
                .forEach(student -> {
                    try {
                        unregister(kurs.getKursID(), student);
                    } catch (RegisterException | SQLException e) {
                        System.out.println(message + e.getMessage());
                    }
                });

        this.kursRepository.delete(kurs);
    }
}