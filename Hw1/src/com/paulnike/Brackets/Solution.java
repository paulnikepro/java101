package com.paulnike.Brackets;

import java.util.Stack;
import java.util.HashMap;

public class Solution {
    public boolean isValid(String s) {
        HashMap<Character, Character> matchingBrackets = new HashMap<>();
        matchingBrackets.put(')', '(');
        matchingBrackets.put('}', '{');
        matchingBrackets.put(']', '[');

        Stack<Character> bracketStack = new Stack<>();

        for (char currentChar : s.toCharArray()) {
            if (matchingBrackets.containsKey(currentChar)) {
                char topBracket = bracketStack.isEmpty() ? '#' : bracketStack.pop();

                if (topBracket != matchingBrackets.get(currentChar)) {
                    return false;
                }
            } else {
                bracketStack.push(currentChar);
            }
        }

        return bracketStack.isEmpty();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        System.out.println(solution.isValid("()"));       // true
        System.out.println(solution.isValid("()[]{}"));   // true
        System.out.println(solution.isValid("(]"));       // false
        System.out.println(solution.isValid("([])"));     // true
        System.out.println(solution.isValid("([)]"));     // false
    }
}