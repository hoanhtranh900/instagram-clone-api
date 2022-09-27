package com.sangnk.core.entity;

import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Auditable;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FOLLOW_REQUEST", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "follower_id",
                "following_id"
        })
})
@org.hibernate.annotations.Table(appliesTo = "FOLLOW_REQUEST", comment = "Table chấp nhận theo dõi")
public class FollowRequest extends Auditable {
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
