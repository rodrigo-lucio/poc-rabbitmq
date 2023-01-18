package br.com.lucio.order.infra.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.Instant;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        Instant startTime = Instant.now();
        filterChain.doFilter(requestWrapper, responseWrapper);
        Duration totalTime = Duration.between(startTime, Instant.now());
        String requestBody = getStringValue(requestWrapper.getContentAsByteArray(),
                request.getCharacterEncoding());
        requestBody = requestBody.replaceAll("\\s*[\\r\\n]+\\s*", "").trim();

        String responseBody = getStringValue(responseWrapper.getContentAsByteArray(), response.getCharacterEncoding());

        log.info("Request processed: URI={}, Method={}, Payload={}; Response code={}, Response={}, Duration={}s",
                request.getRequestURI(), request.getMethod(), requestBody, response.getStatus(), responseBody, totalTime.getSeconds());
        responseWrapper.copyBodyToResponse();
    }

    private String getStringValue(byte[] contentAsByteArray, String characterEncoding) {
        try {
            return new String(contentAsByteArray, 0, contentAsByteArray.length, characterEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
