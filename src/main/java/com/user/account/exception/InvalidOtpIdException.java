package com.user.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Otp Id is invalid")
public class InvalidOtpIdException extends RuntimeException{
}
