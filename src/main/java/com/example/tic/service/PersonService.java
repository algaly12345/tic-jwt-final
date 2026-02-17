package com.example.tic.service;

import com.example.tic.dto.PersonResponse;
import com.example.tic.dto.PersonWithTicketResponse;
import com.example.tic.dto.TicketSummaryDto;
import com.example.tic.entity.Person;
import com.example.tic.entity.PersonStatus;
import com.example.tic.entity.Ticket;
import com.example.tic.exception.NotFoundException;
import com.example.tic.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = repo;
    }

    // =========================
    // Basic Lists (PersonResponse)
    // =========================

    // 🔹 جميع الأشخاص
    public List<PersonResponse> getAll() {
        return repo.findAll().stream().map(this::mapToPersonResponse).toList();
    }

    // 🔹 جميع الأشخاص داخل تكت
    public List<PersonResponse> getByTicket(Long ticketId) {
        return repo.findByTicketId(ticketId).stream().map(this::mapToPersonResponse).toList();
    }

    // 🔹 حسب الحالة
    public List<PersonResponse> getByStatus(PersonStatus status) {
        return repo.findByStatus(status).stream().map(this::mapToPersonResponse).toList();
    }

    // 🔹 حسب التكت + الحالة
    public List<PersonResponse> getByTicketAndStatus(Long ticketId, PersonStatus status) {
        return repo.findByTicketIdAndStatus(ticketId, status).stream().map(this::mapToPersonResponse).toList();
    }

    // =========================
    // Person With Ticket (Single)
    // =========================
    public PersonWithTicketResponse getPersonWithTicket(Long personId) {

        Person p = repo.findWithTicketById(personId)
                .orElseThrow(() -> new NotFoundException("Person not found"));

        return mapToPersonWithTicketResponse(p);
    }

    // =========================
    // Person With Ticket (List)
    // =========================
    public List<PersonWithTicketResponse> getAllWithTicket() {
        // ✅ الأفضل تستخدم findAllWithTicket() لو عندك، لكن هنا نستخدم findAll + (EntityGraph في repo)
        return repo.findAll().stream().map(this::mapToPersonWithTicketResponse).toList();
    }

    // =========================
    // Mappers
    // =========================

    private PersonResponse mapToPersonResponse(Person p) {
        return new PersonResponse(
                p.getId(),
                p.getName(),
                p.getNationalId(),
                p.getMobileAccess(),
                p.getUserId(),
                p.getUserName(),
                p.getStatus(),
                p.getExitTime(),
                p.getReturnTime()
        );
    }

    private PersonWithTicketResponse mapToPersonWithTicketResponse(Person p) {
        Ticket t = p.getTicket();

        TicketSummaryDto ticketDto = (t == null) ? null : new TicketSummaryDto(
                t.getId(),
                t.getTitle(),
                t.getContent(),
                t.getCreatedAt()
        );

        return new PersonWithTicketResponse(
                p.getId(),
                p.getName(),
                p.getNationalId(),
                p.getMobileAccess(),
                p.getUserId(),
                p.getUserName(),
                p.getStatus(),
                p.getExitTime(),
                p.getReturnTime(),
                ticketDto
        );
    }
}
