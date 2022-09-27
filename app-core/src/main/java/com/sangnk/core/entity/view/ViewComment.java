package com.sangnk.core.entity.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.entity.Auditable;
import com.sangnk.core.entity.Post;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from COMMENTS")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViewComment extends Auditable implements Serializable {
    @Size(max = 100)
    @Column(name = "BODY")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private AdmUser user;
}
