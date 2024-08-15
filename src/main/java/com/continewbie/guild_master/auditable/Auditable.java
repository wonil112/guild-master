package com.continewbie.guild_master.auditable;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreRemove;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, Auditable.DeleteListener.class})
public abstract class Auditable {
    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_AT")
    private LocalDateTime modifiedAt;

    // 위에 생성, 수정시간 처럼 스프링에서 어노테이션으로 제공하지 않아서
    // 삭제시간 필드를 만들어주고 이벤트 리스너 클래스를 생성해서 이벤트에 따른 행동을 만들어 주겠다.
    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    //    entity를 삭제할 때 이벤트 리스너인 DeleteListener 클래스를 생성
//    @PreRemove // JPA에서 엔티티가 삭제되기 전 시점에서 동작을 하게 하는 어노테이션 설정
//    Auditable 리스너 클래스가 불러지는 시점에 deletedAt을 현재 시각으로 바꿔주겠다.
//    생성한 DeleteListener 클래스 사용을 위해 @EntityListeners 에 작성한 Auditable.DeleteListener.class 추가.
    public static class DeleteListener{
        @PreRemove
        public void setDeletedAt(Auditable auditable){
            auditable.deletedAt = LocalDateTime.now();
        }
    }

}
