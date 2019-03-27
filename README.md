# 四子棋与TOOT的实现

这个项目实现了两个棋类游戏，分别是四子棋（connect4）和TOOT & OTTO，后者的规则与四子棋类似，只是棋盘更小，并且每位玩家分别有6枚棋子O和6枚棋子T，如果玩家在横、纵或对角线上得到了TOOT（另一方玩家为OTTO）便宣告胜利。

此项目基于Java和JavaFX，感谢YouTube自博主Almas Baimagambetov的[教学视频](https://www.youtube.com/watch?v=B5H_t0A_C14)。

## MenuBG类

首先介绍菜单背景类，这个类的目的是在主界面选择游戏后进入游戏菜单，在菜单后面放一张游戏的图片背景，效果如下：

![connect4_menuBG](https://github.com/tizengyan/images/raw/master/connect4_menuBG.png)

这个类继承自`javafx.scene.Parent`，它的介绍如下：

>The base class for all nodes that have children in the scene graph.
> 
>This class handles all hierarchical scene graph operations, including adding/removingchild nodes, marking branches dirty for layout and rendering, picking,bounds calculations, and executing the layout pass on each pulse.

也就是说如果我们想让场景中有多级的包含、嵌套等结构，可以使用`Parent`，在我们这个类中只有一个成员函数`loadImg()`，它用来读取我们的背景图片并储存，然后返回一个`Pane`类的对象，这个类多用于平面布局，在读取函数中我们先声明一个该类的变量`root`，再将读取的两张图片和文字用`getChildren().add(...)`加入`root`，最后返回它给`MenuBG`的对象，这时`MenuBG`的对象就包含我们设计的带有图片的背景了，后面讲菜单类的时候会使用它。注意要先将加入的两张`ImageView`变量的图片设为不可见。

## 菜单类GameMenu

这个类同样继承自`Parent`，首先创建若干`Text`，初始化为我们需要的菜单按钮的字符，再根据设计的菜单按钮的结构创建相应数量的`VBox`（依次从上往下垂直摆放的容器）和`HBox`（依次从左往右水平摆放的容器），最后初始化需要的菜单按钮。

根据每个按键的功能设置其被鼠标点击之后程序的反应。函数`fadeAway`和`showUp`分别是对输入的节点（模块）加上渐隐或渐显效果，其中用到了`javafx.animation.FadeTransition`的动画效果，最后不要忘记将输入的模块根据需求设为不可见或可见。

最后是`showWinner`函数，这个函数用来在游戏结束时显示胜利的一方或平局，并提供“重新开始”和“返回主菜单”两个选项按钮。由于两个游戏都共用这个菜单，根据所在游戏和模式的不同，会显示不同的胜利标语。

## 游戏类Connect4

继承`javafx`中自带的圆形形状Circle制作棋子，除了大小之外，还要设置颜色，由于只有两种颜色或棋子，`color`可以设置为`bool`型。

`makeBoard`函数用来制作棋盘，先按棋子大小取计算出棋盘大致的长宽矩形，再依次修剪出圆形的孔洞。

`makeColumn`函数辅助我们落子，我们根据列数生成对应数量的矩形，与棋盘同高但宽度为一个棋子，将它们依次摆放在每一列上，每一个矩形都要随时检测鼠标是否进入矩形范围内，如果进入则变换矩形颜色，达到提示玩家的目的，同时设定点击该矩形会在对应的列落子。

`placeDisc`是落子函数，四子棋的规则只允许选择列来落子，因此选择好列后，只需要检查该列的底部目前有多少个棋子，然后落在最上方的空处就可以了。二维数组`grid`用来记录棋盘状态，`javafx.animation.TranslateTransition`被用来制作落子的动画效果，同时检查游戏是否结束。

要判断游戏是否结束，只需按当前落子的坐标，检查上下左右、左上、左下、右上、右下方向前进三个棋子后，是否出现四连。这八种情况可以简化为四种，即垂直、水平和两个斜对角，以落子的坐标为中心，依次向两边延伸3个棋子并装进一个`list`，得到4个最大为7的棋子`list`，然后检查其中是否出现四连即可。

## 游戏类TOOT

这个类和`Connect4`类十分类似，不同的只是棋子不单单是有颜色的圆，而是由两个不同大小的圆相减得到一个环，然后在中间加入`Text`标记棋子种类。设定鼠标左键下T，右键下O，并在程序中计数，如果一方的T或O下的次数达到6次则无法继续落相应的棋子。

最后是检查胜利情况的函数会略复杂一些，还是取四个方向的`list`，这时需要按“正反反正”来检查四连，并根据第一个棋子是T还是O来判断是找到了TOOT还是OTTO，这时我们甚至不需要关心玩家（或电脑）落的是哪一种棋子，只要按照当前落子的坐标检查四个方位就行了。

## 主函数类（project_main）

`createGame`将所有需要的模块装进主场景，`sceneEffect`实现了在游戏的任何时候（除了出现游戏结束菜单时）都可以用`Esc`唤出主菜单的功能。

## 菜单按钮

最后介绍一下菜单的按钮，我们需要不同大小和不同字体的按钮，这里应该设计一个基础的按钮类，可以根据输入的长宽、文字等建立相应的对象。按钮由一个黑色背景方框和白色文字组成，其中方框的透明度有所调整，加了模糊效果，并设置鼠标进入方框范围后方框和字体都会反色，制造一种常见的菜单视觉效果，同时设置点击方框时，对其增加`DropShadow`的效果，并在松开鼠标时去除，这样就完成了点击反馈的视觉效果。

```java
DropShadow drop = new DropShadow(50, Color.WHITE);
drop.setInput(new Glow());
this.setOnMousePressed(e -> setEffect(drop));
this.setOnMouseReleased(e -> setEffect(null));
```