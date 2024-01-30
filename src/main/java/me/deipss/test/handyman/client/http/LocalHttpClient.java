package me.deipss.test.handyman.client.http;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class LocalHttpClient<T> {

    @Autowired
    @Qualifier(HttpClientConfiguration.CLOSEABLE_HTTP_CLIENT)
    private HttpClient httpClient;


    public HttpResult post(String url, T body, List<Header> headers) {
        HttpPost post = new HttpPost(url);
        HttpResult result = new HttpResult();
        if (null != headers && headers.size() > 0) {
            headers.forEach(post::addHeader);
        }
        post.addHeader("Content-Type", "application/json");
        try {
            HttpEntity httpEntity = new StringEntity(JSON.toJSONString(body), StandardCharsets.UTF_8);
            post.setEntity(httpEntity);
            HttpResponse response = httpClient.execute(post);
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                result.setSuccess(true);
                result.setHttpStatus(code);
                result.setData(EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8));
                return result;
            }
        } catch (IOException e) {
            log.error("http post异常，url={}", url, e);
        }
        result.setSuccess(false);
        return result;
    }

    public  HttpResult get(String url, Map<String, String> params, List<Header> headers) {

        HttpGet httpGet = new HttpGet(url);
        HttpResult result = new HttpResult();
        try {

            if (null != headers && !headers.isEmpty()) {
                headers.forEach(httpGet::addHeader);
            }
            if (null != params && !params.isEmpty()) {
                URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
                httpGet.setURI(uriBuilder.build());
            }
            HttpResponse response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code == HttpStatus.SC_OK) {
                result.setSuccess(true);
                result.setHttpStatus(code);
                result.setData(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
                return result;
            }
        } catch (IOException e) {
            log.error("http get error，url={},params={}", url, params, e);
        } catch (URISyntaxException e) {
            log.error("http get build uri error，url={},params={}", url, params, e);
        }
        result.setSuccess(false);
        return result;
    }

}
