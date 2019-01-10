# SCGradlePack

# 使用
## 引入

```groovy

apply "scooper.pack"
```
## 配置实例
```groovy
scooperPack{
    tempDir = 'build\\prePack'
    outDir = 'E://Projects'
    sqlDir = 'sql'
    docDir = 'doc'
    thirdPartDir = 'thirdPart'
    others = ["webpack\\sc-coommon-web*.zip"]
    time = new Date().format("yyyyMMdd")
}

```

# 打包
执行 task scooper.projectPack

# 发布
执行task publishing.publish