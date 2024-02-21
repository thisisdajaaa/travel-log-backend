package com.travellog.travellog.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travellog.travellog.constants.GenderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "profile")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Transactional
@ToString(exclude = {"user"})
public class Profile extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name="first_name")
    private String firstName;

    @Column(name= "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GenderEnum sex;

    @Column()
    private String image;

    @Column(nullable=false, name="address_one")
    private String addressOne;

    @Column(name="address_two")
    private String addressTwo;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false, name="zip_code")
    private Integer zipCode;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToOne(optional = false)
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

}