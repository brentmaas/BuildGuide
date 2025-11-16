# BuildGuide
A Minecraft mod to project circles and stuff. It can be found over at either https://www.curseforge.com/minecraft/mc-mods/build-guide or https://modrinth.com/mod/build-guide.

For older Fabric versions before 0.3.3, see https://github.com/brentmaas/BuildGuideFabric.

# Development
To build the project, use `gradlew build`. To run a certain version (say, Fabric 1.21.10), use `gradlew fabric1.21.10:runClient`.

## Eclipse
Optionally, run `gradlew genSources` beforehand to generate sources. The project supports generating Eclipse files with `gradlew eclipse`. It is, however, HIGHLY recommended you get rid of Buildship, as it stubbornly ignores custom Gradle files and just does its own thing.