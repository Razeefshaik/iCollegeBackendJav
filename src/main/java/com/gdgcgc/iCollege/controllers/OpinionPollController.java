package com.gdgcgc.iCollege.controllers;

import com.gdgcgc.iCollege.dtos.OpinionPollDTOS.PollRequest;
import com.gdgcgc.iCollege.dtos.OpinionPollDTOS.PollResponse;
import com.gdgcgc.iCollege.services.OpinionPollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/polls")
public class OpinionPollController {

    private final OpinionPollService pollService;

    public OpinionPollController(OpinionPollService pollService) {
        this.pollService = pollService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/launch")
    public ResponseEntity<PollResponse> launchPoll(@RequestBody PollRequest request) {
        return ResponseEntity.ok(pollService.createPoll(request));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PollResponse>> getAll() {
        return ResponseEntity.ok(pollService.getAllPolls());
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<PollResponse>> getOngoingPolls() {
        return ResponseEntity.ok(pollService.getOngoingPolls());
    }

    @GetMapping("/history")
    public ResponseEntity<List<PollResponse>> getPastPolls() {
        return ResponseEntity.ok(pollService.getPastPolls());
    }


    @PostMapping("/{id}/vote")
    public ResponseEntity<?> vote(@PathVariable Long id, @RequestParam int optionIndex, Principal principal) {
        try {
            // Get scholarId from the JWT token via Principal
            return ResponseEntity.ok(pollService.castVote(id, optionIndex, principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
