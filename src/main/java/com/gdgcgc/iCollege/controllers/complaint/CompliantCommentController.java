package com.gdgcgc.iCollege.controllers.complaint;


import com.gdgcgc.iCollege.dtos.complaintDTOs.ComplaintCommentRequest;
import com.gdgcgc.iCollege.services.complaint.ComplaintCommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/complaint/{complaintId}/comments")
public class CompliantCommentController {

    private final ComplaintCommentService complaintCommentService;

    public CompliantCommentController(ComplaintCommentService complaintCommentService) {
        this.complaintCommentService = complaintCommentService;
    }


    @PostMapping
    public ResponseEntity<?> addComment(@PathVariable Long complaintId, @RequestBody ComplaintCommentRequest request){

        try{

           return ResponseEntity.ok(complaintCommentService.addComment(complaintId, request));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Adding comment failed "+e.getMessage());
        }

    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable Long complaintId){

        try {


            return ResponseEntity.ok(complaintCommentService.getComments(complaintId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Getting comments failed "+e.getMessage());
        }
    }
}
