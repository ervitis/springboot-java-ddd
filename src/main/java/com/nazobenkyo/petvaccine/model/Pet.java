package com.nazobenkyo.petvaccine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    private String id;

    @Indexed
    private String name;

    @Indexed
    private String ownerEmail;

    private String type;

    @LastModifiedDate
    private DateTime updated;

    @CreatedDate
    private DateTime created;
}
