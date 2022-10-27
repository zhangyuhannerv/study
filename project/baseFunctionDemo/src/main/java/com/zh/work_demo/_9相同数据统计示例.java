package com.zh.work_demo;

import com.zh.pojo.model.TYwglYsXlfinfo;
import com.zh.pojo.vo.WlHzVO;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class _9相同数据统计示例 {
    @Test
    public void demo1(List<Integer> ids) {
        List<TYwglYsXlfinfo> tYwglYsXlfinfos = new ArrayList<>();
        List<WlHzVO> exportList = new ArrayList<>();

        if (tYwglYsXlfinfos.size() > 0) {
            WlHzVO wlHzVO = new WlHzVO();
            BeanUtils.copyProperties(tYwglYsXlfinfos.get(0), wlHzVO);
            for (int i = 0; i < tYwglYsXlfinfos.size(); i++) {
                TYwglYsXlfinfo tYwglYsXlfinfo = tYwglYsXlfinfos.get(i);
                if (!tYwglYsXlfinfo.getWlbm().equals(wlHzVO.getWlbm())) {
                    exportList.add(wlHzVO);
                    wlHzVO = new WlHzVO();
                    BeanUtils.copyProperties(tYwglYsXlfinfo, wlHzVO);
                }

                setHzhj(tYwglYsXlfinfo, wlHzVO);

                if (i == tYwglYsXlfinfos.size() - 1) {
                    exportList.add(wlHzVO);
                }
            }
        }
    }

    /**
     * 计算汇总合计
     *
     * @param tYwglYsXlfinfo
     * @param wlHzVO
     */
    public void setHzhj(TYwglYsXlfinfo tYwglYsXlfinfo, WlHzVO wlHzVO) {
        if ("1".equals(tYwglYsXlfinfo.getZy())) {// 线别
            if (tYwglYsXlfinfo.getXianbie() != null) {// 有线别
                switch (tYwglYsXlfinfo.getXianbie()) {
                    case "1":
                        wlHzVO.setJcXlSl(tYwglYsXlfinfo.getHj().add(wlHzVO.getJcXlSl()));
                        break;
                    case "2":
                        wlHzVO.set_19XlSl(tYwglYsXlfinfo.getHj().add(wlHzVO.get_19XlSl()));
                        break;
                    case "3":
                        wlHzVO.setYfXlSl(tYwglYsXlfinfo.getHj().add(wlHzVO.getYfXlSl()));
                        break;
                }
            } else {// 无线别（后四个特殊模版）
                wlHzVO.setJcXlSl(tYwglYsXlfinfo.getSlJc().add(wlHzVO.getJcXlSl()));
                wlHzVO.set_19XlSl(tYwglYsXlfinfo.getSl19h().add(wlHzVO.get_19XlSl()));
                wlHzVO.setYfXlSl(tYwglYsXlfinfo.getSlYf().add(wlHzVO.getYfXlSl()));
            }

        } else {// 土建
            switch (tYwglYsXlfinfo.getXianbie()) {
                case "1":
                    wlHzVO.setJcTjSl(tYwglYsXlfinfo.getHj().add(wlHzVO.getJcTjSl()));
                    break;
                case "2":
                    wlHzVO.set_19TjSl(tYwglYsXlfinfo.getHj().add(wlHzVO.get_19TjSl()));
                    break;
                case "3":
                    wlHzVO.setYfTjSl(tYwglYsXlfinfo.getHj().add(wlHzVO.getYfTjSl()));
                    break;
            }
        }
    }
}
