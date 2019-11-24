package com.spring.annotation.service;

import com.spring.annotation.dao.AnnotationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 *   
 *
 * @ClassName: annotationService
 * =================================================
 * @Description: TODO
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/20 23:18        
 * @Version: V1.0
 *     
 */
@Service
public class AnnotationService {

    @Qualifier("annotationDao")
    @Autowired(required = false)
    private AnnotationDao annotationDao;
}
