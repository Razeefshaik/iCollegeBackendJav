package com.gdgcgc.iCollege.controllers.complaint;

import com.gdgcgc.iCollege.dtos.complaintDTOs.ComplaintRequest;
import com.gdgcgc.iCollege.services.complaint.ComplaintService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping("/create-complaint")
    public ResponseEntity<?> createComplaint(@RequestBody ComplaintRequest request, Principal principal) {
        try {
            return ResponseEntity.ok(complaintService.createComplaint(principal.getName(), request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-all-complaints")
    public ResponseEntity<?> getAllComplaints(
            @RequestParam(defaultValue = "0") int page, // Default to first page (0-index)
            @RequestParam(defaultValue = "10") int size // Default to 10 items
    ) {
        try {
            return ResponseEntity.ok(complaintService.getAllComplaints(page, size));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/my-complaints")
    public ResponseEntity<?> getMyComplaints(Principal principal) {
        try {
            return ResponseEntity.ok(complaintService.getMyComplaints(principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/get-complaints-by-id")
    public ResponseEntity<?> getComplaintById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(complaintService.getComplaintById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{complaintId}/upvote")
    public ResponseEntity<?> upvoteComplaint(@PathVariable Long complaintId, java.security.Principal principal) {
        try {
            return ResponseEntity.ok(complaintService.upvoteComplaint(complaintId, principal.getName()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
