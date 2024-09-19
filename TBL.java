package postagging;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBL {

    // Gắn nhãn ban đầu cho tất cả các từ là danh từ (NN)
    public static List<TaggedWord> initialTagging(String[] sentence) {
        List<TaggedWord> taggedSentence = new ArrayList<>();
        for (String word : sentence) {
            taggedSentence.add(new TaggedWord(word, "NN")); // Gắn nhãn tất cả là danh từ
        }
        return taggedSentence;
    }

    // Tìm quy tắc tốt nhất từ bộ dữ liệu (dựa trên nhãn đúng)
    public static Rule findBestRule(List<List<TaggedWord>> sentences, List<List<TaggedWord>> goldSentences) {
        Map<Rule, Integer> ruleScore = new HashMap<>();
        int loop = Math.min(sentences.size(), goldSentences.size());
        // Duyệt qua tất cả các câu trong dữ liệu huấn luyện
        for (int sentenceIndex = 0; sentenceIndex < loop; sentenceIndex++) {
            List<TaggedWord> sentence = sentences.get(sentenceIndex);
            List<TaggedWord> goldSentence = goldSentences.get(sentenceIndex);
            // Duyệt qua từng từ trong câu
            for (int i = 0; i < sentence.size(); i++) {
                String predictedTag = sentence.get(i).posTag;
                String correctTag = goldSentence.get(i).posTag;

                // Nếu nhãn không đúng, tạo ra quy tắc chuyển đổi
                if (!predictedTag.equals(correctTag)) {
                    String prevTag = (i > 0) ? sentence.get(i - 1).posTag : "";
                    Rule rule = new Rule(predictedTag, correctTag, prevTag);

                    // Đếm số lần quy tắc này giúp sửa lỗi
                    ruleScore.put(rule, ruleScore.getOrDefault(rule, 0) + 1);
                }
            }
        }

        // Tìm quy tắc có điểm cao nhất (giúp sửa nhiều lỗi nhất)
        Rule bestRule = null;
        int bestScore = 0;
        for (Map.Entry<Rule, Integer> entry : ruleScore.entrySet()) {
            if (entry.getValue() > bestScore) {
                bestScore = entry.getValue();
                bestRule = entry.getKey();
            }
        }
        return bestRule;
    }

    // Áp dụng các quy tắc để giảm lỗi
    public static void applyRules(List<List<TaggedWord>> sentences, List<List<TaggedWord>> goldSentences) {
        boolean changes = true;

        // Lặp qua các câu cho đến khi không còn quy tắc nào có thể giảm lỗi
        while (changes) {
            changes = false;
            Rule bestRule = findBestRule(sentences, goldSentences);
        
            if (bestRule != null) {
                System.out.println("Áp dụng quy tắc: " + bestRule);
                for (List<TaggedWord> sentence : sentences) {
                    for (int i = 0; i < sentence.size(); i++) {
                        if (bestRule.matches(sentence, i)) {
                            bestRule.apply(sentence, i);
                            changes = true;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Câu cần gắn nhãn
        String[] sentence1 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};
        String[] sentence2 = {"A", "fast", "blue", "cat", "runs", "under", "a", "sleepy", "tree"};
        // Câu chưa gán nhãn.
        String[] sentence3 = {"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"};
        String[] sentence4 = {"A", "fast", "blue", "cat", "runs", "under", "a", "sleepy", "tree"};
        String[] sentence5 = {"A", "fast", "red", "cat", "kick", "under", "a",  "tree"};
 
        // Gắn nhãn ban đầu
        List<List<TaggedWord>> taggedSentences = new ArrayList<>();
        taggedSentences.add(initialTagging(sentence1));
        taggedSentences.add(initialTagging(sentence2));
        taggedSentences.add(initialTagging(sentence3));
        taggedSentences.add(initialTagging(sentence4));
        taggedSentences.add(initialTagging(sentence5));

        // Tập dữ liệu huấn luyện với nhãn đúng (gold standard)
        List<List<TaggedWord>> goldSentences = new ArrayList<>();
        goldSentences.add(List.of(
                new TaggedWord("The", "DT"), new TaggedWord("quick", "JJ"),
                new TaggedWord("brown", "JJ"), new TaggedWord("fox", "NN"),
                new TaggedWord("jumps", "VB"), new TaggedWord("over", "IN"),
                new TaggedWord("the", "DT"), new TaggedWord("lazy", "JJ"),
                new TaggedWord("dog", "NN")
        ));
        goldSentences.add(List.of(
                new TaggedWord("A", "DT"), new TaggedWord("fast", "JJ"),
                new TaggedWord("blue", "JJ"), new TaggedWord("cat", "NN"),
                new TaggedWord("runs", "VB"), new TaggedWord("under", "IN"),
                new TaggedWord("a", "DT"), new TaggedWord("sleepy", "JJ"),
                new TaggedWord("tree", "NN")
        ));

        // In ra nhãn ban đầu
        System.out.println("Nhãn ban đầu:");
        for (List<TaggedWord> sentence : taggedSentences) {
            System.out.println(sentence);
        }

        // Áp dụng các quy tắc để giảm lỗi
        applyRules(taggedSentences, goldSentences);

        // In ra kết quả cuối cùng
        System.out.println("Kết quả sau khi áp dụng TBL:");
        for (List<TaggedWord> sentence : taggedSentences) {
            System.out.println(sentence);
        }
    }
    
}
