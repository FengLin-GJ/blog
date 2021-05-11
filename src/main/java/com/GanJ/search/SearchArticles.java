package com.GanJ.search;

import com.GanJ.es.EsArticle;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author GanJ
 * @date 2021/4/7 19:56
 * Describe:
 */
@Service
public class SearchArticles {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<EsArticle> esQuery(int rows, String name, String value) {
        if (rows!=0){
            NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                    //查询条件
                    .withQuery(QueryBuilders.matchPhraseQuery(name, value))
                    //分页
                    .withPageable(PageRequest.of(0, rows))
                    //排序
                    .withSort(SortBuilders.fieldSort("likes").order(SortOrder.DESC))
                    .build();

            return elasticsearchTemplate.queryForList(nativeSearchQuery, EsArticle.class);
        }else {
            return null;
        }
    }
}
