package com.sangnk.core.entity.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Auditable;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from FOLLOWS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViewFollow extends Auditable implements Serializable {
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
