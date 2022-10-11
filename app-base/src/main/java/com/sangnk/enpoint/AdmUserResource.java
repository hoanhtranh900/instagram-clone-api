package com.sangnk.enpoint;

import com.sangnk.core.contants.ConstantAuthor;
import com.sangnk.core.contants.Constants;
import com.sangnk.core.controller.BaseControllerImpl;
import com.sangnk.core.dto.response.ResponseData;
import com.sangnk.core.dto.request.SearchForm;
import com.sangnk.core.entity.view.ViewAdmUser;
import com.sangnk.core.exception.BadRequestException;
import com.sangnk.core.exception.BaseException;
import com.sangnk.core.exception.Result;
import com.sangnk.core.exception.UnauthorizedException;
import com.sangnk.core.utils.JsonHelper;
import com.sangnk.core.entity.AdmUser;
import com.sangnk.core.utils.UtilsCommon;
import com.sangnk.service.AdmUserService;
import com.sangnk.service.impl.AdmUserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/system/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdmUserResource extends BaseControllerImpl<AdmUser, AdmUserServiceImpl> {

    //PreAuthorize -> hasAuthority -> dành cho những role đã bắt đầu ROLE_
    //PreAuthorize -> hasRole -> dành cho những role chưa bắt đầu ROLE_            recommended  hasRole('ROLE_SYSTEM_USER')
    //Secured -> là thuộc tính or giữa nhiều ROLE_

    protected AdmUserResource(AdmUserServiceImpl service, MessageSource messageSource) {
        super(service,  messageSource);
    }

    @Autowired private AdmUserService<AdmUser> admUserService;
    @Autowired private MessageSource messageSource;

    @ApiOperation(response = AdmUser.class, notes = Constants.NOTE_API + "empty_note", value = "Danh sách người dùng", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/getPage")
    public ResponseEntity<ResponseData> getPage(Pageable pageable, @ApiParam(value = Constants.NOTE_API_PAGEABLE) @RequestParam @Valid String search) {
        SearchForm searchObject = JsonHelper.jsonToObject(search, SearchForm.class);
        Page<ViewAdmUser> pages = admUserService.getPage(searchObject, pageable);
        return new ResponseEntity<>(new ResponseData<>(pages, Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser.class, notes = Constants.NOTE_API + "empty_note", value = "Thêm mới người dùng", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PostMapping
    public ResponseEntity<ResponseData> add(@RequestBody @Valid AdmUser user) throws BadRequestException {
        return new ResponseEntity<>(new ResponseData<>(admUserService.add(user), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser.class, notes = Constants.NOTE_API + "empty_note", value = "Lấy người dùng theo ID", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/initEdit/{userId}")
    public ResponseEntity<ResponseData> initEdit(@PathVariable("userId") @Valid Long userId) throws Throwable {
        return new ResponseEntity<>(new ResponseData<>(admUserService.get(userId).orElseThrow(() -> new BaseException(messageSource.getMessage("error.ENTITY_NOT_FOUND", new Object[]{"User"}, UtilsCommon.getLocale()))), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser.class, notes = Constants.NOTE_API + "empty_note", value = "Chỉnh sửa người dùng", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PutMapping
    public ResponseEntity<ResponseData> edit(@RequestBody AdmUser dto) throws Throwable {
        return new ResponseEntity<>(new ResponseData<>(admUserService.edit(dto), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser[].class, notes = Constants.NOTE_API + "empty_note", value = "Xóa người dùng", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PostMapping(path = "/delete")
    public ResponseEntity<ResponseData> deleteByIds(@RequestBody @Valid List<Long> ids) {
        return new ResponseEntity<>(new ResponseData<>(admUserService.deleteByIds(ids), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser[].class, notes = Constants.NOTE_API + "empty_note", value = "Khóa người dùng", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PostMapping(path = "/lock")
    public ResponseEntity<ResponseData> locks(@RequestBody @Valid List<Long> ids) {
        return new ResponseEntity<>(new ResponseData<>(admUserService.locks(ids), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser[].class, notes = Constants.NOTE_API + "empty_note", value = "Mở khóa người dùng", authorizations = {@Authorization(value = Constants.API_KEY)})
    @PostMapping(path = "/unlock")
    public ResponseEntity<ResponseData> unlocks(@RequestBody @Valid List<Long> ids) {
        return new ResponseEntity<>(new ResponseData<>(admUserService.unlocks(ids), Result.SUCCESS), HttpStatus.OK);
    }

    @ApiOperation(response = AdmUser.class, notes = Constants.NOTE_API + "empty_note", value = "Danh sách người dùng đã từng chat", authorizations = {@Authorization(value = Constants.API_KEY)})
    @GetMapping(value = "/getListChatRecent")
    public ResponseEntity<ResponseData> getListChatRecent() {
        List<ViewAdmUser> pages = admUserService.getListChatRecent();
        return new ResponseEntity<>(new ResponseData<>(pages, Result.SUCCESS), HttpStatus.OK);
    }
}
