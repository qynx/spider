package xyz.zix.spider.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.zix.spider.exceptions.CommonException;
import xyz.zix.spider.exceptions.RsNotFoundException;
import xyz.zix.spider.repo.vo.ZixRsp;

@Component
@org.springframework.web.bind.annotation.ControllerAdvice
public class ZixControllerAdvice {

    @ExceptionHandler(RsNotFoundException.class)
    @ResponseBody
    public Object notFound(RsNotFoundException rsNotFoundException) {
        ResponseEntity<ZixRsp> entity = new ResponseEntity<>(
                ZixRsp.ofFail(404, "access resource does not exist"),
                HttpStatus.NOT_FOUND
        );
        return entity;
    }


    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public Object notFound(CommonException commonException) {
        ResponseEntity<ZixRsp> entity = new ResponseEntity<>(
                ZixRsp.ofFail(400, commonException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
        return entity;
    }
}
