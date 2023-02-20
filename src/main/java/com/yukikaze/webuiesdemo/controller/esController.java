package com.yukikaze.webuiesdemo.controller;

import com.yukikaze.webuiesdemo.service.IEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/index")
public class esController {
    @Autowired
    private IEsService esService;

    /**
     * get方式接收chineseTags参数并转换成英文tags
     *
     * @param chineseTags 中文tags
     * @return 英文tags
     */
    @GetMapping("/tags")
    public List<String> getTags(@RequestParam("chineseTags") String chineseTags) {
        return esService.getTags(chineseTags);
    }

    /**
     * 搜索框检索时,获取输入拼音,返回相似中文tags
     * @param pinyinTags 拼音
     * @return 中文tags
     */
    @GetMapping("/pinyin")
    public List<String> getSuggestions(@RequestParam("pinyinTags") String pinyinTags) {
        return esService.getSuggestions(pinyinTags);
    }
}
