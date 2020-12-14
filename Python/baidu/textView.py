#!/usr/bin/python3
# -*- coding: UTF-8 -*-
from aip import AipOcr
import re
import os

''' APPID AK SK '''
APP_ID = '22240664'
API_KEY = 'uhO3u1hRCRHefQqMG90PVSyw'
SECRET_KEY = 'wG3QXaqGfd6TiwVSN0vxCyi5muwI0EGi'
# 初始化AipFace对象
client = AipOcr(APP_ID,API_KEY,SECRET_KEY)
def get_file_content(filePath):
    with open(filePath,'rb') as fp:
        return fp.read()
# 读取图片
# 定义参数变量
options = {
    'detect_direction': 'true',
    'language_type': 'CHN_ENG',
}

# 调用通用文字识别接口
def addImage(image):
    def addOptions(options):
        return client.basicGeneral(image,options)
    return addOptions

def setData(resultData):
    text = []
    name = []
    for i in range(len(resultData)):
        value = resultData[i]['words']
        if re.match('[^A-Za-z0-9]',value):
            if re.match('[A-Za-z0-9]',resultData[i+1]['words']):
                name.append(value)
                text.append(resultData[i+1]['words'])
    f = open("E:\\PythonSpace\\baidu\\baidu.txt",'a',encoding='utf-8')
    f.write(name[0] + ":" + text[0])
    for x in range(1,len(name)):
        f.write("," + name[x] + ":" + text[x])
    f.write("。\n")
    f.close()

g = os.walk("images")
for path,d,filelist in g:  
    for filename in filelist:
        if filename.endswith('png'):
            image = get_file_content(os.path.join(path, filename))
            addOption = addImage(image)
            result = addOption(options)
            result = result['words_result']
            setData(result)