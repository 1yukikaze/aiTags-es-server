package com.yukikaze.webuiesdemo;

import com.alibaba.fastjson.JSON;
import com.yukikaze.webuiesdemo.pojo.TranslationDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class DocumentTest {
    public RestHighLevelClient client;

    @BeforeEach
    public void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://124.223.0.216:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    @Test//读取prop添加文件
    void testAddDocument() throws IOException {
        Properties prop = new Properties();
        //IO流获取文件
        InputStream inputStream = new FileInputStream("src/test/resources/prop.properties");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        prop.load(inputStreamReader);
        System.out.println(prop.toString());
        //遍历存入对象
        Set<Object> keySet = prop.keySet();
        BulkRequest request = new BulkRequest(); //导入方法
        for (Object key: keySet) {
            TranslationDoc translationDoc = new TranslationDoc();
            translationDoc.setChineseTag((String) key);
            translationDoc.setEnglishTag((String) prop.get(key));
            System.out.println(translationDoc.toString());
            request.add(new IndexRequest("es_translation_index")
                            .id(UUID.randomUUID().toString())
                            .source(JSON.toJSONString(translationDoc), XContentType.JSON));
        }
        client.bulk(request, RequestOptions.DEFAULT);
    }


}
