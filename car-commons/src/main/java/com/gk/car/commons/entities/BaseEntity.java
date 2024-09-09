package com.gk.car.commons.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity implements Serializable {

  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "version")
  @Version
  private int version;

  @Column(name = "created_At")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column(name = "updated_At")
  @LastModifiedDate
  private LocalDateTime updatedAt;
}
