package com.luv2code.springit.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

//@Entity
//@Data
//@NoArgsConstructor
@Entity
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String body;

    // a comment has one link
    @ManyToOne
    @NonNull
    private Link link;

//    public Comment(String body, Link link) {
//        this.body = body;
//        this.link = link;
//    }

}
