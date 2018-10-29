package com.wxc.happyturtle.entity;

import java.io.Serializable;

/**
 * Created by WXC on 2018/08/11.
 */
public class BasicEntity implements Serializable {
    private static final long serialVersionUID = 7688360757482057576L;
    private int offset;
    private int size;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
