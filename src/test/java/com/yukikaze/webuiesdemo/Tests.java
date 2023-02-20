package com.yukikaze.webuiesdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.*;

@SpringBootTest
class Tests {

    //properties value值 两两拼接成一个key:value
    @Test
    void propTest() throws IOException {
        Properties prop = new Properties();
        InputStream inputStream = new FileInputStream("D:\\MyProjects\\spring_cloud\\webui-es-demo\\src\\test\\resources\\property-tooltt.properties");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        prop.load(inputStreamReader);
        SortedMap sortedMap = new TreeMap(prop);
        Set set = sortedMap.keySet();
        Iterator it = set.iterator();
        Map<String, String> map = new LinkedHashMap<>();
        while (it.hasNext()) {
            String key = prop.getProperty(it.next().toString());
            String value = prop.getProperty(it.next().toString());
            map.put(key, value);
        }
        System.out.println(map.toString());

        Properties properties = new Properties();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        System.out.println(properties.toString());
        OutputStream stream = new FileOutputStream("D:\\MyProjects\\spring_cloud\\webui-es-demo\\prop333.properties");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(stream, "UTF-8");
        properties.store(outputStreamWriter,"填充数据");
    }

}
