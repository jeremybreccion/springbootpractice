package app.entities;

import app.validators.CheckRetailPrice;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * if this class will be inherited by child entities (e.g. sneakers [main], apparel, etc), then there may be cases where product code
 * is unique to an entity but may appear in another entity. as far as i know, there is no check for duplicates in 2 or more tables
 *
 * PRICES ARE in PHP
 *
 * FOR NOW, product name is NOT unique
 */
@MappedSuperclass
public class Product {
    @NotNull(message = "Product code must not be null")
    @NotBlank(message = "Product code must not be blank")
    @Size(max = 15, message = "Product code must not exceed {max} characters")
    //maybe consider a pattern? if so, use @Pattern
    //declare as unique in child entity
    private String productCode;

    @NotNull(message = "Product name must not be null")
    @NotBlank(message = "Product name must not be blank")
    @Size(max = 80, message = "Product name must not exceed {max} characters")
    private String productName;

    // Product description can be null because it may be upcoming releases with no final details
    @Size(max = 255, message = "Product name must not exceed {max} characters")
    private String description;

    // Release Date can be null because it may be upcoming releases with no final date
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    //Retail Price can be null because it may be upcoming releases with no final price
    @PositiveOrZero(message = "Retail price must be a positive number or 0")
    private double retailPrice;

    //Actual price may be different from retail price in case of discounts
    @PositiveOrZero(message = "Actual price must be a positive number or 0")
    //custom validator that checks the retail price value before setting actual price (DO NOT USE FOR NOW)
    //@CheckRetailPrice(retailPriceField = "retailPrice")
    private double actualPrice;

    /**
     * add other attributes like picture (blob or image path), "isReleased" flag?? etc
     */

    public Product() {
    }

    public Product(@NotNull(message = "Product code must not be null")
                   @NotBlank(message = "Product code must not be blank")
                   @Size(max = 15, message = "Product code must not exceed {max} characters")
                           String productCode,
                   @NotNull(message = "Product name must not be null")
                   @NotBlank(message = "Product name must not be blank")
                   @Size(max = 80, message = "Product name must not exceed {max} characters")
                           String productName,
                   @Size(max = 255, message = "Product name must not exceed {max} characters")
                           String description,
                           Date releaseDate,
                   @Positive(message = "Retail price must be a positive number")
                           double retailPrice,
                   @Positive(message = "Actual price must be a positive number")
                           double actualPrice) {
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.releaseDate = releaseDate;
        this.retailPrice = retailPrice;
        this.actualPrice = actualPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) throws IllegalStateException {

        if (retailPrice == 0 && actualPrice != 0) throw new IllegalStateException("Must set retail price first before setting actual price");

        //in case that actual price must not be greater than retail price, put condition here and throw error if fail
        this.actualPrice = actualPrice;
    }

    public void setActualPriceAsRetailPrice() throws IllegalStateException {

        if (retailPrice == 0) throw new IllegalStateException("Must set retail price first before setting actual price");

        actualPrice = retailPrice;
    }

    /**
     * arbitrary discount mechanic (up to 75% only)
     * @param discount
     * @throws IllegalArgumentException if discount is not between 0 to 0.75
     */
    public void applyDiscount(double discount) throws IllegalArgumentException {
        //check
        if (discount < 0 || discount > 0.75) throw new IllegalArgumentException("Discount must range from 0.00 to 0.75");

        actualPrice *= 1 - discount;
    }


}
