# Java类库 monchickey 2.0.1 20170803

# 2.0.1 20170803
    - 完善DataCompute类 生成基于name和全局唯一uuid的方法

# 2.0.0 20170610
    - 修改项目名以及类注释由coodog 为 monchickey 包结构由coodog为com.monchickey

## monchickey最常用的封装的包和类：
* 1、com.monchickey.dataprocess 主要是结构化数据的处理包括字符串、文本、json、xml等数据类型，不包括图片视频等非结构化的媒体类型
*   DataExtract 数据抽取类，一般从很多字符串中获取有用的数据
*   DataValidation  数据验证类，用户对用户输入的表单数据正确性进行一定范围的匹配或者逻辑验证，保证数据的准确性
*   DataFilter 数据过滤类，对用户输入进行脱壳和一定过滤，和数据验证配合使用，防止恶意注入，保证数据的安全性
*   DataConversion 数据类型转换类，比如基本类型的转换，json map互转，json 对象互转等
*   DataCompute 数据计算类(一般用于计数，求值，加解密，哈希，统计某些信息等)
* 2、com.monchickey.fileprocess
*   ConfigTools 配置文件读取工具
*   FileIO 常用的文件IO流操作
*   FileCompress 文件压缩解压
* 3、com.monchickey.imageprocess 图像处理类，包括图片裁剪、加水印、格式转换等
*   ImageCut 图片裁剪类
* 4、com.monchickey.networkcommunication
*   NetworkTools 网络常用的图片下载，输出等工具
*   WebGrab web抓取，请求类，用于抓取页面、发送get/post请求等
