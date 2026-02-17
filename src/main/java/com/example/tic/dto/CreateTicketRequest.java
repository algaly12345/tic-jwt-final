package com.example.tic.dto;

import java.util.List;

public record CreateTicketRequest(
        String title,
        String content,
        List<CreatePersonRequest> persons
) {}
