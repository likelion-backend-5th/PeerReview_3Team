package com.example.project_2_seohyunkim;

import lombok.Getter;

@Getter
public enum ResponseMessage {
    REGISTER_USER("회원가입이 완료되었습니다."),
    REGISTER_FAIL_USER("회원가입이 실패했습니다."),
    LOGIN_USER("로그인 했습니다."),
    CREATE_IMAGE("프로필 이미지를 저장했습니다."),

    CREATE_ARTICLE("피드를 생성했습니다."),
    UPDATE_ARTICLE("피드를 업데이트했습니다."),
    DELETE_ARTICLE("피드를 삭제했습니다."),

    CREATE_COMMENT("댓글을 등록했습니다."),
    UPDATE_COMMENT("댓글을 업데이트했습니다."),
    DELETE_COMMENT("댓글을 삭제했습니다."),

    CREATE_LIKE("좋아요를 등록했습니다."),
    DELETE_LIKE("좋아요를 취소했습니다.");


    private final String message;
    private ResponseMessage(String message) {
        this.message = message;
    }
}
