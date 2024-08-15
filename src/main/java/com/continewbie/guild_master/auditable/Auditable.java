package com.continewbie.guild_master.auditable;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class Auditable {
    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @Setter
    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;



}
