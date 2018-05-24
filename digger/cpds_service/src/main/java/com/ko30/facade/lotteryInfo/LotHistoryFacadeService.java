package com.ko30.facade.lotteryInfo;

import java.util.List;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.common.JsonPageResponse;
import com.ko30.entity.common.PageInfo;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.vo.winningInfo.LotHistoryVo;


/**
 * 
* @ClassName: LotHistoryFacadeService 
* @Description: 开奖记录服务接口 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月24日 下午4:46:05 
*
 */
public interface LotHistoryFacadeService extends BaseFacadeService<AppLotHistory, Long> {

	AppLotHistory saveEntity(AppLotHistory entity);
	
	/**
	 * 
	* @Title: queryByLotCode 
	* @Description: 按彩票类型编码获取彩票历史列表 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<LotHistoryVo>    返回类型 
	* @throws
	 */
	List<LotHistoryVo> queryByLotGroupCode(LotHistoryVo queryParam);
	
	
	/**
	 * 
	* @Title: query4Page 
	* @Description: 按条件分页获取记录 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return JsonPageResponse<LotHistoryVo>    返回类型 
	* @throws
	 */
	JsonPageResponse<LotHistoryVo> query4Page(LotHistoryVo queryParam,PageInfo pageInfo);
	
	/**
	 * 
	* @Title: isExist 
	* @Description: 检查当前记录是否已经存在 （lotCode,preDrawIssue）
	* @param @param lot
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	boolean checkisExist(AppLotHistory lot);
	
	/**
	 * 
	* @Title: getTheLastOneByLotCode 
	* @Description: 根据类型码，获取最后一条历史记录 
	* @param @param lotCode
	* @param @return    设定文件 
	* @return AppLotHistory    返回类型 
	* @throws
	 */
	AppLotHistory getTheLastOneByLotCode(Integer lotCode);
	
	/**
	 * 
	* @Title: getNewListByCountAndLotCode 
	* @Description: 查询指定彩中的最新指定记录数
	* @param @param lotCode
	* @param @param count
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	List<AppLotHistory> getNewListByCountAndLotCode(Integer lotCode,Integer count,int calenderType);
	
}
