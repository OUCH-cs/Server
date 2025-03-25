package com.onebridge.ouch.apiPayload.handler;

import com.onebridge.ouch.apiPayload.code.error.ErrorCode;
import com.onebridge.ouch.apiPayload.exception.OuchException;

public class TempHandler extends OuchException {

	public TempHandler(ErrorCode errorCode) {
		super(errorCode);
	}
}
