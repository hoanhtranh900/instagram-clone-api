package com.sangnk.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LIKES",  uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "post_id",
                "user_id"
        })
})
@org.hibernate.annotations.Table(appliesTo = "LIKES", comment = "Table lượt thích")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AdmUser user;
}
