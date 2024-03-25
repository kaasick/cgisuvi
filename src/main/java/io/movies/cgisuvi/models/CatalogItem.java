package io.movies.cgisuvi.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movies")

public class CatalogItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "title", nullable = false)
    private String title;
    @Column (name = "genre")
    private String genre;
    @Column (name = "lang")
    private String language;
    @Column (name = "age")
    private String ageLimit;
    @Column (name = "time")
    private String startTime; // LocalDateTime?
}
