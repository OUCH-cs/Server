package com.hy.ouch.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MessageResponse2<T> {

	private boolean success;

	private String code;

	private String message;

	private T data;
}
