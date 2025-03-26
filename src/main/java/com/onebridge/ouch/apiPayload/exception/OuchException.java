package com.onebridge.ouch.apiPayload.exception;

import com.onebridge.ouch.apiPayload.code.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OuchException extends RuntimeException {

	private final ErrorCode errorCode;
}
