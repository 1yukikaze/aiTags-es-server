package com.yukikaze.webuiesdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.yukikaze.webuiesdemo.pojo.JsonDoc;
import com.yukikaze.webuiesdemo.service.IEsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class esService implements IEsService {
    @Autowired
    private RestHighLevelClient client;

    @Value("${esIndex}")//termTags与matchTags接口查询索引库
    private String indexName;
    @Value("${listEsIndex}")//ListTags接口列表查询索引库
    private String listIndexName;
    /**
     * 返回List类型英语tags通用解析方法
     *
     * @param response 返回的请求
     * @return list集合
     */
    private List<String> responseDispose(SearchResponse response) {
        List<String> list = new ArrayList<>();
        //解析响应
        SearchHits searchHits = response.getHits();
        //文档数组
        SearchHit[] hits = searchHits.getHits();
        //遍历
        for (SearchHit hit : hits) {
            // 获取文档
            String json = hit.getSourceAsString();
            JsonDoc jsonDoc = JSON.parseObject(json, JsonDoc.class);
            //反序列化
            String englishTag = jsonDoc.getEnglishTag();
            list.add(englishTag);
        }
        return list;
    }

    /**
     * 精准查询
     *
     * @param chineseTags 中文tags
     * @return 英文tag的List集合
     */
    @Override
    public List<String> getTermTags(String chineseTags) {
        //判断是否带有逗号作为分割符号
        boolean b = chineseTags.contains(",");
        if ("".equals(chineseTags)) {
            log.info("输入空格无效");
            return List.of("error");

        } else if (b) {
            //精准查询
            String[] tags = chineseTags.split(",");
            List<String> allList = new ArrayList<>();
            //遍历逐个查询 后续可能会优化
            for (String tag : tags) {
                try {
                    SearchRequest request = new SearchRequest(indexName);
                    request.source().query(QueryBuilders.termQuery("chineseTag", tag));
                    //发送请求
                    SearchResponse response = client.search(request, RequestOptions.DEFAULT);

                    List<String> list = responseDispose(response);
                    allList.addAll(list);
                    log.info("精准查询成功,返回结果:" + list);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return allList;
        } else {
            try {
                //只查询单个
                SearchRequest request = new SearchRequest(indexName);
                request.source().query(QueryBuilders.termQuery("chineseTag", chineseTags));
                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                List<String> list = responseDispose(response);
                log.info("精准查询成功,返回结果:" + list);
                return list;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 模糊查询 按相关度降序组合字符串
     *
     * @param chineseTags 中文tags
     * @return 返回list集合
     */
    @Override
    public List<String> getMatchTags(String chineseTags) {
        //模糊查询
        SearchRequest request = new SearchRequest(indexName);
        request.source().query(QueryBuilders.matchQuery("chineseTag", chineseTags));
        //发送请求
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            List<String> list = responseDispose(response);
            log.info("模糊查询成功,返回结果:" + list);
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 相关度检索 根据相关度返回对应的词缀列表
     *
     * @param chineseTags 中文字符串
     * @return 返回map集合
     */
    @Override
    public Map<String, String> getListTags(String chineseTags) {
        SearchRequest request = new SearchRequest(listIndexName);
        request.source().query(QueryBuilders.matchQuery("chineseTag", chineseTags));
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            Map<String, String> map = new TreeMap<>();
            //解析响应
            SearchHits searchHits = response.getHits();
            //文档数组
            SearchHit[] hits = searchHits.getHits();
            //遍历
            for (SearchHit hit : hits) {
                // 获取文档
                String json = hit.getSourceAsString();
                JsonDoc jsonDoc = JSON.parseObject(json, JsonDoc.class);
                //反序列化
                String chineseTag = jsonDoc.getChineseTag();
                String englishTag = jsonDoc.getEnglishTag();
                map.put(chineseTag, englishTag);
            }
            log.info("相关度查询完成,返回结果:" + map);
            return map;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> getSuggestions(String pinyinTags) {
        return null;
    }
}
