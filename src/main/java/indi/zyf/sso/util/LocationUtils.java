package indi.zyf.sso.util;

public class LocationUtils {
	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 通过经纬度获取距离(单位：米)
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance(String lalo1, String lalo2) {
		String[] t1 = lalo1.split(",");
		String[] t2 = lalo2.split(",");
		double lat1 = Double.valueOf(t1[0]);
		double lng1 = Double.valueOf(t1[1]);
		double lat2 = Double.valueOf(t2[0]);
		double lng2 = Double.valueOf(t2[1]);
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000d) / 10000d;
		return s;
	}

	public static void main(String[] args) {
		// System.out.println(getDistance("37.87059,112.548879","37.77729797363281,112.53557586669922"));
		System.out.println(getDistance("114.030616,22.597172", "112.56566,37.73605"));
	}
}
