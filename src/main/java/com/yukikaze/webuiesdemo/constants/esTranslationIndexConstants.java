package com.yukikaze.webuiesdemo.constants;

//索引库创建json,创建方法在test目录下
public class esTranslationIndexConstants {
    public static final String MAPPING_TEXT1 = "{\n" +
            "  \"settings\": {\n" +
            "    \"analysis\": {\n" +
            "      \"analyzer\": {\n" +
            "        \"text_analysis\":{\n" +
            "          \"tokenizer\": \"ik_smart\",\n" +
            "          \"filter\": \"py\"\n" +
            "        },\n" +
            "        \"completion_analyzer\": {\n" +
            "          \"tokenizer\": \"keyword\",\n" +
            "          \"filter\": \"py\"\n" +
            "        }\n" +
            "      },\n" +
            "      \"filter\":{\n" +
            "        \"py\":{\n" +
            "          \"type\": \"pinyin\",\n" +
            "          \"keep_full_pinyin\": false,\n" +
            "          \"keep_joined_full_pinyin\": true,\n" +
            "          \"keep_original\": true,\n" +
            "          \"limit_first_letter_length\": 16,\n" +
            "          \"remove_duplicated_term\": true,\n" +
            "          \"none_chinese_pinyin_tokenize\": false\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"chineseTag\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"index\": true, \n" +
            "        \"analyzer\": \"text_analysis\",\n" +
            "        \"search_analyzer\": \"ik_smart\"\n" +
            "      },\n" +
            "      \"englishTag\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"suggestion\":{\n" +
            "        \"type\": \"completion\",\n" +
            "        \"analyzer\": \"completion_analyzer\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";
}
