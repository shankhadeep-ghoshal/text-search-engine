package algo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatchTextPercentage {
    private final Map<String, Set<Integer>> wordMap;
    private final String[] pattern;

    public MatchTextPercentage(final Map<String, Set<Integer>> wordMap, final String[] pattern) {
        assert wordMap != null;
        assert pattern != null;

        this.wordMap = wordMap;
        this.pattern = pattern;
    }

    /**
     * Words are first checked whether they are matching ir not. If they are matching, then check
     * if the current word is the next word of the previously matched world. If yes then add 1.0
     * to the weighted sum else 0.5. <br>
     * The percentage match is calculated as (weighted sum / total number of words to match) * 100
     * @return the average weighted sum of the matches
     */
    public double calculatePercentageWordMatchInText() {
        boolean matching = false;
        double weightedSum = 0.0;
        Tuple<String, Set<Integer>> cache = null;
        Map<Tuple<String, String>, Set<Integer>> matchedPatternsMap = new HashMap<>();

        for (int i = 0; i < pattern.length; i++) {
            if (wordMap.containsKey(pattern[i])) {
                Set<Integer> wordMapSet = wordMap.get(pattern[i]);
                if (cache == null) {
                    weightedSum += 1.0;
                } else {
                    Tuple<String, String> stringTuple = new Tuple<>(cache.getKey(), pattern[i]);

                    for (int cachedSetPos : cache.getValue()) {
                        for (int wordMapSetPos : wordMapSet) {
                            if ((cachedSetPos + 1) == wordMapSetPos) {
                                if (matchedPatternsMap.containsKey(stringTuple)) {
                                    Set<Integer> tempSet = matchedPatternsMap.get(stringTuple);
                                    if (!tempSet.contains(wordMapSetPos)) {
                                        tempSet.add(wordMapSetPos);
                                        matching = true;
                                    }
                                } else {
                                    HashSet<Integer> obsPos = new HashSet<>();
                                    obsPos.add(wordMapSetPos);
                                    matchedPatternsMap.put(new Tuple<>(cache.getKey(), pattern[i]),
                                            obsPos);
                                    matching = true;
                                }
                                break;
                            }
                        }
                    }
                    weightedSum += matching ? 1.0 : 0.5;
                    matching = false;
                }
                cache = new Tuple<>(pattern[i], wordMapSet);
            }
        }

        return (weightedSum/pattern.length) * 100;
    }

    private static class Tuple <K, V> {
        private final K key;
        private final V value;

        private Tuple(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (! (o instanceof Tuple)) return false;
            Tuple t = (Tuple) o;
            return getKey().equals(t.getKey()) && getValue().equals(t.getValue());
        }
    }
}