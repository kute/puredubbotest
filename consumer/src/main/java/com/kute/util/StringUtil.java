package com.kute.util;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * created by bailong001 on 2018/09/21 17:38
 */
public class StringUtil {

    public static Boolean isAnyNullOrEmpty(String ... ss) {
        if(null == ss) {
            return true;
        }
        return Iterables.any(Lists.newArrayList(ss), t -> Strings.isNullOrEmpty(t));
    }

    public static Boolean isAllNullOrEmpty(String ... ss) {
        if(null == ss) {
            return true;
        }
        return Iterables.all(Lists.newArrayList(ss), t -> Strings.isNullOrEmpty(t));
    }

}
