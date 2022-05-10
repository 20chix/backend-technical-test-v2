package com.tui.proof.model;

import com.tui.proof.validator.OneOf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.util.Precision;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

import static java.lang.Math.toIntExact;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pilotes")
@Validated
public class Pilotes {
    private static final double PRICE_PER_PILOTES = 1.33;
    private static final Integer UPDATE_ORDER_THRESHOLD = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long orderNumber;

    @OneOf(value = {5, 10, 15}, message = "You can only order 5, 10 or 15 pilotes at the time")
    private Integer numberOfPilotes;
    @NotBlank(message = "Street is mandatory")
    private String street;
    @NotBlank(message = "Postcode is mandatory")
    private String postcode;
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotBlank(message = "Country is mandatory")
    private String country;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "Telephone is mandatory")
    private String telephone;

    @NotBlank(message = "Email must be valid")
    @Pattern(regexp = "^(.+)@(\\S+)$", message = "Email must be valid")
    private String email;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    private double orderTotal;

    @Transient
    private Boolean canUpdateOrder;
    @Transient
    private Boolean orderAgeInMinutes;


    @PrePersist
    protected void setOrderTotal() {
        this.orderTotal = Precision.round((numberOfPilotes * PRICE_PER_PILOTES), INTEGER_TWO);
    }

    public Boolean getCanUpdateOrder() {
        if  (getOrderAgeInMinutes() >= UPDATE_ORDER_THRESHOLD){
            return false;
        }
        return true;
    }

    public Integer getOrderAgeInMinutes() {
        if (nonNull(this.createdAt)){
            return toIntExact(MINUTES.between(this.createdAt, now()));
        }
        return INTEGER_ZERO;

    }
}
