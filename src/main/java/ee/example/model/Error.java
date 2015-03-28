package ee.example.model;

import java.io.Serializable;

/**
 * Created by Vahur Kaar on 25.03.2015.
 */
public class Error implements Serializable {

    private String errorMessage;
    private String description;

    public Error(String errorMessage, String description) {
        this.errorMessage = errorMessage;
        this.description = description;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDescription() {
        return description;
    }

}
