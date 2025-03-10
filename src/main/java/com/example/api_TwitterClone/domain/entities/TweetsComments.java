package com.example.api_TwitterClone.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Entity
@Table(name = "comments")
public class TweetsComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text", nullable = true, length = 280)
    private String text;

    @Column(name = "banner", nullable = true)
    private String banner;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "tweet_id", nullable = false)
    private Tweets tweet;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "date", updatable = false)
    private Date date;

    @PrePersist
    protected void onCreate() { if(this.date == null) this.date = new Date(); }

    public TweetsComments() {}
}
