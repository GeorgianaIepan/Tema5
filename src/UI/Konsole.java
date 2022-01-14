package UI;

import controller.RegistrationSystemController;
import exception.DeleteKursFromLehrerException;
import exception.ExistException;
import exception.RegisterException;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Konsole {
    private RegistrationSystemController registrationSystem;


    public Konsole() {
        try {
            this.registrationSystem = new RegistrationSystemController();
        } catch (SQLException e) {
            System.out.println("Error");
        }
        System.out.println(
                        "\n 1:   Register " +
                        "\n 2:   Unregister " +
                        "\n-----------------------------------------------" +
                        "\n 3:   Print Liste Kurse " +
                        "\n 4:   Print Liste Personen " +
                        "\n 5:   Print Liste Studenten " +
                        "\n 6:   Print Liste Lehrer " +
                        "\n-----------------------------------------------" +
                        "\n 7:   Kurse mit freie Platze " +
                        "\n 8:   Print Liste Studenten von einer Kurs " +
                        "\n 9:   Credit Anzahl andern " +
                        "\n 10:  Kurs delete von Lehrer" +
                        "\n-----------------------------------------------" +
                        "\n 11:  Sort Liste Studenten " +
                        "\n 12:  Sort Liste Kurs " +
                        "\n 13:  Filter Liste Studenten " +
                        "\n 14:  Filter Liste Kurs " +
                        "\n-----------------------------------------------" +
                        "\n 15:  Add Kurs " +
                        "\n 16:  Add Person " +
                        "\n 17:  Add Student " +
                        "\n 18:  Add Lehrer" +
                        "\n-----------------------------------------------" +
                        "\n 0:   Exit\n");

        Scanner command = new Scanner(System.in);
        int commandInput;
        do {
            System.out.println("\nCommand: ");
            switch (commandInput = command.nextInt()) {
                case 0:
                    System.out.println("Exit");
                    break;
                case 1:
                    System.out.println("Register");
                    ui_getAllCourses();
                    ui_getAllStudents();
                    ui_register();
                    break;
                case 2:
                    System.out.println("Unregister");
                    ui_getAllCourses();
                    ui_getAllStudents();
                    ui_unregister();
                    break;
                case 3:
                    System.out.println("Print Liste Kurs");
                    ui_getAllCourses();
                    break;
                case 4:
                    System.out.println("Print Liste Personen");
                    ui_getAllPersons();
                    break;
                case 5:
                    System.out.println("Print Liste Studenten");
                    ui_getAllStudents();
                    break;
                case 6:
                    System.out.println("Print Liste Lehrer");
                    ui_getAllLehrer();
                    break;
                case 7:
                    System.out.println("Kurs mit freie Platze");
                    ui_retrieveKursWithFreePlaces();
                    break;
                case 8:
                    System.out.println("Print Liste Studenten von einer Kurs");
                    ui_getAllCourses();
                    ui_retrieveStudentsEnrolledForKurs();
                    break;
                case 9:
                    System.out.println("Change Credit Anzahl");
                    ui_getAllCourses();
                    ui_changeCreditFromKurs();
                    break;
                case 10:
                    System.out.println("Delete Kurs");
                    ui_getAllCourses();
                    ui_deleteKursFromLehrer();
                    break;
                case 11:
                    System.out.println("Sort Liste Studenten");
                    ui_sortListeStudenten();
                    break;
                case 12:
                    System.out.println("Sort Liste Kurs");
                    ui_sortListeKurs();
                    break;
                case 13:
                    System.out.println("Filter Liste Studenten");
                    ui_filterListeStudenten();
                    break;
                case 14:
                    System.out.println("Filter Liste Kurs");
                    ui_filterListeKurs();
                    break;
                case 15:
                    System.out.println("Add Kurs");
                    ui_addKurs();
                    break;
                case 16:
                    System.out.println("Add Person");
                    ui_addPerson();
                    break;
                case 17:
                    System.out.println("Add Student");
                    ui_addStudent();
                    break;
                case 18:
                    System.out.println("Add Lehrer");
                    ui_addLehrer();
                    break;
            }

        } while (commandInput != 0);
    }


    private void ui_register() {
        Scanner idVorlesung = new Scanner(System.in);
        Scanner idStudent = new Scanner(System.in);
        long KursID, StudentID;

        System.out.println("Kurs ID: ");
        KursID = idVorlesung.nextLong();
        System.out.println("Student ID: ");
        StudentID = idStudent.nextLong();
        try {
            registrationSystem.controller_register(KursID, StudentID);
        } catch (RegisterException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_unregister() {
        Scanner idKurs = new Scanner(System.in);
        Scanner idStudent = new Scanner(System.in);
        long KursID, StudentID;

        System.out.println("Kurs ID: ");
        KursID = idKurs.nextLong();
        System.out.println("Student ID: ");
        StudentID = idStudent.nextLong();
        try {
            registrationSystem.controller_unregister(KursID, StudentID);
        } catch (RegisterException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_getAllCourses() {
        try {
            this.registrationSystem.controller_getAllCourses()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    private void ui_getAllPersons() {
        try {
            this.registrationSystem.controller_getAllPersons()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_getAllStudents() {
        try {
            this.registrationSystem.controller_getAllStudents()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_getAllLehrer() {
        try {
            this.registrationSystem.controller_getAllLehrer()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_retrieveKursWithFreePlaces() {
        try {
            for (Map.Entry map : registrationSystem.controller_retrieveCoursesWithFreePlaces().entrySet()) {
                System.out.println(map.getKey() + " " + map.getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_retrieveStudentsEnrolledForKurs() {
        Scanner idKurs = new Scanner(System.in);
        long KursId;

        System.out.println("Kurs ID: ");
        KursId = idKurs.nextLong();
        try {
            for (Long student : registrationSystem.controller_retrieveStudentsEnrolledForACourse(KursId)) {
                System.out.println(student);
            }
        } catch (ExistException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_changeCreditFromKurs() {
        Scanner idVorlesung = new Scanner(System.in);
        Scanner credits = new Scanner(System.in);
        long KursId;
        int newCredit;

        System.out.println("Kurs ID: ");
        KursId = idVorlesung.nextLong();
        System.out.println("New Credits: ");
        newCredit = credits.nextInt();
        try {
            registrationSystem.controller_changeCreditFromACourse(KursId, newCredit);
        } catch (RegisterException | ExistException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_deleteKursFromLehrer() {
        Scanner idKurs = new Scanner(System.in);
        Scanner idLehrer = new Scanner(System.in);
        long KursId, LehrerId;

        System.out.println("Kurs ID: ");
        KursId = idKurs.nextLong();
        System.out.println("Lehrer ID: ");
        LehrerId = idLehrer.nextLong();
        try {
            registrationSystem.controller_deleteKursFromLehrer(LehrerId, KursId);
        } catch (DeleteKursFromLehrerException | RegisterException | SQLException e) {
            System.out.println(e.getMessage());
        }

        ui_getAllCourses();
    }


    private void ui_sortListeStudenten() {
        try {
            this.registrationSystem.controller_sortListeStudenten()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_sortListeKurs() {
        try {
            this.registrationSystem.controller_sortListeKurs()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_filterListeStudenten() {
        try {
            this.registrationSystem.controller_filterListeStudenten()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_filterListeKurs() {
        try {
            this.registrationSystem.controller_filterListeKurse()
                    .forEach(System.out::println);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_addPerson() {
        Scanner firstName = new Scanner(System.in);
        Scanner lastName = new Scanner(System.in);
        Scanner id = new Scanner(System.in);
        String FirstName, LastName;
        long Id;

        System.out.println("ID: ");
        Id = id.nextLong();
        System.out.println("firstName: ");
        FirstName = firstName.nextLine();
        System.out.println("lastName: ");
        LastName = lastName.nextLine();
        try {
            registrationSystem.controller_addPerson(Id, FirstName, LastName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private void ui_addStudent() {
        Scanner studentID = new Scanner(System.in);
        Scanner personID = new Scanner(System.in);
        long StudentID, PersonID;

        System.out.println("Person ID: ");
        PersonID = personID.nextLong();
        System.out.println("Student ID: ");
        StudentID = studentID.nextLong();

        try {
            registrationSystem.controller_addStudent(PersonID, StudentID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    private void ui_addLehrer() {
        Scanner lehrerID = new Scanner(System.in);
        Scanner personID = new Scanner(System.in);
        long LehrerID, PersonID;

        System.out.println("Person ID: ");
        PersonID = personID.nextLong();
        System.out.println("Student ID: ");
        LehrerID = lehrerID.nextLong();

        try {
            registrationSystem.controller_addLehrer(PersonID, LehrerID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    private void ui_addKurs() {
        Scanner name = new Scanner(System.in);
        Scanner idLehrer = new Scanner(System.in);
        Scanner idKurs = new Scanner(System.in);
        Scanner maxEnrollment = new Scanner(System.in);
        Scanner credits = new Scanner(System.in);
        String Name;
        long IdLehrer, IdKurs;
        int MaxEnrollment, Credits;

        System.out.println("Kurs ID: ");
        IdKurs = idKurs.nextLong();
        System.out.println("Kurs Name: ");
        Name = name.nextLine();
        System.out.println("Lahrer ID: ");
        IdLehrer = idLehrer.nextLong();
        System.out.println("Maximal Enrollment: ");
        MaxEnrollment = maxEnrollment.nextInt();
        System.out.println("Credit: ");
        Credits = credits.nextInt();
        try {
            registrationSystem.controller_addVorlesung(Name, IdLehrer, IdKurs, MaxEnrollment, Credits);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}