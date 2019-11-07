
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
    param1: string;
    param2: string;
}

export enum RelationshipType {
    INHERITANCE = 'INHERITANCE',
    ASSOCIATION = 'ASSOCIATION'
}

export enum AssociationParameter {
    NONE = '', ONE = '1', ZEROONE = '0...1', ZEROSTAR = '0...*', ONESTAR = '1...*'
}

export namespace AssociationParameter {
    let reverseMode = new Map<string, AssociationParameter>();
   
    export function getAssociationParameterFromLabel(label: string): string { 
        Object.keys(AssociationParameter).forEach((param: AssociationParameter) => {
            const modeValue: string = AssociationParameter[<any>param];
            reverseMode.set(modeValue, param);
            
        });
        return reverseMode.get(label);
    }
    
}