
export class Relationship {
    id: string;

    source: string;
    target: string;

    relationshipType: RelationshipType;

    sourceCardinality: AssociationCardinality;
    targetCardinality: AssociationCardinality;

    hidden: boolean;
}

export enum RelationshipType {
    INHERITANCE = 'INHERITANCE',
    ASSOCIATION = 'ASSOCIATION',
    AGGREGATION = 'AGGREGATION', //left in to ensure compatibility with old configs
}

export namespace RelationshipType {
    export function getLabelFromRelationshipType(relationshipType: RelationshipType) {
        switch (relationshipType) {
            case RelationshipType.INHERITANCE: return 'Vererbung';
            case RelationshipType.ASSOCIATION: return 'Assoziation';
            case RelationshipType.AGGREGATION: return 'Aggreation';
        }
    }

    // export function getIconPathForRelationshipType(relationshipType: RelationshipType) {
    //     switch (relationshipType) {
    //         case RelationshipType.INHERITANCE: return 'Vererbung';
    //         case RelationshipType.ASSOCIATION: return 'Assoziation';
    //         case RelationshipType.COMPOSITION: return 'Komposition';
    //         case RelationshipType.AGGREGATION: return 'Aggreation';
    //     };
    // }
}

export enum AssociationCardinality {
    // NONE = '', ONE = '1', ZEROONE = '0...1', ZEROSTAR = '0...*', ONESTAR = '1...*'
    NONE = '', ONE = 'ONE', N = 'N'
}

export namespace AssociationCardinality {
    // const reverseMode = new Map<string, AssociationCardinality>();

    // export function getAssociationParameterFromLabel(label: string): string {
    //     Object.keys(AssociationCardinality).forEach((param: AssociationCardinality) => {
    //         const modeValue: string = AssociationCardinality[<any>param];
    //         reverseMode.set(modeValue, param);

    //     });
    //     return reverseMode.get(label);
    // }

    export function getLabelForAssociationCardinality(cardinality: AssociationCardinality) {
        return cardinality === AssociationCardinality.ONE ? '1' :
            cardinality === AssociationCardinality.N ? 'N' : '';
    }

}
