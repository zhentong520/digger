package com.ko30.common.util;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

/**
 * 主键生成策略
 * @author admin
 *
 */
public class MyKeyGenerator implements IdentifierGenerator, Configurable {

	private short __id = 0;
	
	@Override
	public void configure(Type type, Properties params, Dialect d)
			throws MappingException {
		// TODO Auto-generated method stub
	}

	@Override
	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		String t = null;
		String r = null;
		if(++__id >= 1000)
			__id = 0;
		r = StringUtils.leftPad(new Short(__id).toString(), 3, '0');
		t = new Long(new Date().getTime()).toString();
		return Long.parseLong(t + r);
	}

}
