// Online Java Compiler
// Use this editor to write, compile and run your Java code online

class statisticindicator {
    public static void main(String[] args) {
        System.out.println(solution(new int[]{1,2,2,2,2,3,3,3,1,1,2,2}));
        System.out.println(solution(new int[]{2,2,2,4,4,4,4,4,4}));
    }
    private static int solution(int[] arr) {
        int max = 0;
        for (int i : arr) {
            max = Math.max(i, max);
        }
        //每个数字出现次数
        int[] res1 = new int[max+1];
        for (int i : arr) {
            res1[i]++;
        }
        int ind1 = 0;
        for (int i = 1 ; i <= max; i++) {
            if (res1[i] == i) ind1++;
        }

        ///
        int ind2 = 0;
        int p = 1;
        while (p < arr.length) {
            if (p == arr[p]) {
                int k = arr[p];
                int count = 1;
                while (p < arr.length && arr[p] == k) {
                    p++;
                    count++;
                }
                if (count == k) {
                    ind2++;
                }
            }
            p++;
        }
        return Math.abs(ind1 - ind2);
    }
}