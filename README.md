# minecraft-maven-template
A project template for minecraft plugins to work with maven. 
It is designed to simply create plugins without caring about loading commands and listeners.
Simply to learn how to use maven and load custom user data from a custom data source.

## Based on
###Frameworks:
- maven
###Dependencies:
- [Lombok](https://projectlombok.org)
- [Spigot](https://www.spigotmc.org/wiki/spigot-maven/)
- [Reflections](https://github.com/ronmamo/reflections)

###IDE settings:
- Using IntelliJ 2020
- JDK 15.0.1
- Language level: 8
- IDE plugins: lombok
## How to use?
I would prefer [IntelliJ](https://www.jetbrains.com/de-de/idea/) 2018 or newer.

Download the project. You can replace the project name and version in the [pom.xml](pom.xml) also plugin settings like main class and version number.

IMPORTANT: 
Change the outputDirectory in the pom.xml file to your plugins folder of your minecraft server.
```xml 
<project.outputDirectory>I:\Servers\Minecraft\plugins</project.outputDirectory>
```
The library dependencies are being automatically loaded with maven to the build jar file:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>3.2.4</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <createDependencyReducedPom>false</createDependencyReducedPom>
            </configuration>
        </execution>
    </executions>
</plugin>
```
Ready to instantly program your own minecraft plugin! 

## Create a command (fast)
To create a command, you need to make a new class in the [de.bleikind.commands](src/main/java/de/bleikind/commands) package.
The class must be extended by [CommandTemplate](src/main/java/de/bleikind/commands/CommandTemplate.java)
```java
public class TestCommand extends CommandTemplate{ 
    public TestCommand() {
        super("commandName", "This is a description.", Main.getInstance().getProperties().get("project.name") + ".permissionName");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String name, String[] args) {
        return Lists.newArrayList();
    }
}
```
At the end you need to add the command name to the [plugin.yml](src/main/resources/plugin.yml) file like this:
```yaml
commands:
  ...
  commandName:
```

## Run minecraft server in IntelliJ IDE console
[Here](https://www.spigotmc.org/threads/intellij-live-debug-run-your-server-inside-the-ide.364782/)