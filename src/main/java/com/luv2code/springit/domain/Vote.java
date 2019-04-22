package com.luv2code.springit.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
public class Vote extends Auditable {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private short direction; // 1 or -1 up or down

    @NonNull
    @ManyToOne
    private Link link;




}
