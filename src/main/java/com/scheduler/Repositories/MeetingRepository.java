package com.scheduler.Repositories;

import com.scheduler.Models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Integer> {

    @Query("SELECT m FROM Meeting m WHERE m.meetingDate = CURRENT_DATE")
    List<Meeting> findMeetingsToday();
}
