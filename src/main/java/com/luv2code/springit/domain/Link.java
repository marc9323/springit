package com.luv2code.springit.domain;

import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//@Entity
//@Data
//@NoArgsConstructor
@Entity
@RequiredArgsConstructor
@Getter @Setter
@ToString
@NoArgsConstructor
public class Link extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String url;

    // comments, a Link has many comments, a list
    @OneToMany(mappedBy = "link")
    private List<Comment> comments = new ArrayList<>();

//    public Link(String title, String url){
//        this.title = title;
//        this.url = url;
//    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

}
