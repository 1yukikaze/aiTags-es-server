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

@Service
@Slf4j
public class esService implements IEsService {
    @Autowired
    private RestHighLevelClient client;
    @Value("${esIndex}")
    private String indexName;

    @Override
    public List<String> getTags(String chineseTags) {
        //判断字符串中是否带有",",如果有为精准查询,拆分字符串,没有则为模糊查询
        boolean b = chineseTags.contains(",");
        if ("".equals(chineseTags)) {
            return null;
        }
        if (b) {
            //精准查询
            String[] tags = chineseTags.split(",");
            List<String> list = new ArrayList<>();
            //遍历逐个查询 后续可能会优化
            for (String tag : tags) {
                try {
                    SearchRequest request = new SearchRequest(indexName);
                    request.source().query(QueryBuilders.termQuery("chineseTag", tag));
                    //发送请求
                    SearchResponse response = client.search(request, RequestOptions.DEFAULT);
                    //解析响应
                    SearchHits searchHits = response.getHits();
                    //文档数组
                    SearchHit[] hits = searchHits.getHits();
                    // 4.3.遍历
                    for (SearchHit hit : hits) {
                        // 获取文档source
                        String json = hit.getSourceAsString();
                        JsonDoc jsonDoc = JSON.parseObject(json, JsonDoc.class);
                        //反序列化
                        String englishTag = jsonDoc.getEnglishTag();
                        list.add(englishTag);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            log.info("精准查询成功,返回结果:" + list);
            return list;
        } else {
            //模糊查询
            SearchRequest request = new SearchRequest(indexName);
            request.source().query(QueryBuilders.matchQuery("chineseTag", chineseTags));
            //发送请求
            SearchResponse response;
            try {
                response = client.search(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //解析响应
            SearchHits searchHits = response.getHits();
            SearchHit[] hits = searchHits.getHits();
            List<String> list = new ArrayList<>();
            for (SearchHit hit : hits) {
                // 获取文档source
                String json = hit.getSourceAsString();
                JsonDoc jsonDoc = JSON.parseObject(json, JsonDoc.class);
                //反序列化
                String englishTag = jsonDoc.getEnglishTag();
                list.add(englishTag);
                log.info("模糊查询成功,返回结果:" + list);
            }
            return list;
        }
    }

    @Override
    public List<String> getSuggestions(String pinyinTags) {
        return null;
    }
}
