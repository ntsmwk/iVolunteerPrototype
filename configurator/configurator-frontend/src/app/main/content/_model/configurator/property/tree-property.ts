import { PropertyType } from './property';

export class TreePropertyEntry {
    id: string;
    value: string;
    selectable: boolean;

    custom: boolean;

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

    requiredMessage: string;

    timestamp: Date;
    tenantId: string;

    entries: TreePropertyEntry[];
    relationships: TreePropertyRelationship[];

    custom: boolean;

    constructor(init?: Partial<TreePropertyDefinition>) {
        Object.assign(this, init);
    }
}
