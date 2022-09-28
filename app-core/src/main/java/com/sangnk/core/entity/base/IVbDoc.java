package com.sangnk.core.entity.base;

import java.util.Date;

public interface IVbDoc {

    String getId();

    void setId(String id);

    String getDocCode();

    void setDocCode(String docCode);

    Date getDocDate();

    //--- DATABASE MAPPING : DOC_DATE ( DATE )
    void setDocDate(Date docDate);

    String getSignerId();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerId(String signerId);

    String getSignerRoleId();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerRoleId(String signerRoleId);

    String getSignerRoleName();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerRoleName(String signerRoleName);

    String getSignerPosId();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerPosId(String signerPosId);

    String getSignerPosName();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerPosName(String signerPosName);

    String getSignerDeptName();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerDeptName(String signerDeptName);

    String getSignerDeptId();

    //--- DATABASE MAPPING : SIGNER_ID ( VARCHAR2 )
    void setSignerDeptId(String signerDeptId);

    String getSignerName();

    //--- DATABASE MAPPING : SIGNER_NAME ( VARCHAR2 )
    void setSignerName(String signerName);

    String getPublisherId();

    //--- DATABASE MAPPING : PUBLISHER_ID ( VARCHAR2 )
    void setPublisherId(String publisherId);

    String getPublisherName();

    //--- DATABASE MAPPING : PUBLISHER_NAME ( VARCHAR2 )
    void setPublisherName(String publisherName);

    String getDocTypeId();

    //--- DATABASE MAPPING : DOC_TYPE_ID ( VARCHAR2 )
    void setDocTypeId(String docTypeId);

    String getDocTypeName();

    //--- DATABASE MAPPING : DOC_TYPE_NAME ( VARCHAR2 )
    void setDocTypeName(String docTypeName);

    String getPriorityId();

    //--- DATABASE MAPPING : PRIORITY_ID ( VARCHAR2 )
    void setPriorityId(String priorityId);

    String getPriorityName();

    //--- DATABASE MAPPING : PRIORITY_NAME ( VARCHAR2 )
    void setPriorityName(String priorityName);

    String getSecurityId();

    //--- DATABASE MAPPING : SECURITY_ID ( VARCHAR2 )
    void setSecurityId(String securityId);

    String getSecurityName();

    //--- DATABASE MAPPING : SECURITY_NAME ( VARCHAR2 )
    void setSecurityName(String securityName);

    String getAreaId();

    //--- DATABASE MAPPING : AREA_ID ( VARCHAR2 )
    void setAreaId(String areaId);

    String getAreaName();

    //--- DATABASE MAPPING : AREA_NAME ( VARCHAR2 )
    void setAreaName(String areaName);

    Long getNumberOfPage();

    //--- DATABASE MAPPING : NUMBER_OF_PAGE ( NUMBER )
    void setNumberOfPage(Long numberOfPage);

    String getQuote();

    //--- DATABASE MAPPING : QUOTE ( VARCHAR2 )
    void setQuote(String quote);

    Long getIsLawDocument();

    //--- DATABASE MAPPING : IS_LAW_DOCUMENT ( NUMBER )
    void setIsLawDocument(Long isLawDocument);

    String getNote();

    //--- DATABASE MAPPING : NOTE ( VARCHAR2 )
    void setNote(String note);

    String getCopyFrom();

    //--- DATABASE MAPPING : COPY_FROM ( VARCHAR2 )
    void setCopyFrom(String copyFrom);

    String getCurrentVersion();

    //--- DATABASE MAPPING : CURRENT_VERSION ( VARCHAR2 )
    void setCurrentVersion(String currentVersion);

    Long getIsDirectionDocument();

    void setIsDirectionDocument(Long isDirectionDocument);

    String getDocTypeCode();

    void setDocTypeCode(String docTypeCode);

    String getSignerUserDeptRoleId();

    void setSignerUserDeptRoleId(String signerUserDeptRoleId);

    Long getSteeringType();

    void setSteeringType(Long steeringType);
}
