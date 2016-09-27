package com.netease.welkin.web;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Session工具类
 * 
 * @author chen liu
 */
public abstract class SessionUtil {
    private SessionUtil() {
    }

    /**
     * Cookie名称
     */
    private static final String SESSION_ID = "NETEASE_LIVE_SESSION_ID";

    /**
     * 获取Session(从Cookie中获取session id)
     * 
     * @param req HttpServletRequest
     * @return BbsSession
     */
    public static BbsSession getSession(HttpServletRequest req) {
        return getSession(req, null);
    }

    /**
     * 获取Session(从Cookie中获取session id)
     * 
     * @param req HttpServletRequest
     * @param res HttpServletResponse 当res不为空时，如果session不存在，会自动创建，并生成cookie
     * @return BbsSession
     */
    public static BbsSession getSession(HttpServletRequest req, HttpServletResponse res) {
        String sid = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equalsIgnoreCase(SESSION_ID)) {
                    sid = c.getValue();
                }
            }
        }
        if (sid == null) {
            // 找不到session
            if (res != null) {
                sid = UUID.randomUUID().toString();

                // 创建BBS_SESSION_ID的Cookie
                Cookie cookie = new Cookie(SESSION_ID, sid);
//                Cookie cookie = new Cookie("", sid);
                cookie.setMaxAge(-1);
                cookie.setDomain(req.getServerName());
                System.err.println("domain="+cookie.getDomain());
                cookie.setPath("/");
                res.addCookie(cookie);
            } else {
                return null;
            }
        }
        return new BbsSessionImpl(sid);
    }
}
