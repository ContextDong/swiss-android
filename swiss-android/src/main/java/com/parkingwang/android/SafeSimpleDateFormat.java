package com.parkingwang.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 董棉生(dongmiansheng @ parkingwang.com)
 * @since 2018/5/12
 */


public class SafeSimpleDateFormat extends ThreadLocal<SimpleDateFormat> {

    private final String mPattern;

    public SafeSimpleDateFormat(String pattern) {
        this.mPattern = pattern;
    }

    @Override
    protected SimpleDateFormat initialValue() {
        return new SimpleDateFormat(mPattern);
    }

    public String format(Date date) {
        return date == null ? null : get().format(date);
    }

    public String format(long date) {
        return date == 0 ? null : get().format(new Date(date));
    }

    public Date parse(String time) {
        if (time == null) {
            return null;
        }
        try {
            return get().parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
}
