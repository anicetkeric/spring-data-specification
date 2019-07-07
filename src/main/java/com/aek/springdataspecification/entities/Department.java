package com.aek.springdataspecification.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * <h2>Department</h2>
 *
 * @author aek - macintoshhd
 * createdAt : 2019-07-07 15:44
 * <p>
 * Description: Department class
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Department {

    @Id
    private Long id;
    private String name;
}
