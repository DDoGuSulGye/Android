package com.DataStructure.cau310navi.Data;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Created by leesd on 2018-11-28.
 */

public class DataPool {
    ArrayList<String> temp = new ArrayList<String>();
    ArrayList<Integer> result = new ArrayList<Integer>();

    //클래스 노드 객체 생성
    ArrayList<ClassNode> c = new ArrayList<ClassNode>();

    public  ArrayList<ClassNode> getData(Context context) {
        try {
            InputStream is = context.getResources().getAssets().open("class.xls");

            Workbook wb = Workbook.getWorkbook(is);
            if (wb != null) {
                Sheet sheet = wb.getSheet(0); //시트 불러오기
                if (sheet != null) {

                    int colTotal = sheet.getColumns(); //전체컬럼
                    int rowIndexStart = 1; //row인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    StringBuilder sb;

                    for (int row = rowIndexStart; row < rowTotal; row++) {
                        sb = new StringBuilder();
                        for (int col = 0; col < colTotal; col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            temp.add(contents);
                            sb.append("col" + col + " : " + contents + " , ");
                        }
                        Log.i("test", sb.toString());
                        ClassNode classnode = new ClassNode();
                        classnode.setClassName(temp.get(0));

                        for (int i = 0; i < 9; i++) {
                            if (temp.get(1 + i) != "") {
                                result.add(Integer.parseInt(temp.get(1 + i)));
                            } else {
                                result.add(0);
                            }

                        }
                        classnode.setMonday(result);
                        result.clear();

                        for (int i = 0; i < 9; i++) {
                            if (temp.get(10 + i) != "") {
                                result.add(Integer.parseInt(temp.get(10 + i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setTuesday(result);
                        result.clear();

                        for (int i = 0; i < 9; i++) {
                            if (temp.get(19 + i) != "") {
                                result.add(Integer.parseInt(temp.get(19 + i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setWednesday(result);
                        result.clear();

                        for (int i = 0; i < 9; i++) {
                            if (temp.get(28 + i) != "") {
                                result.add(Integer.parseInt(temp.get(28 + i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setThursday(result);
                        result.clear();

                        for (int i = 0; i < 9; i++) {
                            if (temp.get(37 + i) != "") {
                                result.add(Integer.parseInt(temp.get(37 + i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setFriday(result);
                        result.clear();
                        classnode.setNearbyElevator(temp.get(46));

                        c.add(classnode);
                        Log.d("Check", c.get(row - 1).getClassName());

                        temp.clear();


                    }

                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (BiffException e2) {
            e2.printStackTrace();
        }

        return c;
    }
}
