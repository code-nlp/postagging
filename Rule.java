package postagging;
import java.util.List;

public class Rule {
    String fromTag;
    String toTag;
    String prevTagContext;

    public Rule(String fromTag, String toTag, String prevTagContext) {
        this.fromTag = fromTag;
        this.toTag = toTag;
        this.prevTagContext = prevTagContext;
    }

    // Kiểm tra xem quy tắc có thể áp dụng tại vị trí nhất định trong câu hay không
    public boolean matches(List<TaggedWord> sentence, int index) {
        boolean matchesFromTag = sentence.get(index).posTag.equals(fromTag);
        boolean matchesPrevTag = (index > 0) ? sentence.get(index - 1).posTag.equals(prevTagContext) : true;
        return matchesFromTag && matchesPrevTag;
    }

    // Áp dụng quy tắc
    public void apply(List<TaggedWord> sentence, int index) {
        sentence.get(index).posTag = toTag;
    }

    @Override
    public String toString() {
        return "Rule: " + fromTag + " -> " + toTag + " if prevTag is " + prevTagContext;
    }
}
