package com.yukikaze.webuiesdemo;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static com.yukikaze.webuiesdemo.constants.esTranslationIndexConstants.MAPPING_TEXT1;

@SuppressWarnings({"FieldCanBeLocal", "SpellCheckingInspection"})
@SpringBootTest
class IndexTest {
    @Autowired
    public RestHighLevelClient client;

    /**
     * 索引库的创建和删除方法,谨慎使用
     */
    private final String esIndex = MAPPING_TEXT1; //索引库
    //指定索引库,索引库存放在src/main/java/com/yukikaze/webuiesdemo/constants

    @Value("es_translation_index")//指定创建的索引库的名称
    private String createEsIndex;
    @Test//新建索引库方法
    void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(createEsIndex);
        //准备请求的参数：DSL语句
        request.source(esIndex, XContentType.JSON);
        //发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Value("es_translation_index")//指定删除的索引库的名称
    private String deleteEsIndex;
    @Test//删除索引库方法
    void deleteIndex() throws IOException {
        //创建Request对象
        DeleteIndexRequest request = new DeleteIndexRequest(deleteEsIndex);
        //发送请求
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

}
