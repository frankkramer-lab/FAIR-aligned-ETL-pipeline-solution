package de.uni_a.nifi.processors.myCustomProcessor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RedcapResponse {

    @JsonUnwrapped
    private Person person;
    @JsonUnwrapped
    private Anamnese anamnese;
    @JsonUnwrapped
    private Question question;
    @JsonIgnore
    private String uniqueID;
}
