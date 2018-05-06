package cn.itcast.ssm.mapper;

import java.util.List;

import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.QueryVo;

public interface ItemsMapperCustom {
	
	//商品列表
	public List<Items> findItemsList(QueryVo queryVo) throws Exception;
	
	//根据id查询商品信息
	public Items findItemById(int id) throws Exception;
}
