package com.xion.reddit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment extends Auditable {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String body;
    @ManyToOne
    @NonNull
    private Link link;
}
