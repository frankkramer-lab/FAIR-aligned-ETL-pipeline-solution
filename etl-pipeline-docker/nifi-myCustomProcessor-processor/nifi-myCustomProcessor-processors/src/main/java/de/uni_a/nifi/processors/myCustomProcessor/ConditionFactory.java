package de.uni_a.nifi.processors.myCustomProcessor;

import org.hl7.fhir.r4.model.*;

public class ConditionFactory {
    enum AnamneseType {
        ACTIVITY,
        DIABETES,
        HEART_CONDITION,
        OTHER_DISEASE,
        TUMOR
    }

    /**
     * Create a specific Condition resource for a given patient, the Anamnese and the given type
     * @param patient
     * @param anamnese
     * @param anamneseType
     * @return
     */
    public Condition createCondition(Patient patient, Anamnese anamnese, AnamneseType anamneseType, String uniqueID) {
        Condition cond = new Condition()
                .setSubject(new Reference(patient));
        cond.setId(uniqueID);
        String status = "active";
        // use if for Docker Nifi Build ver
        if (anamneseType == AnamneseType.ACTIVITY) {
            // set activity extension
            Boolean move = "1".equals(anamnese.move);
            cond.addExtension(createActivity(move, anamnese.move_hour));
            // set clinical status
            if (!move)
                status = "inactive";
        }
        // set code (physical activity)
        if (anamneseType == AnamneseType.DIABETES) {
            // add existance
            if (!anamnese.diabetes.isEmpty())
                cond.addExtension(createYesNoUnknownExtension(anamnese.diabetes));
            if (!anamnese.diabetes.contains("1"))
                status = "inactive";
        }
        if (anamneseType == AnamneseType.HEART_CONDITION) {
            // create existance
            if (!anamnese.heart_disease.contains("1"))
                status = "inactive";
            if (!anamnese.heart_disease.isEmpty())
                cond.addExtension(createYesNoUnknownExtension(anamnese.heart_disease));
        }
        if (anamneseType == AnamneseType.OTHER_DISEASE) {
            // set other disease extension
            Boolean exists = "1".equals(anamnese.other_disease);
            if (!exists)
                status = "inactive";
            cond.addExtension(createOtherDiseaseExt(exists, anamnese.other_disease_type));
        }
        if (anamneseType == AnamneseType.TUMOR) {
            // set other disease extension
            Boolean otherTumorTherapy = "0".equals(anamnese.other_tumor_therapy);
            if (!anamnese.other_tumor.contains("1"))
                status = "inactive";
            cond.addExtension(createOtherTumorExt(anamnese.other_tumor, otherTumorTherapy));
            if (otherTumorTherapy)
                cond.setCode(createTumorType(anamnese.other_tumor_type));
        }

        cond.setClinicalStatus(new CodeableConcept().addCoding(new Coding().setCode(status)));
        // set verification status
        cond.setCode(createConditionProvisionalCoding());
        return cond;
    }


    /**
     * Create a tumor type codeable concept based on the tumor type mapping
     * @param type
     * @return
     */
    private CodeableConcept createTumorType(String type) {
        CodeableConcept tumorType = new CodeableConcept();
        tumorType.addCoding(
                new Coding()
                        .setCode(PersonValues.tumorDiseaseMap.get(type).get(0))
                        .setSystem(PersonValues.tumorDiseaseMap.get(type).get(1))
                        .setDisplay(PersonValues.tumorDiseaseMap.get(type).get(2)));
        return tumorType;
    }

    /**
     * create an extension for other tumor types based on the given descritption and therapy information
     * @param other_tumor
     * @param other_tumor_therapy
     * @return
     */
    private Extension createOtherTumorExt(String other_tumor, Boolean other_tumor_therapy) {
        Extension tumor = new Extension()
                .setUrl("http://example.org/StructureDefinition/tumor-extension");
        if (!other_tumor.isBlank())
            tumor.addExtension(createYesNoUnknownExtension(other_tumor));
        tumor.addExtension(createTreatmentExt(other_tumor_therapy));
        return tumor;
    }

    /**
     * create an extension for other diseases based on the disease and yes/no
     * @param yesNo
     * @param disease
     * @return
     */
    private Extension createOtherDiseaseExt(Boolean yesNo, String disease) {
        Extension otherDisease = new Extension()
                .setUrl("http://example.org/StructureDefinition/other-disease-extension");
        otherDisease.addExtension(createYesNoExtension(yesNo));
        otherDisease.addExtension(createDiseaseExt(disease));
        return otherDisease;
    }

    /**
     * create an extension for the treatment based on the given value
     * @param val
     * @return
     */
    private Extension createTreatmentExt(Boolean val) {
        return new Extension()
                .setUrl("Treatment")
                .setValue(new BooleanType(val));
    }

    /**
     * create an extension for the disease based on the given vlaue
     * @param val
     * @return
     */
    private Extension createDiseaseExt(String val) {
        return new Extension()
                .setUrl("Disease")
                .setValue(new StringType(val));
    }

    /**
     * create an extension for an activity based on wether the patient moved and the moving hours
     * @param move
     * @param move_hours
     * @return
     */
    private Extension createActivity(Boolean move, String move_hours) {
        Extension act = new Extension()
                .setUrl("http://example.org/StructureDefinition/physical-activity-extension");
        act.addExtension(createYesNoExtension(move));
        if (!move_hours.isEmpty())
            act.addExtension(createHoursPerWeek(Double.parseDouble(move_hours)));
        return act;
    }

    /**
     * create an extension for questions with Yes, No and unknown options based on the answer
     * @param answer
     * @return
     */
    private Extension createYesNoUnknownExtension(String answer) {
        return new Extension()
                .setUrl("http://example.org/StructureDefinition/yes-no-unknown-extension")
                .setValue(
                        new Coding()
                                .setCode(PersonValues.yesNoUnkownMap.get(answer).get(0))
                                .setSystem(PersonValues.yesNoUnkownMap.get(answer).get(1))
                                .setDisplay(PersonValues.yesNoUnkownMap.get(answer).get(2))
                );
    }

    /**
     * Create a codeable concept for provisional codings
     * @return
     */
    private CodeableConcept createConditionProvisionalCoding() {
        return new CodeableConcept()
                .addCoding(new Coding().setCode("provisional"));
    }

    /**
     * create an extension for Yes/No questions based on the answer value
     * @param val
     * @return
     */
    private Extension createYesNoExtension(Boolean val) {
        return new Extension()
                .setUrl("YesNoExtension")
                .setValue(new BooleanType(val));
    }

    /**
     * create an extension for given hours per week
     * @param hours
     * @return
     */
    private Extension createHoursPerWeek(Double hours) {
        return new Extension()
                .setUrl("HoursPerWeek")
                .setValue(new DecimalType(hours));
    }
}
