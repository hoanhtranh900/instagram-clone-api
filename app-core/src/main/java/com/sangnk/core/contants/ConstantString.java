package com.sangnk.core.contants;

import com.sangnk.core.dto.response.ConfigSystem;

import java.util.*;

/*
* ConstantString
* */
public class ConstantString {
    public static final Map<String, Object> map;

    static {
        Map<String, Object> all = new HashMap<>();
        all.put("STATUS", STATUS.lstAll);
        all.put("IS_DELETE", IS_DELETE.lstAll);

        List<ConfigSystem> ADM_USER_TYPE_USER = new ArrayList<>();
        for (ADM_USER_TYPE.Type str : ADM_USER_TYPE.Type.values()) {
            ADM_USER_TYPE_USER.add(
                    ConfigSystem.builder().name(str.getAction()).value(str.description).build()
            );
        }
        all.put("ADM_USER_TYPE_USER", ADM_USER_TYPE_USER);

        List<ConfigSystem> LST_PENALIZE_DECISION_STATUS = new ArrayList<>();
        for (PENALIZE_DECISION.Status str : PENALIZE_DECISION.Status.values()) {
            LST_PENALIZE_DECISION_STATUS.add(
                    ConfigSystem.builder().id(str.getAction()).value(str.description).build()
            );
        }
        all.put("PENALIZE_DECISION_STATUS", LST_PENALIZE_DECISION_STATUS);

        List<ConfigSystem> LST_FILE_ATTACHMENT = new ArrayList<>();
        for (FILE_ATTACHMENT.FILE str : FILE_ATTACHMENT.FILE.values()) {
            LST_FILE_ATTACHMENT.add(
                    ConfigSystem.builder()
                            .id(str.getObjectType())
                            .value(str.getStorageType())
                            .description(str.getDescription())
                            .build()
            );
        }
        all.put("FILE_ATTACHMENT", LST_FILE_ATTACHMENT);

        List<ConfigSystem> ADMINISTRATIVE_SANCTION_TYPE = new ArrayList<>();
        for (ADMINISTRATIVE_SANCTION.Type str : ADMINISTRATIVE_SANCTION.Type.values()) {
            ADMINISTRATIVE_SANCTION_TYPE.add(
                    ConfigSystem.builder().id(str.getAction()).value(str.description).build()
            );
        }
        all.put("ADMINISTRATIVE_SANCTION_TYPE", ADMINISTRATIVE_SANCTION_TYPE);

        List<ConfigSystem> ADMINISTRATIVE_SANCTION_METHOD_TYPE = new ArrayList<>();
        for (ADMINISTRATIVE_SANCTION.MethodType str : ADMINISTRATIVE_SANCTION.MethodType.values()) {
            ADMINISTRATIVE_SANCTION_METHOD_TYPE.add(
                    ConfigSystem.builder().id(str.getAction()).value(str.description).build()
            );
        }
        all.put("ADMINISTRATIVE_SANCTION_METHOD_TYPE", ADMINISTRATIVE_SANCTION_METHOD_TYPE);

        List<ConfigSystem> DECISION_PENALIZE_ATTACHMENT_TYPE = new ArrayList<>();
        for (DECISION_PENALIZE_ATTACHMENT.Type str : DECISION_PENALIZE_ATTACHMENT.Type.values()) {
            DECISION_PENALIZE_ATTACHMENT_TYPE.add(
                    ConfigSystem.builder().id(str.getAction()).value(str.description).build()
            );
        }
        all.put("DECISION_PENALIZE_ATTACHMENT_TYPE", DECISION_PENALIZE_ATTACHMENT_TYPE);

        List<ConfigSystem> CM_UNIT_LEVEL = new ArrayList<>();
        for (CM_UNIT.LEVEL str : CM_UNIT.LEVEL.values()) {
            CM_UNIT_LEVEL.add(
                    ConfigSystem.builder().id(str.getAction()).value(str.description).build()
            );
        }
        all.put("CM_UNIT_LEVEL", CM_UNIT_LEVEL);

        map = Collections.unmodifiableMap(all);
    }

    /*
    * Readme
    * cách tạo interface: tên_bảng: enum cột
    * */

    public interface STATUS {
        Long active = 0L;
        Long lock = 1L;
        Long expire = 2L;

