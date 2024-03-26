package io.movies.cgisuvi.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movies")

public class CatalogItem {
    //Defining a object to represent a movie and all that goes with it.
    //Id is used to later assign to appropriate url for the seating.
    //startTime should be changed to LocalDateTime to accomodate different days of the week

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

    //This little workaround was done with the help of OpenAI's ChatGPT 4

    //@Convert(converter = JsonToListConverter.class) // Json to list converter
    //Doesnt compile without it
    @Transient  // Not directly mapped to a database column, because we expect a List of values or something similar.
    private List<Integer> takenSeats;

    // The seats will be comma-separated in a string file ...
    @Column(name = "takenSeats")
    private String takenSeatsString;
    //... which will get translated into a list

    //of course this could all be avoided by adding a dependency and creating a converter. But I've dug myself too far too late.

    public List<Integer> getTakenSeats() {
        // spilt and parse thhe values
        return Arrays.stream(takenSeatsString.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    public void setTakenSeats(List<Integer> takenSeats) {
        // set the int values into a big string (csv)
        this.takenSeatsString = takenSeats.stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
        this.takenSeats = takenSeats;
    }

}
//@Convert(converter = JsonToListConverter.class) // Json to list converter
//Doesnt compile without it
