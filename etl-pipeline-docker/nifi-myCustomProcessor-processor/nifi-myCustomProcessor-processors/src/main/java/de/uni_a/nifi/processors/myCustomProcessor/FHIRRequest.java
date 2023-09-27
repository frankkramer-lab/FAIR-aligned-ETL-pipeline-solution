package de.uni_a.nifi.processors.myCustomProcessor;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.Questionnaire;

public class FHIRRequest {
    static final String baseURL = "http://localhost:8080/fhir";
    public Questionnaire getQuestionnaireFromAPI(FhirContext ctx, String reference) {
        IGenericClient client = createFHIRClient(ctx);
        return client
                .read()
                .resource(Questionnaire.class)
                .withId(reference)
                .execute();
    }

    private IGenericClient createFHIRClient(FhirContext ctx) {
        return ctx.newRestfulGenericClient(baseURL);
    }
}
