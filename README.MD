# CommentShell

简体中文 | [English](README_EN.MD)

CommentShell(CSL)一个可以在注释中运行shell命令的intellij插件。

其可以帮助开发者快速在文件内执行指定命令，而不需要在终端去敲打shell命令， 如：开发golang的grpc服务时，我们需要用protoc工具生成pb.go，传统做法是编写完proto文件后在打开终端输入shell命令去生成pb文件，
但是现在，你可以用CLS去替代传统做法提高你的开发效率，只需要在单行注释中以x:generate开头， 后面跟上你需要执行的shell命令即可，如:我期望通过shell命令打印 `hello proto`，
你可以通过 `//x:generate echo hello proto` 这样一行单行注释即可实现

## 支持的语言

* .gitignore
* Antlr(g4)
* Api(go-zero)
* Dart
* Dockerfile
* Drools
* Gherkin
* Gradle
* Groovy
* Html
* Java
* JavaScript
* JFlex
* JSPX
* Kotlin
* Lua
* Markdown
* Perl
* PHP
* Properties
* Proto
* Python
* R
* React
* Ruby
* Rust
* Scala
* SQl
* TypeScript
* Vue
* XML
* YAML

以上语言或者文件已经经过测试，除此外，只要编程语言或者某类型文件支持单行注释语法且指定单行注释类型和语法书写规范理论均可使用（见下文）。

## 支持的注释类型
* Java风格单行注释
    ```text
    //x:generate echo hello java
    ```
* Yaml风格单行注释
    ```text
    #x:generate echo hello yaml
    ```
* SQL风格单行注释
    ```text
    --x:generate echo hello sql
    ```
* xml/html风格单行注释
    ```text
    <!--x:generate echo hello xml-->
    ```
## 书写规则

### 格式

```text
[linePrefix]x:generate [shell] [arg...] [lineSuffix]
```
* `linePrefix` 为单行注释开始标志，仅支持`//`，`#`，`--`，`<!--`
* `lineSuffix` 为单行注释结束标志，仅支持 `-->` 此为html/xml风格单行注释特有
* `x:generate` 为关键字

**注意**
* linePrefix与 `x:generate` 之间不能有任何其他内容

### 常量
* `%DIR%` 代表当前文件的文件目录，如:`/foo/bar`
* `%FILENAME%` 代表当前文件路径，如：`/foo/bar/foobar.txt`
* `%PERCENT_SIGN%` 代表百分号符号本身%
* `"%BASE%"` 代表当前文件名称，如：`foobar.txt`

### 示例
* 在Java文件注释中输出 `hello java`
    ```text
    //x:generate echo hello java
    ```
    output:
    ```text
    hello java
    ```
* 在proto文件中生成pb.go
    ```text
    //x:generate protoc --go_out=plugins=grpc:. %FILENAME%
    ```
* 在Java代码中输出 `PATH` 环境变量值
    ```text
    //x:generate echo $PATH
    ```

## GIF演示

![demo](./demo.gif)
