// Does math with big nonnegative integers
public class CustomBigInteger {
    private static class DigitNode {
        DigitNode prev;
        int val;
        DigitNode next;

        public DigitNode(DigitNode prev, int val, DigitNode next) {
            this.prev = prev;
            this.val = val;
            this.next = next;
        }
    }
    private final DigitNode start;
    private final DigitNode end;

    public CustomBigInteger(String num) {
        start = new DigitNode(null, num.charAt(0) - 48, null);
        DigitNode curr = start;
        for (int i = 1; i < num.length(); i++) {
            curr.next = new DigitNode(curr, num.charAt(i) - 48, null);
            curr = curr.next;
        }
        end = curr;
    }

    // num >= 0
    public CustomBigInteger(long num) {
        this(String.valueOf(num));
    }

    private CustomBigInteger(DigitNode start, DigitNode end) {
        this.start = start;
        this.end = end;
    }

    // Returns add + other
    public CustomBigInteger add(CustomBigInteger other) {
        DigitNode currThis = end;
        DigitNode currOther = other.end;
        int currDigit = currThis.val + currOther.val;
        DigitNode first = new DigitNode(null, currDigit % 10, null);
        DigitNode sum = first;
        currThis = currThis.prev;
        currOther = currOther.prev;
        int carry = currDigit / 10;
        while (currThis != null && currOther != null) {
            currDigit = currThis.val + currOther.val + carry;
            sum.prev = new DigitNode(null, currDigit % 10, sum);
            sum = sum.prev;
            currThis = currThis.prev;
            currOther = currOther.prev;
            carry = currDigit / 10;
        }
        if (currThis == null) {
            while (currOther != null) {
                currDigit = currOther.val + carry;
                sum.prev = new DigitNode(null, currDigit % 10, sum);
                sum = sum.prev;
                currOther = currOther.prev;
                carry = currDigit / 10;
            }
        } else {
            while (currThis != null) {
                currDigit = currThis.val + carry;
                sum.prev = new DigitNode(null, currDigit % 10, sum);
                sum = sum.prev;
                currThis = currThis.prev;
                carry = currDigit / 10;
            }
        }
        if (carry != 0) {
            sum.prev = new DigitNode(null, carry, sum);
            sum = sum.prev;
        }
        return new CustomBigInteger(sum, first);
    }

    // Better if other is shorter
    public CustomBigInteger multiply(CustomBigInteger other) {
        CustomBigInteger[] premultiplied = new CustomBigInteger[10];
        for (int i = 0; i < 10; i++) {
            premultiplied[i] = this.multiply(i);
        }
        DigitNode curr = other.end;
        CustomBigInteger sum = premultiplied[curr.val];
        curr = curr.prev;
        int places = 1;
        while (curr != null) {
            if (curr.val != 0) {
                sum = sum.add(premultiplied[curr.val].shift(places));
            }
            curr = curr.prev;
            places++;
        }
        return sum;
    }

    // Returns this * digit
    private CustomBigInteger multiply(int digit) {
        DigitNode currThis = end;
        int currDigit = currThis.val * digit;
        DigitNode first = new DigitNode(null, currDigit % 10, null);
        DigitNode sum = first;
        currThis = currThis.prev;
        int carry = currDigit / 10;
        while (currThis != null) {
            currDigit = currThis.val * digit + carry;
            sum.prev = new DigitNode(null, currDigit % 10, sum);
            sum = sum.prev;
            currThis = currThis.prev;
            carry = currDigit / 10;
        }
        if (carry != 0) {
            sum.prev = new DigitNode(null, carry, sum);
            sum = sum.prev;
        }
        return new CustomBigInteger(sum, first);
    }

    // places > 0
    // Returns this shifted to the left by given number of places
    private CustomBigInteger shift(int places) {
        DigitNode first = new DigitNode(null, 0, null);
        DigitNode node = first;
        for (int i = 1; i < places; i++) {
            node.prev = new DigitNode(null, 0, node);
            node = node.prev;
        }
        node.prev = end;
        return new CustomBigInteger(start, first);
    }

    // Returns integer sqrt of this
    public CustomBigInteger sqrt() {
        DigitNode curr = start;
        int length = 1;
        while (curr.next != null) {
            length++;
            curr = curr.next;
        }

        int newLength = (length + 1) / 2;
        DigitNode first = new DigitNode(null, 0, null);
        DigitNode currGuess = first;
        for (int i = 1; i < newLength; i++) {
            currGuess.prev = new DigitNode(null, 0, currGuess);
            currGuess = currGuess.prev;
        }

        DigitNode currNode = currGuess;
        CustomBigInteger currGuessCBI = new CustomBigInteger(currGuess, first);
        getSqrt: while (currNode != null) {
            int lo = 0;
            int hi = 10;
            // Goes down, checking if currGuess is too high or low
            while (hi != lo + 1) {
                int mid = (lo + hi) / 2;
                currNode.val = mid;
                int compare = currGuessCBI.multiply(currGuessCBI).compareTo(this);
                if (compare == -1) {
                    lo = mid;
                } else if (compare == 1) {
                    hi = mid;
                } else {
                    break getSqrt;
                }
            }
            currNode.val = lo;
            currNode = currNode.next;
        }
        return currGuessCBI;
    }

    // Returns -1 if this < other, 0 if this = other, 1 if this > other
    // Assumes no leading zeroes
    public int compareTo(CustomBigInteger other) {
        DigitNode currThis = start;
        DigitNode currOther = other.start;
        int answer = 0;
        while (currThis != null && currOther != null) {
            if (answer == 0 && currThis.val != currOther.val) {
                if (currThis.val < currOther.val) {
                    answer = -1;
                } else {
                    answer = 1;
                }
            }
            currThis = currThis.next;
            currOther = currOther.next;
        }
        if (currThis == null) {
            if (currOther == null) {
                return answer;
            }
            return -1;
        }
        return 1;
    }

    // Returns this as a String
    // ex: null -> 3 -> 9 -> 4 -> null ==> 394
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DigitNode curr = start;
        while (curr.next != null) {
            sb.append(curr.val);
            curr = curr.next;
        }
        sb.append(curr.val);
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomBigInteger four = new CustomBigInteger("9");
        System.out.println(four.sqrt());
        CustomBigInteger nineteen = new CustomBigInteger("19");
        System.out.println(nineteen.sqrt());
        CustomBigInteger oneHundredTwentyTwo = new CustomBigInteger("122");
        System.out.println(oneHundredTwentyTwo.sqrt());
    }
}
