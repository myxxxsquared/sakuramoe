package sakuramoe;

import javax.servlet.http.Cookie;

public class CookieReader {
	public static String readCookie(Cookie[] cookies, String name) {
		if (cookies == null)
			return null;

		for (Cookie cookie : cookies) {
			if (cookie.getName() == name)
				return cookie.getValue();
		}
		return null;
	}
}
