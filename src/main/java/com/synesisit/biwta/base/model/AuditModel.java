package com.synesisit.biwta.base.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"createdAt","updatedAt","createdBy", "updatedBy"},
        allowGetters = true
)
@Getter  @Setter
public abstract class AuditModel<U> implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at",nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at",nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    @CreatedBy
    @Column(name="created_by",updatable = false)
    private U createdBy;

    @LastModifiedBy
    @Column(name="updated_by")
    private U updatedBy;


}
