
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
}


export enum PropertyKind {
    TEXT = "TEXT", LONG_TEXT = "LONG_TEXT", WHOLE_NUMBER = "WHOLE_NUMBER", FLOAT_NUMBER = "FLOAT_NUMBER", BOOL = "BOOL", 
    DATE = "DATE", COMPETENCE = "COMPETENCE", LIST = "LIST"
}

export enum RuleKind {
    MAX = "max", MIN= "min", GREATER = "gtr", GREATER_OR_EQUAL = "gte", LESS = "lss", LESS_OR_EQUAL = "leq", EQUAL = "eq"
}
