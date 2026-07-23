package no08_string.practice;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum() {
        int[] nums = { 0, 0, 0, 0 };
        List<List<Integer>> res = new ArrayList<>();
        int max, min;
        max = min = nums[0];
        for (int c : nums) {
            max = max < c ? c : max;
            min = min > c ? c : min;
        }
        int len = max - min + 1;

        for (int i = 0; i < nums.length - 1; i++) {
            int[] hash = new int[len];
            for (int j = i + 1; j < nums.length; j++) {
                hash[nums[j] - min]++;
            }
            int target = -nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                hash[nums[j] - min]--;
                int value = target - nums[j];
                int index = value - min;
                if (index >= 0 && index < len && hash[index] > 0) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(nums[i]);
                    tmp.add(nums[j]);
                    tmp.add(value);
                    res.add(tmp);
                    hash[index] = 0;
                }
            }
        }
        return res;
    }

    public static void main(String... agrs) {
        Solution a = new Solution();
        System.out.println(a.threeSum().toString());
    }
}