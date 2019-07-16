package com.vtest.it.patparameterannalysis.services.tools;

import org.springframework.stereotype.Service;

@Service
public class QuickSort {
    public void sort(Double[] nums,int l,int r){
        if(l<r){
            int i,j;
            double x;
            i=l;
            j=r;
            x=nums[i];
            while(l<r){
                    while(l<r&&nums[r]>x){
                        r--;
                    }
                    if (l<r){
                        nums[l++]=nums[r];
                    }
                    while(l<r&&nums[l]<x){
                        l++;
                    }
                    if(l<r){
                        nums[r--]=nums[l];
                    }
            }
            nums[l]=x;
            sort(nums,i,l-1);
            sort(nums,l+1,j);
        }
    }
}
