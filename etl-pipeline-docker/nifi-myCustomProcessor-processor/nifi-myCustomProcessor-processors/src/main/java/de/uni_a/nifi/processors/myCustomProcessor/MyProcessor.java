/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.uni_a.nifi.processors.myCustomProcessor;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.util.BundleBuilder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.commons.io.IOUtils;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.io.OutputStreamCallback;
import org.apache.nifi.processor.util.StandardValidators;
import org.checkerframework.checker.units.qual.A;
import org.hl7.fhir.r4.model.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicReference;

@Tags({"example"})
@CapabilityDescription("Provide a description")
@SeeAlso({})
@ReadsAttributes({@ReadsAttribute(attribute="", description="")})
@WritesAttributes({@WritesAttribute(attribute="", description="")})
public class MyProcessor extends AbstractProcessor {

    public static final Relationship ORIGINAL = new Relationship.Builder()
            .name("ORIGINAL")
            .description("Input flow file")
            .build();
    public static final Relationship FAILED = new Relationship.Builder()
            .name("FAILED")
            .description("Parser failed to convert the response")
            .build();
    public static final Relationship SUCCESS = new Relationship.Builder()
            .name("SUCCESS")
            .description("Successfully converted the input to FHIR resources")
            .build();

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        descriptors = new ArrayList<>();
        descriptors = Collections.unmodifiableList(descriptors);

        relationships = new HashSet<>();
        relationships.add(SUCCESS);
        relationships.add(FAILED);
        relationships.add(ORIGINAL);
        relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return this.relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return descriptors;
    }

    @OnScheduled
    public void onScheduled(final ProcessContext context) {

    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) {
        FlowFile flowFile = session.get();

        final AtomicReference<String> redcapResponse = new AtomicReference<>(null);

        if (flowFile == null) {
            return;
        }

        // read flow file
        session.read(flowFile, in -> {
            redcapResponse.set(in.toString());
                final String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
                redcapResponse.set(contents);
        });
        // convert the incoming flow file to FHIR resources
        String res = parseResponse(redcapResponse.get());
        FlowFile ret = session.clone(flowFile);
        //try {
            // output flow file
            ret = session.write(ret, new OutputStreamCallback() {
                @Override
                public void process(OutputStream out) throws IOException {
                    out.write(res.getBytes());
                }
            });
            session.transfer(ret, SUCCESS);
        session.transfer(flowFile, ORIGINAL);
        //} catch (Exception e) {
        //    session.transfer(flowFile, FAILED);
        //    getLogger().error(e.getMessage());
        //}
    }

    private String parseResponse(String raw) {
        ObjectMapper objectMapper = JsonMapper.builder()
                .configure(JsonWriteFeature.WRITE_NUMBERS_AS_STRINGS, true)
                .build();
        objectMapper.configOverride(String.class)
                .setSetterInfo(JsonSetter.Value.forValueNulls(Nulls.AS_EMPTY));
        List<RedcapResponse> responses;
        try {
            responses = objectMapper.readValue(raw, new TypeReference<List<RedcapResponse>>() {});
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        FHIRBundler bundler = new FHIRBundler();
        // System.out.println(bundler.bundleQuestionnaire("WAVES1"));
        FhirContext ctx = FhirContext.forR4();
        List<BundleBuilder> bundles = new ArrayList<>();
        BundleBuilder bb = new BundleBuilder(ctx)
                .setBundleField("type", "transaction");
        for (RedcapResponse r: responses) {
            r.setUniqueID("sl1-" + r.getPerson().getRecord_id());
            List<Resource> resources = bundler.bundle(r);
            resources.forEach( (x) -> bb.addTransactionUpdateEntry(x));
        }
        IParser parser = ctx.newJsonParser();
        return parser.encodeResourceToString(bb.getBundle());
    }
}
