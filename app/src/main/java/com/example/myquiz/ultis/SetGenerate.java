package com.example.myquiz.ultis;

import com.example.myquiz.models.SetClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SetGenerate {
    int number;
    public SetGenerate(int number){
        this.number = number;
    }
    public List<SetClass> generateName(){
        List<SetClass> setClasses = new ArrayList<>();
        if(number>0){
            for(int i=1; i<=number; i++){
                setClasses.add(new SetClass(String.valueOf(i), "Bài "+i, ""));
            }
        }

        return  setClasses;
    }
}
