class Solution {
    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        // try every cell as a starting point
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == word.charAt(0)) {
                    if (dfs(board, word, i, j, 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, String word, int i, int j, int index) {
        // if all characters are matched
        if (index == word.length()) {
            return true;
        }

        // check bounds and character match
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length
                || board[i][j] != word.charAt(index)) {
            return false;
        }

        // mark visited
        char temp = board[i][j];
        board[i][j] = '#';

        // explore 4 directions
        int[] di = {-1, 1, 0, 0};
        int[] dj = {0, 0, -1, 1};

        for (int d = 0; d < 4; d++) {
            int ni = i + di[d];
            int nj = j + dj[d];
            if (dfs(board, word, ni, nj, index + 1)) {
                board[i][j] = temp; // restore before returning
                return true;
            }
        }

        // backtrack (unmark)
        board[i][j] = temp;
        return false;
    }

    // Optional: for testing locally
    public static void main(String[] args) {
        Solution s = new Solution();
        char[][] board = {
            {'A', 'B', 'C', 'E'},
            {'S', 'F', 'C', 'S'},
            {'A', 'D', 'E', 'E'}
        };
        String word = "ABCCED";
        System.out.println(s.exist(board, word)); // Output: true
    }
}
