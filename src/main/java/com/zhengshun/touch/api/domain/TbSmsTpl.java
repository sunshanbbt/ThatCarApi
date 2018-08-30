package com.zhengshun.touch.api.domain;


import java.io.Serializable;

/**
 *
 * This class corresponds to the database table tb_sms_tbl
 *
 */
@SuppressWarnings("serial")
public class TbSmsTpl implements Serializable{

    private Long id;

    private String type;

    private String typeName;

    private String tpl;

    private String number;

    private String state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTpl() {
        return tpl;
    }

    public void setTpl(String tpl) {
        this.tpl = tpl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}