import {Competence} from './competence';


export class TaskTemplate {
  id: string;
  name: string;
  description: string;
  address: Address;
  material: Material;
  requiredCompetences: Competence[];
  acquirableCompetences: Competence[];
  workflowKey: string;
}

export class Address {
    latitude: string;
    langitude: string;
    street: string;
    streetNumber: string;
    city: string;
    region: string;
    country: string;
  }

  export class Material {
    name: string;
    description: string;
  }