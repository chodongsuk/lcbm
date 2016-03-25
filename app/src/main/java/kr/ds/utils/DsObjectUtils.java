package kr.ds.utils;

import java.util.List;
import java.util.Map;

/**
 * ObjectUtils
 * 
 * @author Chodongsuk
 * @since 2015.02.02
 * 
 */
public class DsObjectUtils {
	public static boolean isEmpty(Object s) {
		if (s == null) {
			return true;
		}
		if ((s instanceof String) && (((String) s).trim().length() == 0)) {
			return true;
		}
		if (s instanceof Map) {
			return ((Map<?, ?>) s).isEmpty();
		}
		if (s instanceof List) {
			return ((List<?>) s).isEmpty();
		}
		return false;
	}
}
