package com.madXdesign.superofertas.utils;

import java.text.NumberFormat;
import java.util.Date;

import android.content.Context;
import android.text.format.DateFormat;

public class Formatter {
	public static String money(Number n) {
		return NumberFormat.getCurrencyInstance().format(n.doubleValue());
	}
	
	public static String date(Date d, Context c) {
	    return DateFormat.getDateFormat(c).format(d);
	}
}
