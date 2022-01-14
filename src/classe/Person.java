package classe;

public class Person {
    private Long personID;
    private String firstName;
    private String lastName;

    public Person() {
    }

    /**
     * @param personID
     * @param firstName
     * @param lastName
     */

    public Person(Long personID, String firstName, String lastName) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setPersonID(Long personID) {
        this.personID = personID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personID=" + personID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

}