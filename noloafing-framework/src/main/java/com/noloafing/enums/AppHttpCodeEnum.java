package com.noloafing.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(501,"用户名已存在"),
     PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "用户名不能为空"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    REQUIRE_PASSWORD(506,"密码不能为空"),
    CONTENT_NOT_NULL(507,"内容不能为空"),
    UPLOAD_TYPE_ERROR(508,"文件上传类型仅支持PNG/JPG/JPEG"),
    UPLOAD_FAIL(509,"文件上传失败"),
    REQUIRE_EMAIL(510,"邮箱不能为空"),
    PASSWORD_MAKE_SURE(511,"确认密码必须一致"),
    REQUIRE_NICKNAME(512,"昵称不能为空"),
    NICKNAME_EXIST(513,"昵称已存在"),
    NO_SPACE(514,"不能包含空格"),
    TOO_LONG(515,"内容过长"),
    INVALID_ID(516,"无效的ID"),
    REQUIRED_TITLE(517,"标题不能为空"),
    REQUIRED_TAG(518,"标签不能为空"),
    REQUIRED_SUMMARY(519,"摘要不能为空"),
    REQUIRED_CATEGORY(520,"分类不能为空"),
    REQUIRED_NAME(521,"名称不能为空"),
    REQUIRED_STATUS(522,"状态不能为空"),
    UPDATE_FAILED(600,"更新失败"),
    OPERATE_FAILED(601,"操作失败"),
    ;
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}