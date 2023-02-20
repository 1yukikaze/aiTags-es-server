package com.yukikaze.webuiesdemo.pojo;

import java.util.*;


public class TranslationDoc {
    private String chineseTag;
    private String englishTag;
    private List<String> suggestion;

    public TranslationDoc() {
    }

    public TranslationDoc(String chineseTag, String englishTag) {
        this.chineseTag = chineseTag;
        this.englishTag = englishTag;
        if (this.chineseTag.contains("/")) {
            String[] chineseTagArr = this.chineseTag.split("/");
            //封装
            this.suggestion = new ArrayList<>();
            Collections.addAll(this.suggestion, chineseTagArr);
        } else {
            this.suggestion = List.of(this.chineseTag);
        }
    }

    public String getChineseTag() {
        return chineseTag;
    }

    public void setChineseTag(String chineseTag) {
        this.chineseTag = chineseTag;
        if (this.chineseTag.contains("/")) {
            String[] chineseTagArr = this.chineseTag.split("/");
            //封装
            this.suggestion = new ArrayList<>();
            Collections.addAll(this.suggestion, chineseTagArr);
        } else {
            this.suggestion = List.of(this.chineseTag);
        }
    }

    public String getEnglishTag() {
        return englishTag;
    }

    public void setEnglishTag(String englishTag) {
        this.englishTag = englishTag;
    }

    @Override
    public String toString() {
        return "TranslationDoc{" +
                "chineseTag='" + chineseTag + '\'' +
                ", englishTag='" + englishTag + '\'' +
                ", suggestion=" + suggestion +
                '}';
    }
}
