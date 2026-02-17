package com.example.tic.repository;



import com.example.tic.entity.Person;
import com.example.tic.entity.PersonStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByTicketId(Long ticketId);

    Optional<Person> findByIdAndTicketId(Long id, Long ticketId);

    // جميع الأشخاص
    @EntityGraph(attributePaths = {"ticket"})
    List<Person> findAll();

    // جميع الأشخاص داخل تكت معيّن


    // حسب الحالة
    List<Person> findByStatus(PersonStatus status);

    // حسب التكت + الحالة
    List<Person> findByTicketIdAndStatus(Long ticketId, PersonStatus status);


    @EntityGraph(attributePaths = {"ticket"})
    Optional<Person> findWithTicketById(Long id);

}
