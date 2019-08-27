import { isNullOrUndefined } from "util";
import { PropertyConstraint } from "./Constraint";

export class PropertyDefinition<T> {
    id: string;
    name: string;

    custom: boolean;
    type: PropertyType;

    propertyConstraints: PropertyConstraint<any>[];
}

export class ClassProperty<T> {
    id: string;
    name: string;
    
    defaultValues: any[];
    allowedValues: any[];

    type: PropertyType;

    immutable: boolean;
    updateable: boolean;
    required: boolean;

    position: number;

    propertyConstraints: PropertyConstraint<any>[];
}

export class PropertyInstance<T> {
    id: string;
    name: string;
    
    values: any[];
    allowedValues: any[];

    type: PropertyType;

    immutable: boolean;
    updateable: boolean;
    required: boolean;

    position: number;

    propertyConstraints: PropertyConstraint<any>[];
}


export enum PropertyType {
    TEXT = "TEXT", LONG_TEXT = "LONG_TEXT", WHOLE_NUMBER = "WHOLE_NUMBER", FLOAT_NUMBER = "FLOAT_NUMBER", BOOL = "BOOL", 
    DATE = "DATE", COMPETENCE = "COMPETENCE", LIST = "LIST", MAP = "MAP", GRAPH="GRAPH", MULTI="MULTI"
}


//Legacy Stuff

export class Property<T> {
    id: string;
    name: string;

    values: ListEntry<T>[];

    order: number;

    defaultValues?: ListEntry<T>[];
    legalValues?: ListEntry<T>[];
    rules?: Rule[];

    custom: boolean;
    show: boolean; //is set from frontend

    type: PropertyType;

    properties?: Property<any>[];



    public static getValue(property: Property<any>): any {
        if (!isNullOrUndefined(property.values) && property.values.length >= 1) {
            return property.values[0].value;
        } else {
            return null;
        }
    }

    public static getDefaultValue(property: Property<any>): any {
        if (!isNullOrUndefined(property.defaultValues) && property.defaultValues.length >= 1) {
            return property.defaultValues[0].value;
        } else {
            return null;
        }
    }
}

export class ListEntry<T> {
    id: string;
    value: T;

    constructor (id: string, value: T) {
        this.id = id;
        this.value = value;
    }  
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


export enum RuleKind {
    REQUIRED = "REQUIRED", REQUIRED_TRUE = "REQUIRED_TRUE", REGEX_PATTERN = "REGEX_PATTERN", MAX_LENGTH = "MAX_LENGTH", 
    MIN_LENGTH = "MIN_LENGTH", MAX = "MAX", MIN = "MIN", REQUIRED_OTHER = "REQUIRED_OTHER", MIN_OTHER = "MIN_OTHER", 
    MAX_OTHER = "MAX_OTHER"
}
