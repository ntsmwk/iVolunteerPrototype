import { isNullOrUndefined } from "util";
import { ConfigurableObject } from "./Configurable";

export class Property<T> extends ConfigurableObject{
    id: string;
    name: string;

    values: ListEntry<T>[];

    order: number;

    defaultValues?: ListEntry<T>[];
    legalValues?: ListEntry<T>[];
    rules?: Rule[];

    custom: boolean;

    kind: PropertyKind;

    properties?: Property<any>[];



    public static getValue(property: Property<any>): any {
        if (!isNullOrUndefined(property.values) && property.values.length >= 1) {
            
            // if (property.kind == 'DATE') {return new Date(property.values[0].value).toLocaleString();}

            return property.values[0].value;
        } else {
            // return '';
            return null;
        }
    }

    public static getDefaultValue(property: Property<any>): any {
        if (!isNullOrUndefined(property.defaultValues) && property.defaultValues.length >= 1) {

            // if (property.kind == 'DATE') {return new Date(property.values[0].value).toLocaleString();}


            return property.defaultValues[0].value;
        } else {
            // return '';
            return null;
        }
    }

}

export class MultiPropertyRet {
    id: string;
    name: string;
    order: number;
    kind: PropertyKind;
    rules?: Rule[];
    propertyIDs: string[];
}

export class ListEntry<T> {
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
    values: ListEntry<any>[];
    defaultValues: ListEntry<any>[];
    kind: PropertyKey;
    order: number;
    custom: boolean

    //has to be set from frontend
    show: boolean;

}

export class PropertyParentItem {
    id: string;
    name: string;
}

export class PropertyParentSubTemplate extends PropertyParentItem{};
export class PropertyParentTemplate extends PropertyParentItem{};

export class Rule {
    id: string;
    kind: RuleKind;
    value?: number;
    data?: string;
    
    key?: string;
    keyOther?: string;
    message: string;

}

export enum PropertyKind {
    TEXT = "TEXT", LONG_TEXT = "LONG_TEXT", WHOLE_NUMBER = "WHOLE_NUMBER", FLOAT_NUMBER = "FLOAT_NUMBER", BOOL = "BOOL", 
    DATE = "DATE", COMPETENCE = "COMPETENCE", LIST = "LIST", MAP = "MAP", GRAPH="GRAPH", MULTI="MULTI"
}

export enum RuleKind {
    REQUIRED = "REQUIRED", REQUIRED_TRUE = "REQUIRED_TRUE", REGEX_PATTERN = "REGEX_PATTERN", MAX_LENGTH = "MAX_LENGTH", 
    MIN_LENGTH = "MIN_LENGTH", MAX = "MAX", MIN = "MIN", REQUIRED_OTHER = "REQUIRED_OTHER", MIN_OTHER = "MIN_OTHER", 
    MAX_OTHER = "MAX_OTHER"
}
