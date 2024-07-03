//package com.example.http.payload.response;
//
//import lombok.Data;
//import lombok.extern.log4j.Log4j2;
//import org.slf4j.helpers.MessageFormatter;
//import org.springframework.context.i18n.LocaleContextHolder;
//
//import java.text.MessageFormat;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//@Log4j2
//@Data
//public class MessageResponse {
//    private String message;
//
//    private String getMessage(String code, Locale language, Object... args) {
//        ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", language);
//        String message;
//        try {
//            message = resourceBundle.getString(code);
//            message = MessageFormat.format(message, args);
//        } catch (Exception ex) {
//            log.debug(ex.getMessage(), ex);
//            message = code;
//        }
//        return MessageFormatter.arrayFormat(message, args).getMessage();
//
//    }
//
//    public String getMessage(String code) {
//        return getMessage(code, LocaleContextHolder.getLocale(), null);
//    }
//
//    public MessageResponse(String message) {
//        this.message = this.getMessage(message);
//    }
//
//
//    public MessageResponse(String message, Object... args) {
//        this.message = getMessage(message, args);
//    }
//
//    public String getMessage(String code, Object... args) {
//        return this.getMessage(code, LocaleContextHolder.getLocale(), args);
//    }
////	private String getMessage(String code) {
////		return getMessage(code, LocaleContextHolder.getLocale());
////	}
////
////
////	public MessageResponse(String errorCode) {
////	    this.message = getMessage(errorCode);
////	  }
//
//}
