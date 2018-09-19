package com.kute.util;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * created by kute on 2018/08/08 14:28
 */
public class KeyUtil {

    public static String getMethodKey(Class<?> serviceClass, String methodName) {
        Preconditions.checkNotNull(serviceClass);
        return Joiner.on(".").useForNull("").join(serviceClass.getName(), methodName, "dubbo.retry");
    }

    public static String getRedisKey(String uuid) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(uuid));
        return Joiner.on("_").join(uuid, "key");
    }
}
