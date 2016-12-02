/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.loyalove.baseboot.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Filename MapUtils.java
 * @Description
 */
public class MapUtils {
    private static Logger logger = LoggerFactory.getLogger(MapUtils.class);

    /**
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> introspect(Object obj) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            BeanInfo info = Introspector.getBeanInfo(obj.getClass());
            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                Object Object = reader.invoke(obj);
                String name = pd.getName();
                if (reader != null)
                    if (Object != null && !name.equals("class")) {
                        result.put(name, Object);
                    }
            }
        } catch (Exception e) {
            logger.warn("Object转Map出错: obj={}", obj);
            logger.warn("Object转Map出错", e);
        }

        return result;
    }
}
