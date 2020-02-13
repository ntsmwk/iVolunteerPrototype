import { isNullOrUndefined } from "util";
import { PropertyConstraint } from "./Constraint";

export class PropertyDefinition<T> {
    id: string;
    name: string;

    custom: boolean;
    multiple: boolean;

    type: PropertyType;

    allowedValues: T[];

    required: boolean;
    propertyConstraints: PropertyConstraint<T>[];
}

export class ClassProperty<T> {
    id: string;
    name: string;

    defaultValues: T[];
    allowedValues: T[];

    multiple: boolean;

    type: PropertyType;

    immutable: boolean;
    updateable: boolean;
    required: boolean;

    position: number;

    propertyConstraints: PropertyConstraint<T>[];

    public static getDefaultValue(templateProperty: TemplateProperty<any>): any {
        if (!isNullOrUndefined(templateProperty.defaultValues) && templateProperty.defaultValues.length >= 1) {
            return templateProperty.defaultValues[0];
        } else {
            return null;
        }
    }
}

export class EnumReference {
    enumClassId: string;
    value?: string;

    constructor(enumClassId: string) {
        this.enumClassId = enumClassId;
    }

}

export class TemplateProperty<T> extends ClassProperty<T>{
}

export class PropertyInstance<T> {
    id: string;
    name: string;

    values: T[];
    allowedValues: T[];

    type: PropertyType;

    required: boolean;

    position: number;

    propertyConstraints: PropertyConstraint<T>[];

    public static getValue(propertyInstance: PropertyInstance<any>): any {
        if (!isNullOrUndefined(propertyInstance.values) && propertyInstance.values.length >= 1) {
            return propertyInstance.values[0];
        } else {
            return null;
        }
    }

    constructor(classProperty: ClassProperty<T>, values?: T[]) {
        this.id = classProperty.id;
        this.name = classProperty.name;
        this.values = values;
        this.allowedValues = classProperty.allowedValues;
        this.type = classProperty.type;
        this.required = classProperty.required;
        this.position = classProperty.position;
        this.propertyConstraints = classProperty.propertyConstraints;
    }

}


export enum PropertyType {
    TEXT = "TEXT", LONG_TEXT = "LONG_TEXT", WHOLE_NUMBER = "WHOLE_NUMBER", FLOAT_NUMBER = "FLOAT_NUMBER", BOOL = "BOOL",
    DATE = "DATE", COMPETENCE = "COMPETENCE", LIST = "LIST", ENUM = "ENUM", MAP = "MAP", GRAPH = "GRAPH", MULTI = "MULTI"
}


export class PropertyItem {
    id: string;
    name: string;
}

export class PropertyParentSubTemplate extends PropertyItem { };
export class PropertyParentTemplate extends PropertyItem { };

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
