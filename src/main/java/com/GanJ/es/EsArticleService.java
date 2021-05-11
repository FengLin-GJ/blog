package com.GanJ.es;

import com.GanJ.component.StringAndArray;
import com.GanJ.utils.DataMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GanJ
 * @date 2021/3/30 16:13
 * Describe:
 */
@Service
public class EsArticleService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public DataMap findAllArticles(int pageNum, int rows, String name, String value) {

        PageHelper.startPage(pageNum, rows);

        List<EsArticle> esArticles = esQuery(pageNum - 1, rows, name, value);
        if (esArticles == null || esArticles.size() == 0) {
            return DataMap.success().setData(null);
        }
        PageInfo<EsArticle> pageInfo = new PageInfo<>(esArticles);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;
        for (EsArticle esArticle : esArticles) {
            map = new HashMap<>();
            map.put("thisArticleUrl", "/article/" + esArticle.getArticleId());
            map.put("articleTags", StringAndArray.stringToArray(esArticle.getArticleTags()));
            map.put("articleTitle", esArticle.getArticleTitle());
            map.put("articleType", esArticle.getArticleType());
            map.put("publishDate", esArticle.getPublishDate());
            map.put("originalAuthor", esArticle.getOriginalAuthor());
            map.put("articleCategories", esArticle.getArticleCategories());
            map.put("articleTabloid", esArticle.getArticleTabloid());
            map.put("likes", esArticle.getLikes());
            newArticles.add(map);
        }
        JSONArray jsonArray = JSONArray.fromObject(newArticles);
        Map<String, Object> thisPageInfo = new HashMap<>();
        thisPageInfo.put("pageNum", pageInfo.getPageNum());
        thisPageInfo.put("pageSize", pageInfo.getPageSize());
        thisPageInfo.put("total", pageInfo.getTotal());
        thisPageInfo.put("pages", pageInfo.getPages());
        thisPageInfo.put("isFirstPage", pageInfo.isIsFirstPage());
        thisPageInfo.put("isLastPage", pageInfo.isIsLastPage());

        jsonArray.add(thisPageInfo);
        return DataMap.success().setData(jsonArray);
    }

    public List<EsArticle> esQuery(int pageNum, int rows, String name, String value) {
        if (rows != 0) {
            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    //查询条件
                    .withQuery(QueryBuilders.matchPhraseQuery(name, value))
                    //分页
                    .withPageable(PageRequest.of(pageNum, rows))
                    //排序
                    .withSort(SortBuilders.fieldSort("likes").order(SortOrder.DESC))
                    .build();

            return elasticsearchTemplate.queryForList(nativeSearchQuery, EsArticle.class);
        } else {
            return null;
        }
    }

}
