class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int n = nums.length;
        Set<Integer> ss = new HashSet<>();
        for (int num : nums) ss.add(num);
        
        if (t == 0 && n == ss.size()) 
            return false;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < i + 1 + k && j < n; j++) {
                long diff = Math.abs((long) nums[i] - (long) nums[j]);
                if (diff <= t)
                    return true;
            }
        }
        return false;
    }
}
