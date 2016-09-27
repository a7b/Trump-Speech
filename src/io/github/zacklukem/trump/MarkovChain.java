package io.github.zacklukem.trump;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * // TODO write javadoc
 *
 * @author zmayhew
 */
public class MarkovChain {
    HashMap <String, HashMap<String, Double>> state;

    String start = "__start__";

    String end = "__end__";

    /**
     *
     */
    public MarkovChain() {
        state = new HashMap<>();
        createState(start);
        createState(end);
    }

    public String generate() {
        String current = start;
        ArrayList <String> walk = new ArrayList<>();
        while (!current.equals(end)) {
            current = getRandom(current);
            if (!current.equals(end))
                walk.add(current);
        }
        String sentance = "";
        for (String word : walk) {
            sentance = sentance + word + " ";
        }
        sentance = sentance.substring(0, sentance.length()-1) + ".";
        return sentance;
    }

    private void createState(String newState) {
        if (!state.containsKey(newState)) {
            HashMap<String, Double> frequency = new HashMap<>();
            state.put(newState, frequency);
        }
    }
    private void transition(String startState, String endState) {
        createState(startState);
        createState(endState);

        HashMap<String, Double> frequency = state.get(startState);
        if (frequency.containsKey(endState)) {
            // inc freq tabl
            double value = frequency.get(endState);
            value++;
            frequency.put(endState, value);
        } else {
            frequency.put(endState, 1.0);
        }
    }

    public String getRandom(String currentState) {
        HashMap<String, Double> frequency = state.get(currentState);
        if (frequency.size() == 0) {
            return end;
        }
        double count = 0;
        for (String key : frequency.keySet()) {
            count += frequency.get(key);
        }
        double random = Math.random() * count;
        double current = 0;
        for (String key : frequency.keySet()) {
            current += frequency.get(key);
            if (current > random) return key;
        }
        return end;
    }

    /**
     * - sentence has had ending punctuation removed
     *  "the cat in the hat ate the bat"
     * @param sentence - one sentence of input
     */
    public void train(String sentence) {
        String[] word = sentence.split("\\s");
        transition(start, word[0]);
        for (int i = 1; i < word.length-1; i++) {
            transition(word[i], word[i+1]);
        }
        transition(word[word.length - 1], end);
    }
}
