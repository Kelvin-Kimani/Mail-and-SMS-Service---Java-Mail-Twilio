package com.scheduler.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String emailAddress;
    private String phoneNumber;


    /* Relationship */
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Meeting> meetings = new ArrayList<>();
}
