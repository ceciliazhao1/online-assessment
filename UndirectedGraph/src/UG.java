// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;
class UG {
    public static void main(String[] args) {
        int []A=new int[]{1,2,2,3,3,1,1};
        System.out.println(solution(A));
        //alice
        System.out.println(solution(new int[]{1,2,2,3,3}));
        //bob
        System.out.println(solution(new int[]{1,2,2,3,3,3,3}));
        //alice
        System.out.println(solution(new int[]{3,2,2,3,3,3,1}));
        //alice
        System.out.println(solution(new int[]{3,2,2,3,3,3,1,1}));
        //Bob
    }
    public static String solution(int[] A) {
        List<Integer> list= new ArrayList<>();
        for(int num:A){
            list.add(num);
        }
        int pivot=-1;
        pivot=dfs(list,pivot);
        return pivot==1?"Alice":"Bob";
    }
    private static int dfs(List<Integer> list, Integer pivot){

        int size=list.size();
        if(size<=1) return pivot;
        int cur=-1;
        for (int i=0;i<size-1;i++){
            if(list.get(i)==list.get(i+1)) {
                cur=i;
                pivot *= -1;
                break;
            }
        }
        if(cur!=-1){
            list.remove(cur);
            list.remove(cur);
            pivot=dfs(list,pivot);
        }else{
            return pivot;
        }
        return pivot;
    }
}