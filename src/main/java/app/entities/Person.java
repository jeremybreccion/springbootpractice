package app.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * This is not a table and will be inherited by child classes
 */

// use MappedSuperclass to carry over the constraints
@MappedSuperclass
public class Person {
    @NotNull(message = "First name must not be null")
    @NotBlank(message = "First name must not be blank")
    @Size(max = 50, message = "First name must not exceed {max} characters")
    private String firstName;

    @NotNull(message = "Last name must not be null")
    @NotBlank(message = "Last name must not be blank")
    @Size(max = 30, message = "Last name must not exceed {max} characters")
    private String lastName;

    public Person() {
    }

    public Person(@NotNull @NotBlank @Size(max = 50) String firstName, @NotNull @NotBlank @Size(max = 30) String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}
