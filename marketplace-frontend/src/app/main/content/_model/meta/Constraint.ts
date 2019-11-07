import { PropertyType } from "./Property";

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