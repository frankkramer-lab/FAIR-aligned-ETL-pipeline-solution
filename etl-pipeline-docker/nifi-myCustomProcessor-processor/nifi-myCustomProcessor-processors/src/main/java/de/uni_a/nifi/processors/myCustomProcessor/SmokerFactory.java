package de.uni_a.nifi.processors.myCustomProcessor;

import org.hl7.fhir.r4.model.*;

public class SmokerFactory {
    /**
     * create an Observation for a patients smoking habits based on the anamnese answers
     * @param patient
     * @param anamnese
     * @return
     */
    public Observation createSmokerObservation(Patient patient, Anamnese anamnese, String uniqueID) {
        Observation smoker = new Observation()
                .addPerformer(new Reference(patient))
                .setSubject(new Reference(patient))
                .setStatus(Observation.ObservationStatus.FINAL)
                .setCode(new CodeableConcept()
                        .addCoding(new Coding().setCode("social-history")));

        smoker.setId(uniqueID);
        Boolean smoking = "1".equals(anamnese.smoker);

        Integer years = (anamnese.smoking_years.isEmpty()) ? 0 : Integer.parseInt(anamnese.smoking_years);
        Integer perDay = (anamnese.smoking_days.isEmpty()) ? 0 : Integer.parseInt(anamnese.smoking_days);
        // add years
        smoker.addExtension(createSmokingYears(years));
        // set code (Smoking VS)
        smoker.setCode(createSmokingCode(smoking, perDay));
        return smoker;
    }

    /**
     * create a smoking codeable concept based on wether patients a smoker and smoking days
     * @param smoker
     * @param days
     * @return
     */
    private CodeableConcept createSmokingCode(Boolean smoker, Integer days) {
        // set range
        int mapping = 0;
        if (smoker) {
            if (days < 1)
                mapping = 1;
            else if (days <= 9)
                mapping = 2;
            else if (days <= 19)
                mapping = 3;
            else if (days <= 39)
                mapping = 4;
            else
                mapping = 5;
        }
        // map
        return new CodeableConcept()
                .addCoding(
                        new Coding()
                                .setCode(PersonValues.smokerMap.get(mapping).get(0))
                                .setSystem(PersonValues.smokerMap.get(mapping).get(1))
                                .setDisplay(PersonValues.smokerMap.get(mapping).get(2))
                );
    }

    /**
     *
     * @param years
     * @return
     */
    private Extension createSmokingYears(Integer years) {
        return new Extension()
                .setUrl("http://example.org/StructureDefinition/years-of-smoking")
                .setValue(new IntegerType(years));
    }
}
