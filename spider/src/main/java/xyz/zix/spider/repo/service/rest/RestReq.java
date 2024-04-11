package xyz.zix.spider.repo.service.rest;

import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import xyz.zix.spider.repo.utils.JsonUtils;

import java.io.Serializable;
import java.util.Map;


@Data
public class RestReq implements Serializable {

    private HttpMethod method = HttpMethod.GET;
    private String url;
    private HttpHeaders headers = new HttpHeaders();
    private Map<String, Object> param;
    private String body;
    private String decodeEncoding;

    private Long rspLogId;
    private HttpHeaders rspHeader;
    private String rspBody;
    private Integer rspStatusCode;
    private Exception rspEx;

    @SneakyThrows
    public String getBody() {
        if (null != rspEx) {
            throw rspEx;
        }
        return body;
    }


    public <T> T body(Class<T> type) {
        return JsonUtils.fromJson(getBody(), type);
    }

}
