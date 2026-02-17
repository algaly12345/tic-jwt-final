package com.example.tic.controller;

import com.example.tic.dto.PersonResponse;
import com.example.tic.dto.PersonWithTicketResponse;
import com.example.tic.entity.PersonStatus;
import com.example.tic.service.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    // ✅ جميع الأشخاص
    @GetMapping
    public List<PersonResponse> all() {
        return service.getAll();
    }

    // ✅ جميع الأشخاص داخل تكت
    @GetMapping("/ticket/{ticketId}")
    public List<PersonResponse> byTicket(@PathVariable Long ticketId) {
        return service.getByTicket(ticketId);
    }

    // ✅ حسب الحالة
    @GetMapping("/status/{status}")
    public List<PersonResponse> byStatus(@PathVariable PersonStatus status) {
        return service.getByStatus(status);
    }

    // ✅ حسب التكت + الحالة
    @GetMapping("/ticket/{ticketId}/status/{status}")
    public List<PersonResponse> byTicketAndStatus(
            @PathVariable Long ticketId,
            @PathVariable PersonStatus status
    ) {
        return service.getByTicketAndStatus(ticketId, status);
    }


    // ✅ LIST: جميع الأشخاص مع تفاصيل التكت
    @GetMapping("/with-ticket")
    public List<PersonWithTicketResponse> allWithTicket() {
        return service.getAllWithTicket();
    }
}
