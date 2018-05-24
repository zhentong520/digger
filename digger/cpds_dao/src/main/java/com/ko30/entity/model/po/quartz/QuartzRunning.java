package com.ko30.entity.model.po.quartz;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ko30.entity.model.AbstractEntity;

/**
 * 
* @ClassName: QuartzRunning 
* @Description: 定时任务运行记录 
* @author A18ccms a18ccms_gmail_com 
* @date 2016年9月27日 下午4:33:32 
*
 */
@Entity
@Table(name = "t_quartz_running")
public class QuartzRunning extends AbstractEntity<Long> {

	/** 
	* @Fields serialVersionUID :
	*/
	private static final long serialVersionUID = -2142854605097161739L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 所运行的类名
	 */
	private String className;
	
	/**
	 * 描述
	 */
	private String descr;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 状态
	 */
	private String state;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
}
