package de.uni_a.nifi.processors.myCustomProcessor;

import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static de.uni_a.nifi.processors.myCustomProcessor.QuestionnaireValues.*;

public class QuestionnaireResponseFactory {

    /**
     * create a questionnaire response object based on an existing questionanire, the patient and the patients response
     * @param questionnaireID
     * @param patient
     * @param response
     * @return
     */
    public QuestionnaireResponse createQuestionnaireResponse(String questionnaireID, Patient patient, Question response, String uniqueID) {
        QuestionnaireResponse r = new QuestionnaireResponse()
                .setStatus(statusMap.get(response.waves_complete));
        // r.setQuestionnaire(questionnaire.getId());
        r.setQuestionnaire(questionnaireID);
        r.setIdentifier(new Identifier()
                .setValue(patient.getId()));

        r.setId(uniqueID);
        r.setSubject(new Reference(patient));
        QuestionnaireResponse.QuestionnaireResponseItemComponent group_1 = createResponseItem(item_1_link_id, item_1_text);
        group_1.addItem(createResponseItem(item_1_1_link_id, item_1_1_text).setAnswer(Collections.singletonList(createAnswerItem(response.first_diagnose, item_1_1_answers))));
        group_1.addItem(createResponseItem(item_1_2_link_id, item_1_2_text).setAnswer(Collections.singletonList(createAnswerItem(response.diagnose_through, item_1_2_answers))));
        boolean cancer_family = "1".equals(response.cancer_family);
        group_1.addItem(createResponseItem(item_1_3_link_id, item_1_3_text)
                .setAnswer(Collections.singletonList(new QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent()
                        .setValue(new BooleanType(cancer_family)))));

        QuestionnaireResponse.QuestionnaireResponseItemComponent group_2 = createResponseItem(item_2_link_id, item_2_text);
        group_2.addItem(createResponseItem(item_2_1_link_id, item_2_1_text).setAnswer(
                createMultiAnswerItem(item_2_1_name, response.answers, item_2_1_answers))
        );
        QuestionnaireResponse.QuestionnaireResponseItemComponent group_2_2 = createResponseItem(item_2_2_link_id, item_2_2_text);
        group_2_2.addItem(createResponseItem(item_2_2_1_link_id, item_2_2_1_text)
                .setAnswer(Collections.singletonList(createCodedAnswerItem(response.answers.get(item_2_2_1_name), scaleMap))));
        group_2_2.addItem(createResponseItem(item_2_2_2_link_id, item_2_2_2_text)
                .setAnswer(Collections.singletonList(createCodedAnswerItem(response.answers.get(item_2_2_2_name), scaleMap))));
        group_2_2.addItem(createResponseItem(item_2_2_3_link_id, item_2_2_3_text)
                .setAnswer(Collections.singletonList(createCodedAnswerItem(response.answers.get(item_2_2_3_name), scaleMap))));
        group_2_2.addItem(createResponseItem(item_2_2_4_link_id, item_2_2_4_text)
                .setAnswer(Collections.singletonList(createCodedAnswerItem(response.answers.get(item_2_2_4_name), scaleMap))));
        group_2_2.addItem(createResponseItem(item_2_2_5_link_id, item_2_2_5_text)
                .setAnswer(Collections.singletonList(createCodedAnswerItem(response.answers.get(item_2_2_5_name), scaleMap))));
        group_2_2.addItem(createResponseItem(item_2_2_6_link_id, item_2_2_6_text)
                .setAnswer(Collections.singletonList(createCodedAnswerItem(response.answers.get(item_2_2_6_name), scaleMap))));

        group_2.addItem(createResponseItem(item_2_3_link_id, item_2_3_text).setAnswer(Collections.singletonList(createAnswerItem(response.rating_talk, item_2_3_answers))));
        group_2.addItem(createResponseItem(item_2_4_link_id, item_2_4_text).setAnswer(Collections.singletonList(createAnswerItem(response.talk_duration, item_2_4_answers))));
        group_2.addItem(createResponseItem(item_2_5_link_id, item_2_5_text).setAnswer(Collections.singletonList(createAnswerItem(response.talk_understandability, item_2_5_answers))));
        // DONE: hier multi-answer
        group_2.addItem(createResponseItem(item_2_6_link_id, item_2_6_text).setAnswer(
                createMultiAnswerItem(item_2_6_name, response.answers, item_2_6_answers))
        );
        group_2.addItem(createResponseItem(item_2_7_link_id, item_2_7_text).setAnswer(
                createMultiAnswerItem(item_2_7_name, response.answers, item_2_7_answers))
        );
        // TODO: hier Enable WHen answer (VERBESSERN)
        if (response.answers.containsKey(item_2_8_name)) {
            group_2.addItem(createResponseItem(item_2_8_link_id, item_2_8_text).setAnswer(
                    Collections.singletonList(
                            new QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent()
                                    .setValue(new StringType(response.answers.get(item_2_8_name)))
                    )
            ));
        }

        QuestionnaireResponse.QuestionnaireResponseItemComponent answer_2_9 = createResponseItem(item_2_9_link_id, item_2_9_text);
        if (!response.patient_capacity.isEmpty())
                answer_2_9.setAnswer(Collections.singletonList(createAnswerItem(response.patient_capacity, item_2_9_answers)));

        group_2.addItem(answer_2_9);
        r.addItem(group_1);
        r.addItem(group_2);
        return r;
    }

    /**
     * create a response item based on the questionnaire linkid and the text
     * @param linkId
     * @param text
     * @return
     */
    private QuestionnaireResponse.QuestionnaireResponseItemComponent createResponseItem(String linkId, String text) {
        return new QuestionnaireResponse.QuestionnaireResponseItemComponent()
                .setLinkId(linkId)
                .setText(text);
    }

    /**
     * create an answer item based on the given answer and its answer option mappings
     * @param answer
     * @param choice
     * @return
     */
    private QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent createAnswerItem(String answer, Questionnaire.QuestionnaireItemAnswerOptionComponent[] choice) {
        if (answer.isEmpty())
            return null;
        return new QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent()
                .setValue(choice[Integer.parseInt(answer)-1].getValue());
    }

    /**
     * create an answer item based on a given mapping and its answer
     * @param answer
     * @param codeMap
     * @return
     */
    private QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent createCodedAnswerItem(String answer, Map<String, Coding> codeMap) {
        return new QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent()
                .setValue(codeMap.get(answer));
    }

    /**
     * create an answer list from the given multi answer option questions
     * @param filter
     * @param answers
     * @param options
     * @return
     */
    private ArrayList<QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent> createMultiAnswerItem(String filter, Map<String, String> answers, Questionnaire.QuestionnaireItemAnswerOptionComponent[] options) {
        // TODO: remake
        Set<String> adsf = answers.keySet()
                .stream()
                .filter(s -> s.matches(filter + ".*"))
                .collect(Collectors.toSet());
        ArrayList<QuestionnaireResponse.QuestionnaireResponseItemAnswerComponent> l = new ArrayList<>();
        for (String answer_key: adsf) {
            l.add(createAnswerItem(answers.get(answer_key), options));
        }
        //System.out.println("Name: " + filter + " Answer: " + adsf.toString() + " response: " + l.toString());
        return l;
    }
}
