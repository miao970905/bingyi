package com.ischoolbar.programmer.controller.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ischoolbar.programmer.entity.admin.Bingyi;
import com.ischoolbar.programmer.entity.admin.User;
import com.ischoolbar.programmer.page.admin.Page;
import com.ischoolbar.programmer.service.admin.bingyiService;
/**
 * 禀议书管理
 * @author administered
 * */
@RequestMapping("/admin/bingyi")
@Controller
public class BingyiController {
	/**
	 * 禀议书列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model){
		Map<String, Object> queryMap = new HashMap<String, Object>();
		//model.addObject("roleList", roleService.findList(queryMap));
		model.setViewName("bingyi/list");
		return model;
	}
	/**
	 * 获取用户列表
	 * @param page
	 * @param username
	 * @param roleId
	 * @param sex
	 * @return
	 */
	@RequestMapping(value="/list",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getList(Page page,
			@RequestParam(name="username",required=false,defaultValue="") String username,
			@RequestParam(name="roleId",required=false) Long roleId,
			@RequestParam(name="sex",required=false) Integer sex
			){
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", username);
		queryMap.put("roleId", roleId);
		queryMap.put("sex", sex);
		queryMap.put("offset", page.getOffset());
		queryMap.put("pageSize", page.getRows());
		//ret.put("rows", bingyiService.findList(queryMap));
		//ret.put("total", bingyiService.getTotal(queryMap));
		return ret;
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Bingyi bingyi){
		Map<String, String> ret = new HashMap<String, String>();
		if(bingyi == null){
			ret.put("type", "error");
			ret.put("msg", "请填写正确的用户信息！");
			return ret;
		}
		if(StringUtils.isEmpty(bingyi.getBingyifanhao())){
			ret.put("type", "error");
			ret.put("msg", "请填写用户名！");
			return ret;
		}
		if(StringUtils.isEmpty(bingyi.getBingyianjianming())){
			ret.put("type", "error");
			ret.put("msg", "请填写密码！");
			return ret;
		}
		if(bingyi.getBingyishixiang() == null){
			ret.put("type", "error");
			ret.put("msg", "请选择所属角色！");
			return ret;
		}
		
//		if(bingyiService.add(bingyi) <= 0){
//			ret.put("type", "error");
//			ret.put("msg", "用户添加失败，请联系管理员！");
//			return ret;
//		}
		ret.put("type", "success");
		ret.put("msg", "角色添加成功！");
		return ret;
	}
	
	/**
	 * 批量删除用户
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(String ids){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(ids)){
			ret.put("type", "error");
			ret.put("msg", "选择要删除的数据！");
			return ret;
		}
		if(ids.contains(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		//if(bingyiService.delete(ids) <= 0){
			if(5 <= 0){
			ret.put("type", "error");
			ret.put("msg", "用户删除失败，请联系管理员！");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "用户删除成功！");
		return ret;
	}
	
	/** 读Excel文件内容 */
	public void showExcel(String excelName) {
		File file = new File(excelName);
		FileInputStream in = null;
		try {
				// 创建对Excel工作簿文件的引用
				in = new FileInputStream(file);
				HSSFWorkbook hwb = new HSSFWorkbook(in);
				HSSFSheet sheet = hwb.getSheet("myFirstExcel");// 根据指定的名字来引用此Excel中的有效工作表
				// 读取Excel 工作表的数据
				System.out.println("下面是Excel文件" + file.getAbsolutePath() + "的内容：");
				HSSFRow row = null;
				HSSFCell cell = null;
				int rowNum = 0; 
				// 行标
				int colNum = 0; 
				// 列标
				for (; rowNum < 9; rowNum++) {
					// 获取第rowNum行
					row = sheet.getRow((short) rowNum);
					for (colNum = 0; colNum < 5; colNum++) {
						cell = row.getCell((short) colNum);// 根据当前行的位置来创建一个单元格对象
						System.out.print(cell.getStringCellValue() + "\t");// 获取当前单元格中的内容
					}
					System.out.println(); // 换行
				}
				in.close();
			} catch (Exception e) {
				System.out.println("读取Excel文件" + file.getAbsolutePath() + "失败：" + e);
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e1) {
				}
			}
		}
	}
	public void exmain(String[] args) {
		//BingyiController excel = new BingyiController();
		String excelName = "D:/ExcelExamRead.xls";
		showExcel(excelName);
	}
}
