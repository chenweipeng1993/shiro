package cn.itcast.ssm.mapper;

import java.util.List;

import cn.itcast.ssm.po.SysPermission;

public interface SysPermissionMapperCustom {
    
	//根据用户id获取权限菜单 
	List<SysPermission> findMenuByUserid(String userid)throws Exception;
	//根据用户id获取权限
	List<SysPermission> findPermissionByUserid(String userid)throws Exception;
}