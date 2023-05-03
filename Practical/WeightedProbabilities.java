import java.util.*;

// Most efficient when only put() on new values and getRandom() are called
public class WeightedProbabilities<V> {
    private final TreeMap<Integer, V> tree;
    private final HashMap<V, Integer> hash;
    private final Random rand = new Random();
    private int max;

    /**
     * Creates new WeightedProbabilities, which facilitates getting values with weighted probabilities.
     * Note: Works best when using only put() on new values and getRandom().
     * remove(), updateWeight(), and getWeights() presumably work but aren't very efficient.
     */
    public WeightedProbabilities() {
        tree = new TreeMap<>();
        hash = new HashMap<>();
        max = 0;
    }

    /**
     * Puts in the new value with the specified weight.
     * Note: The program won't work properly if you put in something already put in, so don't do that.
     *
     * @throws IllegalArgumentException if weight is not positive.
     */
    public void put(V value, int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException();
        }
        max += weight;
        tree.put(max - 1, value);
        hash.put(value, max - 1);
    }

    /**
     * Removes a value. Assumes there's only one of it.
     *
     * @return the removed value if it was there, null otherwise
     */
    public V remove(V value) {
        Integer weight = hash.remove(value);
        if (weight == null) {
            return null;
        }
        V removed = tree.remove(weight);
        Map.Entry<Integer, V> lighter = tree.lowerEntry(weight);
        // where "real" weights are the ones put in while "fake" ones are the ones
        // the program works with
        int realWeight = weight;
        if (lighter == null) {
            realWeight += 1;
        } else {
            realWeight -= lighter.getKey();
        }
        max -= realWeight;
        updateHeavier(weight, weight - realWeight);
        return removed;
    }

    /**
     * Updates the weight of a value to the one specified.
     *
     * @throws IllegalArgumentException if weight is not positive
     */
    public void updateWeight(V value, int newWeight) {
        if (newWeight <= 0) {
            throw new IllegalArgumentException();
        }
        int oldWeight = hash.get(value);
        Map.Entry<Integer, V> lighter = tree.lowerEntry(oldWeight);
        int realWeight = oldWeight;
        if (lighter == null) {
            realWeight += 1;
        } else {
            realWeight -= lighter.getKey();
        }
        int fakeNewWeight = oldWeight - realWeight + newWeight;
        max -= realWeight;
        max += newWeight;
        updateHeavier(oldWeight, fakeNewWeight);
    }

    /**
     * Updates the weights of all values that had weight greater than equal to oldWeight.
     * Note that oldWeight and newWeight are "fake" not "real" as defined earlier.
     */
    private void updateHeavier(int oldWeight, int newWeight) {
        int diff = newWeight - oldWeight;
        Set<Integer> heavierWeights = new HashSet<>(tree.tailMap(oldWeight, true).keySet());
        for (Integer heavierWeight : heavierWeights) {
            V heavy = tree.remove(heavierWeight);
            tree.put(heavierWeight + diff, heavy);
            hash.replace(heavy, heavierWeight + diff);
        }
    }

    /**
     * Returns a random value with the specified weighted probabilities.
     */
    public V getRandom() {
        return tree.ceilingEntry(rand.nextInt(max)).getValue();
    }

    /**
     * Returns the set of values.
     */
    public Set<V> getValues() {
        return hash.keySet();
    }

    /**
     * Returns the set of weights.
     */
    public List<Integer> getWeights() {
        Set<Integer> weights = tree.keySet();
        List<Integer> returnedWeights = new LinkedList<>();
        if (weights.size() == 0) {
            return returnedWeights;
        }
        Iterator<Integer> iter = weights.iterator();
        int next = iter.next();
        returnedWeights.add(next + 1);
        while (iter.hasNext()) {
            int curr = next;
            next = iter.next();
            returnedWeights.add(next - curr);
        }
        return returnedWeights;
    }

    /**
     * Returns the total weight.
     */
    public int getTotalWeight() {
        return max;
    }

    public static void main(String[] args) {
        WeightedProbabilities<String> barterRates = new WeightedProbabilities<>();
        barterRates.put("Enchanted Book", 5);
        barterRates.put("Iron Boots", 8);
        barterRates.put("Fire Res Splash Pot", 8);
        barterRates.put("Fire Res Pot", 8);
        barterRates.put("Water Bottle", 10);
        barterRates.put("Iron Nugget", 10);
        barterRates.put("Ender Pearl", 10);
        barterRates.put("String", 20);
        barterRates.put("Nether Quartz", 20);
        barterRates.put("Obsidian", 40);
        barterRates.put("Crying Obsidian", 40);
        barterRates.put("Fire Charge", 40);
        barterRates.put("Leather", 40);
        barterRates.put("Soul Sand", 40);
        barterRates.put("Nether Brick", 40);
        barterRates.put("Spectral Arrow", 40);
        barterRates.put("Gravel", 40);
        barterRates.put("Blackstone", 40);
        String[] drops = {"Enchanted Book", "Iron Boots", "Fire Res Splash Pot", "Fire Res Pot", "Water Bottle",
            "Iron Nugget", "Ender Pearl", "String", "Nether Quartz", "Obsidian", "Crying Obsidian", "Fire Charge",
            "Leather", "Soul Sand", "Nether Brick", "Spectral Arrow", "Gravel", "Blackstone"};
        int[] nums = new int[drops.length];
        // 10^7
        int times = 10000000;
        for (int i = 0; i < times; i++) {
            String rand = barterRates.getRandom();
            int index = 0;
            for (; index < drops.length; index++) {
                if (rand.equals(drops[index])) {
                    break;
                }
            }
            nums[index]++;
        }
        for (int num : nums) {
            System.out.println((double) num / times);
        }
    }
}
