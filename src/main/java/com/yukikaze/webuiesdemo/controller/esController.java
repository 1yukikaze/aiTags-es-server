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

    /**精准查询
     * get请求接收chineseTags参数并转换成英文tags
     * @param chineseTags 中文tags
     * @return 英文tags
     */
    @GetMapping("/termTags")
    public List<String> getTermTags(@RequestParam("chineseTags") String chineseTags) {
        return esService.getTermTags(chineseTags);
    }

    /**模糊查询
     * get请求接收chineseTags参数并转换成英文tags
     * @param chineseTags 中文tags
     * @return 英文tags
     */
    @GetMapping("/matchTags")
    public List<String> getMatchTags(@RequestParam("chineseTags") String chineseTags){
        return esService.getMatchTags(chineseTags);
    }







    /**================================未实现功能=================================*/
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
