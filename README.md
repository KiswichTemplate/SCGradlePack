# SCGradlePack

# 配置实例
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

# 发布
执行task publishing.publish