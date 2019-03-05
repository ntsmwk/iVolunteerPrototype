
export class Property<T> {
    id: string;
    name: string;
    value: T;

    values?: ListValue<T>[];

    defaultValue: T;
    legalValues?: ListValue<T>[];
    rules?: Rule[];

    kind: PropertyKind;
}

export class ReturnProperty extends Property<string> {
    
}

export class ListValue<T> {
    id: string;
    value: string;

    constructor (id: string, value: string) {
        this.id = id;
        this.value = value;
    }
    
}

export class PropertyListItem {
    id: string;
    name: string;
    value: any;
    kind: PropertyKey;
}

export class Rule {
    id: string;
    kind: RuleKind;
    value?: number;
    data?: string;
    message: string;
}


export enum PropertyKind {
    TEXT = "TEXT", LONG_TEXT = "LONG_TEXT", WHOLE_NUMBER = "WHOLE_NUMBER", FLOAT_NUMBER = "FLOAT_NUMBER", BOOL = "BOOL", 
    DATE = "DATE", COMPETENCE = "COMPETENCE", LIST = "LIST", MULTIPLE="MULTIPLE"
}

export enum RuleKind {
    REQUIRED = "REQUIRED", REQUIRED_TRUE = "REQUIRED_TRUE", REGEX_PATTERN = "REGEX_PATTERN", MAX_LENGTH = "MAX_LENGTH", 
    MIN_LENGTH = "MIN_LENGTH", MAX = "MAX", MIN = "MIN"
}
