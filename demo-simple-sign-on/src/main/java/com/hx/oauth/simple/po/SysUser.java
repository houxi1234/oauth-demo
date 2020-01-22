package com.hx.oauth.simple.po;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author houxi
 * @since 2019-10-23
 */
@Data
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户昵称
     */
    private String userName;
    private String password;


    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 出生日期
     */
    private String birthDate;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    private String status;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;


}
