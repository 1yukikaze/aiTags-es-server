package com.yukikaze.webuiesdemo.service;

import java.util.List;

public interface IEsService {
    List<String> getTermTags(String chineseTags);

    List<String> getMatchTags(String chineseTags);

    List<String> getSuggestions(String pinyinTags);

}
