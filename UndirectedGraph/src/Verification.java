import java.util.*;
public class Verification {
    // key is country value+" "+business_type,
    // values are required fields sorted in lexicographically order
    private static Map<String, List<String>> required = new HashMap<>();
    static {
        // country individual
        List<String> fs =Arrays.asList(
                "first_name", "last_name", "date_of_birth", "social_security_number", "email", "phone");
        Collections.sort(fs);
        required.put("US individual", fs);

        fs = Arrays.asList(
                "first_name",
                "last_name",
                "first_name_kana",
                "last_name_kana",
                "date_of_birth",
                "tax_id_number",
                "email");
        Collections.sort(fs);
        required.put("JP individual", fs);

        fs = Arrays.asList("first_name", "last_name", "tax_id_number", "email", "phone");
        Collections.sort(fs);
        required.put("FR individual", fs);

        // country company
        fs = Arrays.asList("name", "employer_id_number", "email", "phone");
                Collections.sort(fs);
        required.put("US company", fs);
                fs = Arrays.asList("name", "tax_id_number", "phone");
        Collections.sort(fs);
        required.put("JP company", fs);

        fs = Arrays.asList("name", "director_name", "tax_id_number", "phone");
        fs.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        required.put("FR company", fs);

    }

    public static List<String> verify_merchants(List<String> lines) {
        // will keep parsed merchant and provided field value pairs
        Map<String, Map<String, String>> input = new HashMap<>();
        for (String line : lines) {
            String[] cuts = line.split(",");
            input.putIfAbsent(cuts[0], new HashMap<>());
            input.get(cuts[0]).put(cuts[1], cuts[2]);
        }
        //merchant_id    ,      file_name          ,file_value;
        //                country//business_type

        List<String> merchants = new ArrayList<>(input.keySet());
        Collections.sort(merchants);
        //lexicoorder

        List<String> r = new ArrayList<>(merchants.size());
        StringBuilder mr;
        for (String m : merchants) {
            Map<String, String> kv = input.get(m);// file_name      ,      file_value;
            mr = new StringBuilder();
            if (!kv.containsKey("country") || !kv.containsKey("business_type")) {
                mr.append(m).append(":UNVERIFIED:");
                if (!kv.containsKey("business_type"))
                    mr.append("business_type,");
                if (!kv.containsKey("country"))
                    mr.append("country,");
                mr.deleteCharAt(mr.length() - 1);//delete comma
                r.add(mr.toString());
                continue;
            }
            //country + business all have       value          +          value
            //list  里需要核实的数据
            for (String f : required.get(kv.get("country") + " " + kv.get("business_type"))) {
                if (!kv.containsKey(f)) {
                    if (mr.length()==0) {
                        mr.append(m).append(":UNVERIFIED:");
                    }else {
                        mr.append(f).append(",");
                    }
                }
            }
            if (mr.length()==0)
                r.add(m + ":VERIFIED");
            else {
                mr.deleteCharAt(mr.length() - 1);//delete comma
                r.add(mr.toString());
            }
        }
        return r;
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
