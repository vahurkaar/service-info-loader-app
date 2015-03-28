package ee.example.model;

import java.io.Serializable;

/**
 * Contact data object.
 *
 * @author Vahur Kaar
 * @since 25.03.2015
 */
public class Contact implements Serializable {

    private String name;
    private String phoneNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
