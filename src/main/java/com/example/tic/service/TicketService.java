package com.example.tic.service;

import com.example.tic.dto.*;
import com.example.tic.entity.Person;
import com.example.tic.entity.PersonStatus;
import com.example.tic.entity.Ticket;
import com.example.tic.exception.NotFoundException;
import com.example.tic.exception.PersonExitRequest;
import com.example.tic.exception.PersonReturnRequest;
import com.example.tic.repository.PersonRepository;
import com.example.tic.repository.TicketRepository;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;


@Service
public class TicketService {

    private final TicketRepository ticketRepo;
    private final PersonRepository personRepo;

    public TicketService(TicketRepository ticketRepo, PersonRepository personRepo) {
        this.ticketRepo = ticketRepo;
        this.personRepo = personRepo;
    }

    public TicketResponse createTicket(CreateTicketRequest req, String userId, String userName) {

        if (req.persons() == null || req.persons().isEmpty()) {
            throw new IllegalArgumentException("Persons list is required");
        }

        Ticket ticket = new Ticket();
        ticket.setTitle(req.title());
        ticket.setContent(req.content());



        // link persons
        for (CreatePersonRequest p : req.persons()) {
            Person person = new Person();
            person.setName(p.name());
            person.setNationalId(p.nationalId());
            person.setMobileAccess(p.mobileAccess());
            person.setExitTime(p.exitTime());
            person.setReturnTime(p.returnTime());

            person.setUserId(userId);
            person.setUserName(userName);

            person.setTicket(ticket);
            ticket.getPersons().add(person);

            person.setStatus(PersonStatus.INSIDE);
            person.setExitTime(null);
            person.setReturnTime(null);

        }

        Ticket saved = ticketRepo.save(ticket); // cascade saves persons too
        return toResponse(saved);
    }

    public TicketResponse getTicket(Long id) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
        return toResponse(ticket);
    }

    public List<PersonResponse> getPersonsByTicket(Long ticketId) {
        if (!ticketRepo.existsById(ticketId)) {
            throw new NotFoundException("Ticket not found");
        }
        return personRepo.findByTicketId(ticketId).stream()
                .map(this::toPersonResponse)
                .toList();
    }

    private TicketResponse toResponse(Ticket t) {
        return new TicketResponse(
                t.getId(),
                t.getTitle(),
                t.getContent(),
                t.getCreatedAt(),
                t.getPersons().stream().map(this::toPersonResponse).toList()
        );
    }

    private PersonResponse toPersonResponse(Person p) {
        return new PersonResponse(
                p.getId(),
                p.getName(),
                p.getNationalId(),
                p.getMobileAccess(),
                p.getUserId(),
                p.getUserName(),
                p.getStatus(),     // ✅
                p.getExitTime(),
                p.getReturnTime()
        );

    }


    public PersonResponse markExit(Long ticketId, Long personId, PersonExitRequest req) {

        Person p = personRepo.findByIdAndTicketId(personId, ticketId)
                .orElseThrow(() -> new NotFoundException("Person not found in this ticket"));

        if (p.getExitTime() != null) {
            throw new IllegalStateException("Exit already recorded");
        }

        Instant exit = (req != null && req.exitTime() != null) ? req.exitTime() : Instant.now();
        p.setExitTime(exit);
        p.setReturnTime(null);

        p.setStatus(PersonStatus.OUTSIDE);

        Person saved = personRepo.save(p);
        return toPersonResponse(saved);
    }

    public PersonResponse markReturn(Long ticketId, Long personId, PersonReturnRequest req) {

        Person p = personRepo.findByIdAndTicketId(personId, ticketId)
                .orElseThrow(() -> new NotFoundException("Person not found in this ticket"));

        if (p.getExitTime() == null) {
            throw new IllegalStateException("Cannot set return time before exit time");
        }

        if (p.getReturnTime() != null) {
            throw new IllegalStateException("Return already recorded");
        }

        Instant ret = (req != null && req.returnTime() != null) ? req.returnTime() : Instant.now();

        if (ret.isBefore(p.getExitTime())) {
            throw new IllegalStateException("Return time cannot be before exit time");
        }

        p.setReturnTime(ret);
        p.setStatus(PersonStatus.RETURNED);

        Person saved = personRepo.save(p);
        return toPersonResponse(saved);
    }



}
