# aiTags-es-server

基于java与elasticsearch搜索引擎提供的ai绘图tag中文检索接口

## 运行环境

### 版本依赖

elasticsearch版本:7.12.1

java:jdk17

### 搜索引擎IP地址以及指定索引库

在main目录的application.yaml中更改

### 索引库创建表以及索引文档

在项目test目录

test中有批量导入的测试方法

**注意:导入前请提前转换为 中文=英文 形式的properties文件**

为提升检索吻合度,请装载elasticsearch引擎的ik分词器插件,并导入一份只留有中文的分词文档

索引文档将持续整理更新

## 接口以及参数传递

**精准查询**

请求方式:Get

请求地址:/index/termTags

请求参数:chineseTags  中文字符串 单条直接传,多条查询请用 , 分隔

返回体:

单条查询

```json
[
  "cat_ears"
]
```

多条查询

```json
[
  "cat_ears",
  "dog_ears"
]
```

**模糊查询**

请求方式:Get

请求地址:/index/matchTags

请求参数:chineseTags 中文字符串

返回体:

```json
[
  "dog_ears",
  "day",
  "cat_ears"
]
```

**相关度检索**

请求方式:Get

请求地址:/index/listTags

请求参数:chineseTags 中文字符串

返回体:

```json
{
  "狗耳": "dog_ears",
  "猫耳": "cat_ears",
  "白天": "day"
}
```



**暂未实现功能****[需要网页端]**

1.根据用户输入内容搜索框动态展示联想tags

2.待定...

3.待定..

