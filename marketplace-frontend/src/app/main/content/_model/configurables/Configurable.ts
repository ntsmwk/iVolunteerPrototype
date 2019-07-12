export class ConfigurableObject {
    id: string;
    configurableType: string;

}


export class ConfigurableClass {
    id: string;
    configurables: ConfigurableObject[];
    
}