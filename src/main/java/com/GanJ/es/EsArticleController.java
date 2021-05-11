package com.GanJ.es;

import com.GanJ.constant.CodeType;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GanJ
 * @date 2021/3/30 14:55
 * Describe:
 */
@RestController
@Slf4j
public class EsArticleController {

    @Autowired
    EsArticleService esArticleService;

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String searchArticles(@RequestParam("rows") int rows,
                                 @RequestParam("pageNum") int pageNum,
                                 @RequestParam("name") String name,
                                 @RequestParam("value") String value) {
        try {
            DataMap data = esArticleService.findAllArticles(pageNum, rows, name, value);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("Homepage get paged article exception", e);
        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}
