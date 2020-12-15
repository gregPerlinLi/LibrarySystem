package com.gregperlinli.util;

import java.util.List;

/**
 * @author gregperlinli
 */
public class EmptyUtil {
    /**
     * @Description judge the object is null
     * @author gregperlinli
     * @param obj object name
     * @return is null
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null)
        {
            return true;
        }
        if ((obj instanceof List))
        {
            return ((List) obj).size() == 0;
        }
        if ((obj instanceof String))
        {
            return ((String) obj).trim().equals("");
        }
        return false;
    }

    /**
     * @Description judge the object isn't null
     *
     * @param obj object name
     * @return isn't null
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
