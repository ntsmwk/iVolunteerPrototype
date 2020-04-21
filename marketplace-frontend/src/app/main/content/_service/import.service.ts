import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { ClassDefinition, ClassInstance } from "../_model/meta/Class";
import { Participant } from "../_model/participant";
import { Marketplace } from "../_model/marketplace";

@Injectable({ providedIn: "root" })
export class ImportService {
  constructor(private httpClient: HttpClient) {}

  import(
    classDefinition: ClassDefinition,
    participant: Participant,
    classInstances: ClassInstance[],
    marketplace: Marketplace,
    tenantId: string
  ) {
    // TODO...
    this.httpClient.post(`${marketplace.url}/import/`, classInstances);
  }
}
