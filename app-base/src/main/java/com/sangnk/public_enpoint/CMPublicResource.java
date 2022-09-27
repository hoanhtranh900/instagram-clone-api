package com.sangnk.public_enpoint;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/public/v1/common", produces = MediaType.APPLICATION_JSON_VALUE)
@ApiOperation(value = "Các api chung không bắt buộc login hệ thống")
public class CMPublicResource {


}
