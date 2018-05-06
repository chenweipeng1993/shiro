package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.mapper.ItemsMapperCustom;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.QueryVo;
import cn.itcast.ssm.service.ItemService;

public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	@Autowired
	private ItemsMapper itemsMapper;

	@Override
	public List<Items> findItemsList(QueryVo queryVo) throws Exception {
		//查询商品信息
		return itemsMapperCustom.findItemsList(queryVo);
	}

	@Override
	public Items findItemById(int id) throws Exception {
		
		
		return itemsMapperCustom.findItemById(id);
	}

	@Override
	public void saveItem(Items items) throws Exception {
		
		itemsMapper.updateByPrimaryKeyWithBLOBs(items);
		
	}

}
