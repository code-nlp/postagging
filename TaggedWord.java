package postagging;

public class TaggedWord {
    protected String word;
    protected String posTag;

    public TaggedWord(String word, String posTag) {
        this.word = word;
        this.posTag = posTag;
    }

    @Override
    public String toString() {
        return word + "/" + posTag;
    }
}