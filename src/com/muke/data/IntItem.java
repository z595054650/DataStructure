package com.muke.data;

/**
 * Created by thinkpad on 2017/5/5.
 */
public class IntItem{

    private int data;

    private int other;

    public IntItem(int data){
        this.data=data;
    }

    public int getData(){
        return data;
    }

    @Override
    public boolean equals(Object obj) {
        return this.data>((IntItem)obj).data;
    }

    @Override
    public String toString() {
        return ""+data;
    }


}
