package com.scheduler.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Meeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int meetingId;
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate meetingDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime endTime;
    private int ownerId;

    /* Relationship */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "meetings_users",
            joinColumns = @JoinColumn(name = "meeting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private List<User> users = new ArrayList<>();
}
