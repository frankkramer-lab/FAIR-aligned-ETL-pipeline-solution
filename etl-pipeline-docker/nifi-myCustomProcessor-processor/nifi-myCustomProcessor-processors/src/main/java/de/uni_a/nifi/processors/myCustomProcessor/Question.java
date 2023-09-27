package de.uni_a.nifi.processors.myCustomProcessor;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Patients personal questions
 */
public class Question {
    @JsonProperty("erstdiagnose_durch")
    public String first_diagnose;

    @JsonProperty("diagnose_per")
    public String diagnose_through;
    @JsonProperty("fanden")
    public String found;
    @JsonProperty("krebserkrankung_in_familie")
    public String cancer_family;
    @JsonProperty("bewertung_umfang_aufkl_rung")
    public String rating_talk;
    @JsonProperty("erstgespr_ch_dauer")
    public String talk_duration;
    @JsonProperty("verst_ndlichkeit_aufkl_rung")
    public String talk_understandability;
    @JsonProperty("aufnhamef_higkeit_patient")
    public String patient_capacity;
    @JsonProperty("waves_complete")
    public String waves_complete;

    /**
     * Collect multiple answers
     */
    public Map<String, String> answers = new HashMap<>();

    @JsonAnySetter
    public void setAnswers(String question, String answer) {
        //System.out.println(question + " : " + answer);
        if (answer == null)
            answer = "";
        if (question.matches(QuestionnaireValues.item_2_1_name + ".*|" + QuestionnaireValues.item_2_6_name + ".*|" + QuestionnaireValues.item_2_7_name + ".*")) {
            if (answer.contains("1")) {
                Pattern p = Pattern.compile("(\\d+)(?!.*\\d)");
                Matcher m = p.matcher(question);
                if (m.find())
                    answers.put(question, m.group(1));
            }
        } else if (question.matches("bewertung_arzt_.*")) {
            answers.put(question, answer);
        }
    }

    @Override
    public String toString() {
        return "erstdiagnose_durch: " + this.first_diagnose + "\n"
                + "diagnose_per " + this.diagnose_through + "\n"
                + "fanden " + this.found + "\n"
                + "krebserkrankung_in_familie " + this.cancer_family + "\n"
                + "bewertung_umfang_aufkl_rung " + this.rating_talk + "\n"
                + "erstgespr_ch_dauer " + this.talk_duration+ "\n"
                + "verst_ndlichkeit_aufkl_rung " + this.talk_understandability+ "\n"
                + "aufnahmef_higkeit_patient " + this.patient_capacity + "\n"
                + "waves_complete " + this.waves_complete + "\n"
                + "answers " + this.answers;
    }

}
