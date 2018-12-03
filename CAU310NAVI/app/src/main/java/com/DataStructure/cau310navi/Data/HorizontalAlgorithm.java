package com.DataStructure.cau310navi.Data;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class HorizontalAlgorithm {
    Context context;

    Map<String,Map<String,Double>> cityMapB6 = new HashMap<>();
    Map<String,Map<String,Double>> cityMapB5 = new HashMap<>();
    Map<String,Map<String,Double>> cityMapB4 = new HashMap<>();
    Map<String,Map<String,Double>> cityMapB3 = new HashMap<>();
    Map<String,Map<String,Double>> cityMapB2 = new HashMap<>();
    Map<String,Map<String,Double>> cityMapB1 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap1 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap2 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap3 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap4 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap5 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap6 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap7 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap8 = new HashMap<>();
    Map<String,Map<String,Double>> cityMap9 = new HashMap<>();

    int floor;

    public HorizontalAlgorithm(Context context) {
        this.context = context;
        cityMapB6 = makingHashMap(0);
        cityMapB5 = makingHashMap(1);
        cityMapB4 = makingHashMap(2);
        cityMapB3 = makingHashMap(3);
        cityMapB2 = makingHashMap(4);
        cityMapB1 = makingHashMap(5);
        cityMap1 = makingHashMap(6);
        cityMap3 = makingHashMap(7);
        cityMap4 = makingHashMap(8);
        cityMap5 = makingHashMap(9);
        cityMap6 = makingHashMap(10);
        cityMap7 = makingHashMap(11);
        cityMap8 = makingHashMap(12);
        cityMap9 = makingHashMap(13);
    }

    public Map<String,Map<String,Double>> getFloorCityMap(int floor){
        switch(floor){
            case -6 :
                return cityMapB6;
            case -5 :
                return cityMapB5;
            case -4 :
                return cityMapB4;
            case -3 :
                return cityMapB3;
            case -2 :
                return cityMapB2;
            case -1 :
                return cityMapB1;
            case 1 :
                return cityMap1;
            case 2 :
                return cityMap2;
            case 3 :
                return cityMap3;
            case 4 :
                return cityMap4;
            case 5 :
                return cityMap5;
            case 6 :
                return cityMap6;
            case 7 :
                return cityMap7;
            case 8 :
                return cityMap8;
            case 9 :
                return cityMap9;
             default :
                return null;
        }
    }


    public Map<String,Map<String,Double>> makingHashMap(int i){
        Map<String,Map<String,Double>> cityMap = new HashMap<>();
        String prevKeyName = null;
        try {
            Log.i("DataSet", "DataSet호출 try시작");
            InputStream is = context.getResources().getAssets().open("test.xls");
            Workbook wb = Workbook.getWorkbook(is);
            Log.d("wb", wb.toString());
            if (wb != null) {
                Sheet sheet = wb.getSheet(i); //시트 불러오기 지4, 7 층 데이터
                if (sheet != null) {
                    int colTotal = sheet.getColumns();
                    int rowindexStart = 1;  //row인덱스 시작
                    int rowTotal = sheet.getRows();

                    Log.i("DataSet", "DataSet호출 if문시작");
                    StringBuilder sb;
                    for (int row = rowindexStart; row < rowTotal; row++) {
                        String srcNode = sheet.getCell(0, row).getContents();
                        String destNode = sheet.getCell(1, row).getContents();
                        //String tmpWeight = sheet.getCell(2, row).getContents();
                        Cell temp = sheet.getCell(2, row);
                        NumberCell nc = (NumberCell) temp;
                        double numberTemp = nc.getValue();
                        //  int weight = Integer.parseInt(tmpWeight);

                        if (prevKeyName != null && prevKeyName.equals(srcNode)) {
                            cityMap.get(srcNode).put(destNode, numberTemp);
                        } else {
                            cityMap.put(srcNode, new HashMap<String, Double>());
                            cityMap.get(srcNode).put(destNode, numberTemp);
                        }
                        prevKeyName = srcNode;
                    }
                }
            }

            Log.i("DataSet", "값 입력 완료");
            //    Log.i("어레이값", allCityMap.get(0).toString());
        } catch(IOException e){
            e.printStackTrace();
        } catch(BiffException e){
            e.printStackTrace();
        }
        return cityMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> dijkstraReturnPath(String startNode, String endNode, Map<String,Map<String,Double>>floorMap) {
        int n = floorMap.size();    // Node의 수
        int e = 0;                  // Edge의 수
        double dijkstraMinimumDistance = 0.0;

        e = floorMap.keySet().stream().map((key) -> floorMap.get(key).size()).reduce(e, Integer::sum); // Edge의 수를 계산

        Map<String, Integer> keyToInteger = new HashMap<>(); // 노드 이름으로 그에 상응하는 숫자를 찾을수 있는 Map
        int keyIndex = 0;
        for (Object key : floorMap.keySet()) {
            keyToInteger.put(key.toString(), keyIndex);
            keyIndex++;
        }
        Map<Integer, String> integerToKey = new HashMap<>(); // 숫자로 그에 상응하는 노드 이름은 찾을수 있는 Map
        keyToInteger.keySet().forEach((key) -> {
            integerToKey.put(keyToInteger.get(key), key);
        });
        //System.out.println(integerToKey);

        double distance[] = new double[n];          // 최단 거리를 저장할 변수
        boolean[] check = new boolean[n];           // 해당 노드를 방문했는지 체크할 변수
        double cost[][] = new double[n][n];         // 모든 노드 간의 거리를 저장하는 배열
        int prev[] = new int[n];                          // 각 노드의 직전 노드를 저장

        floorMap.keySet().forEach((i) -> {  // 노드 간의 거리 계산 및 저장
            floorMap.get(i).keySet().forEach((j) -> {
                cost[keyToInteger.get(i)][keyToInteger.get(j)] = floorMap.get(i).get(j); //double값리턴
                //System.out.println(i + "->" + j + " = " + cost[keyToInteger.get(i)][keyToInteger.get(j)]);
            });
        });

        int start = keyToInteger.get(startNode); // 시작 노드 초기화
        int end = keyToInteger.get(endNode);     // 종착 노드 초기화

        for (int i = 0; i < n; i++) {            // distance[i], prev[i], check[i] 초기화
            distance[i] = Double.MAX_VALUE;
            prev[i] = start;
            check[i] = false;
        }

        distance[start] = 0;                     // 시작점의 거리는 0

        double minDistance = 0;
        int nextNode = 0;

        for (int a = 0; a < n; a++) {

            minDistance = Double.MAX_VALUE;

            //최소값 찾기
            for (int i = 0; i < n; i++) {                     // 기준 노드에서 인접 노드중 가장 가까운 노드 선택
                if (!check[i] && distance[i] < minDistance) { // 방문안했고 거리가 무한이 아닌 노드
                    nextNode = i;
                    minDistance = distance[i];
                }
            }

            check[nextNode] = true; // 해당 정점 확인 표시

            if(minDistance == Double.MAX_VALUE) break; //연결된 곳이 없으면 종료

            for (int i = 0; i < n; i++) {
                if (distance[nextNode] + cost[nextNode][i] < distance[i] && !check[i] && cost[nextNode][i]!=0){ // 방문을 안하고 위의 기준 노드에서 가장 가까운 인접 노드가 아닐때
                    distance[i] = distance[nextNode] + cost[nextNode][i];
                    prev[i] = nextNode;
                }
            }
        }

        //System.out.println(distance[end]);
        dijkstraMinimumDistance = distance[end];

        ArrayList<String> path = new ArrayList<>();
        int traverse = end;
        while(traverse != start) {
            path.add(0, integerToKey.get(traverse));
            traverse = prev[traverse];
        }

        path.add(0, startNode);
        path.add(String.valueOf(dijkstraMinimumDistance));
        for(int i =0; i<path.size();i++){
            Log.d("path@",path.get(i).toString());
        }
        return path;
    }

}
