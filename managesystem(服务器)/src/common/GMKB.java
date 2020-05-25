package common;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class GMKB {
	private static DecimalFormat df = null;
	static {
		// �������ָ�ʽ������һλ��ЧС��
		df = new DecimalFormat("#0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);
	}
	
	//����ļ��Ĵ�С��ʽ��
	public static String getFormatFileSize(long length) {
		double size = ((double) length) / (1 << 30);
		if (size >= 1) {
			return df.format(size) + "GB";
		}
		size = ((double) length) / (1 << 20);
		if (size >= 1) {
			return df.format(size) + "MB";
		}
		size = ((double) length) / (1 << 10);
		if (size >= 1) {
			return df.format(size) + "KB";
		}
		return length + "B";
	}
}
