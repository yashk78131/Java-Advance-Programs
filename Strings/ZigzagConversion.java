class Solution {
    public String convert(String s, int numRows) {
      if (numRows == 1) return s;

        StringBuilder[] zigzag = new StringBuilder[numRows];
        for (int k = 0; k < numRows; k++) {
            zigzag[k] = new StringBuilder();
        }

        int i = 0, row = 0;
        boolean direction = true;

        while (true) {
            if (direction) {
                while (row < numRows && i < s.length()) {
                    zigzag[row++].append(s.charAt(i++));
                }
                row = numRows - 2;
            } else {
                while (row >= 0 && i < s.length()) {
                    zigzag[row--].append(s.charAt(i++));
                }
                row = 1;
            }
            if (i >= s.length()) break;
            direction = !direction;
        }

        StringBuilder ans = new StringBuilder();
        for (StringBuilder sb : zigzag) {
            ans.append(sb);
        }

        return ans.toString();  
    }
}