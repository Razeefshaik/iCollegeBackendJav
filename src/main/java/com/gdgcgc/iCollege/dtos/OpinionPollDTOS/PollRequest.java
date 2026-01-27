package com.gdgcgc.iCollege.dtos.OpinionPollDTOS;

import lombok.Data;

import java.util.List;

@Data
public class PollRequest {

    private String question;
    private List<String> options;
    private int durationHours;
    private boolean isAnonymous;


}
