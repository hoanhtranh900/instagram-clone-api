package com.sangnk.core.entity.base;

public interface Deletable extends Updatable {

    public Long getIsDelete();

    public void setIsDelete(Long isDelete);
}
