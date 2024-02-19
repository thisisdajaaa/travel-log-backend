package com.travellog.travellog.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "country")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = { "travelLogs" })
public class Country extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @Column(name = "code", nullable = false, length = 5)
    private String code;

    @OneToMany(mappedBy = "country")
    private List<TravelLog> travelLogs;
}
