# CommentShell

[简体中文](README.MD) | English

CommentShell (CSL) a comment shell runner, which can help developers quickly execute specified commands in files without having to type shell commands in the terminal. 

For example, when developing the grpc service of golang, we need Use the protoc tool to generate pb.go.
The traditional way is to open the terminal and enter the shell command to generate the pb file after writing the proto file. 
But now, you can use CLS to replace the traditional method to improve your development efficiency, 
just in a single line comment Start with x:generate, and then add the shell commands you need to execute. 

For example, I expect to print `hello proto` through the shell command. 
You can do this with a single line comment like `//x:generate echo hello proto` .
