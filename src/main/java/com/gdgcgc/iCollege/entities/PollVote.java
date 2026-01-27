package com.gdgcgc.iCollege.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data


@Table(name="poll_votes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"poll_id", "scholarId"})
})
public class PollVote {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;


    @Column(name="poll_id")
    private  Long pollId;

    private String scholarId;
    private int optionIndex;

}
