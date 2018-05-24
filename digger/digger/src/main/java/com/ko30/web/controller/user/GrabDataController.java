package com.ko30.web.controller.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ko30.entity.model.vo.param.UserParam;
import com.ko30.quartz.service.GrabLotteryHistoryService;
import com.ko30.web.controller.base.BaseController;

/**
 * 
* @ClassName: UserController 
* @Description: 用户控制器
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月18日 下午6:18:52 
*
 */
@RestController
@RequestMapping("/grabData")
public class GrabDataController extends BaseController {

	
	@Autowired
	private GrabLotteryHistoryService grabHistoryService;
	
	/**
	 * 
	* @Title: sendSms 
	* @Description: 根据短信模板发送短信验证码 
	* @param @param param
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object sendSmsByTempId(UserParam param) {
		return null;
	}
	
	/**
	 * 
	* @Title: registerByPhone 
	* @Description: TODO 根据手机号码注册
	* @param @param param
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object registerByPhone(UserParam param){
		return null;
	}
	
	
	private Map<String, Object> tempMap=null;
	/**
	 * 
	* @Title: grabData 
	* @Description: 按条件抓取数据 
	* @param @param param
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object grabData(Map<String, Object> param){
		
		this.tempMap=param;
		new Thread(new GrabDataRunner()).start();
		
		return "";
	}
	
	/**
	 * 
	* @ClassName: GrabDataRunner 
	* @Description: 抓取数据
	* @author A18ccms a18ccms_gmail_com 
	* @date 2017年9月4日 上午9:50:42 
	*
	 */
	class GrabDataRunner implements Runnable{
		@Override
		public void run() {
			grabHistoryService.grabRmenLotteryHistory(tempMap);
		}
		
	}
	
}
