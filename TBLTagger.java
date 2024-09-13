package postagging;
import java.util.ArrayList;
import java.util.List;


/*
 * Abbreviation	Meaning
    CC	coordinating conjunction
    CD	cardinal digit
    DT	determiner
    EX	existential there
    FW	foreign word
    IN	preposition/subordinating conjunction
    JJ	This NLTK POS Tag is an adjective (large)
    JJR	adjective, comparative (larger)
    JJS	adjective, superlative (largest)
    LS	list market
    MD	modal (could, will)
    NN	noun, singular (cat, tree)
    NNS	noun plural (desks)
    NNP	proper noun, singular (sarah)
    NNPS	proper noun, plural (indians or americans)
    PDT	predeterminer (all, both, half)
    POS	possessive ending (parent\ ‘s)
    PRP	personal pronoun (hers, herself, him, himself)
    PRP$	possessive pronoun (her, his, mine, my, our )
    RB	adverb (occasionally, swiftly)
    RBR	adverb, comparative (greater)
    RBS	adverb, superlative (biggest)
    RP	particle (about)
    TO	infinite marker (to)
    UH	interjection (goodbye)
    VB	verb (ask)
    VBG	verb gerund (judging)
    VBD	verb past tense (pleaded)
    VBN	verb past participle (reunified)
    VBP	verb, present tense not 3rd person singular(wrap)
    VBZ	verb, present tense with 3rd person singular (bases)
    WDT	wh-determiner (that, what)
    WP	wh- pronoun (who)
    WRB	wh- adverb (how)
 */


public class TBLTagger {
    // Initial tagging method (e.g., naive tagger, which tags all nouns)
    public static List<TaggedWord> initialTagging(List<String> sentence) {
        List<TaggedWord> taggedSentence = new ArrayList<>();
        for (String word : sentence) {
            // Naive initial tagging: Tag every word as "NN" (Noun) initially
            taggedSentence.add(new TaggedWord(word, "NN"));
        }
        return taggedSentence;
    }

    // Apply transformation rules to improve the tags
    public static void applyTransformationRules(List<TaggedWord> taggedSentence, List<TransformationRule> rules) {
        for (int i = 0; i < taggedSentence.size(); i++) {
            for (TransformationRule rule : rules) {
                rule.applySimpleRule(taggedSentence, i);
            }
        }
    }

    public static void main(String[] args) {
        // Sample sentence
        // The quick brown fox jumps over the lazy dog
        // separating words 
        List<String> sentenceSeparatingWords = List.of("The","quick", "brown", "fox", "jumps", "over", "the","lazy","dog");

        // Step 1: Initial tagging
        List<TaggedWord> taggedSentence = initialTagging(sentenceSeparatingWords);

        // Step 2: Define transformation rules
        List<TransformationRule> rules = new ArrayList<>();
        rules.add(new TransformationRule("NN", "DT", "NN"));  // If a noun follows another noun, change it to determiner (simplified rule)
        rules.add(new TransformationRule("NN", "VB", "DT"));  // If a noun follows a determiner, change it to verb (simplified rule)
        //rules.add(new TransformationRule("DT", "NN", "VB"));
        //rules.add(new TransformationRule("DT", "VB", "NN"));
        //rules.add(new TransformationRule("NN", "JJ", "VB"));
        
        // Step 3: Apply transformation rules
        applyTransformationRules(taggedSentence, rules);

        // Output the final tagged sentence
        for (TaggedWord taggedWord : taggedSentence) {
            System.out.println(taggedWord);
        }
    }
}
