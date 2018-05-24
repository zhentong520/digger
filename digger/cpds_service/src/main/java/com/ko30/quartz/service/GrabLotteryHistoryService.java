package com.ko30.quartz.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.common.util.HttpClientUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.service.lotteryInfo.LotHistoryService;


/**
 * 
* @ClassName: GrabRemenLotteryHistoryService 
* @Description: 获取热门彩 服务类
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月29日 下午6:29:02 
*
 */
@Service
//@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class GrabLotteryHistoryService {

	private final Logger logger = Logger.getLogger(GrabLotteryHistoryService.class);
	
	private LotHistoryService lotHistoryService;
	
	// 保存不同请求
	private static List<Map<String, Object>> reMenUrls=null; 

	private Map<String, Object> map=null;
	public GrabLotteryHistoryService() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		
		reMenUrls=Lists.newArrayList();
		// 热门彩部分 请求路径    groupCode=4
		//reMenUrls.add("http://api.1680210.com/pks/getPksHistoryList.do?lotCode=10001");//PK拾
		map=Maps.newConcurrentMap();//PK拾
		map.put("url", "http://api.1680210.com/pks/getPksHistoryList.do?lotCode=10001");
		map.put("lotName", "北京pk拾");
		map.put("lotCode", 10001);
		//reMenUrls.add(map);
		
		//reMenUrls.add("http://api.1680210.com/pks/getPksHistoryList.do?lotCode=10037");//极速赛车
		map=Maps.newConcurrentMap();//极速赛车
		map.put("url", "http://api.1680210.com/pks/getPksHistoryList.do?lotCode=10037");
		map.put("lotName", "极速赛车");
		map.put("lotCode", 10037);
		//reMenUrls.add(map);
		
		//reMenUrls.add("http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10002");//重庆时时彩
		map=Maps.newConcurrentMap();//重庆时时彩
		map.put("url", "http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10002");
		map.put("lotName", "重庆时时彩");
		map.put("lotCode", 10002);
		//reMenUrls.add(map);
		
	
		
		//reMenUrls.add("http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10003");//天津时时彩
		map=Maps.newConcurrentMap();//天津时时彩
		map.put("url", "http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10003");
		map.put("lotName", "天津时时彩");
		map.put("lotCode", 10003);
		//reMenUrls.add(map);
		
		//reMenUrls.add("http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10004");//新疆时时彩
		map=Maps.newConcurrentMap();//新疆时时彩
		map.put("url", "http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10004");
		map.put("lotName", "新疆时时彩");
		map.put("lotCode", 10004);
		//reMenUrls.add(map);
		
		
		//reMenUrls.add("http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10036");// 极速时时彩
		map=Maps.newConcurrentMap();//极速时时彩
		map.put("url", "http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10036");
		map.put("lotName", "极速时时彩");
		map.put("lotCode", 10036);
		reMenUrls.add(map);
		
		
		//reMenUrls.add("http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10008");// 十一运夺金
		map=Maps.newConcurrentMap();//十一运夺金
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10008");
		map.put("lotName", "十一运夺金");
		map.put("lotCode", 10008);
		reMenUrls.add(map);
		
		//reMenUrls.add("http://api.1680210.com/klsf/getHistoryLotteryInfo.do?lotCode=10005");// 广东快乐10分
		map=Maps.newConcurrentMap();// 广东快乐十分
		map.put("url", "http://api.1680210.com/klsf/getHistoryLotteryInfo.do?lotCode=10005");
		map.put("lotName", "广东快乐10分");
		map.put("lotCode", 10005);
		reMenUrls.add(map);
		
		
		//reMenUrls.add("http://api.1680210.com/klsf/getHistoryLotteryInfo.do?lotCode=10009");// 重庆幸运农场
		map=Maps.newConcurrentMap();// 重庆幸运农场
		map.put("url", "http://api.1680210.com/klsf/getHistoryLotteryInfo.do?lotCode=10009");
		map.put("lotName", "重庆幸运农场");
		map.put("lotCode", 10009);
		reMenUrls.add(map);
		
		//reMenUrls.add("http://api.1680210.com/gxklsf/getHistoryLotteryInfo.do?lotCode=10038");// 广西快乐十分
		map=Maps.newConcurrentMap();//PK拾
		map.put("url", "http://api.1680210.com/gxklsf/getHistoryLotteryInfo.do?lotCode=10038");
		map.put("lotName", "广西快乐十分");
		map.put("lotCode", 10038);
		reMenUrls.add(map);
		
		//reMenUrls.add("http://api.1680210.com/LuckTwenty/getPcLucky28List.do?");//pc蛋蛋幸运28
		map=Maps.newConcurrentMap();//PK拾
		map.put("url", "http://api.1680210.com/LuckTwenty/getPcLucky28List.do?");
		map.put("lotName", "pc蛋蛋幸运28");
		map.put("lotCode", 10046);
		reMenUrls.add(map);	
	}
	
	
	/**
	 * 
	* @Title: getOpenLotteryResultData 
	* @Description: 请求第三方接口，获取数据 
	* @param @param url
	* @param @param groupCode
	* @param @return    设定文件 
	* @return reMenUrls<AppLotHistory>    返回类型 
	* @throws
	 */
	private List<AppLotHistory> getOpenLotteryResultData(String url, Integer groupCode) {

		List<AppLotHistory> list=null;
		try {
			String contentStr = HttpClientUtil.get(url);
			//logger.info("现在得到的数据是：" + contentStr);
			JSONObject contentJsonObj = JSONObject.parseObject(contentStr);
			// 取不到对应key值的时候，为获取数据异常
			JSONObject resultjsonObj = contentJsonObj.getJSONObject("result");
			if (AssertValue.isNotNullAndNotEmpty(resultjsonObj.get("data").toString())) {
				JSONArray dataJsonArr = resultjsonObj.getJSONArray("data");
				if (dataJsonArr.size()>0) {
					list = JSONArray.parseArray(dataJsonArr.toString(), AppLotHistory.class);
				}
			}
		} catch (Exception e) {
			logger.info("抓取数据转化实体异常：" + e.getMessage());
			try {
				Thread.sleep(3000);// 休眠一秒后再请求
				this.getOpenLotteryResultData(url, groupCode);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		return list;
	}
	
	
	/**
	 * 
	* @Title: getDataByLotteryCode 
	* @Description: 保存得到的历史记录实体 
	* @param @param url
	* @param @param groupCode
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	public List<AppLotHistory> getDataByLotteryCode(String url,Integer groupCode,String lotName,Integer lotCode) {

		List<AppLotHistory> list = this.getOpenLotteryResultData(url,groupCode);
		List<AppLotHistory> datas = null;// 保存目标类型彩票
		if (AssertValue.isNotNullAndNotEmpty(list)) {
			datas = Lists.newArrayList();
			for (AppLotHistory lot : list) {
				// 将得到的彩票汉字名称转首字每大写
				String objLotName = lotName.replace('⑥', '六');// 替换其它特殊符号
				String templotteryAlias = CommUtil.getPinYinHeadChar(objLotName).toLowerCase();
				if (!lotHistoryService.checkisExist(lot)) {
					lot.setLotAlias(templotteryAlias);
					lot.setLotName(lotName);
					lot.setLotGroupCode(groupCode);
					lot.setLotCode(lotCode);
					datas.add(lot);
				}
			}
		}
		return datas;
	}
	
	
	/**
	 * 
	 * @Title: saveEntitys
	 * @Description: 保存得到的集合,并返回最后一个
	 * @param @param list 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private AppLotHistory saveEntitysAndReturnLastOne(List<AppLotHistory> list) {
		AppLotHistory lot = null;
		if (AssertValue.isNotNullAndNotEmpty(list)) {
			lotHistoryService.save(list);
			lot = list.get(list.size() - 1);
		}
		return lot;
	}
	
	
	/**
	 * 
	* @Title: grabAndSaveLotHistory 
	* @Description: 获取并保存历史记录 
	* @param @param url
	* @param @param groupCode
	* @param @return    设定文件 
	* @return AppLotHistory    返回类型 
	* @throws
	 */
	public AppLotHistory grabAndSaveLotHistory(String url, Integer groupCode,String lotName,Integer lotCode) {
		List<AppLotHistory> list = getDataByLotteryCode(url, groupCode,lotName,lotCode);
		AppLotHistory lot = saveEntitysAndReturnLastOne(list);
		return lot;
	}
	
	
	/**
	 * 
	* @Title: saveRemenLotHistory 
	* @Description: 获取并保存热门彩历史记录 (递归调用)
	* @param @param url
	* @param @param groupCode
	* @param @return    设定文件 
	* @return AppLotHistory    返回类型 
	* @throws
	 */
	private static String queryDateStr = null;
	private static int tempGroupCode = 0;
	private static int breakDays=0;// 中断日期数
	public AppLotHistory saveRemenLotHistory(String url, Integer groupCode,AppLotHistory lot,String lotName,Integer lotCode,String endTimeStr) {
		// 将请求url的参数的分成两个部分 date=2017-08-08 & lotCode=10038
		// 设置查询终止日期 2016-10-01
		// String queryDateStr = null;
		Calendar cal=Calendar.getInstance();
		if (AssertValue.isNotNull(lot)) {
			breakDays=0;
			//Date queryEndDate = CommUtil.formatDate("2016-10-01");
			Date queryEndDate = CommUtil.formatDate(endTimeStr);
			if (queryEndDate.after(lot.getPreDrawTime())) {
				return lot;
			}
			// 记录最后一天减去1天
			String tempqueryDateStr = CommUtil.formatShortDate(lot.getPreDrawTime());
			Date tempDate = CommUtil.formatDate(tempqueryDateStr);
			cal.setTime(tempDate);
			cal.add(Calendar.DATE, -1);
			queryDateStr = CommUtil.formatShortDate(cal.getTime());
		}
		// 中间哪一天停止
		else if(tempGroupCode == groupCode.intValue() && !CommUtil.formatDate(queryDateStr).after(CommUtil.formatDate(endTimeStr))&& breakDays<5){
			breakDays++;
			Date tempDate = CommUtil.formatDate(queryDateStr);
			cal.setTime(tempDate);
			cal.add(Calendar.DATE, -1);
			queryDateStr = CommUtil.formatShortDate(cal.getTime());
		} 
		else if (tempGroupCode != groupCode.intValue()) {
			breakDays=0;
			// cal.add(Calendar.DATE, -1);
			queryDateStr = CommUtil.formatShortDate(cal.getTime());
		}
		// 日期条件递归往期查询
		String[] urlStrs = url.split("&");
		String targetUrlParam_1=urlStrs[0];
		String targetUrlParam_2="date="+queryDateStr;
		// 拼接新的请求地址
		url=targetUrlParam_1+"&"+targetUrlParam_2;
		logger.info("现在的请求地址是："+url+"    queryDateStr="+queryDateStr);
		lot=this.grabAndSaveLotHistory(url, groupCode,lotName,lotCode);
		
		// 执行递归查询
		tempGroupCode=groupCode;
		lot=this.saveRemenLotHistory(url, groupCode, lot,lotName,lotCode);
		return lot;
	}
	
	
	/**
	 * 
	* @Title: saveRemenLotHistory 
	* @Description: 默认往回查询到15年。 
	* @param @param url
	* @param @param groupCode
	* @param @param lot
	* @param @param lotName
	* @param @param lotCode
	* @param @return    设定文件 
	* @return AppLotHistory    返回类型 
	* @throws
	 */
	public AppLotHistory saveRemenLotHistory(String url, Integer groupCode,AppLotHistory lot,String lotName,Integer lotCode) {
		String endTimeStr="2005-01-01";
		lot=this.saveRemenLotHistory( url,  groupCode, lot, lotName, lotCode, endTimeStr);
		return lot;
	}

	
	/**
	 * 
	* @Title: doSaveRemenLotterys 
	* @Description:  获取并保存热门彩历史记录 ()
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void doSaveRemenLotterys() {
		Iterator<Map<String, Object>> it = reMenUrls.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			String url = map.get("url").toString();
			String lotName = map.get("lotName").toString();
			Integer lotCode =Integer.parseInt(map.get("lotCode")+"");
			this.saveRemenLotHistory(url, 4, null, lotName,lotCode);
		}
	}
	
	/**
	 * 
	* @Title: getUrls 
	* @Description: 获取到所有地址 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getUrls() {
		return AssertValue.isNotNullAndNotEmpty(reMenUrls) ? reMenUrls : Lists.newArrayList();
	}
	
	/**
	 * 
	* @Title: grabRmenLotteryHistory 
	* @Description: 获取热门彩
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void grabRmenLotteryHistory(Map<String, Object> param) {
		if (!AssertValue.isNotNull(param)) {
			return;
		}
		String url = param.get("url").toString();
		String lotName = param.get("lotName").toString();
		Integer lotCode = Integer.parseInt(param.get("lotCode") + "");
		
		// 得到日期排后的一条
		AppLotHistory lot=lotHistoryService.getTheLastOneByLotCode(lotCode);
		String beginTimeStr=CommUtil.formatShortDate(lot.getPreDrawTime());
		this.saveRemenLotHistory(url, 4, null, lotName,lotCode,beginTimeStr);
	}
}
