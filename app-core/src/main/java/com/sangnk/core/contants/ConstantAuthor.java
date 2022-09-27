package com.sangnk.core.contants;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ConstantAuthor {

    public static boolean contain(String role, Authentication auth) {
        System.out.println("ConstantAuthor|contain(" + role + ")");
        List<String> user_root = new ArrayList<>(Arrays.asList(Constants.supper_admin.split(",")));
        if (user_root.contains(auth.getName())) return true;
        Collection<? extends GrantedAuthority> checkRight = auth.getAuthorities();
        boolean authorized = checkRight.contains(new SimpleGrantedAuthority(role));
        if (!authorized) {
            return false;
        }
        return true;
    }

    public interface Ckfinder { // quản lý file
        String view = "ROLE_SYSTEM_CKFINDER_VIEW";
        String add = "ROLE_SYSTEM_CKFINDER_ADD";
        String edit = "ROLE_SYSTEM_CKFINDER_EDIT";
        String delete = "ROLE_SYSTEM_CKFINDER_DELETE";
    }

    public interface Dept { // phòng ban
        String view = "ROLE_SYSTEM_DEPT_VIEW";
        String add = "ROLE_SYSTEM_DEPT_ADD";
        String edit = "ROLE_SYSTEM_DEPT_EDIT";
        String delete = "ROLE_SYSTEM_DEPT_DELETE";
    }

    public interface User { // người dùng
        String view = "ROLE_SYSTEM_USER_VIEW";
        String add = "ROLE_SYSTEM_USER_ADD";
        String edit = "ROLE_SYSTEM_USER_EDIT";
        String delete = "ROLE_SYSTEM_USER_DELETE";
    }

    public interface Group { // nhóm quyền
        String view = "ROLE_SYSTEM_GROUP_VIEW";
        String add = "ROLE_SYSTEM_GROUP_ADD";
        String edit = "ROLE_SYSTEM_GROUP_EDIT";
        String delete = "ROLE_SYSTEM_GROUP_DELETE";
    }

    public interface Authority { //Quyền
        String view = "ROLE_SYSTEM_AUTHORITY_VIEW";
        String edit = "ROLE_SYSTEM_AUTHORITY_EDIT";
        String add = "ROLE_SYSTEM_AUTHORITY_ADD";
        String delete = "ROLE_SYSTEM_AUTHORITY_DELETE";
    }

    public interface Right { //menu
        String view = "ROLE_SYSTEM_RIGHT_VIEW";
        String edit = "ROLE_SYSTEM_RIGHT_EDIT";
        String add = "ROLE_SYSTEM_RIGHT_ADD";
        String delete = "ROLE_SYSTEM_RIGHT_DELETE";
    }

    public interface ParamSystem { // Tham số hệ thống
        String view = "ROLE_SYSTEM_PARAM_VIEW";
        String edit = "ROLE_SYSTEM_PARAM_EDIT";
        String add = "ROLE_SYSTEM_PARAM_ADD";
        String delete = "ROLE_SYSTEM_PARAM_DELETE";
    }

    public interface Province { // tỉnh thành phố
        String view = "ROLE_SYSTEM_PROVINCE_VIEW";
        String edit = "ROLE_SYSTEM_PROVINCE_EDIT";
        String add = "ROLE_SYSTEM_PROVINCE_ADD";
        String delete = "ROLE_SYSTEM_PROVINCE_DELETE";
    }

    public interface District { // quận huyện
        String view = "ROLE_SYSTEM_DISTRICT_VIEW";
        String edit = "ROLE_SYSTEM_DISTRICT_EDIT";
        String add = "ROLE_SYSTEM_DISTRICT_ADD";
        String delete = "ROLE_SYSTEM_DISTRICT_DELETE";
    }

    public interface Commune { // phường xã
        String view = "ROLE_SYSTEM_COMMUNE_VIEW";
        String edit = "ROLE_SYSTEM_COMMUNE_EDIT";
        String add = "ROLE_SYSTEM_COMMUNE_ADD";
        String delete = "ROLE_SYSTEM_COMMUNE_DELETE";
    }

    public interface CmUnit { // đơn vị
        String view = "ROLE_SYSTEM_CMUNIT_VIEW";
        String edit = "ROLE_SYSTEM_CMUNIT_EDIT";
        String add = "ROLE_SYSTEM_CMUNIT_ADD";
        String delete = "ROLE_SYSTEM_CMUNIT_DELETE";
    }

    public interface AdministrativeSanction { // biện pháp xử lý hành chính
        String view = "ROLE_SYSTEM_ADMINISTRATIVESANCTION_VIEW";
        String edit = "ROLE_SYSTEM_ADMINISTRATIVESANCTION_EDIT";
        String add = "ROLE_SYSTEM_ADMINISTRATIVESANCTION_ADD";
        String delete = "ROLE_SYSTEM_ADMINISTRATIVESANCTION_DELETE";
    }

    public interface DocumentBasic { // văn bản căn cứ
        String view = "ROLE_SYSTEM_DOCUMENTBASIC_VIEW";
        String edit = "ROLE_SYSTEM_DOCUMENTBASIC_EDIT";
        String add = "ROLE_SYSTEM_DOCUMENTBASIC_ADD";
        String delete = "ROLE_SYSTEM_DOCUMENTBASIC_DELETE";
    }

    public interface ViolateCriminally { // vi phạm hình sự
        String view = "ROLE_SYSTEM_VIOLATECRIMINALLY_VIEW";
        String edit = "ROLE_SYSTEM_VIOLATECRIMINALLY_EDIT";
        String add = "ROLE_SYSTEM_VIOLATECRIMINALLY_ADD";
        String delete = "ROLE_SYSTEM_VIOLATECRIMINALLY_DELETE";
    }

    public interface ViolationField { // Lĩnh vực vi phạm
        String view = "ROLE_SYSTEM_VIOLATIONFIELD_VIEW";
        String edit = "ROLE_SYSTEM_VIOLATIONFIELD_EDIT";
        String add = "ROLE_SYSTEM_VIOLATIONFIELD_ADD";
        String delete = "ROLE_SYSTEM_VIOLATIONFIELD_DELETE";
    }

    public interface DecisionPenalize { //quyết định xử phạt
        String view = "ROLE_SYSTEM_DECISIONPENALIZE_VIEW";
        String edit = "ROLE_SYSTEM_DECISIONPENALIZE_EDIT";
        String add = "ROLE_SYSTEM_DECISIONPENALIZE_ADD";
        String delete = "ROLE_SYSTEM_DECISIONPENALIZE_DELETE";
    }

    public interface SuggestionProfile { //quyết định xử phạt
        String view = "ROLE_SYSTEM_SUGGESTIONPROFILE_VIEW";
        String edit = "ROLE_SYSTEM_SUGGESTIONPROFILE_EDIT";
        String add = "ROLE_SYSTEM_SUGGESTIONPROFILE_ADD";
        String delete = "ROLE_SYSTEM_SUGGESTIONPROFILE_DELETE";
    }

    public interface DecisionPenalizeAttachment { //quyết định xử phạt đính kèm
        String view = "ROLE_SYSTEM_DECISIONPENALIZEATTACHMENT_VIEW";
        String edit = "ROLE_SYSTEM_DECISIONPENALIZEATTACHMENT_EDIT";
        String add = "ROLE_SYSTEM_DECISIONPENALIZEATTACHMENT_ADD";
        String delete = "ROLE_SYSTEM_DECISIONPENALIZEATTACHMENT_DELETE";
    }
}
