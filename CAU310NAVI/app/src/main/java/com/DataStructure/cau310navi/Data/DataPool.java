package com.DataStructure.cau310navi.Data;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    //같은 층, 같은 구역의 배열 번호가 몇번인지 저장할 array
    ArrayList<Integer> forSameElevatorIndex = new ArrayList<Integer>();

    ElevatorNode A, B, C;

    //hashMap
    Map<String,Map<String,Double>> cityMap = new HashMap<>();
    String prevKeyName = null;
    Context context = null;

    String destination;
    int destinationIndex; //몇번째 index에 해당강의실 정보가 있는지 저장
    int destinationStair;
    int nearPeopleElevator=0; // 같은 구역에 있는 사람 수
    int nearPeopleStair=0; // 같은 구역에 있는 사람 수
    String destinationNearbyElevator;
    String destinationNearbyStair;
    String whatDay = null;
    boolean boomTime = false; // 40 ~ 70분 사이가 boomtime

    public DataPool(Context context) {
        this.context = context;
    }

    public void fillClassNode(){
        try {
            InputStream is = context.getResources().getAssets().open("class.xls");
            Workbook wb = Workbook.getWorkbook(is);
            if(wb != null){
                Sheet sheet  = wb.getSheet(0); //시트 불러오기
                if(sheet != null){

                    int colTotal = sheet.getColumns(); //전체컬럼
                    int rowIndexStart = 1; //row인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal-1).length;

                    for(int row=rowIndexStart;row<rowTotal;row++){
                        for(int col=0;col<colTotal;col++){
                            String contents = sheet.getCell(col,row).getContents();
                            temp.add(contents);
                        }

                        ClassNode classnode = new ClassNode();
                        classnode.setClassName(temp.get(0));
                        //c.get(c.size()).setClassName(temp.get(0));
                        //Log.d("className",  c.get(c.size()).getClassName());
                        for(int i=0;i<9;i++) {
                            if(temp.get(1+i) != "") {
                                result.add(Integer.parseInt(temp.get(1+i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setMonday(result);
                        result.clear();

                        for(int i=0;i<9;i++) {
                            if(temp.get(10+i) != "") {
                                result.add(Integer.parseInt(temp.get(10+i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setTuesday(result);
                        result.clear();

                        for(int i=0;i<9;i++) {
                            if(temp.get(19+i) != "") {
                                result.add(Integer.parseInt(temp.get(19+i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setWednesday(result);
                        result.clear();

                        for(int i=0;i<9;i++) {
                            if(temp.get(28+i) != "") {
                                result.add(Integer.parseInt(temp.get(28+i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setThursday(result);
                        result.clear();

                        for(int i=0;i<9;i++) {
                            if(temp.get(37+i) != "") {
                                result.add(Integer.parseInt(temp.get(37+i)));
                            } else {
                                result.add(0);
                            }
                        }
                        classnode.setFriday(result);

                        result.clear();

                        classnode.setNearbyElevator(temp.get(46));
                        classnode.setNearbyStair(temp.get(47));
                        classnode.setFloor(temp.get(48));
                        c.add(classnode);
                        temp.clear();

                    }

                }
            }

        } catch(IOException e){
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }

    public int countFloorAreaElevator(int classTime, String floor, String whatDay, String area){
        int floorPeople = 0;
        for(int i = 0 ; i < c.size() ; i++){
            if(floor.equals(c.get(i).getFloor()) && area.equals(c.get(i).getNearbyElevator())){
                switch (whatDay){
                    case "mon" :
                        floorPeople += c.get(i).getMonday().get(classTime-1);
                        break;
                    case "tue" :
                        floorPeople += c.get(i).getTuesday().get(classTime-1);
                        break;
                    case "wed" :

                        floorPeople += c.get(i).getWednesday().get(classTime-1);
                        break;
                    case "thr" :
                        floorPeople += c.get(i).getThursday().get(classTime-1);
                        break;
                    case "fri" :
                        floorPeople += c.get(i).getFriday().get(classTime-1);
                        break;
                }
            }
        }
        return floorPeople;
    }
    public int countDestinationAreaElevator(int classTime, String destination, String whatDay){

        for(int i =0;i<c.size();i++){
            if (destination.equals(c.get(i).getClassName()))  {
                destinationIndex = i;
            }
        }
        destinationNearbyElevator = c.get(destinationIndex).getNearbyElevator(); //그 강의실의 해당구역

        //같은층, 같은구역에 있는 강의실들의 인덱스를 조사하기 위해서
        for(int i =0;i<c.size();i++){
            if (c.get(destinationIndex).getFloor().equals(c.get(i).getFloor()))  { //층수가 같은 것들 중에서
                if(c.get(destinationIndex).getNearbyElevator().equals(c.get(i).getNearbyElevator())){ //구역이같은곳
                    forSameElevatorIndex.add(i);
                }
            }
        }
        switch(whatDay) {
            case "mon" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getMonday());
                    nearPeopleElevator += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "tue" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getTuesday());
                    nearPeopleElevator += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "wed" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getWednesday());
                    nearPeopleElevator += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "thr" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getThursday());
                    nearPeopleElevator += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "fri" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getFriday());
                    nearPeopleElevator += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
        }
        String nn = Integer.toString(nearPeopleElevator);
        Log.d("근처", nn);

        return nearPeopleElevator;
    }

    public int countDestinationAreaStair(int classTime, String destination, String whatDay){

        for(int i =0;i<c.size();i++){
            if (destination.equals(c.get(i).getClassName()))  {
                destinationIndex = i;
            }
        }
        destinationNearbyStair = c.get(destinationIndex).getNearbyStair(); //그 강의실의 해당구역

        //같은층, 같은구역에 있는 강의실들의 인덱스를 조사하기 위해서
        for(int i =0;i<c.size();i++){
            if (c.get(destinationIndex).getFloor().equals(c.get(i).getFloor()))  { //층수가 같은 것들 중에서
                if(c.get(destinationIndex).getNearbyStair().equals(c.get(i).getNearbyStair())){ //구역이같은곳
                    forSameElevatorIndex.add(i);
                }
            }
        }
        switch(whatDay) {
            case "mon" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getMonday());
                    nearPeopleStair += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "tue" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getTuesday());
                    nearPeopleStair += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "wed" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getWednesday());
                    nearPeopleStair += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "thr" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getThursday());
                    nearPeopleStair += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;
            case "fri" :
                for (int i = 0; i < forSameElevatorIndex.size(); i++) {
                    int tempIndex = forSameElevatorIndex.get(i); //같은 구역의 강의실 인덱스를 데려옴
                    ArrayList<Integer> temp = new ArrayList<>(c.get(tempIndex).getFriday());
                    nearPeopleStair += temp.get(classTime - 1); // 지정해둔 classTime의 인원을 nearPeople(전체 명수)에 더함
                }break;

        }
        String nn = Integer.toString(nearPeopleStair);
        Log.d("근처", nn);

        return nearPeopleStair;
    }

    public StairNode setStairNode(String area, String whatDay, int lowest, int highest){
        StairNode node = new StairNode(area, whatDay, lowest, highest);
        switch(area) {
            case "D" :
                node.setTimeByStair(0);
                break;
            case "E" :
                node.setTimeByStair(1);
                break;
            case "F" :
                node.setTimeByStair(2);
                break;
            case "G" :
                node.setTimeByStair(3);
                break;
        }

        return node;
    }
    public ElevatorNode setElevatorNode(String area, String whatDay, int lowest, int highest, int myFloor, int classTime){
        ElevatorNode node = new ElevatorNode(area, whatDay, lowest, highest);
        int totalPeople = 0;
        int moveTotalFloor = highest-lowest;
        for(int i = lowest ; i <= highest ; i++) {
            if(i == 0)
                continue;
            int people = countFloorAreaElevator(classTime, i + "", whatDay, area);
            node.floorPeople.put(i, people);
            totalPeople += people;
        }

        node.setPeople(totalPeople);

        //몇번 반복해야 하는지 계산. ->

        for(int i = lowest ; i <= myFloor ; i++){
            if(i == 0)
                continue;
            totalPeople -= node.getFloorPeople().get(i);
        }

        if(area.equals("B"))
            totalPeople /= 2;
        else
            totalPeople /= 5;

        node.count = totalPeople / 20;

        //Spend Time

        if(boomTime==true){
            node.spendTimeShortest = (10*(moveTotalFloor)+1.5*(moveTotalFloor+1));
            node.spendTimeLongest = ((10*(moveTotalFloor-1)+(1.5*moveTotalFloor)/2)*2)
                    +(10*moveTotalFloor+1.5*(moveTotalFloor-1));
        }else{
            //붐비는 시간이 아닐 경우
            node.spendTimeShortest = (10*(moveTotalFloor/2)+1.5*((moveTotalFloor/2)-1));
            node.spendTimeLongest = (10*(moveTotalFloor/2-1)+(1.5*(moveTotalFloor/2))/2)
                    +(10*(node.getCount()/2)+1.5*((node.getCount()/2)-1));
        }

        return node;
    }

    public ElevatorNode getOptimaleElevatorNode(ElevatorNode A, ElevatorNode B, ElevatorNode C, String startNode, int floor, boolean boomTime, HorizontalAlgorithm horizontalAlgorithm, Map<String,Map<String,Double>> cityMap){
        ArrayList<String> fromA = horizontalAlgorithm.dijkstraReturnPath(startNode, A.area, cityMap);
        ArrayList<String> fromB = horizontalAlgorithm.dijkstraReturnPath(startNode, B.area, cityMap);
        ArrayList<String> fromC = horizontalAlgorithm.dijkstraReturnPath(startNode, C.area, cityMap);

        double [][]spendAllTime = new double[3][2]; // 첫번째 : 시간, 두번째 : 거리 , 0 : A, 1 : B, 2 : C

        double minTime = 0;
        int minTimeNode = 0;
        int mindistNode = 0;

        spendAllTime[0][1] = Double.parseDouble(fromA.get(fromA.size()-1));
        spendAllTime[1][1] = Double.parseDouble(fromB.get(fromB.size()-1));
        spendAllTime[2][1] = Double.parseDouble(fromC.get(fromC.size()-1));

        if(boomTime){
            spendAllTime[0][0] = Double.parseDouble(fromA.get(fromA.size()-1)) + A.spendTimeLongest;
            spendAllTime[1][0] = Double.parseDouble(fromB.get(fromB.size()-1)) + B.spendTimeLongest;
            spendAllTime[2][0] = Double.parseDouble(fromC.get(fromC.size()-1)) + C.spendTimeLongest;
        } else {
            spendAllTime[0][0] = Double.parseDouble(fromA.get(fromA.size()-1)) + A.spendTimeShortest;
            spendAllTime[1][0] = Double.parseDouble(fromB.get(fromB.size()-1)) + B.spendTimeShortest;
            spendAllTime[2][0] = Double.parseDouble(fromC.get(fromC.size()-1)) + C.spendTimeShortest;
        }

        minTime = spendAllTime[0][0];
        for(int i = 1 ; i < 3 ; i++){
            if(spendAllTime[i][0] < minTime){
                minTime = spendAllTime[i][0];
                mindistNode = i;
            }
        }
        // 시간만 하고 거리 안함 할 것

        if(mindistNode == 0){
            return A;
        } else if (mindistNode == 1){
            return B;
        } else
            return C;
    }
//    public StairNode getOptimalStairNode
//    public StairNode getOptimalStairNode(StairNode D, StairNode E, StairNode F, StairNode G, HorizontalAlgorithm horizontalAlgorithm){
//
//    }
}
