package com.hx.oauth.simple.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hx.oauth.simple.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author houxi
 * @since 2019-10-23
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {


    SysUser findByPhoneNumber(String phoneNumber);

    SysUser findByUserName(String userName);

}
