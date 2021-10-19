# -*- coding: utf-8 -*-
"""
Created on Fri Jun 18 14:49:17 2021

@author: ZSL
@email: 303466906@qq.com

使用说明：
1.下载ananconda软件，并安装。
  登录  https://mirrors.tuna.tsinghua.edu.cn/anaconda/archive/  下载ananconda软件 
  建议下载Anaconda3-2019.10-Windows-x86_64.exe  为python3.7版本。
2.安装vtda包
  打开cmd  即Anaconda Powershell Prompt (Anaconda3)
  输入 pip install vtda -U 
3.分析数据
  使用本demo更改路径和文件名即可使用。
"""
import sys
from vtda import (read_dasp_data,  # 读取dasp数据
                  batch_frequency_vibration_level,  # 分频振级
                  batch_octave_3,  # 倍频程
                  batch_fft,  # 频谱
                  batch_vibration_level,  # Z振级
                  batch_noise_level, batch_noise_level_ssd)  # A声级
                  # handle_noise_room_data, #计算室内二次噪声数据 
                  # handle_noise_data,      #计算噪声数据（车内噪声、轨旁噪声、环境噪声）
                  # handle_displacement_data,#计算位移数据
                  # handle_rail_force_data,  #计算轮轨力数据


# dir_='E:/城轨中心/2项目/20210509南宁地铁2号线线路改造/测试数据/南宁地下数据'  #目录 注意目录之间需要用 '/' 而不是 '\'
# name='20210227南宁地铁2号线上行16+018啊' #文件名 注意文件名不要带试验号
# dir_='C:/Users/ZSL/Desktop/数据'
# name='20210620北京地铁6号线白花K3+470上行'
# dir_='E:/20210711东郊梯形轨枕1断面'
# name='20210710东郊测试断面一'
# dir_='C:/Users/ZSL/Desktop/16/713北安河车辆段阻尼器'
# name='20210713北安河车辆段阻尼器'
dir_=sys.argv[1]
name=sys.argv[2]

#read_dasp_data 读取dasp数据函数
data,info=read_dasp_data(name=name,   #待读取的实验名
                         dir_=dir_,   #待读取的路径
                         num_shiyan='all',#读取的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需读取所有数据 num_shiyan='all'
                         num_tongdao='all',#读取的通道号，num_tongdao='3,7,9,20-34' #通道号，如需读取所有数据 num_tongdao='all'
                         )
#Z振级批量处理 
batch_vibration_level(data=data,  #时域数据
                      info=info,  #数据信息
                      name=name,  #实验名，保存计算结果时用
                      dir_=dir_,  #路径，保存计算结果时用
                      cdxs=0.875,  #重叠系数
                      weight=None,#'weight_vibration_z_13441_1992',
                      #frec=[1,5000],
                      num_shiyan='all',#计算的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需计算所有数据 num_shiyan='all'
                      num_tongdao='all',#计算的通道号，num_tongdao='3,7,9,20-34' #通道号，如需计算所有数据 num_tongdao='all'
                      )
#分频振级批量处理
batch_frequency_vibration_level(data=data,  #时域数据
                      info=info,  #数据信息
                      name=name,  #实验名，保存计算结果时用
                      dir_=dir_,  #路径，保存计算结果时用                   
                      cdxs=0.75,  #重叠系数
                      weight=None,#'weight_vibration_z_13441_1992',
                      # frec=[1,5000],
                      num_shiyan='all',#计算的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需计算所有数据 num_shiyan='all'
                      num_tongdao='all',#计算的通道号，num_tongdao='3,7,9,20-34' #通道号，如需计算所有数据 num_tongdao='all'
                      ) 
#1/3倍频程批量处理
batch_octave_3(data=data,  #时域数据
              info=info,  #数据信息
              name=name,  #实验名，保存计算结果时用
              dir_=dir_,  #路径，保存计算结果时用                   
              cdxs=0.75,  #重叠系数
              num_shiyan='all',#计算的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需计算所有数据 num_shiyan='all'
              num_tongdao='all',#计算的通道号，num_tongdao='3,7,9,20-34' #通道号，如需计算所有数据 num_tongdao='all'
              ) 
#频谱批量处理
batch_fft(data=data,  #时域数据
          info=info,  #数据信息
          name=name,  #实验名，保存计算结果时用
          dir_=dir_,  #路径，保存计算结果时用                   
          cdxs=0.75,  #重叠系数
          num_shiyan='all',#计算的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需计算所有数据 num_shiyan='all'
          num_tongdao='all',#计算的通道号，num_tongdao='3,7,9,20-34' #通道号，如需计算所有数据 num_tongdao='all'
          )  
#A声级批量处理-环境噪声、车内噪声
batch_noise_level(data=data,  #时域数据
                      info=info,  #数据信息
                      name=name,  #实验名，保存计算结果时用
                      dir_=dir_,  #路径，保存计算结果时用
                      fft_len=1, #分析窗长，车内噪声为30s，可以选择1s
                      cdxs=0.75,  #重叠系数，应根据窗长进行调整
                      num_shiyan='all',#计算的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需计算所有数据 num_shiyan='all'
                      num_tongdao='all',#计算的通道号，num_tongdao='3,7,9,20-34' #通道号，如需计算所有数据 num_tongdao='all'
                      )
#A声级批量处理-室内结构二次噪声(secondary structure noise)
# batch_noise_level_ssd(data=data,  #时域数据
#                   info=info,  #数据信息
#                   name=name,  #实验名，保存计算结果时用
#                   dir_=dir_,  #路径，保存计算结果时用
#                   num_shiyan='1',#计算的试验号，num_shiyan='3,7,9,20-34'  #试验号，如需计算所有数据 num_shiyan='all'
#                   num_tongdao='1',#计算的通道号，num_tongdao='3,7,9,20-34' #通道号，如需计算所有数据 num_tongdao='all'
#                   )