        String active_str = "Đang hoạt động";
        String lock_str = "Đã khóa";
        String expire_str = "Đã hết hạn";

        List<ConfigSystem> lstAll = Arrays.asList(ConfigSystem.builder().id(active).value(active_str).build(), ConfigSystem.builder().id(lock).value(lock_str).build(), ConfigSystem.builder().id(expire).value(expire_str).build());
        static String getStatusStr(Long status) {
            if (status == null) {
                return null;
            }
            switch(status.intValue()) {
                case 0:
                    return STATUS.active_str;
                case 1:
                    return STATUS.lock_str;
                case 2:
                    return STATUS.expire_str;
            }
            return null;
        }
    }

    public interface IS_DELETE {
        Long active = 0L;
        Long delete = 1L;

        String active_str = "Đang sử dụng";
        String delete_str = "Đã xóa";

        List<ConfigSystem> lstAll = Arrays.asList(ConfigSystem.builder().id(active).value(active_str).build(), ConfigSystem.builder().id(delete).value(delete_str).build());
        static String getStatusStr(Long status) {
            if (status == null) {
                return null;
            }
            switch(status.intValue()) {
                case 0:
                    return IS_DELETE.active_str;
                case 1:
                    return IS_DELETE.delete_str;
            }
            return null;
        }
    }

    public interface ATTACH_DOCUMENT {
        Long ENCRYPTED = 1L;
        Long PDF = 1L;
        Long ORIGINAL = 1L;
    }

    public interface ATTACHMENT {
        enum FILE {
            DECISIONPENALIZEATTACHMENT_ATTACHMENT(1L, "FILE ĐÍNH KÈM QUYẾT ĐỊNH XỬ PHẠT ĐÍNH KÈM"),
            DECISIONPENALIZEATTACHMENT_DECISION(2L, "FILE QUYẾT ĐỊNH QUYẾT ĐỊNH XỬ PHẠT ĐÍNH KÈM"),

            ;

            public final Long objectType;
            public final String description;

            FILE(Long objectType, String description) {
                this.objectType = objectType;
                this.description = description;
            }

