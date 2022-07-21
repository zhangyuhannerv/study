
//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母组成 
// 
// Related Topics 字符串 动态规划 👍 5476 👎 0

package com.cute.leetcode.editor.cn;

public class _5LongestPalindromicSubstring {
    public static void main(String[] args) {
        Solution solution = new _5LongestPalindromicSubstring().new Solution();
    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public String longestPalindrome(String s) {
            int left = 0;
            int right = 0;
            int maxLen = 0;
            for (int i = 0; i < s.length(); i++) {
                int len = Math.max(getSubStringLength(s, i, i + 1), getSubStringLength(s, i, i));
                if (len > maxLen) {
                    maxLen = len;
                    left = i - (len - 1) / 2;
                    right = i + len / 2;
                }
            }
            return s.substring(left, right + 1);
        }

        public int getSubStringLength(String s, int left, int right) {
            while (left >= 0 || right < s.length()) {
                if (s.charAt(left) == s.charAt(right)) {
                    left--;
                    right++;
                } else {
                    break;
                }
            }
            return (right - 1) - (left + 1) + 1;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)

}