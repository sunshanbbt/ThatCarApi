package com.zhengshun.touch.api.common.constans;


public enum TripScheduleStatusEnum {

    UNDERWAY( 1, "进行中" ),
    FINISH( 2, "行程结束" ),
    OVER_TIME( 3, "行程超时，并发送超时预警" ),
    AUTO( 4, "用户主动发送预警" )
    ;

    public Integer code;
    public String note;

    public void setCode( Integer code ) {
        this.code = code;
    }

    public void setNote( String note ) {

        this.note = note;
    }

    TripScheduleStatusEnum(Integer code, String note ) {
        this.note = note;
        this.code = code;
    }

}
