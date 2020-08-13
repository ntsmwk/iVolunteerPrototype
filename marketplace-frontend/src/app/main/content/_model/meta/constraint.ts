import { PropertyType } from './property/property';

export class PropertyConstraint<T> {
    id: string;
    constraintType: ConstraintType;

    value: T;
    propertyType: PropertyType;

    message?: string;
}

export enum ConstraintType {
    MIN = 'min', MAX = 'max', MIN_LENGTH = 'min_length', MAX_LENGTH = 'max_length', PATTERN = 'pattern'
}

export namespace ConstraintType {
    const labelAssignment = [
        { type: ConstraintType.MIN, label: 'Minimum' },
        { type: ConstraintType.MAX, label: 'maximum' },
        { type: ConstraintType.MIN_LENGTH, label: 'Mindestlänge' },
        { type: ConstraintType.MAX_LENGTH, label: 'Höchstlänge' },
        { type: ConstraintType.PATTERN, label: 'Regex Pattern' },
    ];

    export function getLabelForConstraintType(constraintType: ConstraintType) {
        const assignment = labelAssignment.find(la => la.type === constraintType);
        if (assignment === undefined) {
            return constraintType;
        } else {
            return assignment.label;
        }
    }
}
