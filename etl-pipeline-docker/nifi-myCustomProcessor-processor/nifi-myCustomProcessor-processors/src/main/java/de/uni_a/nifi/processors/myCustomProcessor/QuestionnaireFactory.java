package de.uni_a.nifi.processors.myCustomProcessor;

import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.StringType;

import java.util.Arrays;
import java.util.Collections;

public class QuestionnaireFactory {
    /**
     * Create a given questionnaire based on fixed values
     * @return
     */
    public Questionnaire createQuestionnaire() {
        Questionnaire q = new Questionnaire()
                .setStatus(QuestionnaireValues.questionnaireStatus)
                .setUrl("http://example.org/Questionnaire/Fragebogen-KFDM-WAVES")
                .setTitle(QuestionnaireValues.questionnaireTitle)
                .setDescription(QuestionnaireValues.questionnaireDescription)
                .setExperimental(QuestionnaireValues.questionnaireExperimental);
        q.setId(QuestionnaireValues.questionnaireID);
        q.setIdentifier(Collections.singletonList(new Identifier().setValue(QuestionnaireValues.questionnaireID)));
        // add Item 1 (Rahmenbedingungen)
        Questionnaire.QuestionnaireItemComponent display_1 = createQuestionnaireItem(QuestionnaireValues.item_1_link_id, Questionnaire.QuestionnaireItemType.DISPLAY, QuestionnaireValues.item_1_text);
        // add Item 1.1
        display_1.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_1_1_link_id, QuestionnaireValues.item_1_1_text, QuestionnaireValues.item_1_1_answers));        // add Item 1.2
        // add Item 1.2
        display_1.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_1_2_link_id, QuestionnaireValues.item_1_2_text, QuestionnaireValues.item_1_2_answers));
        // add Item 1.3
        display_1.addItem(createBooleanQuestionnaireItem(QuestionnaireValues.item_1_3_link_id, QuestionnaireValues.item_1_3_text));

        // add Item Container 2 (Erleben waehrend Gespraech)
        Questionnaire.QuestionnaireItemComponent group_2 = createQuestionnaireItem(QuestionnaireValues.item_2_link_id, Questionnaire.QuestionnaireItemType.GROUP, QuestionnaireValues.item_2_text);
        // add Item 2.1
        group_2.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_2_1_link_id, QuestionnaireValues.item_2_1_text, QuestionnaireValues.item_2_1_answers));
        // add Group 2.2
        Questionnaire.QuestionnaireItemComponent group_2_2 = createQuestionnaireItem(QuestionnaireValues.item_2_2_link_id, Questionnaire.QuestionnaireItemType.GROUP, QuestionnaireValues.item_2_2_text);
        // add Item 2.2.1
        group_2_2.addItem(createCodedChoiceQuestionnaireItem(QuestionnaireValues.item_2_2_1_link_id, QuestionnaireValues.item_2_2_1_text, QuestionnaireValues.scaleAnswers, QuestionnaireValues.scaleCodings));
        // add Item 2.2.2
        group_2_2.addItem(createCodedChoiceQuestionnaireItem(QuestionnaireValues.item_2_2_2_link_id, QuestionnaireValues.item_2_2_2_text, QuestionnaireValues.scaleAnswers, QuestionnaireValues.scaleCodings));
        // add Item 2.2.3
        group_2_2.addItem(createCodedChoiceQuestionnaireItem(QuestionnaireValues.item_2_2_3_link_id, QuestionnaireValues.item_2_2_3_text, QuestionnaireValues.scaleAnswers, QuestionnaireValues.scaleCodings));
        // add Item 2.2.4
        group_2_2.addItem(createCodedChoiceQuestionnaireItem(QuestionnaireValues.item_2_2_4_link_id, QuestionnaireValues.item_2_2_4_text, QuestionnaireValues.scaleAnswers, QuestionnaireValues.scaleCodings));
        // add Item 2.2.5
        group_2_2.addItem(createCodedChoiceQuestionnaireItem(QuestionnaireValues.item_2_2_5_link_id, QuestionnaireValues.item_2_2_5_text, QuestionnaireValues.scaleAnswers, QuestionnaireValues.scaleCodings));
        // add Item 2.2.6
        group_2_2.addItem(createCodedChoiceQuestionnaireItem(QuestionnaireValues.item_2_2_6_link_id, QuestionnaireValues.item_2_2_6_text, QuestionnaireValues.scaleAnswers, QuestionnaireValues.scaleCodings));
        group_2.addItem(group_2_2);

        // add item 2.3
        group_2.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_2_3_link_id, QuestionnaireValues.item_2_3_text, QuestionnaireValues.item_2_3_answers));
        // add item 2.4
        group_2.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_2_4_link_id, QuestionnaireValues.item_2_4_text, QuestionnaireValues.item_2_4_answers));
        // add item 2.5
        group_2.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_2_5_link_id, QuestionnaireValues.item_2_5_text, QuestionnaireValues.item_2_5_answers));
        // add item 2.6
        group_2.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_2_6_link_id, QuestionnaireValues.item_2_6_text, QuestionnaireValues.item_2_6_answers));
        // add item 2.7
        group_2.addItem(createOpenChoiceQuestionnaireItem(QuestionnaireValues.item_2_7_link_id, QuestionnaireValues.item_2_7_text, QuestionnaireValues.item_2_7_answers));
        // add item 2.8/string
        group_2.addItem(createQuestionnaireItem(QuestionnaireValues.item_2_8_link_id, Questionnaire.QuestionnaireItemType.STRING, QuestionnaireValues.item_2_8_text)
                .setEnableWhen(Collections.singletonList(createQuestionnaireCondition(QuestionnaireValues.item_2_7_link_id, "Sonstiges"))));
        // add item 2.8/2.9
        group_2.addItem(createChoiceQuestionnaireItem(QuestionnaireValues.item_2_9_link_id, QuestionnaireValues.item_2_9_text, QuestionnaireValues.item_2_9_answers));

        q.addItem(display_1);
        q.addItem(group_2);
        return q;
    }

    /**
     * @param id
     * @param type
     * @param text
     * @return
     */
    private Questionnaire.QuestionnaireItemComponent createQuestionnaireItem(String id, Questionnaire.QuestionnaireItemType type, String text) {
        return new Questionnaire.QuestionnaireItemComponent()
                .setLinkId(id)
                .setType(type)
                .setText(text);
    }

    /**
     *
     * @param origQId
     * @param answer
     * @return
     */
    private Questionnaire.QuestionnaireItemEnableWhenComponent createQuestionnaireCondition(String origQId, String answer) {
        return new Questionnaire.QuestionnaireItemEnableWhenComponent()
                .setQuestion(origQId)
                .setAnswer(new StringType(answer));
    }

    /**
     *
     * @param id
     * @param val
     * @param answerOptions
     * @return
     */
    private Questionnaire.QuestionnaireItemComponent createChoiceQuestionnaireItem(String id, String val, Questionnaire.QuestionnaireItemAnswerOptionComponent[] answerOptions) {
        return createQuestionnaireItem(id, Questionnaire.QuestionnaireItemType.CHOICE, val)
                .setRequired(false)
                .setAnswerOption(Arrays.asList(answerOptions));
    }

    /**
     *
     * @param id
     * @param val
     * @param answerOptions
     * @return
     */
    private Questionnaire.QuestionnaireItemComponent createOpenChoiceQuestionnaireItem(String id, String val, Questionnaire.QuestionnaireItemAnswerOptionComponent[] answerOptions) {
        return createQuestionnaireItem(id, Questionnaire.QuestionnaireItemType.OPENCHOICE, val)
                .setRequired(false)
                .setAnswerOption(Arrays.asList(answerOptions));
    }

    /**
     *
     * @param id
     * @param val
     * @param answerOptions
     * @param valueCodings
     * @return
     */
    private Questionnaire.QuestionnaireItemComponent createCodedChoiceQuestionnaireItem(String id, String val, Questionnaire.QuestionnaireItemAnswerOptionComponent[] answerOptions, Coding[] valueCodings) {
        return createChoiceQuestionnaireItem(id, val, answerOptions)
                .setCode(Arrays.asList(valueCodings))
                .setRequired(false);
    }

    /**
     *
     * @param id
     * @param val
     * @return
     */
    private Questionnaire.QuestionnaireItemComponent createBooleanQuestionnaireItem(String id, String val) {
        return createQuestionnaireItem(id, Questionnaire.QuestionnaireItemType.BOOLEAN, val)
                .setRequired(false);
    }
}
