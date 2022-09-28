package com.sangnk.core.entity.base;

import java.util.Date;

public interface IVbProcess {
    String getId();

    void setId(String id);

    String getDocId();

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : DOC_ID ( VARCHAR2 )
    abstract void setDocId(String docId);

    String getSenderId();

    //--- DATABASE MAPPING : SENDER_ID ( VARCHAR2 )
    void setSenderId(String senderId);

    String getSenderName();

    //--- DATABASE MAPPING : SENDER_NAME ( VARCHAR2 )
    void setSenderName(String senderName);

    String getDeptSenderId();

    //--- DATABASE MAPPING : DEPT_SENDER_ID ( VARCHAR2 )
    void setDeptSenderId(String deptSenderId);

    String getDeptSenderName();

    //--- DATABASE MAPPING : DEPT_SENDER_NAME ( VARCHAR2 )
    void setDeptSenderName(String deptSenderName);

    String getRoleSenderId();

    //--- DATABASE MAPPING : ROLE_SENDER_ID ( VARCHAR2 )
    void setRoleSenderId(String roleSenderId);

    String getRoleSenderName();

    //--- DATABASE MAPPING : ROLE_SENDER_NAME ( VARCHAR2 )
    void setRoleSenderName(String roleSenderName);

    String getSenderPosName();

    //--- DATABASE MAPPING : SENDER_POS_NAME ( VARCHAR2 )
    void setSenderPosName(String senderPosName);

    //--- DATABASE MAPPING : SEND_TIME ( TIMESTAMP(6) )
    void setSendTime(Date sendTime);

    String getReceiverPositionId();

    void setReceiverPositionId(String receiverPostionId);

    String getReceiverId();

    //--- DATABASE MAPPING : RECEIVER_ID ( VARCHAR2 )
    void setReceiverId(String receiverId);

    String getReceiverName();

    //--- DATABASE MAPPING : RECEIVER_NAME ( VARCHAR2 )
    void setReceiverName(String receiverName);

    String getDeptReceiverId();

    //--- DATABASE MAPPING : DEPT_RECEIVER_ID ( VARCHAR2 )
    void setDeptReceiverId(String deptReceiverId);

    String getDeptReceiverName();

    //--- DATABASE MAPPING : DEPT_RECEIVER_NAME ( VARCHAR2 )
    void setDeptReceiverName(String deptReceiverName);

    String getRoleReceiverId();

    //--- DATABASE MAPPING : ROLE_RECEIVER_ID ( VARCHAR2 )
    void setRoleReceiverId(String roleReceiverId);

    String getRoleReceiverName();

    //--- DATABASE MAPPING : ROLE_RECEIVER_NAME ( VARCHAR2 )
    void setRoleReceiverName(String roleReceiverName);

    String getReceiverPosName();

    //--- DATABASE MAPPING : RECEIVER_POS_NAME ( VARCHAR2 )
    void setReceiverPosName(String receiverPosName);

    Long getProcessType();

    //--- DATABASE MAPPING : PROCESS_TYPE ( NUMBER )
    void setProcessType(Long processType);

    Long getStatus();

    //--- DATABASE MAPPING : STATUS ( NUMBER )
    void setStatus(Long status);

    String getFlowInstanceId();

    //--- DATABASE MAPPING : FLOW_INSTANCE_ID ( VARCHAR2 )
    void setFlowInstanceId(String flowInstanceId);

    String getStepId();

    //--- DATABASE MAPPING : STEP_ID ( VARCHAR2 )
    void setStepId(String stepId);

    String getParentId();

    //--- DATABASE MAPPING : PARENT_ID ( VARCHAR2 )
    void setParentId(String parentId);

    //--- DATABASE MAPPING : IS_READ ( NUMBER )
    void setIsRead(Long isRead);

    abstract Date getReadTime();

    //--- DATABASE MAPPING : READ_TIME ( TIMESTAMP(6) )
    void setReadTime(Date readTime);

    //--- DATABASE MAPPING : IS_COMPLETE ( NUMBER )
    void setIsComplete(Long isComplete);

    Date getCompleteTime();

    String getDelegateInfoId();

    //--- DATABASE MAPPING : DELEGATE_INFO_ID ( VARCHAR2 )
    void setDelegateInfoId(String delegateInfoId);

    String getReceiverUserDeptRoleId();

    void setReceiverUserDeptRoleId(String receiverUserDeptRoleId);
}
