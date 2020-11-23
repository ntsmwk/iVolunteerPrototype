import { isNullOrUndefined } from 'util';
import { PropertyConstraint } from '../constraint';
import { TreePropertyEntry } from './tree-property';

// export class FlatPropertyDefinition<T> {
//     id: string;
//     name: string;
//     description: string;

//     tenantId: string;

//     custom: boolean;
//     multiple: boolean;

//     type: PropertyType;

//     allowedValues: T[];

//     unit: string;

//     required: boolean;
//     requiredMessage: string;
//     propertyConstraints: PropertyConstraint<T>[];

//     timestamp: Date;
//     visible: boolean;
//     tabId: number;

//     computed: boolean;

// }

export class ClassProperty<T> {
    id: string;
    name: string;

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

    computed: boolean;


    public static getDefaultValue(templateProperty: ClassProperty<any>): any {
        if (!isNullOrUndefined(templateProperty.defaultValues) && templateProperty.defaultValues.length >= 1) {
            return templateProperty.defaultValues[0];
        } else {
            return null;
        }
    }
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

    computed: boolean;

    public static getValue(propertyInstance: PropertyInstance<any>): any {
        if (!isNullOrUndefined(propertyInstance.values) && propertyInstance.values.length >= 1) {
            return propertyInstance.values[0];
        } else {
            return null;
        }
    }

    // constructor(classProperty: ClassProperty<T>, values?: T[]) {
    //     this.id = classProperty.id;
    //     this.name = classProperty.name;
    //     this.values = values;
    //     this.allowedValues = classProperty.allowedValues;
    //     this.type = classProperty.type;
    //     this.required = classProperty.required;
    //     this.position = classProperty.position;
    //     this.propertyConstraints = classProperty.propertyConstraints;
    //     this.visible = classProperty.visible;
    //     this.tabId = classProperty.tabId;

    //     if (classProperty.type === PropertyType.TREE) {

    //         const rootValue = this.values[0] as unknown as TreePropertyEntry;

    //         if (isNullOrUndefined(rootValue)) {
    //             return;
    //         }

    //         let i = (classProperty as ClassProperty<unknown> as ClassProperty<TreePropertyEntry>)
    //             .allowedValues.findIndex(a => a.id === rootValue.id);
    //         let currentLevel = rootValue.level;

    //         for (i; i >= 0; i--) {
    //             const currentAllowedValue = this.allowedValues[i] as unknown as TreePropertyEntry;
    //             if (currentAllowedValue.level < currentLevel) {
    //                 // this.values.push(classProperty.allowedValues[i]);
    //                 rootValue.parents.push(currentAllowedValue);
    //                 currentLevel--;
    //             }
    //         }
    //     }
    // }
}


export enum PropertyType {
    TEXT = 'TEXT', LONG_TEXT = 'LONG_TEXT', WHOLE_NUMBER = 'WHOLE_NUMBER', FLOAT_NUMBER = 'FLOAT_NUMBER', BOOL = 'BOOL',
    DATE = 'DATE', COMPETENCE = 'COMPETENCE', LIST = 'LIST', MAP = 'MAP', GRAPH = 'GRAPH', MULTI = 'MULTI',
    TUPLE = 'TUPLE', TREE = 'TREE', LOCATION = 'LOCATION',
}

export namespace PropertyType {
    const labelAssignment = [
        { type: PropertyType.TEXT, label: 'Text' },
        { type: PropertyType.LONG_TEXT, label: 'Textfeld' },
        { type: PropertyType.WHOLE_NUMBER, label: 'Ganze Zahl' },
        { type: PropertyType.FLOAT_NUMBER, label: 'Kommazahl' },
        { type: PropertyType.BOOL, label: 'Boolean' },
        { type: PropertyType.DATE, label: 'Datum' },
        { type: PropertyType.TUPLE, label: 'Tupel' },
        { type: PropertyType.TREE, label: 'Tree-Property' },
        { type: PropertyType.LOCATION, label: 'Ort' },


    ];


    export function getLabelForPropertyType(propertyType: string) {
        const assignment = labelAssignment.find(la => la.type === propertyType);
        if (assignment === undefined) {
            return propertyType;
        } else {
            return assignment.label;
        }
    }
}


export class PropertyItem {
    id: string;
    name: string;
    type: PropertyType;
}


// export class PropertyParentSubTemplate { id: string; name: string; }
// export class PropertyParentTemplate { id: string; name: string; }

// export class Rule {
//     id: string;
//     kind: RuleKind;
//     value?: number;
//     data?: string;

//     key?: string;
//     keyOther?: string;
//     message: string;
// }


// export enum RuleKind {
//     REQUIRED = 'REQUIRED', REQUIRED_TRUE = 'REQUIRED_TRUE', REGEX_PATTERN = 'REGEX_PATTERN', MAX_LENGTH = 'MAX_LENGTH',
//     MIN_LENGTH = 'MIN_LENGTH', MAX = 'MAX', MIN = 'MIN', REQUIRED_OTHER = 'REQUIRED_OTHER', MIN_OTHER = 'MIN_OTHER',
//     MAX_OTHER = 'MAX_OTHER'
// }
