export class TreePropertyEntry {
    id: string;
    value: string;
    selectable: boolean;

    level: number;
    position: number[];

    parents: TreePropertyEntry[];

    constructor() {
        this.parents = [];
    }
}

export class TreePropertyRelationship {
    id: string;
    sourceId: string;
    targetId: string;
}

export class TreePropertyDefinition {
    id: string;
    name: string;
    description: string;

    multiple: boolean;
    required: boolean;

    timestamp: Date;
    tenantId: string;

    entries: TreePropertyEntry[];
    relationships: TreePropertyRelationship[];
}
