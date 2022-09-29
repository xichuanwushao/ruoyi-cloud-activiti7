package com.ruoyi.danny.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : wuxiao
 * @date : 17:09 2022/9/29
 */

public class ReimgoodsVo {
    @JsonProperty("desc")
    private String desc;
    @JsonProperty("money")
    private String money;
    @JsonProperty("title")
    private String title;
    @JsonProperty("type")
    private String type;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
