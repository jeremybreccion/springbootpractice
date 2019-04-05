package app.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * set combination of first name and last name as unique
 * set email as unique
 */

@Entity
@Table(name = "customers",
        uniqueConstraints = {
            @UniqueConstraint(name = "UNIQUE_FULL_NAME", columnNames = { "firstName", "lastName" }),
            @UniqueConstraint(name = "UNIQUE_EMAIL", columnNames = "email") })
public class Customer extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "shipping_address")
    @Size(max = 200, message = "Shipping address must not exceed {max} characters")
    private String shippingAddress;

    @Column(name = "email")
    @Email(message = "Email must be in correct format")
    @NotNull(message = "Email must not be null")
    @NotBlank(message = "Email must not be blank")
    @Size(max = 30, message = "Email must not exceed {max} characters")
    private String email;

    @Column(name = "points")
    @ColumnDefault("0")
    @PositiveOrZero(message = "points must be greater than or equal to 0")
    private double points;

    @Column(name = "is_subscribed")
    @ColumnDefault("false")
    private boolean subscribed;

    //include mobile number string? here

    public Customer() {
    }

    public Customer(@NotNull @NotBlank @Size(max = 50) String firstName,
                    @NotNull @NotBlank @Size(max = 30) String lastName,
                    @Email @NotNull @NotBlank @Size(max = 30) String email) {
        super(firstName, lastName);
        this.email = email;
        //default values not working!
        //set to 0
        this.points = 0;
        //set to false
        this.subscribed = false;
    }

    public Customer(@NotNull @NotBlank @Size(max = 50) String firstName,
                    @NotNull @NotBlank @Size(max = 30) String lastName,
                    @Size(max = 200) String shippingAddress,
                    @Email @NotNull @NotBlank @Size(max = 30) String email,
                    float points,
                    boolean isSubscribed) {
        super(firstName, lastName);
        this.shippingAddress = shippingAddress;
        this.email = email;
        this.points = points;
        this.subscribed = isSubscribed;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    /**
     * arbitrary point mechanic
     * @param amount
     * @throws IllegalArgumentException if argument is less than 1000
     */
    public void increasePoints(double amount) throws IllegalArgumentException {

        if (amount < 1000) {
            throw new IllegalArgumentException("Amount must be at least 1000");
        } else {
            this.points += (amount >= 1000 && amount < 15000) ? amount / 1000 + 5 :
                          (amount >= 15000 && amount < 30000) ? amount / 800 + 3 :
                          amount / 500;
        }
    }

    /**
     *
     * @param points
     */
    public void decreasePoints(double points) {

        //commented because there is @PositiveOrZero validation on "points" attribute
        //if (this.points - points < 0) throw new UnsupportedOperationException("Stored points must be sufficient");

        this.points -= points;
    }
}
