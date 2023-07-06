package com.hitex.halago.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.config.Constant;
import com.hitex.halago.model.ContactCustomer;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.repository.ContactCustomerRepository;
import com.hitex.halago.service.ContactCustomerService;
import com.hitex.halago.utils.RequestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@RestController
public class ContactCustomerController {

    Logger logger = LoggerFactory.getLogger(ContactCustomerController.class);
    @Autowired
    ContactCustomerRepository contactCustomerRepository;
    @Autowired
    ContactCustomerService contactCustomerService;

    ResponseData responseData = new ResponseData(1, "Chưa có dữ liệu", null);

    @RequestMapping(value = "/getListContactCustomer", method = RequestMethod.POST)
    public ResponseEntity<?> getListCampaign(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
            if (baseRequest.getWsRequest() != null) {
                Integer pageSize = jsonNode.get("pageSize").asInt();
                Integer pageNumber = jsonNode.get("pageNumber").asInt();
                List<ContactCustomer> contactCustomers = contactCustomerService.getListContactCustomer(pageSize, pageNumber);
                ResponseBase responseBase = new ResponseBase();
                responseBase.setSiRes(null);
                responseBase.setData(contactCustomers);
                responseBase.setTotal(contactCustomerRepository.totalContact());
                responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", responseBase);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/findContactCustomerById", method = RequestMethod.POST)
    public ResponseEntity<?> findCampaignById(InputStream inputStream) {
        try {
            responseData = new ResponseData(1, "Không có dữ liệu trả về", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                if (jsonNode.get("id") != null) {
                    int id = jsonNode.get("id").asInt();
                    ContactCustomer contactCustomer = contactCustomerService.findCustomerContact(id);
                    responseData = new ResponseData(Constant.SUCCESS, "Tìm kiếm thành công", new ResponseBase(null, contactCustomer));
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e.getCause());
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }

    @RequestMapping(value = "/insertContactCustomer", method = RequestMethod.POST)
    public ResponseEntity<?> insertCampaign(InputStream inputStream) {
        try {
            responseData = new ResponseData(Constant.FAILED, "Thêm liên hệ khách hàng thất bại", null);
            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
            if (baseRequest.getWsRequest() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
                ContactCustomer contactCustomer = new ContactCustomer();
                contactCustomer.setName(jsonNode.get("name").asText());
                contactCustomer.setPhone(jsonNode.get("phone").asText());
                contactCustomer.setEmail(jsonNode.get("email").asText());
                if (!jsonNode.has("note")) {
                    contactCustomer.setNote("");
                } else {
                    contactCustomer.setNote(jsonNode.get("note").asText());
                }
                contactCustomer.setCreated(new Date());
                contactCustomerRepository.save(contactCustomer);
                responseData = new ResponseData(Constant.SUCCESS, "Thêm liên hệ khách hàng thành công", new ResponseBase(null, contactCustomer));
            } else {
                responseData = new ResponseData(Constant.FAILED, "Dữ liệu đầu vào không hợp lệ", null);
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responseData);
    }
}
