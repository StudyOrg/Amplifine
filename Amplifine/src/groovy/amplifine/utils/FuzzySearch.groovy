package amplifine.utils

class FuzzySearch {
    public static int levensteinDistance(String s1, String s2) {
        int m = s1.length()
        int n = s2.length()

        int[] d1
        int[] d2 = new int[n + 1]

        for (int i = 0; i <= n; i++) {
            d2[i] = i
        }

        for (int i = 1; i <= m; i++) {
            d1 = d2
            d2 = new int[n + 1]

            for (int j = 0; j <= n; j++) {
                if (j == 0) {
                    d2[j] = i
                } else {
                    int cost = (s1.charAt(i - 1) != s2.charAt(j - 1)) ? 1 : 0
                    if (d2[j - 1] < d1[j] && d2[j - 1] < d1[j - 1] + cost) {
                        d2[j] = d2[j - 1] + 1
                    } else if (d1[j] < d1[j - 1] + cost) {
                        d2[j] = d1[j] + 1
                    } else {
                        d2[j] = d1[j - 1] + cost
                    }
                }
            }
        }

        return d2[n]
    }
}
