# BuildGuide
A Minecraft mod to project circles and stuff. It can be found over at either https://www.curseforge.com/minecraft/mc-mods/build-guide or https://www.modrinth.com/mod/build-guide.

For older Fabric versions before 0.3.3, see https://github.com/brentmaas/BuildGuideFabric.

# Development
To build the project, use `gradlew build`. To run a certain version (say, Forge 1.19.4), use `gradlew forge1.19.4:runClient`.

## Eclipse
The project supports generating Eclipse files with `gradlew eclipse`. It is, however, HIGHLY recommended you get rid of Buildship, as it stubbornly ignores custom Gradle files and just does its own thing.