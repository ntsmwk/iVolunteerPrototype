
export class Relationship {
    id: string;

    classId1: string;
    classId2: string;
    
    relationshipType: RelationshipType;
}

export class Inheritance extends Relationship {
    superClassId: string;
}

export class Association extends Relationship {
    param1: AssociationParameter;
    param2: AssociationParameter;
}

export enum RelationshipType {
    INHERITANCE = 'INHERITANCE',
    ASSOCIATION = 'ASSOCIATION'
}

export enum AssociationParameter {
    NONE ='', ONE = '1', ZEROONE = '0...1', ZEROSTAR = '0...*', ONESTAR = '1...*'
}