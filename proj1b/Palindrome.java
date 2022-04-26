public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        int len = word.length();
        for (int i = 0; i < len; i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque);
    }

    public boolean isPalindrome(Deque<Character> dq) {
        if (dq.size() <= 1) {
            return true;
        }
        if (dq.removeFirst() != dq.removeLast()) {
            return false;
        } else {
            return isPalindrome(dq);
        }
    }

    // According to the rules below, chars within one is still considered to be equal.
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() <= 1 || word == null) {
            return true;
        }
        int len = word.length();
        for (int i = 0; i < len / 2; i++) {
            if (!cc.equalChars(word.charAt(i), word.charAt(len - i - 1))) {
                return false;
            }
        }
        return true;
    }
}