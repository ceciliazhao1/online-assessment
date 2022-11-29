import java.util.*;
class HW {
        public static void main(String[] args) {
            String [] str=solution(new String[] {"Hey @id123,id321 review this Pr PLEASE! id321 go away",
                    "Hey @id7 nice appro@ch! Great job! @id800 what"
            , "@id123,id321 thx"}, new String[] {"id123","id234","id7","id321"});
            for(String s:str){
                System.out.println(s);
            }
            String [] str1=solution(new String[] {"no id1,id2,id3,id4, @id1 can do it"}, new String[] {"id1","id2","id3","id4"});
            for(String s:str1){
                System.out.println(s);
            }
            String [] str2=solution(new String[] {"no @proach,id2,id3,id4, @id1 can do it"}, new String[] {"id1","id2","id3","id4"});
            for(String s:str2){
                System.out.println(s);
            }
            String [] str3=solution(new String[] {"id2,id3,id4, @id1"}, new String[] {"id1","id2","id3","id4"});
            for(String s:str3){
                System.out.println(s);
            }
        }

        public static String[] solution(String[] s,String [] members) {

            HashMap<String, Integer> map= new HashMap<>();
            for(String m:members){
                map.put(m,0);
            }
            for(String str: s) {
                Set<String> set= new HashSet<>();
                int left=0;
                while(left<str.length()){
                    char isAt= str.charAt(left);
                    if(isAt=='@'){
                        loop(map,str,left,set,str.length());
                    }
                    left++;
                }
            }
            String[] res= new String[members.length];
            int i=0;
            for(String str:map.keySet()){
                res[i++]=str+"="+map.get(str);
            }
            Arrays.sort(res,(a,b)->helper(a,b));

            return res;
        }
        private static int helper(String a, String b){
            String []aa= a.split("=");
            String []bb= b.split("=");
            int cur=Integer.parseInt(aa[1])-Integer.parseInt(bb[1]);
            if(cur>0){
                return -1;
            }else if(cur<0){
                return 1;
            }else{
                return aa[0].compareTo(bb[0]);
            }
        }
        private static void loop(HashMap<String, Integer> map, String str, int left,Set<String> set,int len){
            StringBuilder sb = new StringBuilder();
            left++;
            if(left==len){ return; }
            while(left<len&&(Character.isDigit(str.charAt(left))||Character.isLetter(str.charAt(left)))){
                sb.append(str.charAt(left));
                left++;
            }

            int size=sb.toString().length();
            if(size<=2|| size>=6) {
                return;
            }
            if(sb.toString().charAt(0)!='i'|| sb.toString().charAt(1)!='d'){
                return;
            }
            while(size>2){
                if(Character.isDigit(sb.toString().charAt(size-1))){
                    size--;
                }else{
                    return;
                }
            }
            if (set.contains(sb.toString())) {

            } else {

                if (map.containsKey(sb.toString())) {
                    map.put(sb.toString(), map.get(sb.toString()) + 1);
                }
                set.add(sb.toString());
            }

            if(left<len && str.charAt(left)==',' ){
                loop(map,str,left,set,len);
            }else{
                return;
            }
        }
}
