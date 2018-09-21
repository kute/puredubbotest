package com.kute.util;

import org.springframework.beans.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.validation.BeanPropertyBindingResult;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * created by kute on 2018/09/20 18:41
 */
public class ParamUtil {


    public static Object[] resolveArguments(Map<String, Object> paramMap,  Method sourceMethod) {
        Class[] paramTypes = sourceMethod.getParameterTypes();
        String[] paramNames = paramNames(sourceMethod);
        Object[] result = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            result[i] = resolveArgument(paramMap, paramTypes[i], paramNames[i]);
        }
        return result;
    }

    public static Object resolveArgument(Map<String, Object> paramMap, Class paramType, String paramName) {
        Object result = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        if (BeanUtils.isSimpleProperty(paramType)) {
            SimpleTypeConverter simpleTypeConverter = new SimpleTypeConverter();
            simpleTypeConverter.registerCustomEditor(Date.class, dateEditor);
            result = simpleTypeConverter.convertIfNecessary(paramMap.get(paramName), paramType);
        } else {
            result = BeanUtils.instantiateClass(paramType);
            BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(result,
                    paramName, true, 256);
            PropertyAccessor propertyAccessor = bindingResult.getPropertyAccessor();
            bindingResult.getPropertyAccessor().registerCustomEditor(Date.class, dateEditor);
            MutablePropertyValues propertyValues = new MutablePropertyValues(paramMap);
            handle(propertyAccessor, propertyValues);
            bindingResult.getPropertyAccessor().setPropertyValues(propertyValues);
        }
        return result;
    }

    private static void handle(PropertyAccessor propertyAccessor, MutablePropertyValues mpvs) {
        PropertyValue[] pvArray = mpvs.getPropertyValues();
        for (PropertyValue pv : pvArray) {
            String field = pv.getName();
            if (!propertyAccessor.isWritableProperty(field) || !mpvs.contains(field)) {
                mpvs.removePropertyValue(pv);
            }
        }
    }


    private static String[] reflectParamNames(Method method) {
        Parameter[] params = method.getParameters();
        String[] paramNames = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i].isNamePresent()) {
                paramNames[i] = params[i].getName();
            } else {
                return null;
            }
        }
        return paramNames;
    }

    private static String[] paramNames(Method method) {
        String[] paramNames = reflectParamNames(method);
        if (paramNames == null) {
            paramNames = new LocalVariableTableParameterNameDiscoverer().getParameterNames(method);
        }
        return paramNames;
    }
}
