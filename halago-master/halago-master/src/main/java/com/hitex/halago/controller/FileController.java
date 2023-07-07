package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.security.JwtService;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

@RestController
public class FileController {
    Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private JwtService jwtService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Upload File thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                String role = jwtService.getUsernameFromToken(baseRequest.getToken());
                if (Constant.BRAND.equals(role) || Constant.ADMIN.equals(role) || Constant.INFLUENCER.equals(role) || Constant.STAFF.equals(role)) {
                    String fileData = jsonNode.get("fileData").asText();
                    String fileName = jsonNode.get("fileName").asText();
                    byte[] fileByte;
                    fileByte = Base64.getDecoder().decode(fileData);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                    String fd = simpleDateFormat.format(new Date());
                    String pathFile = fd + "/" + fileName.replaceAll(" ", "_");
                    String locationPath = Constant.IMAGE_CAMPAIGN_DIRECTION + pathFile;
                    String locationUrl = Constant.IMAGE_CAMPAIGN_DOMAIN + pathFile;
                    FileOutputStream fileOutputStream = FileUtils.openOutputStream(new File(locationPath));
                    IOUtils.write(fileByte, fileOutputStream);
                    IOUtils.closeQuietly(fileOutputStream);
                    responseData = new ResponseData(Constant.SUCCESS, "Upload file thành công", new ResponseBase(null, locationUrl));
                } else {
                    responseData = new ResponseData(Constant.FAILED, "Bạn không có quyền upfile", null);
                }
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
