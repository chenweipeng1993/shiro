package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.controller.exceptionResolver.CustomException;
import cn.itcast.ssm.mapper.SysPermissionMapper;
import cn.itcast.ssm.mapper.SysPermissionMapperCustom;
import cn.itcast.ssm.mapper.SysUserMapper;
import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.po.SysPermission;
import cn.itcast.ssm.po.SysPermissionExample;
import cn.itcast.ssm.po.SysUser;
import cn.itcast.ssm.po.SysUserExample;
import cn.itcast.ssm.service.SysService;
import cn.itcast.ssm.util.MD5;

public class SysServiceImpl implements SysService {
	
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	@Autowired
	private SysUserMapper sysUserMapper;


	@Override
	public List<SysPermission> findSysPermissionList(String userid)
			throws Exception {
		
		return sysPermissionMapperCustom.findMenuByUserid(userid);
	}


	@Override
	public ActiveUser authenticat(String usercode, String password)
			throws Exception {
		
		//账号和密码非空校验 
		//....
		
		SysUserExample sysUserExample = new SysUserExample();
		SysUserExample.Criteria criteria = sysUserExample.createCriteria();
		criteria.andUsercodeEqualTo(usercode);
		List<SysUser> userList = sysUserMapper.selectByExample(sysUserExample);
		if(userList == null || userList.size()<=0){
			throw new CustomException("账号不存在！");
		}
		SysUser sysUser = userList.get(0);
		//密码 
		String password_fromdb = sysUser.getPassword();
		
		//输入 密码 和数据库密码 比较
		if(!password_fromdb.equalsIgnoreCase(new MD5().getMD5ofStr(password))){
			throw new CustomException("账号或密码 错误 ！");
		}
		//认证通过，返回用户身份
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setUsercode(sysUser.getUsercode());
		
		//菜单列表
		List<SysPermission> menus = sysPermissionMapperCustom.findMenuByUserid(sysUser.getId());
		activeUser.setMenus(menus);
		//权限列表
		List<SysPermission> permissions = sysPermissionMapperCustom.findPermissionByUserid(sysUser.getId());
		activeUser.setPermissions(permissions);
		
		return activeUser;
	}

}
