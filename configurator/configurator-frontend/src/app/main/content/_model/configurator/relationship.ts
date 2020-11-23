
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
}

export enum AssociationCardinality {
    NONE = '', ONE = 'ONE', N = 'N'
}

export namespace AssociationCardinality {
    export function getLabelForAssociationCardinality(cardinality: AssociationCardinality) {
        return cardinality === AssociationCardinality.ONE ? '1' :
            cardinality === AssociationCardinality.N ? 'N' : '';
    }

}
