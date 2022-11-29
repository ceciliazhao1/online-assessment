import java.util.*;
import java.io.*;

class UndirectedGraph {
    public static void main(String[] args) {
        int[] A = new int[]{0,1,2,1,4,4};
        int[] B = new int[]{1,2,0,4,5,6};
        int res = UndirectedGraph(A, B);
        System.out.println(res);
    }

    public static int count=0;
    public static int UndirectedGraph(int [] A, int [] B) {

        HashMap<Integer,List<Integer>> map= new HashMap<>();
        HashMap<Integer,Integer> map2= new HashMap<>();
        for(int i=0;i<6;i++) {
            if (!map.containsKey(A[i])) {
                map.put(A[i], new ArrayList<>());
                map.get(A[i]).add(B[i]);
            } else {
                map.get(A[i]).add(B[i]);
            }
            if (!map.containsKey(B[i])) {
                map.put(B[i], new ArrayList<>());
                map.get(B[i]).add(A[i]);
            } else {
                map.get(B[i]).add(A[i]);
            }
        }
        for(int i=0;i<6;i++) {
            map2.put(A[i],map.get(A[i]).size());
            map2.put(B[i],map.get(B[i]).size());
        }
        dfs(map2,map);
        return count;
    }

    private static void dfs(HashMap<Integer,Integer> map2, HashMap<Integer,List<Integer>> map){
        HashSet<Integer> set= new HashSet<>();
        for(int num:map2.keySet()){
            if(map2.get(num)==1){
                set.add(num);
            }
        }
        for(int num:set){
            map2.remove(num);
        }
        if(set.size()==0){
            return;
        }else{
            count++;
        }
        for(int num: set){
            if(map2.containsKey(map.get(num).get(0))) {
                int cur = map2.get(map.get(num).get(0)) - 1;
                if (cur == 0) {
                    map2.remove(map.get(num).get(0));
                } else {
                    map2.put(map.get(num).get(0), cur);
                }
            }
        }
        dfs(map2,map);
    }
}
