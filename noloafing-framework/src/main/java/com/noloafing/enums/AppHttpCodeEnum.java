package com.noloafing.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    // 登录
    REQUIRE_PHONE_NUMBER(400,"手机号不能为空"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    USERNAME_EXIST(50001,"用户名已存在"),
     PHONENUMBER_EXIST(50002,"手机号已存在"),
    EMAIL_EXIST(50003, "邮箱已存在"),
    REQUIRE_USERNAME(50004, "用户名不能为空"),
    LOGIN_ERROR(50005,"用户名或密码错误"),
    REQUIRE_PASSWORD(50006,"密码不能为空"),
    REQUIRE_CHECK_CODE(50007,"请输入验证码"),
    CHECK_CODE_FAILED(50008,"验证码错误"),
    EMAIL_FORMAT_FAILED(50009,"请输入正确的邮箱"),
    PASSWORD_FORMAT_FAILED(500010,"密码长度9-16位且至少包含字母和特殊字符"),
    USERNAME_FORMAT_FAILED(500011,"用户名长度6-12位且不包含除'_','-'以外特殊符号"),
    NICKNAME_FORMAT_FAILED(500012,"昵称长度2-8位且不包含特殊符号"),
    REGISTER_INFO_ERROR(500013,"注册信息填写错误请检查"),
    SEND_EMAIL_CODE_FAILED(500015,"邮件验证码发送失败"),
    SEND_EMAIL_TIMES_MANY(500016,"请求验证码频繁,请24h后尝试"),
    EMAIL_CODE_EXPIRE(500017,"验证码已失效,请重试"),
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
    MENU_CONFLICT(602,"操作失败,与上级菜单冲突!"),
    MENU_INCLUDE_CHILD(602,"目标有子菜单，删除失败!"),
    REQUIRED_ROLE(10001,"角色选择不能为空!"),
    CAN_NOT_OPERATE_CUR_USER(20001,"无法操作当前用户!"),
    USER_NOT_EXIST(60001,"用户不存在"),
    REFRESH_TOKEN_EXPOSED(60002,"用户过期"),
    COMMENT_INCLUDE_BAD_COTENT(60003,"文本涉嫌不合规内容"),
    COMMENT_CONTENT_CHECKED_FALSE(60004,"文本内容审核失败"),
    ADMIN_CODE_REQUEST_MUCH(60005,"验证码请求次数过多，当天已无法发起")
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