package com.yukikaze.webuiesdemo.constants;

//索引库创建json,创建方法在test目录下
@SuppressWarnings("unused")
public class esTranslationIndexConstants {
    public static final String MAPPING_TEXT1 = """
            {
              "settings": {
                "analysis": {
                  "analyzer": {
                    "text_analysis":{
                      "tokenizer": "ik_smart",
                      "filter": "py"
                    },
                    "completion_analyzer": {
                      "tokenizer": "keyword",
                      "filter": "py"
                    }
                  },
                  "filter":{
                    "py":{
                      "type": "pinyin",
                      "keep_full_pinyin": false,
                      "keep_joined_full_pinyin": true,
                      "keep_original": true,
                      "limit_first_letter_length": 16,
                      "remove_duplicated_term": true,
                      "none_chinese_pinyin_tokenize": false
                    }
                  }
                }
              },
              "mappings": {
                "properties": {
                  "chineseTag":{
                    "type": "text",
                    "index": true,\s
                    "analyzer": "text_analysis",
                    "search_analyzer": "ik_smart"
                  },
                  "englishTag":{
                    "type": "keyword",
                    "index": false
                  },
                  "suggestion":{
                    "type": "completion",
                    "analyzer": "completion_analyzer"
                  }
                }
              }
            }""";

    public static final String MAPPING_TEXT2= """
            PUT /es_translation_index
            {
              "settings": {
                "analysis": {
                  "analyzer": {
                    "text_analysis":{
                      "tokenizer": "ik_smart",
                      "filter": "py"
                    },
                    "completion_analyzer": {
                      "tokenizer": "keyword",
                      "filter": "py"
                    }
                  },
                  "filter":{
                    "py":{
                      "type": "pinyin",
                      "keep_full_pinyin": false,
                      "keep_joined_full_pinyin": true,
                      "keep_original": true,
                      "limit_first_letter_length": 16,
                      "remove_duplicated_term": true,
                      "none_chinese_pinyin_tokenize": false
                    }
                  }
                }
              },
              "mappings": {
                "properties": {
                  "chineseTag":{
                    "type": "text",
                    "index": true,
                    "analyzer": "text_analysis",
                    "search_analyzer": "ik_max_word"
                  },
                  "englishTag":{
                    "type": "keyword",
                    "index": false
                  },
                  "suggestion":{
                    "type": "completion",
                    "analyzer": "completion_analyzer"
                  }
                }
              }
            }""";
}
