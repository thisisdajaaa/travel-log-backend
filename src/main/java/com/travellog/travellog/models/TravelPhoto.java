package com.travellog.travellog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "travelPhoto")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "travelLog" })
public class TravelPhoto extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "travel_log_id", nullable = false)
    @JsonIgnore
    private TravelLog travelLog;
}
