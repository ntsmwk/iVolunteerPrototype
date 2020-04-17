
export class Relationship {
    id: string;

    source: string;
    target: string;

    relationshipType: RelationshipType;

    hidden: boolean;
}

export class Inheritance extends Relationship {
    superClassId: string;
}

export class Association extends Relationship {
    sourceCardinality: string;
    targetCardinality: string;
}

export enum RelationshipType {
    INHERITANCE = 'INHERITANCE',
    ASSOCIATION = 'ASSOCIATION',
    AGGREGATION = 'AGGREGATION',
    COMPOSITION = 'COMPOSITION'
}

export namespace RelationshipType {
    export function getLabelFromRelationshipType(relationshipType: RelationshipType) {
        switch (relationshipType) {
            case RelationshipType.INHERITANCE: return 'Vererbung';
            case RelationshipType.ASSOCIATION: return 'Assoziation';
            case RelationshipType.COMPOSITION: return 'Komposition';
            case RelationshipType.AGGREGATION: return 'Aggreation';
        }
    }

    export function getImagePathFromRelationshipType(relationshipType: RelationshipType) {
        return "TODO";
    }
}

export enum AssociationCardinality {
    NONE = '', ONE = '1', ZEROONE = '0...1', ZEROSTAR = '0...*', ONESTAR = '1...*'
}

export namespace AssociationCardinality {
    let reverseMode = new Map<string, AssociationCardinality>();

    export function getAssociationParameterFromLabel(label: string): string {
        Object.keys(AssociationCardinality).forEach((param: AssociationCardinality) => {
            const modeValue: string = AssociationCardinality[<any>param];
            reverseMode.set(modeValue, param);

        });
        return reverseMode.get(label);
    }

}