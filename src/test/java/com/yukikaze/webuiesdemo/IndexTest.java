package com.yukikaze.webuiesdemo;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.yukikaze.webuiesdemo.constants.esTranslationIndexConstants.MAPPING_TEXT1;

@SpringBootTest
class IndexTest {
    @Autowired
    public RestHighLevelClient client;

    @Test//新建索引库方法
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("es_translation_index");
        //准备请求的参数：DSL语句
        request.source(MAPPING_TEXT1, XContentType.JSON);
        //发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test//删除索引库方法
    void deleteIndex() throws IOException {
        //创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest("es_translation_index");
        //发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

}
