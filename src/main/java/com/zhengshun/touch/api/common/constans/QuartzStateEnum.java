package com.zhengshun.touch.api.common.constans;


public enum QuartzStateEnum {

    ON( 10, "启用" ),
    OFF( 20, "禁用" )
    ;

    public Integer code;
    public String note;

    public void setCode( Integer code ) {
        this.code = code;
    }

    public void setNote( String note ) {

        this.note = note;
    }

    QuartzStateEnum(Integer code, String note ) {
        this.note = note;
        this.code = code;
    }

}
