import { isNullOrUndefined } from 'util';
import { PropertyConstraint } from './constraint';

export class PropertyDefinition<T> {
    id: string;
    name: string;
    tenantId: string;

    custom: boolean;
    multiple: boolean;

    type: PropertyType;

    allowedValues: T[];

    unit: string;


    required: boolean;
    propertyConstraints: PropertyConstraint<T>[];

    visible: boolean;
    tabId: number;
}

export class ClassProperty<T> {
    id: string;
    name: string;

    exportLabel: string;

    defaultValues: T[];
    allowedValues: T[];

    unit: string;

    multiple: boolean;

    type: PropertyType;

    immutable: boolean;
    updateable: boolean;
    required: boolean;

    position: number;

    visible: boolean;
    tabId: number;

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

    unit: string;

    type: PropertyType;

    required: boolean;

    position: number;

    visible: boolean;
    tabId: number;

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
        this.visible = classProperty.visible;
        this.tabId = classProperty.tabId;
    }

}


export enum PropertyType {
    TEXT = 'TEXT', LONG_TEXT = 'LONG_TEXT', WHOLE_NUMBER = 'WHOLE_NUMBER', FLOAT_NUMBER = 'FLOAT_NUMBER', BOOL = 'BOOL',
    DATE = 'DATE', COMPETENCE = 'COMPETENCE', LIST = 'LIST', ENUM = 'ENUM', MAP = 'MAP', GRAPH = 'GRAPH', MULTI = 'MULTI',
    TUPLE = 'TUPLE',
}

export namespace PropertyType {
    export function getLabelForPropertyType(propertyType: string) {
        switch (propertyType) {
            case PropertyType.TEXT: return 'Text';
            case PropertyType.LONG_TEXT: return 'Text Field';
            case PropertyType.WHOLE_NUMBER: return 'Number';
            case PropertyType.FLOAT_NUMBER: return 'Float';
            case PropertyType.BOOL: return 'Boolean';
            case PropertyType.DATE: return 'Date';
            case PropertyType.LIST: return 'List';
            case PropertyType.ENUM: return 'Enum';
            case PropertyType.TUPLE: return 'Tuple';

        }
    }
}


export class PropertyItem {
    id: string;
    name: string;
    type: PropertyType;
}


export class PropertyParentSubTemplate { id: string; name: string; }
export class PropertyParentTemplate { id: string; name: string; }

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
    REQUIRED = 'REQUIRED', REQUIRED_TRUE = 'REQUIRED_TRUE', REGEX_PATTERN = 'REGEX_PATTERN', MAX_LENGTH = 'MAX_LENGTH',
    MIN_LENGTH = 'MIN_LENGTH', MAX = 'MAX', MIN = 'MIN', REQUIRED_OTHER = 'REQUIRED_OTHER', MIN_OTHER = 'MIN_OTHER',
    MAX_OTHER = 'MAX_OTHER'
}
