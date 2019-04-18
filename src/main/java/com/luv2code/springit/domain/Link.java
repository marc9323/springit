package com.luv2code.springit.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
public class Link {

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

}
