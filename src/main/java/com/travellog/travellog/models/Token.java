package com.travellog.travellog.models;

import com.travellog.travellog.constants.TokenTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "token")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "user" })
public class Token extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(name = "token", unique = true)
    public String token;

    @Column(name = "token_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public TokenTypeEnum tokenType;

    @Column(name = "is_revoked")
    public boolean isRevoked;

    @Column(name = "is_expired")
    public boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;
}
