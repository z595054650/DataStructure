package com.muke.heap;

import com.muke.data.IntItem;

import java.util.Random;

/**
 * Created by dylan zang on 2017/5/5.
 *
 *              0
 *       1              2
 *  3         4     5        6
 */
public class MinHeap<T>
{
    private Object[] data;

    private int count;

    public MinHeap(int capacity){
        data=new Object[capacity];
        count=0;
    }

    public MinHeap(int[] array){
        for (int i = (array.length-1)/2; i >=0 ; i--) {
            shiftDown(i);
        }
    }

    public void insert( T item){
        data[count]=item;
        shiftUp(count);
        count++;
    }



    private void shiftUp(int k){
        while(k>0){
            // 判断data[(k-1)/2]>data[k],重写对象中的equals（）
            if(data[(k-1)/2].equals(data[k])){
                swap(data,(k-1)/2,k);
            }
           k=(k-1)/2;
        }
    }

    private void swap(Object[] arr,int item1, int item2) {
        Object temp=arr[item2];
        arr[item2]=arr[item1];
        arr[item1]=temp;
    }

    private void shiftDown(int k){
        while(2*k+1<count){
            int j=2*k+1;
            if(j+1<count && data[j].equals(data[j+1])){
               j++;
            }
            if(!data[k].equals(data[j])){
                break;
            }
            swap(data,k,j);
            k=j;
        }
    }

    //在原堆中对数据进行从大到小排列
    public void heapSort(){
        for (int i = count-1; i >0 ; i--) {
            swap(data,0,i);
            shiftDown(0,i);
        }
    }

    //表示堆中元素的个数
    private void shiftDown(int k, int n) {
        while(2*k+1<n){
            int j=2*k+1;
            if(j+1<n && data[j].equals(data[j+1])){
                j++;
            }
            if(!data[k].equals(data[j])){
                break;
            }
            swap(data,j,k);
            k=j;
        }
    }


    public T extractMin(){
        T item=(T)data[0];
        swap(data,0,count-1);
        count--;
        shiftDown(0);
        return  item;
    }

    public int size(){
        return count;
    }

    public boolean isEmpty(){
        return count==0;
    }

    public void print(){
        for (int i = 0; i <count ; i++) {
            System.out.print(data[i]+" ");
        }
    }

    public static void main(String[] args) {

        Random random=new Random();

        MinHeap<IntItem> heap=new MinHeap<IntItem>(100);

        for (int i = 0; i <10 ; i++) {
            IntItem item=new IntItem(random.nextInt(50));
            heap.insert(item);
        }
        heap.print();
        System.out.println();
        /*for (int i = 0; i <10 ; i++) {
            System.out.println("输出最值："+heap.extractMin());
        }
        heap.print();*/
       // System.out.print("输出最值："+heap.extractMin());
        heap.heapSort();
        System.out.println("从大到小排序：");
        heap.print();
    }
}
