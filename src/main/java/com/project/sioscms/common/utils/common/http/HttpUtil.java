package com.project.sioscms.common.utils.common.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
            if (!model.isEmpty()) {
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
}
