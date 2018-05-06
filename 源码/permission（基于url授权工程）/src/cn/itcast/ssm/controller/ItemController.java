package cn.itcast.ssm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.controller.exceptionResolver.CustomException;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.service.ItemService;

/**
 * 
 * <p>
 * Title: UserList
 * </p>
 * <p>
 * Description:用户列表
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-2-3下午2:47:35
 * @version 1.0
 */
@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	//商品分类
	@ModelAttribute("itemtypes")
	public Map<String, String> getItemTypes(){
		
		Map<String, String> itemTypes = new HashMap<String,String>();
		
		itemTypes.put("101", "数码");
		itemTypes.put("102", "母婴");
		
		return itemTypes;
	}
	

	// 查询商品列表
	@RequestMapping("/queryItem")
	public ModelAndView queryItem() throws Exception {
		// 商品列表
		List<Items> itemsList = itemService.findItemsList(null);

		// 创建modelAndView准备填充数据、设置视图
		ModelAndView modelAndView = new ModelAndView();

		// 填充数据
		modelAndView.addObject("itemsList", itemsList);
		// 视图
		modelAndView.setViewName("item/itemsList");

		return modelAndView;
	}

	// 商品修改页面
	// @RequestMapping(value="/editItem/{item_id}",method=RequestMethod.GET)
	// public String editItem(Model model,@PathVariable("item_id") Integer id)
	// throws Exception{
	//
	// //调用service查询商品信息
	// Items item = itemService.findItemById(id);
	//
	// model.addAttribute("item", item);
	//
	// return "item/editItem";
	// }

	//rest显示商品信息
	@RequestMapping(value = "/viewItem/{id}")
	public @ResponseBody
	Items viewItem(@PathVariable("id") Integer id)
			throws Exception {
		
		// 调用service查询商品信息
		Items item = itemService.findItemById(id);

		return item;
	}
	
	@RequestMapping(value = "/editItem")
	public String editItem(@RequestParam(value = "id", required = true) Integer id,
			Boolean status,Model model) throws Exception {
	
		/*if (result.hasErrors()) {
			return "item/editItem";
		}*/
		// 调用service查询商品信息
		Items item = itemService.findItemById(id);
		
		if(item == null){
			throw new CustomException("商品信息不存在!");
		}

		model.addAttribute("item", item);
		// 指定逻辑视图名，经过视图解析器解析为jsp物理路径：/WEB-INF/jsp/item/editItem.jsp
		return "item/editItem";
	}
	@RequestMapping(value = "/editItem/{id}")
	public String editItem_rest(@PathVariable  Integer id,
			Boolean status,Model model) throws Exception {
	
		/*if (result.hasErrors()) {
			return "item/editItem";
		}*/
		// 调用service查询商品信息
		Items item = itemService.findItemById(id);
		
		

		model.addAttribute("item", item);
		// 指定逻辑视图名，经过视图解析器解析为jsp物理路径：/WEB-INF/jsp/item/editItem.jsp
		return "item/editItem_rest";
	}

	// 商品修改提交
	@RequestMapping("/editItemSubmit")
	public String editItemSubmit(@ModelAttribute("item") Items items,BindingResult result,
			MultipartFile pictureFile,Model model,HttpServletRequest request)
			throws Exception {

		System.out.println(items);
			
		//如果存在校验错误则转到商品修改页面
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError objectError:errors){
				System.out.println(objectError.getCode());
				System.out.println(objectError.getDefaultMessage());
			}

			//model.addAttribute("errors", errors);
			return "item/editItem";
		}

		String uploadFileNames = null;
		/*for(int i=0;i<pictureFile.length;i++){
			// 原始文件名称
			String pictureFile_name = pictureFile[i].getOriginalFilename();
			if (pictureFile_name != null && !pictureFile_name.equals("")) {
				// 新文件名称
				String newFileName = UUID.randomUUID().toString()
						+ pictureFile_name.substring(pictureFile_name
								.lastIndexOf("."));

				// 上传图片
				File uploadPic = new java.io.File("F:/develop/upload/temp/"
						+ newFileName);

				if (!uploadPic.exists()) {
					uploadPic.mkdirs();
				}
				// 向磁盘写文件
				pictureFile[i].transferTo(uploadPic);
				uploadFileNames = uploadFileNames == null?"":uploadFileNames;
				uploadFileNames +=newFileName+",";
				
			}
		}*/
		if(uploadFileNames!=null){
			items.setPic(uploadFileNames);
		}

		itemService.saveItem(items);

		return "success";
		// 结果转发到editItem.action，request可以带过去
		// return "forward:editItem.action";
		// 重定向到queryItem.action地址,request无法带过去
		// return "redirect:queryItem.action";

	}

	// 商品修改提交json信息，响应json信息
	@RequestMapping("/editItemSubmit_RequestJson")
	public @ResponseBody Items editItemSubmit_RequestJson(@RequestBody Items items) throws Exception {

		System.out.println(items);

		//itemService.saveItem(items);

		return items;

	}

	// 商品修改提交，提交普通form表单数据，响应json
	@RequestMapping("/editItemSubmit_ResponseJson")
	public @ResponseBody Items editItemSubmit_ResponseJson(Items items) throws Exception {

		System.out.println(items);

//		itemService.saveItem(items);

		return items;
	}

	// 注册属性编辑器
	 @InitBinder(value={"",""})
	 public void initBinder(ServletRequestDataBinder binder) throws Exception
	 {
		 //binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	 binder.registerCustomEditor(Date.class, new CustomDateEditor(new
	 SimpleDateFormat("yyyy-MM-dd HH-mm-ss"),true));
	 }

}
