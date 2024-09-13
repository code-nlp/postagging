package postagging;

import java.util.List;
// POS tagging rule
public class TransformationRule {
    private String posTagged;
    private String correctTag;
    private String contextTagged;

    public TransformationRule(String posTagged, String correctTag, String contextTagged) {
        this.posTagged = posTagged;
        this.correctTag = correctTag;
        this.contextTagged = contextTagged;
    }

    public void applyRule(List<TaggedWord> sentence, int index) {
        // Basic rule: If the current word is tagged as 'posTagged' and the context condition is satisfied, apply the rule
        if (sentence.get(index).posTag.equals(posTagged)) {
            // Example condition: the previous word must have a specific tag, contextTagged
            if (index > 0 && sentence.get(index - 1).posTag.equals(contextTagged)) {
                sentence.get(index).posTag = this.correctTag;
            }
        }
    }
    
}
