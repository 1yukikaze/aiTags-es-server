package com.yukikaze.webuiesdemo.service;

import java.util.List;
import java.util.Map;

public interface IEsService {
    List<String> getTermTags(String chineseTags);

    List<String> getMatchTags(String chineseTags);

    Map<String, String> getListTags(String chineseTags);

    List<String> getSuggestions(String pinyinTags);

}
