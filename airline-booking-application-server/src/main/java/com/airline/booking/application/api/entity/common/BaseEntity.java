package com.airline.booking.application.api.entity.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.ocpsoft.prettytime.PrettyTime;

import java.time.LocalDateTime;

/**
 * @Created 26/08/2025 - 08:09
 * @Package com.airline.booking.application.api.entity.common
 * @Project airline-booking-application-server
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @UuidGenerator
    @Column(name = "public_id", nullable = false, updatable = false)
    private String publicId;


    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    //@LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    public String createdAtTimeAgo() {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(createdAt);
    }

    public String updatedAtTimeAgo() {
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(updatedAt);
    }
}
