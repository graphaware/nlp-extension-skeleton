package com.graphaware.nlp.extension.processor;

import com.graphaware.nlp.annotation.NLPTextProcessor;
import com.graphaware.nlp.domain.AnnotatedText;
import com.graphaware.nlp.dsl.request.PipelineSpecification;
import com.graphaware.nlp.processor.stanford.StanfordTextProcessor;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.util.CoreMap;

import java.util.Arrays;
import java.util.List;

@NLPTextProcessor(name = "CustomTextProcessor")
public class CustomStanfordProcessor extends StanfordTextProcessor {

    private static final List<String> twitterAccounts = Arrays.asList("neo4j", "graphconnect");
    private static final String NAMED_ENTITY = "TwitterAccount";


    @Override
    protected AnnotatedText extendAnnotation(String text, String lang, PipelineSpecification pipelineSpecification, AnnotatedText annotatedText, CoreDocument coreDocument, Annotation document, List<CoreMap> sentences) {
        annotatedText.getSentences().forEach(sentence -> {
            sentence.getTagOccurrences().forEach((integer, tagOccurrences) -> {
                tagOccurrences.forEach(tagOccurrence -> {
                    if (twitterAccounts.contains(tagOccurrence.getValue().toLowerCase())) {
                        tagOccurrence.getElement().setNe(Arrays.asList(NAMED_ENTITY));
                    }
                });
            });
        });

        return annotatedText;
    }
}
