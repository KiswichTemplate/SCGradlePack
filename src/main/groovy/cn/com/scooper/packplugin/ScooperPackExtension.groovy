package cn.com.scooper.packplugin

class ScooperPackExtension {
    String tempDir = 'build\\prePack'
    String outDir = 'E://Projects'
    String sqlDir = 'sql'
    String docDir = 'doc'
    String thirdPartDir = 'thirdPart'
    String[] others = []
    String[] configFiles = [
            "build\\resources\\main\\config.properties",
            "build\\resources\\main\\db.properties"
    ]
    String time = new Date().format("yyyyMMdd")
}
