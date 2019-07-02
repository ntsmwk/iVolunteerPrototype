import { Property } from "./properties/Property";

export class UserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;

    kind: string;

    properties?: Property<any>[];    
    templates?: UserDefinedTaskTemplate[];
}

export class SingleUserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;
    properties: Property<any>[];
}

export class MultiUserDefinedTaskTemplate {
    id: string;
    name: string;
    description: string;
    templates: UserDefinedTaskTemplate[];
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