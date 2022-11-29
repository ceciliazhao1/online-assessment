import java.util.*;

public class veriTRY {
    public static Map<String,List<String>> map= new HashMap<>();
    static{
        List<String> fn = Arrays.asList("first_name", "last_name", "date_of_birth", "social_security_number", "email", "phone");
        Collections.sort(fn);
        map.put("US individuals", fn);
        fn = Arrays.asList("first_name", "last_name", "first_name_kana", "last_name_kana", "date_of_birth", "tax_id_number", "email");
        Collections.sort(fn);
        map.put("JP individuals", fn);
        fn = Arrays.asList("first_name", "last_name", "tax_id_number", "email", "phone");
        Collections.sort(fn);
        map.put("FR individuals", fn);

        fn = Arrays.asList("name", "employed_id_number", "support_email", "phone");
        Collections.sort(fn);
        map.put("US company", fn);
        fn = Arrays.asList("name", "tax_id_number", "phone");
        Collections.sort(fn);
        map.put("JP company", fn);
        fn = Arrays.asList("name", "director_name","tax_id_number","phone");
        Collections.sort(fn);
        map.put("FR company", fn);
    }
    public static List<String> verify_merchants(List<String>arr) {
        List<String> res = new ArrayList<>();
        Map<String, Map<String, String>> input = new HashMap<>();
        Collections.sort(arr);
        for (String s : arr) {
            String[] str = s.split(",");
            if (!input.containsKey(str[0])) {
                input.put(str[0], new HashMap<>());
                input.get(str[0]).put(str[1], str[2]);
            } else {
                input.get(str[0]).put(str[1], str[2]);
            }
        }
        for (String s : input.keySet()) {
            Map<String, String> filename = input.get(s);
            StringBuilder sb = new StringBuilder();
            if (!input.get(s).containsKey("country") || !input.get(s).containsKey("business_type")) {
                sb.append(s).append(":UNVERIFIED:");
                if (!input.get(s).containsKey("business_type")) {
                    sb.append("business_type,");
                }
                if (!input.get(s).containsKey("country")) {
                    sb.append("country,");
                }
                sb.deleteCharAt(sb.length() - 1);
                res.add(sb.toString());
                continue;
            } else {
                for (String ss : map.keySet()) {
                    if ((input.get(s).get("country") + " " + input.get(s).get("business_type")).equals(ss)) {
                        List<String> listnow = map.get(ss);
                        sb = new StringBuilder();
                        for (String sss : listnow) {
                            if (filename.containsKey(sss)) {
                                continue;
                            } else {
                                if (sb.length() == 0) {
                                    sb.append(s).append(":UNVERIFIED:").append(sss + ',');
                                } else {
                                    sb.append(sss + ',');
                                }
                            }
                        }
                        if (sb.length() == 0) {
                            continue;
                        } else {
                            sb.deleteCharAt(sb.length() - 1);
                            res.add(sb.toString());
                        }

                    }
                }

            }
        }
        return res;
    }
        public static void main(String[] args) {

            System.out.println(
                    Arrays.toString(verify_merchants(Arrays.asList("acct_123,business_type,company")).toArray())
                            .equals("[acct_123:UNVERIFIED:country]"));

            System.out.println(
                    Arrays.toString(
                            verify_merchants(
                                    Arrays.asList(
                                            "acct_123,country,US",
                                            "acct_123,business_type,individual",
                                            "acct_123,first_name,Jane",
                                            "acct_123,last_name,Doe",
                                            "acct_123,date_of_birth,01011970",
                                            "acct_123,social_security_number,123456789",
                                            "acct_123,email,test@example.com",
                                            "acct_123,phone,555555555"))
                                    .toArray()).equals("[acct_123:VERIFIED]"));

            System.out.println(
                    Arrays.toString(
                                    verify_merchants(
                                            Arrays.asList(
                                                    "acct_123,tax_id_number,12345689",
                                                    "acct_123,country,FR",
                                                    "acct_123,business_type,company",
                                                    "acct_456,business_type,individual",
                                                    "acct_456,country,JP",
                                                    "acct_456,first_name,Mei",
                                                    "acct_456,last_name,Sato",
                                                    "acct_456,first_name_kana,Mei",
                                                    "acct_456,last_name_kana,Sato",
                                                    "acct_456,date_of_birth,01011970",
                                                    "acct_456,tax_id_number,123456",
                                                    "acct_456,email,test@example.com"))
                                            .toArray())
                            .equals("[acct_123:UNVERIFIED:director_name,name,phone, acct_456:VERIFIED]"));
        }
}
