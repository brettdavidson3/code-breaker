package com.lateralus.codebreaker.model.constant;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class WordConstants {

    public static List<String> getWordList(Difficulty difficulty) {
        switch (difficulty) {
            case Easy:
                return easyWordList();
            case Hard:
                return hardWordList();
            default:
                return normalWordList();
        }
    }

    private static List<String> easyWordList() {
        return newArrayList( // 163 words
                "blue", "milk", "hand", "stop", "fast", "brown", "name", "bake", "kite", "home", "five", "his", "nose", "green", "ask", "girl", "bird", "down", "glad", "baby", "nest", "sing", "made", "gate", "joke", "with", "egg", "ball", "shell", "tool", "book", "cry", "want", "two",
                "water", "store", "sister", "dream", "giant", "pretty", "orange", "draw", "chair", "first", "people", "clean", "sleep", "happy", "thank", "shine", "under", "fresh", "bring", "start", "luck", "carry", "taste", "crisp", "hobby", "cost", "easy", "field", "sting", "never", "brush", "third", "stove", "flew", "white",
                "school", "paint", "because", "space", "worry", "trouble", "hurry", "please", "climb", "smell", "should", "earth", "sugar", "catch", "early", "corner", "learn", "large", "whole", "family", "twice", "quick", "never", "almost", "point", "watch", "speak", "wrong", "scare", "place", "neighbor", "bright", "young", "coin", "proud", "build", "stairs", "choose", "teacher", "special", "afraid", "below", "enjoy", "quiet", "spring",
                "stretch", "wrong", "trust", "month", "shadow", "choice", "ocean", "noise", "radio", "crowd", "kitchen", "library", "judge", "knock", "follow", "chase", "enough", "angry", "remove", "usual", "vacation", "laugh", "satellite", "surprise", "alphabet", "knife", "forecast", "geography", "twelve", "ticket", "borrow", "knot", "scratch", "pencil", "central", "serve", "charge", "answer", "voyage", "annoy", "fudge", "width", "amount", "naughty", "gentle", "celery", "terrible", "position", "insist", "throat"
        );
    }

    private static List<String> normalWordList() {
        return newArrayList( // 229 words
                "disguise", "percent", "recommend", "pleasure", "official", "stomach", "exercise", "instruction", "restaurant", "success", "piano", "decide", "future", "shoulder", "distance", "familiar", "wrinkle", "disease", "journey", "doubt", "breathe", "equal", "discourage", "tournament", "achieve", "sweater", "attitude", "cereal", "similar", "reduce", "pleasant", "memorable", "jealous", "police", "repetition", "average", "nervous", "electric", "guitar", "wreathe", "celebration", "museum", "dangerous", "pronounce", "vacant", "initial", "easiest", "damage", "concern", "loathe",
                "continue", "adventure", "gracious", "drought", "disaster", "chemical", "surgery", "knowledge", "pursue", "serious", "orchestra", "believe", "legible", "establish", "cooperate", "obedience", "necessary", "identical", "coincidence", "vacuum", "grocery", "horrible", "prefer", "resemble", "scissors", "government", "ignore", "column", "science", "responsible", "character", "schedule", "rhythm", "conscience", "imaginary", "sentence", "companion", "fasten", "inquire", "author", "century", "scholar", "cousin", "whistle", "forfeit", "prediction", "possible", "silence", "choir", "evidence",
                "environment", "sympathy", "imagination", "recognize", "committee", "collaborate", "strength", "nonsense", "opportunity", "enormous", "accomplish", "disappear", "familiar", "beneath", "location", "innocent", "guarantee", "ancient", "receipt", "engineer", "tongue", "specific", "misery", "stingy", "collection", "ordinary", "headache", "foreign", "interior", "pierce", "civilization", "entrance", "variety", "congratulate", "happiness", "investigate", "astonish", "original", "personality", "estimate", "graduate", "dialogue", "inferior", "incredible", "stereo", "chemistry", "fatigue", "envelope", "vehicle", "function",
                "constitution", "generosity", "economic", "endeavor", "cylinder", "abbreviate", "picturesque", "molecular", "amateur", "unique", "mischievous", "approximate", "descendant", "epidemic", "communicate", "scheme", "substitute", "acknowledge", "gigantic", "contagious", "legislature", "rehearsal", "prosperous", "tragedy", "sanctuary", "necessary", "customary", "aerial", "category", "definite", "benevolent", "politician", "vocalize", "threaten", "priority", "intercept", "alternate", "ceramic", "essential", "sustain", "poisonous", "ingredient", "frequency", "desolate", "apology", "deceitful", "concept", "durable", "partial", "declaration",
                "auxiliary", "deprivation", "equation", "hideous", "nausea", "reservoir", "studious", "reputable", "antidote", "chronic", "benediction", "credential", "altercation", "grotesque", "poignant", "authentic", "convenience", "sabotage", "camouflage", "tedious", "expedient", "flexibility", "mesmerize", "stringent", "lustrous", "feasibility", "mediator", "tutorial", "perjury"
        );
    }

    private static List<String> hardWordList() {
        return newArrayList( // 246 words
                "abject", "abstract", "acrimonious", "acumen", "aesthetic", "aficionado", "affinity", "affluence", "alacrity", "alchemy", "ambiance", "analogous", "apathy", "arbitrary", "avatar", "benevolent", "bourgeois", "candid", "capricious", "chaparral", "clairvoyant", "chicanery", "cognizant", "cognoscente", "complacent", "compulsory", "conciliatory", "conjecture", "connoisseur", "conspicuous", "deleterious", "destitute", "deviate", "devious", "diligent", "dilettante", "discernible", "disdain", "disparage", "disseminate", "diverse", "dogmatic", "eccentric", "embryonic", "emulate", "enigma", "entomologist", "epiphany", "erudite", "expedite", "exonerate", "extricate", "facetious", "fallacious", "fortuitous", "futile", "gratuitous", "hackneyed", "homogeneous", "imbroglio", "impeccable", "impervious", "impetuous", "incessant", "incorrigible", "indifferent", "Indigenous", "indolent", "incognito", "industrious", "inevitable", "innocuous", "inquisitive", "insatiable", "insecticide", "insidious", "integrity", "iridescent", "jocular", "judicious", "juggernaut", "junta", "kindle", "kinetic", "larvae", "lethargy", "loquacious", "ludicrous", "lugubrious", "malaise", "meticulous", "mitigate", "morose", "mundane", "nemesis", "nihilism", "nirvana", "nocturnal", "novice", "obscure", "obsequious", "oscillate", "ostensible", "ostentatious", "palpable", "pandemonium", "paradigm", "peccadillo", "penitent", "pertinent", "plausible", "pollinate", "precipitous", "precocious", "prerogative", "prevaricate", "proboscis", "propensity", "provocative", "quarantine", "querulous", "quiescent", "raconteur", "ramification", "rapacious", "recant", "recalcitrant", "reclusive", "rectify", "redolent", "redundant", "refutable", "regressive", "relegate", "relinquish", "remonstrate", "reparation", "replenish", "repose", "repudiate", "requisite", "resilient", "resolute", "reticent", "reverence", "rigorous", "rudimentary", "sanguine", "scrutinize", "sedentary", "soporific", "spontaneous", "squander", "stringent", "subterranean", "succinct", "superficial", "terse", "theoretical", "trepidation", "truncate", "ubiquitous", "unctuous", "unobtrusive", "unscrupulous", "vacuous", "vignette", "vindictive", "virtuoso", "virulent", "wanton", "xenophile", "zenith",
                "affectation", "alkaline", "apocalypse", "apostate", "auspices", "autonomy", "boreal", "brogue", "carnage", "chatelaine", "clathrate", "cognitive", "cryptic", "denigrate", "dilettante", "disquisition", "draconian", "efficacy", "elan", "expatriate", "facile", "feckless", "fez", "fiasco", "fissionable", "founder", "gyre", "hegemony", "hermeneutics", "insolvency", "indigenous", "inhibitory", "inscrutable", "insurgency", "interstices", "inviolable", "lattice", "litigious", "littoral", "malleable", "manse", "myriad", "oblique", "oxymoron", "pelagic", "Pentecostal", "philander", "pietism", "pique", "placebo", "polity", "postmodern", "pretext", "promulgate", "pseudonym", "reactionary", "relegate", "renounce", "resonate", "revitalize", "servile", "sophistry", "stymie", "substrate", "template", "wrought"
        );
    }
}
