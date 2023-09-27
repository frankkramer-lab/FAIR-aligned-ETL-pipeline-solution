package de.uni_a.nifi.processors.myCustomProcessor;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class PersonValues {
    static final String loincUrl = "http://loinc.org";
    static final String weightUnit = "kg";
    static final String heightUnit = "cm";
    static final Map<String, List<String>> physicalActivityMap = Map.ofEntries(
            entry("68130003", List.of("http://snomed.info/sct", "Physical activity")),
            entry("4751000", List.of("http://snomed.info/sct", "Leisure physical activity")),
            entry("7934008", List.of("http://snomed.info/sct", "Passive physical exercise")),
            entry("14468000", List.of("http://snomed.info/sct", "Sports activity")),
            entry("35360009", List.of("http://snomed.info/sct", "Isometric physical exercise")),
            entry("61686008", List.of("http://snomed.info/sct", "Physical exercise")),
            entry("86047003", List.of("http://snomed.info/sct", "Active physical exercise")),
            entry("9011000175102", List.of("http://snomed.info/sct", "Physical activity barrier"))
    );

    static final Map<Integer, List<String>> smokerMap = Map.ofEntries(
            entry(0, List.of("nieraucher", "https://ig.fhir.de/basisprofile-de/stable-stu3/Raucherstatus.html", "Nieraucher")),
            entry(1, List.of("230059006", "http://www.snomed.info/sct", "Trivial cigarette smoker (less than one cigarette/day) (finding)")),
            entry(2, List.of("230060001", "http://www.snomed.info/sct", "Light cigarette smoker (1-9 cigs/day)")),
            entry(3, List.of("230062009", "http://www.snomed.info/sct", "Moderate cigarette smoker (10-19 cigs/day)")),
            entry(4, List.of("230063004", "http://www.snomed.info/sct", "Heavy cigarette smoker (20-39 cigs/day)")),
            entry(5, List.of("230064005", "http://www.snomed.info/sct", "Very heavy cigarette smoker (40+ cigs/day)"))
    );

    static final Map<String, List<String>> yesNoUnkownMap = Map.ofEntries(
            entry("1", List.of("Y", "http://terminology.hl7.org/CodeSystem/v2-0136", "Yes")),
            entry("2", List.of("N", "http://terminology.hl7.org/CodeSystem/v2-0136", "No")),
            entry("3", List.of("asked-unknown", "http://terminology.hl7.org/CodeSystem/data-absent-reason", "Don't know"))
    );

    static final Map<String, List<String>> tumorDiseaseMap = Map.ofEntries(
            entry("Gebärmutterhalskrebs",List.of(" 363354003", "http://snomed.info/sct", "Malignant tumour of cervix")),
            entry("Gebärmutterkrebs", List.of(" 371973000", "http://snomed.info/sct", "Malignant neoplasm of uterus (disorder)")),
            entry("Melanom", List.of("372244006", "http://snomed.info/sct", "Malignant melanoma (disorder)")),
            entry("Dickdarmkrebs", List.of("363406005", "http://snomed.info/sct", "Malignant tumour of colon")),
            entry("Magenkrebs", List.of("372014001", "http://snomed.info/sct", "Primary malignant neoplasm of stomach (disorder)")),
            entry("Schilddrüsenkrebs", List.of("94098005", "http://snomed.info/sct", "Primary malignant neoplasm of thyroid gland")),
            entry("Leukämie", List.of("188725004", "http://snomed.info/sct", "Lymphatic leukaemia")),
            entry("Lymphom", List.of("118600007", "http://snomed.info/sct", "Malignant lymphoma (disorder"))
    );

    static final Map<String, List<String>> federalStateMap = Map.ofEntries(
            entry("1", List.of("DE-BW", "Baden-Württemberg")),
            entry("2", List.of("DE-BY", "Bayern")),
            entry("3", List.of("DE-BE", "Berlin")),
            entry("4", List.of("DE-BB", "Brandenburg")),
            entry("5", List.of("DE-HB", "Bremen")),
            entry("6", List.of("DE-HH", "Hamburg")),
            entry("7", List.of("DE-HE", "Hessen")),
            entry("8", List.of("DE-MV", "Mecklenburg-Vorpommern")),
            entry("9", List.of("DE-NI", "Niedersachsen")),
            entry("10", List.of("DE-NW", "Nordrhein-Westfalen")),
            entry("11", List.of("DE-RP", "Rheinland-Pfalz")),
            entry("12", List.of("DE-SL", "Saarland")),
            entry("13", List.of("DE-SN", "Sachsen")),
            entry("14", List.of("DE-ST", "Sachsen-Anhalt")),
            entry("15", List.of("DE-SH", "Schleswig-Holstein")),
            entry("16", List.of("DE-TH", "Thüringen"))
    );

    static final Map<String, List<String>> currentConditionMap = Map.ofEntries(
            entry("1", List.of("SG", "sehr gut")),
            entry("2", List.of("G", "gut")),
            entry("3", List.of("B", "befriedigend")),
            entry("4", List.of("A", "ausreichend")),
            entry("5", List.of("M", "Mangelhaft"))
    );
    static final List<String> excludedMaritalStatus = List.of("A, I, L, C, P, T, UNK");
    static final Map<String, List<String>> genderMap = Map.ofEntries(
            entry("M", List.of("male", "maennlich")),
            entry("W", List.of("female", "weiblich")),
            entry("D", List.of("other", "divers")),
            entry("X", List.of("unknown", "unbestimmt"))
    );
}
