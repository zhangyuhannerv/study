# -*- coding: utf-8 -*-
#wallhaven爬取
#前置：pip安装requests、lxml

import os
from urllib.parse import urlencode
import time
import random
import requests
from lxml import etree

headers = {
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5)\AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.104 Safari/537.36"
        }

#定义创建文件路径函数，将下载的文件存储到该路径
def CreatePath(filepath):
    if not os.path.exists(filepath):
            os.makedirs(filepath)

#定义获取url函数，这里是通过urlencode方法把url的各个部分拼接起来的，拼接起来的url
#像是这样的：https://wallhaven.cc/search?q=girls&categories=111&purity=110&sorting=toplist&order=desc        
def GetUrl(keyword,category,purity):
    params = {
        'q': keyword,
        'categories': category,
        'purity': purity,#\100\010\110
        'sorting': 'favorites', #relevance\random\date_added\views\favorites\toplist\toplist-beta
        'topRange':'1y', #1y\6M\3M\1w\3d\1d
        'order':'desc'#，
        #'page':1
    }
    base_url='https://wallhaven.cc/search?'
    url=base_url + urlencode(params)
    print(url)
    return url

#获取查找到的图片数
def GetPictureNum(url):
    allpic=" "
    try:
        html = requests.get(url, headers=headers) 
        if 200 == html.status_code:
            selector = etree.HTML(html.text) 
            pageInfo = selector.xpath('//header[@class="listing-header"]/h1[1]/text()')#提取出文本
            string = str(pageInfo[0])#图片数是文本中的第一个
            numlist = list(filter(str.isdigit,string))  #有些数字是这样的，11,123,所以需要整理。
            for item in numlist:
                allpic+=item
            totalPicNum=int(allpic)  #把拼接起来的字符串进行整数化
            return totalPicNum
    except requests.ConnectionError:
        return None
        
#获取图片链接
def GetLinks(url,number):
    urls=url+'&page='+str(number)
    try:
        html=requests.get(urls, headers=headers)
        selector=etree.HTML(html.text)
        PicLink=selector.xpath('//a[@class="preview"]/@href')#这里寻找图片的链接地址，以求得到图片编号
    except Exception as e:
        print('Error',e.args)
    return PicLink    
    
#下载函数    
def Download(filepath,url):
    picStr = url.replace('https://wallhaven.cc/w/','') #python3 replace
    picJpg = filepath+'wallhaven-'+picStr+'.jpg'
    picPng = filepath+'wallhaven-'+picStr+'.png'

    if os.path.exists(picJpg)|os.path.exists(picPng):
        return
    #此函数用于图片下载。其中参数url是形如：https://wallhaven.cc/w/eyyoj8 的网址
    #因为wallheaven上只有两种格式的图片，分别是png和jpg，所以设置两种最终地址HtmlJpg和HtmlPng，通过status_code来进行判断，状态码为200时请求成功。
    
    #print(picStr)
    HtmlJpg='https://w.wallhaven.cc/full/'+ picStr[0:2] +'/wallhaven-' + picStr +'.jpg'
    HtmlPng='https://w.wallhaven.cc/full/'+ picStr[0:2] +'/wallhaven-' + picStr +'.png'
    
    try:
        pic=requests.get(HtmlJpg,headers=headers)
        if 200== pic.status_code:
            pic_path= picJpg           
        else:
            pic= requests.get(HtmlPng,headers=headers)
            if 200== pic.status_code:
                pic_path= picPng
            else:
                print("Downloaded error:",picStr)
                return
        with open(pic_path,'wb') as f:
            f.write(pic.content)
            f.close()
        print("Downloaded image:",picStr) 
    except Exception as e:
        print(repr(e))    
        
#主函数
def main():
    filepath = ('/Users/zhangyuhan/MyDocument/wallhaven/')#存储路径。

    keyword = input('请输入关键词:')
    category = input('请输入图片分类，Gneral,Anime,People三种，如果你想要只想选择Anime，就键入010,如果全选就键入111,以此类推:')
    purity = input('请输入权限分类，SFW,Sketchy,NSFW三种，如果你想要只想选择Sketchy，就键入010,如果全选就键入111(无cookie不支持**1),以此类推:')

    # keyword = input('碧蓝航线')
    # category = input('010')
    # purity = input('111')

    CreatePath(filepath) #创建保存路径
    url = GetUrl(keyword,category,purity)   #获取url
    
    PicNum = GetPictureNum(url)#总图片数
    pageNum = int(PicNum/24+1)  #求出总页面数
    print("We found:"+format(PicNum)+" images,"+format(pageNum)+" pages.")

    
    pageInput = input("请输入你想要爬的图片数的开始页【每页24张】:")
    pageStart = int(pageInput)
    
    for i in range(pageStart,pageNum):
        PicUrl=GetLinks(url,i+1)
        for item in PicUrl:
            #print(item)
            Download(filepath,item)
            
            # 打开一个文件
            fo = open("page.txt", "w+",encoding='utf-8')
            fo.write("page"+ str(i) +":"+item)
            # 关闭打开的文件
            fo.close()
            time.sleep(random.uniform(0,5)) #这里是让爬虫在下载完一张图片后休息一下，防被侦查到是爬虫从而引发反爬虫机制。
        time.sleep(random.uniform(10,20)) #这里是让爬虫在下载完一张图片后休息一下，防被侦查到是爬虫从而引发反爬虫机制。      


if __name__ == '__main__':
    main()