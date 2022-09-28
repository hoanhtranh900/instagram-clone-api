package com.sangnk.core.entity.base;

import java.util.Date;

public interface Updatable {

    public Long getUpdatorId();

    public void setUpdatorId(Long updatorId);

    public String getUpdatorName();

    public void setUpdatorName(String updatorName);

    public Date getUpdateTime();

    public void setUpdateTime(Date updateTime);
}
