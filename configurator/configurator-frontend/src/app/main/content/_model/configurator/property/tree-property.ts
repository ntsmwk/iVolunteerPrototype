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

    constructor(name: string, description: string, multiple: boolean, required: boolean, requiredMessage: string) {
        this.name = name;
        this.description = description;
        this.multiple = multiple;
        this.required = required;
        this.requiredMessage = requiredMessage;
    }
}
