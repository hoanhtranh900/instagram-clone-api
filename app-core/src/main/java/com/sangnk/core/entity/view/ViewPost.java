package com.sangnk.core.entity.view;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sangnk.core.entity.Auditable;
import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from POST")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ViewPost extends Auditable implements Serializable {
    @Column(name = "DESCRIPTION")
    private String description;
}
