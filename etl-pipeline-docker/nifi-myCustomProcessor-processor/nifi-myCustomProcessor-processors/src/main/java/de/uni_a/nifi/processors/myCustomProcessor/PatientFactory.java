package de.uni_a.nifi.processors.myCustomProcessor;

import org.hl7.fhir.r4.model.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PatientFactory {
    /**
     * Create a FHIR Patient resource from the input Redcap Person
     * @param person Person data class
     * @return HAPI Fhir Patient resource mapped from the Redcap PErson
     */
    public Patient createFHIRPatient(Person person, String uniqueID) {
        Patient patient = new Patient();
        // patient.setId(person.getRecord_id());
        patient.setId(uniqueID);

        // Set identifier
        Identifier idf = new Identifier()
                .setValue(uniqueID);
        patient.addIdentifier(idf);

        // set birthdate
        try {
            if (!person.getBirthDate().isEmpty()) {
                SimpleDateFormat f = new SimpleDateFormat("dd-MM-YYYY", Locale.GERMANY);
                patient.setBirthDate(f.parse(person.getBirthDate()));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // add weight and height
        if (checkNotNull(person.getBodyheight()))
            patient.addExtension(createBodyweight(patient, person.getBodyweight()));
        if (checkNotNull(person.getBodyheight()))
            patient.addExtension(createBodyheight(patient, person.getBodyheight()));

        // add federal state
        if (checkNotNull(person.getFederalState()))
            patient.addExtension(createCountry(person.getFederalState()));

        // add current (condition)
        if (checkNotNull(person.getCurrentState()))
            patient.addExtension(createCurrentCondition(person.getCurrentState()));

        // add gender
        // String convGender = FHIRValues.genderMap.get(this.gender).get(0);
        // Enumeration<Enumerations.AdministrativeGender> gender = new Enumerations.AdministrativeGenderEnumFactory().fromType(new StringType(convGender));
        // p.setGenderElement(gender);
        if (checkNotNull(person.getGender()))
            patient.setGender(Enumerations.AdministrativeGender.fromCode(person.getGender()));

        // // add marital status
        if (PersonValues.excludedMaritalStatus.contains(person.getMaritalStatus()))
            throw new RuntimeException("The marital status is excluded!");
        else
            patient.setMaritalStatus(new CodeableConcept(new Coding().setCode(person.getMaritalStatus())));
        return patient;
    }

    /**
     * Create a custom bodyheight extension using the unitsofmeasure system
     * @param ref
     * @param bodyheight
     * @return
     */
    private Extension createBodyheight(Patient ref, String bodyheight) {
        return createBodyMeasurement(ref, PersonValues.heightUnit, bodyheight, "8302-2", "Groesse","http://hl7.org/fhir/StructureDefinition/bodyheight");
    }

    /**
     * Create a custom bodyweight extension using the unitsofmeasure system
     * @param ref
     * @param bodyweight
     * @return
     */
    private Extension createBodyweight(Patient ref, String bodyweight) {
        return createBodyMeasurement(ref, PersonValues.weightUnit, bodyweight, "29463-7", "Gewicht", "http://hl7.org/fhir/StructureDefinition/bodyweight");
    }

    /**
     * Create a body measurement (e.g. weight/height)
     * @param patient
     * @param unit
     * @param value
     * @param code
     * @param display
     * @param profileUrl
     * @return
     */
    private Extension createBodyMeasurement(Patient patient, String unit, String value, String code, String display, String profileUrl) {
        Observation m = createObservation(Observation.ObservationStatus.FINAL, code , PersonValues.loincUrl, display, patient);

        // set quantity with system
        m.setValue(createQuantity(value, unit, unit));

        // add the Extension to patient
        Extension e = new Extension()
                .setUrl(profileUrl)
                .setValue(new Reference(m));
        return e;
    }

    /**
     * Create a current condition Extension ranging from 1 to 5 for this person
     * @param currentState
     * @return
     */
    private Extension createCurrentCondition(String currentState) {
        // create coding
        Coding curCode = new Coding()
                .setSystem("http://example.org/CodeSystem/current-condition-waves")
                .setCode(PersonValues.currentConditionMap.get(currentState).get(0))
                .setDisplay(PersonValues.currentConditionMap.get(currentState).get(1));

        Extension condition = new Extension()
                .setUrl("http://example.org/StructureDefinition/current-condition-extension")
                .setValue(curCode);
        return condition;
    }

    /**
     * Create a federal state extension with the given state
     * @param state
     * @return Country Extension
     */
    private Extension createCountry(String state) {
        List<Extension> nested = new ArrayList<Extension>();
        // create federal State Coding
        Coding federalStateVal = new Coding()
                .setSystem("urn:iso:std:iso:3166-2:de")
                .setCode(PersonValues.federalStateMap.get(state).get(0))
                .setDisplay(PersonValues.federalStateMap.get(state).get(1));
        // create germany federal
        Extension federalState = new Extension()
                .setUrl("http://example.org/StructureDefinition/federal-state-extension")
                .setValue(federalStateVal);
        // create other country -> not possible

        nested.add(federalState);

        Extension country = new Extension()
                .setUrl("http://example.org/StructureDefinition/country-extension");
        country.setExtension(nested);

        return country;
    }

    /**
     * Create a simple units of measure quantity
     * @param value
     * @param unit
     * @param code
     * @return
     */
    private SimpleQuantity createQuantity(String value, String unit, String code) {
        SimpleQuantity qt = new SimpleQuantity();
        qt.setValue(Double.parseDouble(value))
                .setUnit(unit)
                .setCode(code)
                .setSystem("http://unitsofmeasure.org");
        return qt;
    }

    /**
     * Create a new Observation from given status, codes, system
     * @param status
     * @param code
     * @param system
     * @param display
     * @param subject
     * @return Observation
     */
    private Observation createObservation(Observation.ObservationStatus status,
                                          String code,
                                          String system,
                                          String display,
                                          Patient subject) {
        Observation obs = new Observation()
                .setStatus(status);

        Coding coding = obs.getCode().addCoding()
                .setCode(code)
                .setSystem(system)
                .setDisplay(display);

        obs.setCode(new CodeableConcept(coding));
        obs.setSubject(new Reference(subject));
        // obs.setId(subject.getId());
        return obs;
    }

    private Boolean checkNotNull(String toCheck) {
        return !toCheck.isEmpty();
    }
}
