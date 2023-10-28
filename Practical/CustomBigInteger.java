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
    private int base = 10;

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

    public CustomBigInteger(int[] vals) {
        this(vals, 10);
    }

    // vals.length >= 1
    // 0 <= val < base for each val in vals
    // base >= 2
    public CustomBigInteger(int[] vals, int base) {
        if (base < 2) {
            throw new IllegalArgumentException();
        }
        start = new DigitNode(null, vals[0], null);
        DigitNode curr = start;
        for (int i = 1; i < vals.length; i++) {
            if (vals[i] < 0 || vals[i] >= base) {
                throw new IllegalArgumentException();
            }
            curr.next = new DigitNode(curr, vals[i], null);
            curr = curr.next;
        }
        end = curr;
        this.base = base;
    }

    private CustomBigInteger(DigitNode start, DigitNode end, int base) {
        this.start = start;
        this.end = end;
        this.base = base;
    }

    // Returns add + other
    public CustomBigInteger add(CustomBigInteger other) {
        if (base != other.base) {
            throw new IllegalArgumentException();
        }
        DigitNode currThis = end;
        DigitNode currOther = other.end;
        int currDigit = currThis.val + currOther.val;
        DigitNode first = new DigitNode(null, currDigit % base, null);
        DigitNode sum = first;
        currThis = currThis.prev;
        currOther = currOther.prev;
        int carry = currDigit / base;
        while (currThis != null && currOther != null) {
            currDigit = currThis.val + currOther.val + carry;
            sum.prev = new DigitNode(null, currDigit % base, sum);
            sum = sum.prev;
            currThis = currThis.prev;
            currOther = currOther.prev;
            carry = currDigit / base;
        }
        if (currThis == null) {
            while (currOther != null) {
                currDigit = currOther.val + carry;
                sum.prev = new DigitNode(null, currDigit % base, sum);
                sum = sum.prev;
                currOther = currOther.prev;
                carry = currDigit / base;
            }
        } else {
            while (currThis != null) {
                currDigit = currThis.val + carry;
                sum.prev = new DigitNode(null, currDigit % base, sum);
                sum = sum.prev;
                currThis = currThis.prev;
                carry = currDigit / base;
            }
        }
        if (carry != 0) {
            sum.prev = new DigitNode(null, carry, sum);
            sum = sum.prev;
        }
        return new CustomBigInteger(sum, first, base);
    }

    // Better if other is shorter
    public CustomBigInteger multiply(CustomBigInteger other) {
        if (base != other.base) {
            throw new IllegalArgumentException();
        }
        CustomBigInteger[] premultiplied = new CustomBigInteger[base];
        for (int i = 0; i < base; i++) {
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
        DigitNode first = new DigitNode(null, currDigit % base, null);
        DigitNode sum = first;
        currThis = currThis.prev;
        int carry = currDigit / base;
        while (currThis != null) {
            currDigit = currThis.val * digit + carry;
            sum.prev = new DigitNode(null, currDigit % base, sum);
            sum = sum.prev;
            currThis = currThis.prev;
            carry = currDigit / base;
        }
        if (carry != 0) {
            sum.prev = new DigitNode(null, carry, sum);
            sum = sum.prev;
        }
        return new CustomBigInteger(sum, first, base);
    }

    // places > 0
    // Returns this but shifted to the left by given number of places
    private CustomBigInteger shift(int places) {
        DigitNode first = new DigitNode(null, 0, null);
        DigitNode node = first;
        for (int i = 1; i < places; i++) {
            node.prev = new DigitNode(null, 0, node);
            node = node.prev;
        }
        node.prev = end;
        return new CustomBigInteger(start, first, base);
    }

    // Returns integer sqrt of this, as in the sqrt rounded down
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
        CustomBigInteger currGuessCBI = new CustomBigInteger(currGuess, first, base);
        getSqrt: while (currNode != null) {
            int lo = 0;
            int hi = base;
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

    // val < base for each val in DigitNodes
    // base >= 2
        // if base = 1, each digit d has 0 <= d < base ==> d = 0 for all d which causes problems
    // Note that the value of each digit is unchanged
    public void setBase(int base) {
        if (base < 2) {
            throw new IllegalArgumentException();
        }
        DigitNode curr = start;
        while (curr.next != null) {
            if (curr.val >= base) {
                throw new IllegalArgumentException();
            }
            curr = curr.next;
        }
        if (curr.val >= base) {
            throw new IllegalArgumentException();
        }
        this.base = base;
    }

    // Returns -1 if this < other, 0 if this = other, 1 if this > other
    // Assumes no leading zeroes
    public int compareTo(CustomBigInteger other) {
        if (base != other.base) {
            throw new IllegalArgumentException();
        }
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
    // if base > 10, separates digits with a space
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DigitNode curr = start;
        while (curr.next != null) {
            sb.append(curr.val);
            if (base > 10) {
                sb.append(" ");
            }
            curr = curr.next;
        }
        sb.append(curr.val);
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomBigInteger four = new CustomBigInteger("4");
        System.out.println(four.sqrt());
        CustomBigInteger fourteen = new CustomBigInteger("14");
        System.out.println(fourteen.sqrt());
        CustomBigInteger oneHundredTwentyTwo = new CustomBigInteger("122");
        System.out.println(oneHundredTwentyTwo.sqrt());
        CustomBigInteger sixThousandPlus = new CustomBigInteger("6136");
        System.out.println(sixThousandPlus.sqrt());
        System.out.println();

        four.setBase(7);
        fourteen.setBase(7);
        oneHundredTwentyTwo.setBase(7);
        sixThousandPlus.setBase(7);
        System.out.println(four.add(fourteen));
        System.out.println(four.add(oneHundredTwentyTwo));
        System.out.println(oneHundredTwentyTwo.multiply(fourteen));
        System.out.println(sixThousandPlus.multiply(fourteen));
        System.out.println(four.sqrt());
        System.out.println(fourteen.sqrt());
        System.out.println(oneHundredTwentyTwo.sqrt());
        System.out.println(sixThousandPlus.sqrt());
        System.out.println();

        four.setBase(16);
        fourteen.setBase(16);
        oneHundredTwentyTwo.setBase(16);
        sixThousandPlus.setBase(16);
        System.out.println(four.add(fourteen));
        System.out.println(four.add(oneHundredTwentyTwo));
        System.out.println(oneHundredTwentyTwo.multiply(fourteen));
        System.out.println(sixThousandPlus.multiply(fourteen));
        System.out.println(four.sqrt());
        System.out.println(fourteen.sqrt());
        System.out.println(oneHundredTwentyTwo.sqrt());
        System.out.println(sixThousandPlus.sqrt());
        System.out.println();

        CustomBigInteger cbi1 = new CustomBigInteger("101011101");
        CustomBigInteger cbi2 = new CustomBigInteger("111011111011");
        cbi1.setBase(2);
        cbi2.setBase(2);
        System.out.println(cbi1.add(cbi2));
        System.out.println(cbi1.multiply(cbi2));
        System.out.println(cbi1.sqrt());
        System.out.println(cbi2.sqrt());
    }
}
