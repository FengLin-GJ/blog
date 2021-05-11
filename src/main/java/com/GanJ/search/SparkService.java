package com.GanJ.search;

import com.GanJ.component.StringAndArray;
import com.GanJ.es.EsArticle;
import com.GanJ.utils.DataMap;
import com.GanJ.utils.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;


import java.io.*;
import java.util.*;

/**
 * @author GanJ
 * @date 2021/4/7 20:25
 * Describe:
 */
@Service
public class SparkService {

    @Autowired
    private SearchArticles searchArticles;

    public Boolean modifyFile(String filePath, String s) {
        List<String> arrayList = new ArrayList<>();
        String line = "";
        boolean isWrite = true;
        try {
            BufferedReader input = new BufferedReader(new FileReader(filePath));
            while ((line = input.readLine()) != null) {
                arrayList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            isWrite = false;
        }
        if (arrayList != null && arrayList.size() != 0 && arrayList.size() == 50) {
            arrayList.remove(0);
        }
        arrayList.add(s);
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(filePath));
            for (String a : arrayList) {
                output.write(a + "\r\n");
                output.flush();
            }
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            isWrite = false;
        }
        return isWrite;
    }

    public Map<String, Integer> labelCount(String username) throws FileNotFoundException {
        FileUtil fileUtil = new FileUtil();
        String url = fileUtil.downloadFile(username, "user/avatar/" + username);

        //配置执行
        SparkConf conf = new SparkConf().setAppName("Java_WordCount").setMaster("local");

        // 创建SparkContext对象:  JavaSparkContext
        JavaSparkContext context = new JavaSparkContext(conf);

        //读入数据
        JavaRDD<String> lines = context.textFile(url);

        //分词
        //       @Override
        JavaRDD<String> words = lines.flatMap((FlatMapFunction<String, String>) line -> Arrays.asList(line.split(",")).iterator());

        //每个单词记一次数
        //       @Override
        JavaPairRDD<String, Integer> wordOne = words.mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1));

        //执行reduceByKey的操作
        JavaPairRDD<String, Integer> count = wordOne.reduceByKey((Function2<Integer, Integer, Integer>) (i1, i2) -> i1 + i2);

        //新需求按单词频数降序排序（king，15）需要转换成（15，king）
        JavaPairRDD<Integer, String> countWords = count.mapToPair(word -> new Tuple2<>(word._2, word._1));//键值对互换

        JavaPairRDD<Integer, String> sortedWords = countWords.sortByKey(false);

        JavaPairRDD<String, Integer> countWords2 = sortedWords.mapToPair(word2 -> new Tuple2<>(word2._2, word2._1));//键值换回来


        //只输出出现最多的前5个标签
        List<Tuple2<String, Integer>> result = countWords2.take(5);

        Map<String, Integer> resultMap = new LinkedHashMap<>();

        //输出
        for (Tuple2<String, Integer> tuple : result) {
            resultMap.put(tuple._1, tuple._2);
        }

        //停止SparkContext对象
        context.stop();

        File file = new File(url);
        if (file.exists()) {
            file.delete();
        }

        return resultMap;
    }

    public Map<String, Integer> weightRatio(Map<String, Integer> map) {
        Integer num = 0;
        for (Integer value : map.values()) {
            num += value;
        }
        Iterator<Map.Entry<String, Integer>> m = map.entrySet().iterator();
        while (m.hasNext()) {
            Map.Entry<String, Integer> entry = m.next();
            int i = (int) Math.round(((double) entry.getValue() / (double) num) * 10);
            map.put(entry.getKey(), i);
        }
        return map;
    }

    public DataMap comAllArticles(Map<String, Integer> comMap) {

        Integer rows = 0;
        for (Integer value : comMap.values()) {
            rows += value;
        }
        PageHelper.startPage(1, rows);

        List<EsArticle> esArticles = new LinkedList<>();
        for (String key : comMap.keySet()) {
            if (comMap.get(key) != 0) {
                List<EsArticle> sArticles;
                sArticles = searchArticles.esQuery(comMap.get(key), "articletags", key);
                for (EsArticle es : sArticles) {
                    esArticles.add(es);
                }
            }
        }
        HashSet<EsArticle> newES = new HashSet<>(esArticles);
        List<EsArticle> esArticles_ = new LinkedList<>(newES);

        PageInfo<EsArticle> pageInfo = new PageInfo<>(esArticles_);
        List<Map<String, Object>> newArticles = new ArrayList<>();
        Map<String, Object> map;
        for (EsArticle esArticle : esArticles_) {
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
        map = new HashMap<>();
        for (String key : comMap.keySet()) {
            map.put(key, comMap.get(key));
        }
        newArticles.add(map);
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

}
