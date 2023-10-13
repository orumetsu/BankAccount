package com.multipolar.bootcamp.gateway.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.multipolar.bootcamp.gateway.dto.ErrorMessageDTO;
import com.multipolar.bootcamp.gateway.dto.AccountDTO;
import com.multipolar.bootcamp.gateway.kafka.AccessLog;
import com.multipolar.bootcamp.gateway.service.AccessLogService;
import com.multipolar.bootcamp.gateway.util.RestTemplateUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final String PRODUCT_URL = "http://localhost:8081/account";
    private final RestTemplateUtil restTemplateUtil;
    private final ObjectMapper objectMapper;
    private final AccessLogService accessLogService;

    public ApiController(RestTemplateUtil restTemplateUtil, ObjectMapper objectMapper, AccessLogService accessLogService, HttpServletRequest httpServletRequest) {
        this.restTemplateUtil = restTemplateUtil;
        this.objectMapper = objectMapper;
        this.accessLogService = accessLogService;
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<?> getAllAccounts(HttpServletRequest request) throws JsonProcessingException {
        try{
            ResponseEntity<?> response = restTemplateUtil.getList(PRODUCT_URL, new ParameterizedTypeReference<>() {});
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                            "GET",
                            PRODUCT_URL,
                            response.getStatusCodeValue(),
                            response.getBody().toString(),
                            request.getRemoteAddr(),
                            request.getHeader("User-Agent"),
                            LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "GET",
                    PRODUCT_URL,
                    ex.getRawStatusCode(),
                    ex.getResponseBodyAsString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PostMapping("/createAccount")
    public ResponseEntity<?> postAccount(@RequestBody AccountDTO accountDTO, HttpServletRequest request) throws JsonProcessingException {
        try {
            ResponseEntity<?> response = restTemplateUtil.post(PRODUCT_URL, accountDTO, AccountDTO.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "POST",
                    PRODUCT_URL,
                    response.getStatusCodeValue(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            List<ErrorMessageDTO> errorResponse = objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "POST",
                    PRODUCT_URL,
                    ex.getRawStatusCode(),
                    ex.getResponseBodyAsString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @GetMapping("/getAccount/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable String id, HttpServletRequest request) throws JsonProcessingException {
        //access accounts API and obtain account data
        String getByIdUrl = PRODUCT_URL + "/" + id;
        try{
            ResponseEntity<?> response = restTemplateUtil.get(getByIdUrl, AccountDTO.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "GET",
                    getByIdUrl,
                    response.getStatusCodeValue(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "GET",
                    getByIdUrl,
                    ex.getRawStatusCode(),
                    ex.getResponseBodyAsString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<?> updateAccountById(@PathVariable String id, @RequestBody AccountDTO accountDTO,
                                               HttpServletRequest request)
            throws JsonProcessingException {
        String getByIdUrl = PRODUCT_URL + "/" + id;
        try{
            ResponseEntity<?> response = restTemplateUtil.put(getByIdUrl, accountDTO, AccountDTO.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "PUT",
                    getByIdUrl,
                    response.getStatusCodeValue(),
                    response.getBody().toString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog success
            AccessLog accessLog = new AccessLog(
                    "PUT",
                    getByIdUrl,
                    ex.getRawStatusCode(),
                    ex.getResponseBodyAsString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }

    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable String id, HttpServletRequest request) throws JsonProcessingException {
        String getByIdUrl = PRODUCT_URL + "/" + id;
        try{
            ResponseEntity<?> response = restTemplateUtil.delete(getByIdUrl);
            //kirim AccessLog success
            String responseBody =
                    (response.getBody() != null) ? response.getBody().toString() :
                    "SUCESSFULLY DELETED USER: " + id;
            AccessLog accessLog = new AccessLog(
                    "DELETE",
                    getByIdUrl,
                    response.getStatusCodeValue(),
                    responseBody,
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }catch (HttpClientErrorException | HttpServerErrorException ex){
            List<ErrorMessageDTO> errorResponse =
                    objectMapper.readValue(ex.getResponseBodyAsString(), List.class);
            //kirim AccessLog fail
            AccessLog accessLog = new AccessLog(
                    "DELETE",
                    getByIdUrl,
                    ex.getRawStatusCode(),
                    ex.getResponseBodyAsString(),
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    LocalDateTime.now().toString()
            );
            accessLogService.logAccess(accessLog);
            return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
        }
    }
}
