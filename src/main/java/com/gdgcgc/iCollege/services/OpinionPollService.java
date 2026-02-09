package com.gdgcgc.iCollege.services;






import com.gdgcgc.iCollege.dtos.OpinionPollDTOS.PollRequest;
import com.gdgcgc.iCollege.dtos.OpinionPollDTOS.PollResponse;
import com.gdgcgc.iCollege.entities.OpinionPoll;

import com.gdgcgc.iCollege.entities.PollVote;
import com.gdgcgc.iCollege.repos.OpinionPollRepository;
import com.gdgcgc.iCollege.repos.PollVoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OpinionPollService {
    private final OpinionPollRepository pollRepository;
    private final PollVoteRepository voteRepository;

    public OpinionPollService(OpinionPollRepository pollRepository, PollVoteRepository pollVoteRepository) {
        this.pollRepository = pollRepository;
        this.voteRepository = pollVoteRepository;
    }

    public PollResponse createPoll(PollRequest request) {
        OpinionPoll poll = new OpinionPoll();
        poll.setQuestion(request.getQuestion());
        poll.setOptions(request.getOptions());
        poll.setAnonymous(request.isAnonymous());
        poll.setExpiresAt(LocalDateTime.now().plusHours(request.getDurationHours()));

        OpinionPoll saved = pollRepository.save(poll);
        return mapToResponse(saved);
    }

//    public PollResponse castVote(Long pollId, int optionIndex) {
//        OpinionPoll poll = pollRepository.findById(pollId)
//                .orElseThrow(() -> new RuntimeException("Poll not found"));
//
//        if (LocalDateTime.now().isAfter(poll.getExpiresAt())) {
//            throw new RuntimeException("Poll has expired");
//        }
//
//        // Update vote count for the chosen option
//        int currentVotes = poll.getVoteCounts().getOrDefault(optionIndex, 0);
//        poll.getVoteCounts().put(optionIndex, currentVotes + 1);
//
//        return mapToResponse(pollRepository.save(poll));
//    }

    public List<PollResponse> getOngoingPolls() {
        return pollRepository.findByExpiresAtAfter(LocalDateTime.now()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<PollResponse> getPastPolls() {
        return pollRepository.findByExpiresAtBefore(LocalDateTime.now()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public PollResponse castVote(Long pollId, int newOptionIndex, String scholarId){
        OpinionPoll poll= pollRepository.findById(pollId).orElseThrow(()->new RuntimeException("Poll not found"));

        if(LocalDateTime.now().isAfter(poll.getExpiresAt())){
            throw new RuntimeException("Poll has expired");
        }

        Optional<PollVote> existingVote= voteRepository.findByPollIdAndScholarId(pollId , scholarId);
        if(existingVote.isPresent()){

            PollVote vote= existingVote.get();
            int oldOption= vote.getOptionIndex();

            if(newOptionIndex==oldOption){
                return mapToResponse(poll);
            }


            int oldCount= poll.getVoteCounts().getOrDefault(oldOption, 0);
            poll.getVoteCounts().put(oldOption, Math.max(0,oldCount-1));

            vote.setOptionIndex(newOptionIndex);
            voteRepository.save(vote);



        }else {
            // First time voting logic
            PollVote newVote = new PollVote();
            newVote.setPollId(pollId);
            newVote.setScholarId(scholarId);
            newVote.setOptionIndex(newOptionIndex);
            voteRepository.save(newVote);
        }

        // 3. Increase count for the NEW option
        int newVotes = poll.getVoteCounts().getOrDefault(newOptionIndex, 0);
        poll.getVoteCounts().put(newOptionIndex, newVotes + 1);

        return mapToResponse(pollRepository.save(poll));
    }

    public List<PollResponse> getAllPolls() {
        return pollRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private PollResponse mapToResponse(OpinionPoll p) {
        return new PollResponse(
                p.getId(), p.getQuestion(), p.getOptions(),
                p.getExpiresAt(), p.isAnonymous(), p.getVoteCounts()
        );
    }
}