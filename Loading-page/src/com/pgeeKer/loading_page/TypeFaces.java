package com.pgeeKer.loading_page;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;

public class TypeFaces {
	public static final Hashtable<String, Typeface> typaface = new Hashtable<String, Typeface>();
	
	public static Typeface getTypeface(Context context, String typefacePath){
		synchronized (typefacePath) {
			if (!typaface.containsKey(typefacePath)) {
				try {
					Typeface t = Typeface.createFromAsset(context.getAssets(), typefacePath);
					typaface.put(typefacePath, t);
				} catch (Exception e) {
					
				}
			}
		}
		return typaface.get(typefacePath);
	}
}
