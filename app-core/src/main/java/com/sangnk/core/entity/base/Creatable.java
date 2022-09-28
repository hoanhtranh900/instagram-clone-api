package com.sangnk.core.entity.base;

import java.util.Date;

public interface Creatable extends Updatable {

    Long getCreatorId();

    void setCreatorId(Long creatorId);

    String getCreatorName();

    void setCreatorName(String creatorName);

    Date getCreateTime();

    void setCreateTime(Date createTime);
}
