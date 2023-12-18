package com.project.sioscms.common.utils.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Objects;

@Slf4j
@Component
public class HttpUtil {
    public static void alertAndRedirect(HttpServletResponse response, String url, String message, ModelMap model) {
        if (response == null) {
            throw new NullPointerException();
        }

        response.setContentType("text/html; charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            StringBuilder sb = new StringBuilder();

            sb.append("<form name='redirect_form' method='post' action='").append(url).append("'>");
            sb.append("</form>");
            if (model != null && !model.isEmpty()) {
                Iterator<String> it = model.keySet().iterator();
                if (it.hasNext()) {
                    String key = it.next();
                    if (!ObjectUtils.isEmpty(model.getAttribute(key))) {
                        String value = Objects.requireNonNull(model.getAttribute(key)).toString();
                        sb.append("<input type='hidden' name='").append(key.toLowerCase()).append("' value='").append(value).append("' />");
                    }
                }
            }
            sb.append("<script>");
            if (StringUtils.hasText(message))
                sb.append("    alert('").append(message).append("');");
            sb.append("    document.redirect_form.submit();");
            sb.append("</script>");

            out.println(sb);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-RealIP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("REMOTE_ADDR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)) {
            InetAddress address = null;
            try {
                address = InetAddress.getLocalHost();
                ip = address.getHostName() + "/" + address.getHostAddress();
            } catch (UnknownHostException e) {
                return ip;
            }
        }
        return ip;
    }
}
