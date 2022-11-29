import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.*;
public class getbolts {
    Set<String> bolts = new HashSet<>();
    Map<String, Long> ave;
    // User:<Date without second, req counts>
    Map<String, Map<Date, Integer>> detect = new HashMap<>();
    Map<String, Long[]> status = new HashMap<>();
    public void process_invites(int N, String csvfilepath) throws IOException {
        try (BufferedReader fb = Files.newBufferedReader(Paths.get(csvfilepath))) {
            String line;
            while ((line = fb.readLine()) != null && N-- > 0) {
                parseLog(line);
            }
            System.out.println("handle");
        } catch (ParseException e) {
            e.printStackTrace();

        }
    }

        private void parseLog(String log) throws ParseException {
            String[] l = log.split(",");
            long t = Long.valueOf(l[0]);
            String eventType = l[1], userId = l[2];
            if (bolts.contains(userId)) return;
            status.putIfAbsent(userId, new Long[] {null, null});
            if (eventType == "invite_requested") {
                Date d = new Date(t);
                d.setSeconds(0);
                if (!detect.containsKey(userId)) {
                    detect.put(userId, new HashMap<>());
                    detect.get(userId).put(d, 1);
                } else {
                    Map<Date, Integer> count = detect.get(userId);
                    count.put(d, count.getOrDefault(d, 0) + 1);
                    if (count.get(d) >= 5) {
                        bolts.add(userId);
                    }
                    if (status.get(userId)[0] != null) {
                        status.get(userId)[0] = t;
                    }
                }
                }else if (eventType == "invite_send") {
                    status.get(userId)[1] = t;
                } else if (eventType == "invite_actived") {
                    Long a = status.get(userId)[0], b = status.get(userId)[1];
                    if (a < b && b < t) {
                        ave.put(userId, t - a);
                    }
                }
            }
}
