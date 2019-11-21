package com.spring.annotation.impor;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 *   
 *
 * @ClassName: MyImportSelector
 * =================================================
 * @Description: 自定义逻辑返回需要导入的组件
 * =================================================
 * CreateInfo:
 * @Author: William.Wangmy
 * @CreateDate: 2019/11/21 22:31        
 * @Version: V1.0
 *     
 */
public class MyImportSelector implements ImportSelector {

    /**
     * 这里返回时全类名的数组
     * @param annotationMetadata
     * @return
     */
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.spring.annotation.bean.Cat"};
    }
}
