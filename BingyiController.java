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
 * ���������
 * @author administered
 * */
@RequestMapping("/admin/bingyi")
@Controller
public class BingyiController {
	/**
	 * �������б�ҳ��
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
	 * ��ȡ�û��б�
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
	 * ����û�
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> add(Bingyi bingyi){
		Map<String, String> ret = new HashMap<String, String>();
		if(bingyi == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ���û���Ϣ��");
			return ret;
		}
		if(StringUtils.isEmpty(bingyi.getBingyifanhao())){
			ret.put("type", "error");
			ret.put("msg", "����д�û�����");
			return ret;
		}
		if(StringUtils.isEmpty(bingyi.getBingyianjianming())){
			ret.put("type", "error");
			ret.put("msg", "����д���룡");
			return ret;
		}
		if(bingyi.getBingyishixiang() == null){
			ret.put("type", "error");
			ret.put("msg", "��ѡ��������ɫ��");
			return ret;
		}
		
//		if(bingyiService.add(bingyi) <= 0){
//			ret.put("type", "error");
//			ret.put("msg", "�û����ʧ�ܣ�����ϵ����Ա��");
//			return ret;
//		}
		ret.put("type", "success");
		ret.put("msg", "��ɫ��ӳɹ���");
		return ret;
	}
	
	/**
	 * ����ɾ���û�
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> delete(String ids){
		Map<String, String> ret = new HashMap<String, String>();
		if(StringUtils.isEmpty(ids)){
			ret.put("type", "error");
			ret.put("msg", "ѡ��Ҫɾ�������ݣ�");
			return ret;
		}
		if(ids.contains(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		//if(bingyiService.delete(ids) <= 0){
			if(5 <= 0){
			ret.put("type", "error");
			ret.put("msg", "�û�ɾ��ʧ�ܣ�����ϵ����Ա��");
			return ret;
		}
		ret.put("type", "success");
		ret.put("msg", "�û�ɾ���ɹ���");
		return ret;
	}
	
	/** ��Excel�ļ����� */
	public void showExcel(String excelName) {
		File file = new File(excelName);
		FileInputStream in = null;
		try {
				// ������Excel�������ļ�������
				in = new FileInputStream(file);
				HSSFWorkbook hwb = new HSSFWorkbook(in);
				HSSFSheet sheet = hwb.getSheet("myFirstExcel");// ����ָ�������������ô�Excel�е���Ч������
				// ��ȡExcel �����������
				System.out.println("������Excel�ļ�" + file.getAbsolutePath() + "�����ݣ�");
				HSSFRow row = null;
				HSSFCell cell = null;
				int rowNum = 0; 
				// �б�
				int colNum = 0; 
				// �б�
				for (; rowNum < 9; rowNum++) {
					// ��ȡ��rowNum��
					row = sheet.getRow((short) rowNum);
					for (colNum = 0; colNum < 5; colNum++) {
						cell = row.getCell((short) colNum);// ���ݵ�ǰ�е�λ��������һ����Ԫ�����
						System.out.print(cell.getStringCellValue() + "\t");// ��ȡ��ǰ��Ԫ���е�����
					}
					System.out.println(); // ����
				}
				in.close();
			} catch (Exception e) {
				System.out.println("��ȡExcel�ļ�" + file.getAbsolutePath() + "ʧ�ܣ�" + e);
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
