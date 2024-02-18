package com.travellog.travellog.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "country")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Country extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;

    @Column(name = "Code", nullable = false, length = 5)
    private String code;
}
