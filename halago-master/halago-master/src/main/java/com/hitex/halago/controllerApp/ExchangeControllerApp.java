package com.hitex.halago.controllerApp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitex.halago.model.request.BaseRequest;
import com.hitex.halago.model.response.ResponseBase;
import com.hitex.halago.model.response.ResponseData;
import com.hitex.halago.utils.DateUtils;
import com.hitex.halago.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController

public class ExchangeControllerApp {
//    @Autowired
//    ExchangeScoreRepository exchangeScoreRepository;
//    @Autowired
//    HistoryScoreRepository historyScoreRepository;
//    @Autowired
//    ExchangeScoreService exchangeScoreService;
//    @Autowired
//    HistoryScoreService historyScoreService;
//    @Autowired
//    private DeviceRepository deviceRepository;
    ResponseData responseData = new ResponseData(1, "loi", null);

//    @RequestMapping(value = "/creatOrderMoney", method = RequestMethod.POST)
//    public ResponseEntity<?> creatOrderMoney(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                System.out.println(jsonNode);
//                int idPersonal, scoreExchange, status, exchangeDate;
//                idPersonal = jsonNode.get("idPersonal").asInt();
//                scoreExchange = jsonNode.get("scoreExchange").asInt();
//                int score = historyScoreRepository.getScore(idPersonal);
//                if (scoreExchange > score) {
//                    responseData = new ResponseData(1, "không thể rút nhiều số tiền trong tài khoản", null);
//                } else {
//                    ExchangeScore exchangeScore1 = new ExchangeScore();
//                    idPersonal = exchangeScore1.setIdPersonal(jsonNode.get("idPersonal").asInt());
//                    scoreExchange = exchangeScore1.setScoreExchange(jsonNode.get("scoreExchange").asInt());
//                    exchangeScore1.setType(2);
//                    status = exchangeScore1.setStatus(0);
//                    exchangeDate = exchangeScore1.setExchangeDate(DateUtils.parseTimeStamp(DateUtils.getCurrentTime()));
//                    int scores = score - scoreExchange;
//                    historyScoreService.updateScore(scores, idPersonal);
//                    ExchangeScore exchangeScore2 = exchangeScoreService.sendScore(exchangeScore1);
//                    responseData = new ResponseData(0, "Bạn đã rút tiền thành công", new ResponseBase(null, exchangeScore2));
//                    NotificationPush notificationPush = new NotificationPush();
//                    List<Device> devices = new ArrayList<>();
//                    devices = deviceRepository.getFcmTokenByIdPersonal(idPersonal);
//                    notificationPush.FunctionPush(devices,"Bạn đã gửi lệnh rút tiền","Đang chờ duyệt");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
//
//    @RequestMapping(value = "/getListHistoryDeal", method = RequestMethod.POST)
//    public ResponseEntity<?> findHistory(InputStream inputStream) {
//        try {
//            BaseRequest baseRequest = RequestUtils.convertToBaseRequest(inputStream);
//            responseData = new ResponseData(1, "loi", null);
//            if (baseRequest.getWsRequest() != null) {
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode jsonNode = objectMapper.readTree(objectMapper.writeValueAsString(baseRequest.getWsRequest()));
//                if (jsonNode.get("idPersonal") != null) {
//                    int id = jsonNode.get("idPersonal").asInt();
//                    List<ExchangeScoreModel> exchangeScore = exchangeScoreService.findHistory(id);
//                    responseData = new ResponseData(0, "thanh cong", new ResponseBase(null, exchangeScore));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(responseData);
//    }
}
