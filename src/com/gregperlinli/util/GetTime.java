package com.gregperlinli.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gregperlinli
 * @Description get times
 */
public class GetTime {
    private static final int MIDNIGHT = 0 , NOON = 12, SUNSET = 18, TONIGHT = 20;

    public static String getMorAftEveNig() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String str = sdf.format(date);

        int time = Integer.parseInt(str);

        if ( time >= MIDNIGHT && time < NOON ) {
            return "morning";
        } else if ( time >= NOON && time < SUNSET ) {
            return "afternoon";
        } else if ( time >= SUNSET && time < TONIGHT ) {
            return "evening";
        } else {
            return "night";
        }
    }
}
