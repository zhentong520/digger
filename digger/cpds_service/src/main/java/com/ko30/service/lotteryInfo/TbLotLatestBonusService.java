package com.ko30.service.lotteryInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TbLotLatestBonus;
import com.ko30.facade.lotteryInfo.TbLotLatestBonusFacadeService;


/**
 * 
* @ClassName: TbLotLatestBonusService 
* @Description: 彩种预测中奖金额累积接口实现 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年10月11日 下午2:29:40 
*
 */
@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class TbLotLatestBonusService extends
		BaseService<TbLotLatestBonus, Long> implements
		TbLotLatestBonusFacadeService {
	
	private final Logger logger=Logger.getLogger(TbLotLatestBonusService.class);
	
	/**
	 * 修改指定彩种的最新预测中奖金额
	 */
	@Override
	public TbLotLatestBonus updateLatestBonusAmount(AppLotHistory lot) {

		if (!AssertValue.isNotNull(lot)) {
			return null;
		}
		
		Searchable search = Searchable.newSearchable();
		search.addSearchFilter("lotCode", SearchOperator.eq,lot.getLotCode());
		List<TbLotLatestBonus> list=this.findAllWithSort(search);
		if (AssertValue.isNotNullAndNotEmpty(list)) {
			TbLotLatestBonus lotLatestBonus=list.get(0);
			// 设置最新开奖期数，及更新时间
			lotLatestBonus.setCurrentIssue(lot.getPreDrawIssue());
			lotLatestBonus.setUpdateTime(new Date());
			
			// 更新彩种累积中奖金额操作
			return this.getLatestBonusAmount(lotLatestBonus);
		} else {
			if (!AssertValue.isNotNull(lot.getLotCode())) {
				return null;
			}
			if (!AssertValue.isNotNullAndNotEmpty(lot.getLotName())) {
				return null;
			}
			if (!AssertValue.isNotNullAndNotEmpty(lot.getPreDrawIssue())) {
				return null;
			}

			
			TbLotLatestBonus lotLatestBonus=new TbLotLatestBonus();
			// 设置最新开奖期数，及更新时间
			lotLatestBonus.setCurrentIssue(lot.getPreDrawIssue());
			lotLatestBonus.setUpdateTime(new Date());
			// 设置默认起始值，及增长范围值
			lotLatestBonus.setBaseAmount(new BigDecimal(5));
			lotLatestBonus.setCurrentAmount(new BigDecimal(1));
			lotLatestBonus.setBeginArea(1);
			lotLatestBonus.setEndArea(3);
			lotLatestBonus.setCreateTime(new Date());
			lotLatestBonus.setLotCode(lot.getLotCode());
			lotLatestBonus.setLotName(lot.getLotName());
			// 更新彩种累积中奖金额操作
			this.getLatestBonusAmount(lotLatestBonus);
			logger.info("找不到对应的彩种预测中奖金额记录");
			return null;
			// throw new BusinessException("找不到对应的彩种预测中奖金额记录");
		}
	}
	
	/**
	 * 
	* @Title: getLatestBonusAmount 
	* @Description: 获取随机金额，进行累加 
	* @param @param param
	* @param @return    设定文件 
	* @return TbLotLatestBonus    返回类型 
	* @throws
	 */
	private TbLotLatestBonus getLatestBonusAmount(TbLotLatestBonus param){
		
		if (!AssertValue.isNotNull(param)) {
			return param;
		}
		
		// 起始范围
		int beginAreaAmount = (0 == param.getBeginArea() ? 1 : param.getBeginArea());
		// 终止范围
		int endAreaAmount = (param.getBeginArea() >= param.getEndArea() ? 100 : param.getEndArea());
		
		// 得到随机的增长金额
		Random random = new Random();
		int ranAmount = random.nextInt(endAreaAmount - beginAreaAmount+ 1)+ beginAreaAmount;
		
        // 检查是否第一次累加
        BigDecimal newCurrentAmoun=null;
        // 基数金额，与当新最新金额比较
        if (param.getBaseAmount().compareTo(param.getCurrentAmount())>0) {
        	newCurrentAmoun=param.getBaseAmount().add(new BigDecimal(ranAmount));
		}else {
			newCurrentAmoun=param.getCurrentAmount().add(new BigDecimal(ranAmount));
		}
        param.setCurrentAmount(newCurrentAmoun);
        
        // 更新指定记录
		return this.saveObj(param);
	}

}
