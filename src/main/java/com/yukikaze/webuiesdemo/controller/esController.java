package com.yukikaze.webuiesdemo.controller;

import com.yukikaze.webuiesdemo.service.IEsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/index")
public class esController {
    @Autowired
    private IEsService esService;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 精准查询
     * get请求接收chineseTags参数并转换成英文tags
     * @param chineseTags 中文tags
     * @return 英文tags
     */
    @GetMapping("/termTags")
    public List<String> getTermTags(@RequestParam("chineseTags") String chineseTags) {
        try {
            CompletableFuture<List<String>> listCompletableFuture = CompletableFuture.supplyAsync(
                    () -> esService.getTermTags(chineseTags), threadPoolTaskExecutor);
            CompletableFuture.allOf(listCompletableFuture);
            return listCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 模糊查询
     * get请求接收chineseTags参数并转换成英文tags
     * @param chineseTags 中文tags
     * @return 英文tags
     */
    @GetMapping("/matchTags")
    public List<String> getMatchTags(@RequestParam("chineseTags") String chineseTags) {
        try {
            CompletableFuture<List<String>> listCompletableFuture = CompletableFuture.supplyAsync(
                    () -> esService.getMatchTags(chineseTags), threadPoolTaskExecutor);
            CompletableFuture.allOf(listCompletableFuture);
            return listCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 相关度检索
     * 根据输入的中文长字符检索列出有相关性的tag标签
     * @param chineseTags 中文字符串
     * @return 返回中英文tag集合
     */
    @GetMapping("/listTags")
    public Map<String, String> getListTags(@RequestParam("chineseTags") String chineseTags) {
        try {
            CompletableFuture<Map<String, String>> listCompletableFuture = CompletableFuture.supplyAsync(
                    () -> esService.getListTags(chineseTags), threadPoolTaskExecutor);
            CompletableFuture.allOf(listCompletableFuture);
            return listCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }


    /*================================未实现功能=================================*/

    /**
     * [需要网页客户端]
     * 搜索框检索时,获取输入拼音,返回相似中文tags
     * @param pinyinTags 拼音
     * @return 中文tags
     */
    @GetMapping("/pinyin")
    public List<String> getSuggestions(@RequestParam("pinyinTags") String pinyinTags) {
        return esService.getSuggestions(pinyinTags);
    }
}
