package com.yukikaze.webuiesdemo.service;

import java.util.List;

public interface IEsService {
    List<String> getTags(String chineseTags);

    List<String> getSuggestions(String pinyinTags);
}
