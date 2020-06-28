export class EnumEntry {
    id: string;
    value: string;
    selectable: boolean;

    level: number;
    position: number[];

    parents: EnumEntry[];

    constructor() {
        this.parents = [];
    }
}

export class EnumRelationship {
    id: string;
    sourceEnumEntryId: string;
    targetEnumEntryId: string;
}

export class EnumDefinition {
    id: string;
    name: string;
    description: string;

    multiple: boolean;
    required: boolean;

    timestamp: Date;
    tenantId: string;

    enumEntries: EnumEntry[];
    enumRelationships: EnumRelationship[];
}
