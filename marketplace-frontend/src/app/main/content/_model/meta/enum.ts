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