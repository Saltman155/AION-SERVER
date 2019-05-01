package com.superywd.aion;

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: saltman155
 * @date: 2019/3/3 19:35
 */

public class Main {

    public static void main(String[] args) {
        char[] tmp = {'a','b','c'};
        range(tmp,0,3);
    }



    public static void range(char[] str,int start,int len){
        if(start == len -1){
            System.out.println(start);
        }else{
            for(int i = 0;i<len;i++){
                System.out.println(str[start+i]);
                char[] tmp1 = new char[len-1];
                List<Character> tmp2 = new ArrayList<>();
                for(int j = 0;j<len;j++){
                    if(j == i){
                        continue;
                    }
                    tmp2.add(str[i]);
                }
                for(int j = 0;j<len-1;j++){
                    tmp1[j] = (char) tmp2.indexOf(j);
                }
                range(tmp1,0,len-1);
            }
        }
    }
}
