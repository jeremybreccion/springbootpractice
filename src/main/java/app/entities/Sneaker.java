package app.entities;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * sneakers have style code. are they different from parent's product code??
 * if yes, then include attribute here
 *
 * if need to include brand name, should it be a separate entity or just string / enum ?
 * if enum, include as string for readability and (integer for performance ?)
 *
 * for sizing, i think it should be a separate entity BUT NO SERVICE.
 * since they are constants and will not change, manually fill them in MySQL table after entity creation
 * need also to consider the brand (e.g. Nike has different sizing than Adidas) and foot type? (e.g. men, women, grade school, toddler)
 *
 * if there is a sizing attribute and it references to a brand, do we need a separate attribute for brand or vice versa??
 *
 */
@Entity
@Table(name = "sneakers", uniqueConstraints = { @UniqueConstraint(name = "UNIQUE_PRODUCT_CODE", columnNames = "productCode")})
public class Sneaker extends Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sneaker_id")
    private long sneakerId;

    @Column(name = "colorway")
    @Size(max = 30, message = "Colorway must not exceed {max} characters")
    private String colorway;

    //style code

    //brand AND/OR sizing

    /**
     * i realized that sneaker brand and sizing is complicated. Study well before implementing
     * ask questions like, is sizing need to be included here if this entity is just for a sneaker model
     */

    public Sneaker() {
    }

    public Sneaker(@NotNull(message = "Product code must not be null")
                   @NotBlank(message = "Product code must not be blank")
                   @Size(max = 15, message = "Product code must not exceed {max} characters")
                           String productCode,
                   @NotNull(message = "Product name must not be null")
                   @NotBlank(message = "Product name must not be blank")
                   @Size(max = 80, message = "Product name must not exceed {max} characters")
                           String productName,
                   @Size(max = 255, message = "Product name must not exceed {max} characters")
                           String description, Date releaseDate,
                   @PositiveOrZero(message = "Retail price must be a positive number or 0")
                           double retailPrice,
                   @PositiveOrZero(message = "Actual price must be a positive number or 0")
                           double actualPrice,
                   @Size(max = 30, message = "Colorway must not exceed {max} characters")
                           String colorway) {
        super(productCode, productName, description, releaseDate, retailPrice, actualPrice);
        this.colorway = colorway;
    }

    public long getSneakerId() {
        return sneakerId;
    }

    public void setSneakerId(long sneakerId) {
        this.sneakerId = sneakerId;
    }

    public String getColorway() {
        return colorway;
    }

    public void setColorway(String colorway) {
        this.colorway = colorway;
    }
}
