package com.gk.car.commons.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
  @CreationTimestamp

  private Long createdAt;

  @Column(name = "updated_At")
  @UpdateTimestamp
  private Long updatedAt;
}
