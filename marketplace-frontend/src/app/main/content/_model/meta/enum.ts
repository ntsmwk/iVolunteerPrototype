export class EnumEntry {
    id: string;
    value: string;
    selectable: boolean;

    level: number;
    position: number[];
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

    timestamp: Date;
    tenantId: string;

    enumEntries: EnumEntry[];
    enumRelationships: EnumRelationship[];
}
