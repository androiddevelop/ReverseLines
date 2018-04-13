# ReverseLines
idea plugin for reverse lines.

### Example

Reverse the order of the elements inserted into the list

#### source code:
```java
List<String> list = new ArrayList<>();
list.add("a");
list.add("b");
list.add("c");
list.add("d");
list.add("e");
list.add("f");
```
#### target code:
```java
List<String> list = new ArrayList<>();
list.add("f");
list.add("e");
list.add("d");
list.add("c");
list.add("b");
list.add("a");
```

#### steps: 
what you need to do is as follows:
1. select the part you want to reverse. In the above example, select the lines 2 to 7.
2. click edit menu and select【Reverse Selected Lines】 Or right click and select【Reverse Selected Lines】 Or simply press the 【command + shift + r 】shortcut


### License

```
Copyright 2018 Yuedong.li

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

> Have any questions please contact me (app@codeboy.me).
