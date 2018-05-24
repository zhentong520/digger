package com.ko30.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.service.GenericService;

@Transactional
@Service
public class GenericServiceImpl<T> implements GenericService<T> {

}
