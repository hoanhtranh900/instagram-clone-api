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
@Table(name = "FOLLOWS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "follower_id",
                "following_id"
        })
})
@org.hibernate.annotations.Table(appliesTo = "FOLLOWS", comment = "Table lượt theo dõi")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Follow extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private AdmUser follower;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "following_id", nullable = false)
    private AdmUser following;
}
