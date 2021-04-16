package com.vista.scheduled;

public enum StatusEnum {
	ENABLED(1),
	DISABLED(2);
	
	private Integer code;

	StatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
	

	
}
