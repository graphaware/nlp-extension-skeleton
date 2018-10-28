package com.graphaware.nlp.extension.processor;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

public class CustomStanfordProcessorTest extends BaseTest {

    @Test
    public void testTwitterAccountsAreMarked() {
        createCustomPipeline();
        executeInTransaction("CALL ga.nlp.annotate({pipeline: 'custom', text: 'You should follow the neo4j account.', id:'test', checkLanguage: false}) YIELD result RETURN result", (result -> {
            assertTrue(result.hasNext());
        }));

        executeInTransaction("MATCH (n:TagOccurrence:NE_Twitteraccount) RETURN n.value AS value", (result -> {
            assertTrue(result.hasNext());
        }));
    }

    private void createCustomPipeline() {
        String textProcessor = CustomStanfordProcessor.class.getName();
        createPipeline(textProcessor, "custom", "tokenize", "ner");
    }
}