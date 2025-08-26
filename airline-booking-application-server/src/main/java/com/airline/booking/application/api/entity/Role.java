package com.airline.booking.application.api.entity;

import com.airline.booking.application.api.entity.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Created 26/08/2025 - 08:07
 * @Package com.airline.booking.application.api.entity
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;
}
