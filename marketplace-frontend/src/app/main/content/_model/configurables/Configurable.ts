import { Property } from "./Property";

export class ConfigurableObject {
    id: string;
    configurableType: string;

}


export class MatchingRule extends ConfigurableObject {

}


export class ConfigurableClass {
    id: string;
    name: string;
    // configurables: ConfigurableObject[];

    properties: Property<any>[];
    matchingRules: MatchingRule[]


    // //messy workaround...
    // static getProperties(configurableClass: ConfigurableClass) {
    //     let properties: Property<any>[] = [];
    //     for (let c of configurableClass.configurables) {
    //         if (c.configurableType == 'property') {
    //             properties.push(c as Property<any>);
    //         }
    //     }

    //     return properties;

    // }
    
}