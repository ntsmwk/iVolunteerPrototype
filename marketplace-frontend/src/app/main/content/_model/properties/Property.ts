import { isNullOrUndefined } from "util";

export class Property<T> {
    id: string;
    name: string;
    // value: T;

    values: ListValue<T>[];

    order: number;

    defaultValue: T;
    legalValues?: ListValue<T>[];
    rules?: Rule[];

    kind: PropertyKind;

    properties?: Property<any>[];

    public static getValue(property: Property<any>): any {
        if (!isNullOrUndefined(property.values) && property.values.length >= 1) {
            return property.values[0].value;
        } else {
            return '';
        }
    }

    public static getId(property: Property<any>): string {
        if (!isNullOrUndefined(property.values) && property.values.length >= 1) {
            return property.values[0].id;
        } else {
            return '';
        }
    }

}

export class ListValue<T> {
    id: string;
    value: T;

    constructor (id: string, value: T) {
        this.id = id;
        this.value = value;
    }  
}

export class PropertyListItem {
    id: string;
    name: string;
    values: ListValue<any>[];
    kind: PropertyKey;
    order: number;


    public static getValue(propertyListItem: PropertyListItem): any {
        if (!isNullOrUndefined(propertyListItem.values) && propertyListItem.values.length >= 1) {
            return propertyListItem.values[0].value;
        } else {
            return undefined;
        }
        
    }
}

export class Rule {
    id: string;
    kind: RuleKind;
    value?: number;
    data?: string;
    message: string;

    copyRule(rule: Rule) {
        this.id = rule.id;
        this.kind = rule.kind;
        this.value = rule.value;
        this.data = rule.data;
        this.message = rule.message;
    }
}

export enum PropertyKind {
    TEXT = "TEXT", LONG_TEXT = "LONG_TEXT", WHOLE_NUMBER = "WHOLE_NUMBER", FLOAT_NUMBER = "FLOAT_NUMBER", BOOL = "BOOL", 
    DATE = "DATE", COMPETENCE = "COMPETENCE", LIST = "LIST", MULTIPLE="MULTIPLE"
}

export enum RuleKind {
    REQUIRED = "REQUIRED", REQUIRED_TRUE = "REQUIRED_TRUE", REGEX_PATTERN = "REGEX_PATTERN", MAX_LENGTH = "MAX_LENGTH", 
    MIN_LENGTH = "MIN_LENGTH", MAX = "MAX", MIN = "MIN"
}
