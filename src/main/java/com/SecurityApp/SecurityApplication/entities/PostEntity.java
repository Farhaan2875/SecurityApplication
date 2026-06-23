package com.SecurityApp.SecurityApplication.entities;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "Posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Audited //need to write to audit the entity //creates addition tables in db (not recommended by companies)
public class PostEntity extends AuditableEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @NotAudited
    private String description;


    //can use these hooks to customize the behavior of entity upon db methods
    //can also use to create custom auditing and not depend on Entity Listener at all
//    @PrePersist
//    void beforeSve(){
//
//    }
//    @PreUpdate
//    void beforeUpate(){
//
//    }
//    @PreRemove
//    void beforeDelete(){
//
//    }


}
