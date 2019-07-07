package com.aek.springdataspecification.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * <h2>Employee</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2019-07-07 15:44
 * <p>
 * Description:
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ManyToOne
    private Department department;

}
