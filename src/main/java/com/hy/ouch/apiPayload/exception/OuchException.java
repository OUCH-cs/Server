package com.hy.ouch.apiPayload.exception;

import com.hy.ouch.apiPayload.code.error.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OuchException extends RuntimeException {

	private final ErrorCode errorCode;
}
