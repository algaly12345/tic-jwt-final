package com.example.tic.controller;

import com.example.tic.dto.CreateTicketRequest;
import com.example.tic.dto.PersonResponse;
import com.example.tic.dto.TicketResponse;
import com.example.tic.exception.PersonExitRequest;
import com.example.tic.exception.PersonReturnRequest;
import com.example.tic.service.JwtService;
import com.example.tic.service.TicketService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final JwtService jwtService;

    public TicketController(TicketService ticketService, JwtService jwtService) {
        this.ticketService = ticketService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> create(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateTicketRequest req
    ) {
        String token = authHeader.replace("Bearer ", "");

        String userId = jwtService.extractClaim(token, c -> (String) c.get("userId"));
        String firstName = jwtService.extractClaim(token, c -> (String) c.get("firstName"));
        String lastName  = jwtService.extractClaim(token, c -> (String) c.get("lastName"));

        String userName = ((firstName == null ? "" : firstName) + " " + (lastName == null ? "" : lastName)).trim();

        return ResponseEntity.ok(ticketService.createTicket(req, userId, userName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicket(id));
    }

    @GetMapping("/{ticketId}/persons")
    public ResponseEntity<List<PersonResponse>> persons(@PathVariable Long ticketId) {
        return ResponseEntity.ok(ticketService.getPersonsByTicket(ticketId));
    }


    @PutMapping("/{ticketId}/persons/{personId}/exit")
    public ResponseEntity<PersonResponse> markExit(
            @PathVariable Long ticketId,
            @PathVariable Long personId,
            @RequestBody(required = false) PersonExitRequest req
    ) {
        return ResponseEntity.ok(ticketService.markExit(ticketId, personId, req));
    }

    @PutMapping("/{ticketId}/persons/{personId}/return")
    public ResponseEntity<PersonResponse> markReturn(
            @PathVariable Long ticketId,
            @PathVariable Long personId,
            @RequestBody(required = false) PersonReturnRequest req
    ) {
        return ResponseEntity.ok(ticketService.markReturn(ticketId, personId, req));
    }

}
