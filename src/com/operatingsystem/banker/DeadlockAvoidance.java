package com.operatingsystem.banker;

import javax.swing.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dylanzang on 2017/5/6.
 */
public class DeadlockAvoidance extends JFrame implements ActionListener {

    //表示进程数
    private int m;
    //表示资源种类
    private int n;
    //进程i请求资源j的最大数量
    private int claim[][];
    //进程i已有j的资源数
    private int allocation[][];
    //进程i还需要j的资源数
    private int need[][];
    //进程i请求资源
    private  int[] request;
    //可申请的进程
    private int[] available;

    private String[] sourceNames;

    private String[] processNames;

    private String[] safeSequence;

    private String requestProcess;


    public DeadlockAvoidance(String title){
        super(title);
        setSize(500, 500);
        setLocation(200,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = getContentPane();
        container.setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
     * 银行家核心算法
     * 如果一个进程请求的资源可以满足就进行分配，不够则不分配
     * */
    public boolean banker(){
        int requestProcessId=getRequestProcessId(requestProcess);
        for (int i = 0; i <n; i++) {
            allocation[requestProcessId][i]=allocation[requestProcessId][i]+request[i];
            need[requestProcessId][i]=need[requestProcessId][i]-request[i];
            available[i]=available[i]-request[i];
        }
        if(!checkSafety()){
            for (int i = 0; i <n; i++) {
                allocation[requestProcessId][i]=allocation[requestProcessId][i]-request[i];
                need[requestProcessId][i]=need[requestProcessId][i]+request[i];
                available[i]=available[i]+request[i];
            }
            return false;
        }
        return true;
    }

   /**
    *通过一个进程名得到进程的id
    * */
    private int getRequestProcessId(String requestProcessName){
        
        for (int i = 0; i <m ; i++) {
            if(processNames[i].compareToIgnoreCase(requestProcessName)==0){
                return i;
            }
        }
        return -1;
    }

    private boolean checkSafety(){
        //存储进程完成后释放的资源和avilable中的资源
        int[] availableResource=new int[n];
        //用于说明一个进程是否执行结束
        boolean [] isFinish=new boolean [m];

        for (int i = 0; i <n ; i++) {
            //在没有进程释放资源的时候，存储可申请的资源
            availableResource[i]=available[i];
        }
        for (int j = 0; j <m ; j++) {
            isFinish[j]=false;
        }

        for (int i = 0; i <m ; i++) {
            //此处考虑一种简单的情况，从第一个进程开始判断，寻找一个安全的执行序列
            for (int j = 0; j <m ; j++) {
                if(!isFinish[j] && judge(j,availableResource)){
                    //如果该该进程未执行结束，且可以申请到需要的资源，则执行结束后释放资源
                    isFinish[j]=true;
                    for (int k = 0; k < n; k++) {
                        availableResource[k]+=need[j][k];
                    }
                    //释放资源后跳出，寻找下一个可以顺利执行结束的资源
                    break;
                }
            }

        }
        if(safety(isFinish)){
            return true;
        }
        return false;
    }
    /**
     *判断pid进程是否可以申请到需要的资源
     */
    private boolean judge(int pid,int[] availableResource){
        for (int i = 0; i <n ; i++) {
            if(need[pid][i]>availableResource[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否存在一个执行序列可以让所有的进程顺利的执行结束
     * */
    private boolean safety(boolean[] isFinish){
        for (int i = 0; i <isFinish.length ; i++) {
            if(!isFinish[i]){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        new DeadlockAvoidance("banker");
    }
}
