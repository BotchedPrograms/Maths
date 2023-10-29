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

    private CustomBigInteger constant(int digit) {
        return new CustomBigInteger(new int[] {digit}, base);
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

    // CustomBigInteger doesn't make sense of negative numbers; only works properly if this >= other
    // see TwosComplement.java for explanation
    public CustomBigInteger subtract(CustomBigInteger other) {
        if (base != other.base) {
            throw new IllegalArgumentException();
        }
        CustomBigInteger negationOther = other.digitwiseNegation();

        DigitNode currThis = end;
        DigitNode currOther = other.end;
        while (currThis.prev != null && currOther.prev != null) {
            currThis = currThis.prev;
            currOther = currOther.prev;
        }
        if (currThis.prev != null) {
            DigitNode negationStart = negationOther.start;
            do {
                negationStart.prev = new DigitNode(null, base - 1, negationStart);
                negationStart = negationStart.prev;
                currThis = currThis.prev;
            } while (currThis.prev != null);    // do-while loop saves one whole condition check
            negationOther = new CustomBigInteger(negationStart, negationOther.end, base);
        } else if (currOther.prev != null) {    // if this < other
            throw new IllegalArgumentException();
        }
        // this and negationOther have same length by now
        CustomBigInteger sum = add(negationOther);
        sum = sum.add(constant(1));
        // gets rid of leading digit
        DigitNode sumStart = sum.start.next;
        // gets rid of leading zeros
        while (sumStart != null && sumStart.val == 0) {
            sumStart = sumStart.next;
        }
        if (sumStart == null) {
            return constant(0);
        }
        sumStart.prev = null;
        return new CustomBigInteger(sumStart, sum.end, base);
    }

    // Returns the digitwise negation of this, that is each digitnode's val = base - val - 1
    private CustomBigInteger digitwiseNegation() {
        DigitNode negationStart = new DigitNode(null, base - start.val - 1, null);
        DigitNode negationEnd = negationStart;
        DigitNode currNode = start;
        while (currNode.next != null) {
            negationEnd.next = new DigitNode(negationEnd, base - currNode.next.val - 1, null);
            negationEnd = negationEnd.next;
            currNode = currNode.next;
        }
        return new CustomBigInteger(negationStart, negationEnd, base);
    }

    // Better if other is shorter
    public CustomBigInteger multiply(CustomBigInteger other) {
        if (base != other.base) {
            throw new IllegalArgumentException();
        }
        // stores multiples of this early to not waste time recalculating them later
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
                sum = sum.add(premultiplied[curr.val].shiftLeft(places));
            }
            curr = curr.prev;
            places++;
        }
        return sum;
    }

    // Returns this * digit
    private CustomBigInteger multiply(int digit) {
        if (digit == 0) {
            return constant(0);
        }
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

    // Returns this / other
    public CustomBigInteger divide(CustomBigInteger other) {
        return divideAndMod(other)[0];
    }

    // Returns this % other
    public CustomBigInteger mod(CustomBigInteger other) {
        return divideAndMod(other)[1];
    }

    // Returns array arr where arr[0] = this / other and arr[1] = this % other
    public CustomBigInteger[] divideAndMod(CustomBigInteger other) {
        if (base != other.base) {
            throw new IllegalArgumentException();
        }
        if (other.start.val == 0 && other.start.next == null) {
            throw new ArithmeticException("/ by zero");
        }
        // stores multiples of other early to not waste time recalculating them later
        CustomBigInteger[] premultiplied = new CustomBigInteger[base];
        for (int i = 0; i < base; i++) {
            premultiplied[i] = other.multiply(i);
        }
        DigitNode dividStart = new DigitNode(null, start.val, null);
        DigitNode dividEnd = dividStart;
        DigitNode currThis = start;
        DigitNode currOther = other.start;
        // skips first few digits until divid / other > 0
        while (currThis.next != null && currOther.next != null) {
            dividEnd.next = new DigitNode(dividEnd, currThis.next.val, null);
            dividEnd = dividEnd.next;
            currThis = currThis.next;
            currOther = currOther.next;
        }
        CustomBigInteger divid = new CustomBigInteger(dividStart, dividEnd, base);
        if (divid.compareTo(other) == -1) {
            if (currThis.next == null) {
                return new CustomBigInteger[] {constant(0), divid};
            }
            dividEnd.next = new DigitNode(dividEnd, currThis.next.val, null);
            dividEnd = dividEnd.next;
            divid = new CustomBigInteger(dividStart, dividEnd, base);
            currThis = currThis.next;
        }
        // divid / other > 0 now
        DigitNode quoStart = new DigitNode(null, -1, null);
        DigitNode quoEnd = quoStart;
        // essentially does long division
        while (currThis.next != null) {
            // determines next multiple of other to subtract divid by
            int lo = 0;
            int hi = base;
            while (hi != lo + 1) {
                int mid = (lo + hi) / 2;
                int compare = premultiplied[mid].compareTo(divid);
                if (compare == -1) {
                    lo = mid;
                } else if (compare == 1) {
                    hi = mid;
                } else {
                    lo = mid;
                    hi = mid + 1;
                }
            }
            quoEnd.val = lo;
            quoEnd.next = new DigitNode(quoEnd, -1, null);
            quoEnd = quoEnd.next;
            divid = divid.subtract(premultiplied[lo]);
            dividEnd = divid.end;
            dividEnd.next = new DigitNode(dividEnd, currThis.next.val, null);
            // in case divid.subtract(premultiplied[lo]) = 0
            if (divid.start.val == 0) {
                dividEnd.next.prev = null;
                divid = new CustomBigInteger(dividEnd.next, dividEnd.next, base);
            } else {
                divid = new CustomBigInteger(divid.start, dividEnd.next, base);
            }
            currThis = currThis.next;
        }
        int lo = 0;
        int hi = base;
        while (hi != lo + 1) {
            int mid = (lo + hi) / 2;
            int compare = premultiplied[mid].compareTo(divid);
            if (compare == -1) {
                lo = mid;
            } else if (compare == 1) {
                hi = mid;
            } else {
                lo = mid;
                hi = mid + 1;
            }
        }
        quoEnd.val = lo;
        divid = divid.subtract(premultiplied[lo]);
        CustomBigInteger quotient = new CustomBigInteger(quoStart, quoEnd, base);
        return new CustomBigInteger[] {quotient, divid};
    }

    // Returns this but shifted to the left by given number of places
    private CustomBigInteger shiftLeft(int places) {
        if (places < 0) {
            return shiftRight(-places);
        }
        // doesn't shift left if this = 0
        if (start.next == null && start.val == 0) {
            return constant(0);
        }
        DigitNode newStart = new DigitNode(null, start.val, null);
        DigitNode newEnd = newStart;
        DigitNode currNode = start;
        while (currNode.next != null) {
            newEnd.next = new DigitNode(newEnd, currNode.next.val, null);
            newEnd = newEnd.next;
            currNode = currNode.next;
        }
        for (int i = 0; i < places; i++) {
            newEnd.next = new DigitNode(newEnd, 0, null);
            newEnd = newEnd.next;
        }
        return new CustomBigInteger(newStart, newEnd, base);
    }

    // Returns this but shifted to the right by given number of places
    private CustomBigInteger shiftRight(int places) {
        if (places < 0) {
            return shiftLeft(-places);
        }
        DigitNode currNode = end;
        for (int i = 0; i < places; i++) {
            if (currNode.prev == null) {
                return constant(0);
            }
            currNode = currNode.prev;
        }
        DigitNode newEnd = new DigitNode(null, currNode.val, null);
        DigitNode newStart = newEnd;
        while (currNode.prev != null) {
            newStart.prev = new DigitNode(null, currNode.prev.val, newStart);
            newStart = newStart.prev;
            currNode = currNode.prev;
        }
        return new CustomBigInteger(newStart, newEnd, base);
    }

    // Returns this^exponent
    public CustomBigInteger pow(CustomBigInteger exponent) {
        if (exponent.start.next == null) {
            if (exponent.start.val == 1) {
                return makeCopy();
            }
            if (exponent.start.val == 0) {
                return constant(1);
            }
        }
        CustomBigInteger smallPow = pow(exponent.divide(constant(2)));
        if (exponent.isEven()) {
            return smallPow.multiply(smallPow);
        }
        return smallPow.multiply(smallPow).multiply(this);
    }

    // Returns this^exponent % modulo
    public CustomBigInteger powMod(CustomBigInteger exponent, CustomBigInteger modulo) {
        if (modulo.start.next == null) {
            if (modulo.start.val == 0) {
                throw new ArithmeticException("\"/ by zero\"");
            }
            if (modulo.start.val == 1) {
                return constant(0);
            }
        }
        return powModPostCheck(exponent, modulo);
    }

    // Does calculations for powMod after checking modulo > 1
    private CustomBigInteger powModPostCheck(CustomBigInteger exponent, CustomBigInteger modulo) {
        if (exponent.start.next == null) {
            if (exponent.start.val == 1) {
                return makeCopy().mod(modulo);
            }
            if (exponent.start.val == 0) {
                return constant(1);
            }
        }
        CustomBigInteger smallPow = powModPostCheck(exponent.divide(constant(2)), modulo);
        if (exponent.isEven()) {
            return smallPow.multiply(smallPow).mod(modulo);
        }
        return smallPow.multiply(smallPow).mod(modulo).multiply(this).mod(modulo);
    }

    // Returns true iff this is even
    private boolean isEven() {
        if (base % 2 == 0) {
            return end.val % 2 == 0;
        }
        DigitNode curr = start;
        int count = 0;
        while (curr != null) {
            if (curr.val % 2 == 1) {
                count++;
            }
            curr = curr.next;
        }
        return count % 2 == 0;
    }

    // Makes copy of this
    private CustomBigInteger makeCopy() {
        DigitNode copyStart = new DigitNode(null, start.val, null);
        DigitNode copyEnd = copyStart;
        DigitNode currStart = start;
        while (currStart.next != null) {
            copyEnd.next = new DigitNode(copyEnd, currStart.next.val, null);
            copyEnd = copyEnd.next;
            currStart = currStart.next;
        }
        return new CustomBigInteger(copyStart, copyEnd, base);
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
            // goes down, checking if currGuess is too high or low
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
    // assumes no leading zeroes
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
        CustomBigInteger thirtyNine = new CustomBigInteger("39");
        CustomBigInteger oneHundredTwentyTwo = new CustomBigInteger("122");
        CustomBigInteger seventeenTwentyNine = new CustomBigInteger("1729");
        CustomBigInteger bigNumber = new CustomBigInteger("93884313611");
        System.out.println("Shifts:");
        System.out.println(four.shiftLeft(2));
        System.out.println(four.shiftRight(2));
        System.out.println(oneHundredTwentyTwo.shiftLeft(3));
        System.out.println(oneHundredTwentyTwo.shiftRight(3));
        System.out.println(seventeenTwentyNine.shiftLeft(1));
        System.out.println(seventeenTwentyNine.shiftRight(1));
        System.out.println();

        System.out.println("Subtraction:");
        System.out.println(seventeenTwentyNine.subtract(four));
        System.out.println(seventeenTwentyNine.subtract(thirtyNine));
        System.out.println(seventeenTwentyNine.subtract(oneHundredTwentyTwo));
        System.out.println(oneHundredTwentyTwo.subtract(four));
        System.out.println(oneHundredTwentyTwo.subtract(thirtyNine));
        System.out.println(thirtyNine.subtract(four));
        System.out.println(four.subtract(four));
        System.out.println();

        System.out.println("Division:");
        System.out.println(bigNumber.divide(four));                             // 93884313611 / 4
        System.out.println(bigNumber.divide(thirtyNine));                       // 93884313611 / 39
        System.out.println(bigNumber.divide(oneHundredTwentyTwo));              // 93884313611 / 122
        System.out.println(bigNumber.divide(seventeenTwentyNine));              // 93884313611 / 1729
        System.out.println(bigNumber.divide(bigNumber));                        // 93884313611 / 93884313611
        System.out.println(seventeenTwentyNine.divide(four));                   // 1729 / 4
        System.out.println(seventeenTwentyNine.divide(thirtyNine));             // 1729 / 39
        System.out.println(seventeenTwentyNine.divide(oneHundredTwentyTwo));    // 1729 / 122
        System.out.println(seventeenTwentyNine.divide(seventeenTwentyNine));    // 1729 / 1729
        System.out.println(oneHundredTwentyTwo.divide(four));                   // 122 / 4
        System.out.println(oneHundredTwentyTwo.divide(thirtyNine));             // 122 / 39
        System.out.println(thirtyNine.divide(four));                            // 39 / 4
        System.out.println(oneHundredTwentyTwo.divide(seventeenTwentyNine));    // 122 / 1729
        System.out.println();

        System.out.println("Division and Mod Random Tests:");
        for (int i = 0; i < 100000; i++) {
            int randInt = (int) (Math.random() * 10000000) + 1;
            CustomBigInteger[] result = bigNumber.divideAndMod(new CustomBigInteger(randInt));
            long expected = 93884313611L / randInt;
            long actual = Long.parseLong(result[0].toString());
            if (expected != actual) {
                System.out.println("error: " + expected + " " + actual);
            }
            expected = 93884313611L % randInt;
            actual = Long.parseLong(result[1].toString());
            if (expected != actual) {
                System.out.println("error: " + expected + " " + actual);
            }
        }
        System.out.println();

        System.out.println("Pow:");
        CustomBigInteger eleven = new CustomBigInteger("11");
        System.out.println(four.pow(four));
        System.out.println(four.pow(eleven));
        System.out.println(four.pow(thirtyNine));
        System.out.println(four.pow(oneHundredTwentyTwo));
        System.out.println(thirtyNine.pow(four));
        System.out.println(thirtyNine.pow(eleven));
        System.out.println(thirtyNine.pow(thirtyNine));
        System.out.println(oneHundredTwentyTwo.pow(four));
        System.out.println(oneHundredTwentyTwo.pow(eleven));
        System.out.println(oneHundredTwentyTwo.pow(thirtyNine));
        System.out.println(seventeenTwentyNine.pow(four));
        System.out.println(seventeenTwentyNine.pow(eleven));
        System.out.println(bigNumber.pow(four));
        System.out.println(bigNumber.pow(eleven));
        System.out.println();

        System.out.println("PowMod:");
        System.out.println(four.powMod(four, bigNumber));
        System.out.println(four.powMod(eleven, bigNumber));
        System.out.println(four.powMod(thirtyNine, bigNumber));
        System.out.println(four.powMod(oneHundredTwentyTwo, bigNumber));
        System.out.println(thirtyNine.powMod(four, bigNumber));
        System.out.println(thirtyNine.powMod(eleven, bigNumber));
        System.out.println(thirtyNine.powMod(thirtyNine, bigNumber));
        System.out.println(oneHundredTwentyTwo.powMod(four, bigNumber));
        System.out.println(oneHundredTwentyTwo.powMod(eleven, bigNumber));
        System.out.println(oneHundredTwentyTwo.powMod(thirtyNine, bigNumber));
        System.out.println(seventeenTwentyNine.powMod(four, bigNumber));
        System.out.println(seventeenTwentyNine.powMod(eleven, bigNumber));
        System.out.println(bigNumber.powMod(four, bigNumber));
        System.out.println(bigNumber.powMod(eleven, bigNumber));
    }
}
