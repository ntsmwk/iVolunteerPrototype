export class EnumEntry {
    id: string;
    value: string;
    selectable: boolean;
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

    enumEntries: EnumEntry[];
    enumRelstionships: EnumRelationship[];
}