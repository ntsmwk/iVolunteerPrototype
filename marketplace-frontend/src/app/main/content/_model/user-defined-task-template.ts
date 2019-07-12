import { Property } from "./configurables/Property";

export class UserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;

    kind: string;

    properties?: Property<any>[];    
    templates?: UserDefinedTaskTemplate[];

    order: number;
}

export class SingleUserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;
    properties: Property<any>[];

    order: number;
}

export class MultiUserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;
    templates: UserDefinedTaskTemplate[];

    order: number;
}

export class UserDefinedTaskTemplateStub {
    id: string;
    name: string;
    description: string;
    kind: string;
}

// export class UserDefinedTaskTemplateIdNameStub {
//     id: string;
//     name: string;

//     templates?: UserDefinedSubTaskTemplateIdNameStub[];
//     properties?: PropertyIdNameStub[];
// }

// export class UserDefinedSubTaskTemplateIdNameStub {
//     id: string;
//     name: string;
// }