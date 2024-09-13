package postagging;

import java.util.List;
// POS tagging rule
public class TransformationRule {
    private String conditionPos;
    private String correctPos;
    private String contextCondition;

    public TransformationRule(String conditionPos, String correctPos, String contextCondition) {
        this.conditionPos = conditionPos;
        this.correctPos = correctPos;
        this.contextCondition = contextCondition;
    }
/* 
    public boolean matches(List<TaggedWord> sentence, int index) {
        // Basic rule: If the current word is tagged as 'conditionPos' and the context condition is satisfied, apply the rule
        if (sentence.get(index).posTag.equals(conditionPos)) {
            // Example condition: the previous word must have a specific tag, contextCondition
            if (index > 0 && sentence.get(index - 1).posTag.equals(contextCondition)) {
                return true;
            }
        }
        return false;
    }

    public void apply(List<TaggedWord> sentence, int index) {
        sentence.get(index).posTag = this.correctPos;
    }
*/
    public void applyRule(List<TaggedWord> sentence, int index) {
        // Basic rule: If the current word is tagged as 'conditionPos' and the context condition is satisfied, apply the rule
        if (sentence.get(index).posTag.equals(conditionPos)) {
            // Example condition: the previous word must have a specific tag, contextCondition
            if (index > 0 && sentence.get(index - 1).posTag.equals(contextCondition)) {
                sentence.get(index).posTag = this.correctPos;
            }
        }
    }
    
}
