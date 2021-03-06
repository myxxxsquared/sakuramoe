package sakuramoe;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Util {
	public static String htmlEncode(String source) {
		if (source == null) {
			return "";
		}
		String html = "";
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			switch (c) {
			case '<':
				buffer.append("&lt;");
				break;
			case '>':
				buffer.append("&gt;");
				break;
			case '&':
				buffer.append("&amp;");
				break;
			case '"':
				buffer.append("&quot;");
				break;
			case 10:
			case 13:
				buffer.append(' ');
				break;
			default:
				buffer.append(c);
			}
		}
		html = buffer.toString();
		return html;
	}

	static class PasswordSHA {
		final static String SALT = ".30c8h*630_&3/3fbpwfd'";

		static byte[] passwordSHA(String password) {
			MessageDigest md;

			try {
				md = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("Cannot find SHA-256", e);
			}

			md.update((password + SALT).getBytes());
			byte[] savepassword = md.digest();

			return savepassword;
		}
	}

	static class HexString {
		static String bytesToHexString(byte[] src) {
			StringBuilder stringBuilder = new StringBuilder("");
			if (src == null || src.length <= 0) {
				return null;
			}
			for (int i = 0; i < src.length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					stringBuilder.append(0);
				}
				stringBuilder.append(hv);
			}
			return stringBuilder.toString();
		}

		static byte[] hexStringToBytes(String hexString) {
			if (hexString == null || hexString.equals("")) {
				return null;
			}
			hexString = hexString.toUpperCase();
			int length = hexString.length() / 2;
			char[] hexChars = hexString.toCharArray();
			byte[] d = new byte[length];
			for (int i = 0; i < length; i++) {
				int pos = i * 2;
				d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
			}
			return d;
		}

		private static byte charToByte(char c) {
			return (byte) "0123456789ABCDEF".indexOf(c);
		}
	}

	public static class CookieReader {
		public static String readCookie(Cookie[] cookies, String name) {
			if (cookies == null)
				return null;

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name))
					return cookie.getValue();
			}
			return null;
		}
	}

	public static String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
