package de.hitec.nhplus.model;

import javafx.beans.property.SimpleStringProperty;

/**
 * The Person class represents a general person with a first name and surname.
 * It is intended to be extended by more specific person types.
 */
public abstract class Person {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty surname;

    /**
     * Constructor to create a new Person object.
     *
     * @param firstName The first name of the person.
     * @param surname   The surname of the person.
     */
    public Person(String firstName, String surname) {
        this.firstName = new SimpleStringProperty(firstName);
        this.surname = new SimpleStringProperty(surname);
    }

    /**
     * Gets the first name of the person.
     *
     * @return the first name of the person.
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * Gets the first name property of the person.
     *
     * @return the first name property.
     */
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     *
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    /**
     * Gets the surname of the person.
     *
     * @return the surname of the person.
     */
    public String getSurname() {
        return surname.get();
    }

    /**
     * Gets the surname property of the person.
     *
     * @return the surname property.
     */
    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    /**
     * Sets the surname of the person.
     *
     * @param surname the surname to set.
     */
    public void setSurname(String surname) {
        this.surname.set(surname);
    }
}
