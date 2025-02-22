package com.hy.ouch.dto.mypage.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MypageUserInfoResponse {

	private String nickname;

	private String email;

	private Long languageId;
}
