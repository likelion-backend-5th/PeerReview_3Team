package com.example.project_1_seohyunkim;


import lombok.Getter;

@Getter
public enum ResponseMessage {
    CREATE_ITEM("등록이 완료되었습니다."),
    UPDATE_ITEM("물품이 수정되었습니다."),
    CREATE_IMAGE("이미지가 등록되었습니다."),
    DELETE_ITEM("물품을 삭제했습니다."),

    CREATE_COMMENT("댓글이 등록되었습니다."),
    UPDATE_COMMENT("댓글이 수정되었습니다."),
    CREATE_REPLY("댓글에 답변이 추가되었습니다."),
    DELETE_COMMENT("댓글을 삭제했습니다."),

    CREATE_PROPOSAL("구매 제안이 등록되었습니다."),
    UPDATE_PROPOSAL("제안이 수정되었습니다."),
    DELETE_PROPOSAL("제안을 삭제했습니다."),
    UPDATE_PROPOSAL_STATUS("제안의 상태가 변경되었습니다."),
    UPDATE_PROPOSAL_CONFIRMED("구매가 확정되었습니다."),

    REGISTER_USER("회원가입이 완료되었습니다."),
    REGISTER_FAIL_USER("회원가입이 실패했습니다."),

    LOGIN_USER("로그인 했습니다.");

    private final String message;
    private ResponseMessage(String message) {
        this.message = message;
    }
}
