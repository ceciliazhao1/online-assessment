import java.io.*;
import java.util.*;
public class getimage {
    //创建BufferedReader读取文件内容
    public static void main(String args[]) throws IOException{

        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Set<String> set= new HashSet<>();
        while ((line=br.readLine())!=null) {
            read(line,set);
        }
        br.close();
        write(set,args[0]);
    }
    private static void read(String line,Set<String> set){
        for(int i=0;i<line.length();i++){
            String sb= line.substring(i,i+4);
            if(sb.equals("GET ")) {
                String [] str=line.substring(i+4).split(" |/");
                for(int j=0;j<str.length;j++){
                    if(str[j].endsWith(".GIF")||str[j].endsWith(".gif")){
                        if(j+3<str.length && str[j+3].equals("200")){
                            set.add(str[j]);
                        }
                    }
                }
            }
        }
    }
    private static void write(Set<String> set,String filename) throws IOException{
        String dir = "gifs_"+filename;
        File file = new File(dir);
        //如果文件不存在，创建文件
        if (!file.exists())
            file.createNewFile();
        //创建BufferedWriter对象并向文件写入内容
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        //向文件中写入内容
        for(String line:set){
            bw.write(line);
        }
        bw.flush();
        bw.close();
    }
}
