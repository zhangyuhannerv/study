package com.zh.work_demo.noise;

/**
 * @ClassName TspBean
 * @Description TODO
 * @Author Zhangyuhan
 * @Date 2021/10/21
 * @Version 1.0
 */
public class TspBean {
    private Integer frequency;// 频率
    private String unit;// 单位
    private String name;// 抿成
    private String startTime;// 起始时间
    private String type;// 类型：时间描述文件还是里程描述文件

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TspBean{" +
                "frequency=" + frequency +
                ", unit='" + unit + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
