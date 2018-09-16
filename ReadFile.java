package com.idk;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadFile {
    private String fileName = "E:\\intelij projects\\ReadFromFile\\src\\text";
    private Scanner x;
    private ArrayList<String> lines = new ArrayList<>();
    public void OpenFile(){
        try {
            x = new Scanner(new File(fileName));
            while(x.hasNextLine()){
                lines.add(x.nextLine());
            }
        }catch (Exception e){
            System.out.println("ERROR IN OPENING");
        }
    }
    public void CloseFile(){
        x.close();
    }
    public ArrayList<String> GetArray(){
        return lines;
    }
}
