package de.uni_a.nifi.processors.myCustomProcessor;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;

public class FHIRBundler {
    /**
     * Create a FHIR Bundle from the Redcap Response including the Patient, Anamnese and the QuestionnaireREsponse
     * @param response
     * @return
     */
    public List<Resource> bundle(RedcapResponse response) {
        FhirContext ctx = FhirContext.forR4();
        List<Resource> resources = new ArrayList<Resource>();

         BundleBuilder bundleBuilder = new BundleBuilder(ctx)
                 .setBundleField("type", "transaction");

        // create patient
        System.out.println(response.getPerson());
        Patient patient = new PatientFactory().createFHIRPatient(response.getPerson(), response.getUniqueID());

        // create response fields
        Anamnese a = response.getAnamnese();
        ConditionFactory cf = new ConditionFactory();
        SmokerFactory sf = new SmokerFactory();
        Integer subID = 0;
        //System.out.println(a);

        Observation smoker = sf.createSmokerObservation(patient, a, response.getUniqueID() + subID++);

        Question q = response.getQuestion();
        QuestionnaireResponseFactory qrf = new QuestionnaireResponseFactory();
        QuestionnaireResponse questionnaireResponse = qrf.createQuestionnaireResponse("WAVES1", patient, q, response.getUniqueID());

        IParser parser = ctx.newJsonParser();
        // bundleBuilder.addTransactionUpdateEntry(patient);
        resources.add(patient);

        for (ConditionFactory.AnamneseType at: ConditionFactory.AnamneseType.values()) {
            String tempID = response.getUniqueID() + subID++;
            Condition cond = cf.createCondition(patient, a, at, tempID);
            // bundleBuilder.addTransactionUpdateEntry(cond);
            resources.add(cond);
        }
        // bundleBuilder.addTransactionUpdateEntry(smoker);
        // bundleBuilder.addTransactionUpdateEntry(questionnaireResponse);
        resources.add(smoker);
        resources.add(questionnaireResponse);

        // return parser.encodeResourceToString(bundleBuilder.getBundle());
        // return bundleBuilder;
        return resources;
    }

    public BundleBuilder bundleQuestionnaire() {
        FhirContext ctx = FhirContext.forR4();

        BundleBuilder bundleBuilder = new BundleBuilder(ctx)
                .setBundleField("type", "transaction");
        QuestionnaireFactory qf = new QuestionnaireFactory();
        Questionnaire q = qf.createQuestionnaire();
        bundleBuilder.addTransactionUpdateEntry(q);
        IParser parser = ctx.newJsonParser();
        // return parser.encodeResourceToString(bundleBuilder.getBundle());
        return bundleBuilder;
    }
}
