package com.travellog.travellog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "travel_log")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "user", "country", "travelPhotos" })
public class TravelLog extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "visit_start_date", nullable = false)
    private LocalDate visitStartDate;

    @Column(name = "visit_end_date", nullable = false)
    private LocalDate visitEndDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @JsonIgnore
    private Country country;

    @OneToMany(mappedBy = "travelLog")
    private List<TravelPhoto> travelPhotos;

    public void addPhoto(TravelPhoto photo) {
        if (this.travelPhotos == null) travelPhotos = new ArrayList<>();
        this.travelPhotos.add(photo);
        photo.setTravelLog(this);
    }
}
