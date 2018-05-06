package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.po.SysPermission;

public interface SysService {
	
	//根据用户id获取权限 
	public List<SysPermission> findSysPermissionList(String userid) throws Exception; 
	
	/**
	 * 
	 * <p>Title: authenticat</p>
	 * <p>Description:用户认证 </p>
	 * @param usercode 用户账号
	 * @param password 用户密码 
	 * @return ActiveUser 用户身份信息
	 * @throws Exception
	 */
	public ActiveUser authenticat(String usercode, String password)throws Exception; 

}
