package com.ko30.web.controller.lotteryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ko30.entity.common.JsonResponse;
import com.ko30.entity.common.JsonStatus;
import com.ko30.entity.common.PageInfo;
import com.ko30.entity.model.vo.winningInfo.LotHistoryVo;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.web.controller.base.BaseController;

/**
 * 
* @ClassName: LotterController 
* @Description: 彩票控制器
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月24日 下午6:21:59 
*
 */
@Controller
@RequestMapping("/lottery")
public class LotterController extends BaseController {

	@Autowired
	private LotHistoryService lotHistoryService;
	
	/**
	 * 
	* @Title: getHistoryByLotCode 
	* @Description: 按彩票类型码分页获取开奖列表 
	* @param @param param
	* @param @param pageInfo
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getHistoryByLotCode(LotHistoryVo param,PageInfo pageInfo){
		return lotHistoryService.query4Page(param, pageInfo);
	}
	
	
	/**
	 * 
	* @Title: getHistoryByLotGroupCode 
	* @Description: 按彩票类型分组获取彩票历史列表 
	* @param @param param
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object getHistoryByLotGroupCode(LotHistoryVo param) {

		JsonResponse response = new JsonResponse();
		response.setData(lotHistoryService.queryByLotGroupCode(param));
		response.setMsg("获取列表成功");
		response.setStatus(JsonStatus.SUCCESS);
		return response;
	}
}
