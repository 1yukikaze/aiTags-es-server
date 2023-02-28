package com.yukikaze.webuiesdemo;

import com.alibaba.fastjson.JSON;
import com.yukikaze.webuiesdemo.pojo.TranslationDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
class DocumentTest {
    public RestHighLevelClient client;

    @Value("${esHttp}")//接入搜索引擎的地址
    private String esHttp;
    @BeforeEach
    public void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create(esHttp)
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    /**
     * 数据导入索引库的方法
     */
    @Value("src/test/resources/Tags3.0.properties")//要传入的文件地址
    private String propInputStream;
    @Value("list_es_translation_index")//要传入的索引库
    private String index;
    @Test//读取prop添加文件
    void testAddDocument() throws IOException {
        Properties prop = new Properties();
        //IO流获取文件
        InputStream inputStream = new FileInputStream(propInputStream);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        prop.load(inputStreamReader);
        //测试
        System.out.println(prop);

        //遍历存入对象
        Set<Object> keySet = prop.keySet();
        BulkRequest request = new BulkRequest(); //导入方法
        for (Object key: keySet) {
            TranslationDoc translationDoc = new TranslationDoc();
            translationDoc.setChineseTag((String) key);
            translationDoc.setEnglishTag((String) prop.get(key));
            //测试
            System.out.println(translationDoc);

            request.add(new IndexRequest(index)
                            .id(UUID.randomUUID().toString())
                            .source(JSON.toJSONString(translationDoc), XContentType.JSON));
        }
        client.bulk(request, RequestOptions.DEFAULT);
    }


}
