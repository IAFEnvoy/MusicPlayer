# MusicPlayer

Play music in local disk by Minecraft.(Only *.wav) Also show lyrics.

使用Minecraft播放本地音乐（仅支持*.wav），同时显示歌词

If game crash when loading, please create a folder name "music" in ".minecraft" folder.

如果游戏启动的时候崩溃了，请尝试在.minecraft目录下新建music文件夹

Require Fabric API and Malilib. F10 open menu by default.

需要Fabric API和Malilib做前置，默认F10打开菜单。

自带网易云音乐搜索+下载（此功能需要在.minecraft目录下新建FFmpeg文件夹并在里面放入ffmpeg.exe，以便支持*.mp3转换为*.wav）

Root command : /music   根指令：/music

download <index> 下载音乐（需要配合search指令），下载完需要使用reload指令刷新

jump: Start to play next song 播放下一首

list: List music 显示音乐列表

loop: Loop music list 循环播放列表

loopone: Loop current song 循环播放当前这一首

nextpage: 下一页（需要配合search指令）

play <index>: Play the music as next one of index (See index by "list" command) 选择下一首播放的歌（index使用list指令里查看）

previouspage: 上一页（需要配合search指令）

random: play music random   随机播放

reload: reload music list from "music" folder  从music文件夹重新载入列表

search <Keyword> : 网易云搜索（注意中文歌请输入拼音，因为网易云api不识别中文，我也不知道为啥）

start: start playing music 开始播放

stop: stop playing music  停止播放

set volumn in menu~

在菜单里设置音量哦~