            public Long getObjectType() {
                return objectType;
            }
            public String getDescription() {
                return description;
            }
            public static String get(Long objectType) {
                for (ATTACHMENT.FILE str : ATTACHMENT.FILE.values()) {
                    if (str.getObjectType().equals(objectType)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }

    public interface ADM_USER_TYPE {
        enum Type {
            system("SYSTEM", "Người dùng quản trị hệ thống"),
            ;
            public final String action;
            public final String description;

            Type(String action, String description) {
                this.action = action;
                this.description = description;
            }
            public String getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }

            public static String get(String action) {
                for (Type str : Type.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }

    public interface PENALIZE_DECISION {
        enum Status {
            luu_tam(0L, "Lưu tạm"),
            ban_hanh(1L, "Ban hành"),
            thi_hanh(2L, "Thi hành"),
            hoan(3L, "Hoãn"),
            truy_cuu_tnbs(4L, "Truy cứu trách nhiệm hình sự"),
            huy_bo(5L, "Hủy bỏ"),
            tam_dinh_chi(6L, "Tạm đình chỉ"),
            cuong_che(7L, "Cưỡng chế"),
            done(8L, "Kết thúc")
            ;
            public final Long action;
            public final String description;

            Status(Long action, String description) {
                this.action = action;
                this.description = description;
            }
            public Long getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }

            public static String get(Long action) {
                for (Status str : Status.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }

    public interface FILE_ATTACHMENT {
        enum FILE {
            DecisionPenalizeAttachment_FILE_DINH_KEM(1L, "FILE_DINH_KEM", "File quyết định xử phạt đính kèm (đính chính, miễn giảm thi hành, bổ sung hủy bỏ 1 phần, khiếu nại khởi kiện)"),
            DecisionPenalizeAttachment_FILE_QUYET_DINH(1L, "FILE_QUYET_DINH", "File quyết định xử phạt đính kèm (đính chính, miễn giảm thi hành, bổ sung hủy bỏ 1 phần, khiếu nại khởi kiện)"),

            PenalizeBehavior_FILE_DINH_KEM(2L, "FILE_DINH_KEM", "File hành vi đính kèm (Thông tin hành vi vi phạm)"),

            PenalizeInfo_FILE_DINH_KEM(3L, "FILE_DINH_KEM", "File đính kèm (Thông tin xử phạt)"),
            PenalizeInfo_FILE_QUYET_DINH(3L, "FILE_QUYET_DINH", "File quyết định (Thông tin xử phạt)"),
            PenalizeInfo_FILE_DINH_KEM_KHAC(3L, "FILE_DINH_KEM_KHAC", "File đính kèm khác (Thông tin xử phạt)"),

            CmDocumentBasic_FILE_VAN_BAN(4L, "FILE_VAN_BAN_CAN_CU", "File văn bản căn cứ"),

            CmViolateCriminally_FILE_DINH_KEM(5L, "FILE_DINH_KEM", "File đính kèm (Danh mục vi phạm xử lý hình sự)"),
            ;

            public final Long objectType;
            private final String storageType;
            public final String description;
            FILE(Long objectType, String storageType, String description) {
                this.objectType = objectType;
                this.storageType = storageType;
                this.description = description;
            }
            public Long getObjectType() {
                return objectType;
            }
            public String getStorageType() {
                return storageType;
            }
            public String getDescription() {
                return description;
            }
            public static String get(Long objectType, String storageType) {
                for (FILE str : FILE.values()) {
                    if (str.getObjectType().equals(objectType) && str.getStorageType().equals(storageType)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }

    public interface ADMINISTRATIVE_SANCTION {
        enum Type {
            HINH_THUC(1L, "Hình thức xử lý hành chính"),
            BIEN_PHAP(2L, "Biện pháp xử lý hành chính"),
            ;
            public final Long action;
            public final String description;

            Type(Long action, String description) {
                this.action = action;
                this.description = description;
            }
            public Long getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }

            public static String get(Long action) {
                for (ADMINISTRATIVE_SANCTION.Type str : ADMINISTRATIVE_SANCTION.Type.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
        enum MethodType {
            CHINH(1L, "Chính"),
            BO_XUNG(2L, "Bổ sung"),
            CHINH_BO_XUNG(3L, "Chính, bổ sung"),
            ;
            public final Long action;
            public final String description;

            MethodType(Long action, String description) {
                this.action = action;
                this.description = description;
            }
            public Long getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }

            public static String get(Long action) {
                for (MethodType str : MethodType.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }

    public interface DECISION_PENALIZE_ATTACHMENT {
        enum Type {
            QUYET_DINH_DINH_CHINH(1L, "Quyết định đính chính"),
            QUYET_DINH_MIEN_GIAM_THI_HANH(2L, "Quyết định miễn giảm thi hành"),
            QUYET_DINH_BO_SUNG_HUY_BO(3L, "Quyết định bổ sung hủy bỏ 1 phần"),
            QUYET_DINH_KHIEU_NAI_KHOI_KIEN(4L, "Quyết định khiếu nại khởi kiện"),
            ;
            public final Long action;
            public final String description;

            Type(Long action, String description) {
                this.action = action;
                this.description = description;
            }
            public Long getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }

            public static String get(Long action) {
                for (Type str : Type.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }

    public interface CM_UNIT {
        enum LEVEL {
            TINH(1L, "Tỉnh/Thành phố"),
            HUYEN(2L, "Quận/Huyện"),
            XA(3L, "Phường/Xã"),
            ;
            public final Long action;
            public final String description;

            LEVEL(Long action, String description) {
                this.action = action;
                this.description = description;
            }
            public Long getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }
            public static String get(Long action) {
                for (LEVEL str : LEVEL.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
        enum TYPE {
            // lưu ý máp giá trị với bảng trong database
            BAN_HANH(1L, "Ban hành"),
            THU_TIEN(2L, "Thu tiền"),
            THI_HANH(3L, "Thi hành"),
            CUONG_CHE(4L, "Cưỡng chế"),
            PHOI_HOP(5L, "Phối hợp"),
            ;
            public final Long action;
            public final String description;

            TYPE(Long action, String description) {
                this.action = action;
                this.description = description;
            }
            public Long getAction() {
                return action;
            }
            public String getDescription() {
                return description;
            }
            public static String get(Long action) {
                for (TYPE str : TYPE.values()) {
                    if (str.getAction().equals(action)) {
                        return str.getDescription();
                    }
                }
                return null;
            }
        }
    }
}
