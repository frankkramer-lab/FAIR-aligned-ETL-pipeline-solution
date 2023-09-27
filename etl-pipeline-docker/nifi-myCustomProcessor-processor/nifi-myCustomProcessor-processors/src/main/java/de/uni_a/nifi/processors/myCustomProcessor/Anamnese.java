package de.uni_a.nifi.processors.myCustomProcessor;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Patient anamnese questions
 */
public class Anamnese {

    /**
     * Questioning whether the person is smoking
     */
    @JsonProperty("rauchen")
    public String smoker;
    @JsonProperty("rauchen_tag")
    public String smoking_days;
    @JsonProperty("rauchen_jahre")
    public String smoking_years;

    /**
     * Movement
     */
    @JsonProperty("bewegung")
    public String move;
    @JsonProperty("bewegung_std")
    public String move_hour;

    /**
     * Tumor info
     */
    @JsonProperty("weitere_tumor")
    public String other_tumor;
    @JsonProperty("weiter_tumor_art")
    public String other_tumor_type;
    @JsonProperty("weiter_tumor_art_sonst")
    public String other_tumor_type_other;
    @JsonProperty("therapie_tumorerkrankung")
    public String other_tumor_therapy;

    /**
     *
     */
    @JsonProperty("herzkreislauferkrankung")
    public String heart_disease;

    /**
     * Diabetes
     */
    @JsonProperty("diabetes")
    public String diabetes;

    /**
     * other diseases
     */

    @JsonProperty("weitere_erkrankungen")
    public String other_disease;
    @JsonProperty("weitere_erkrankungen_art")
    public String other_disease_type;

    /**
     * Symptoms in last 14 days (gedrückte Stimmung)
     * Info: You could add a custom serializer in order to map with
     * regex and properties to lists
     */
    @JsonProperty("symptom___1")
    public String symptom_1;
    @JsonProperty("symptom___2")
    public String symptom_2;
    @JsonProperty("symptom___3")
    public String symptom_3;
    @JsonProperty("symptom___4")
    public String symptom_4;

    @Override
    public String toString() {
        return "Rauchen: " + this.smoker + "\n" +
                "rauchen_tag: " + this.smoking_days +"\n" +
                "rauchen_jahre: " + this.smoking_years +"\n" +
                "bewegung: " + this.move +"\n" +
                "bewegung_std: " + this.move_hour +"\n" +
                "weitere_tumor: " +this.other_tumor +"\n" +
                "weiter_tumor_art: " + this.other_tumor_type +"\n" +
                "therapie_tumorerkrankung: " + this.other_tumor_therapy +"\n" +
                "herzkreislauferkrankung: " + this.heart_disease +"\n" +
                "diabetes: " + this.diabetes +"\n" +
                "weitere_erkrankungen: " + this.other_disease +"\n" +
                "weitere_erkrankungen_art: " + this.other_disease_type +"\n" +
                "symptom_1: " + this.symptom_1 + "\n" +
                "symptom_2: " + this.symptom_2 + "\n" +
                "symptom_3: " + this.symptom_3 + "\n" +
                "symptom_4: " + this.symptom_4;
    }

    // TODO: add symptom 1-4 as other Anamnese (create custom jsonreader)
}
