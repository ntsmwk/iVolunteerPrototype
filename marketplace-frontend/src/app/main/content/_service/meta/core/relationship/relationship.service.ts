import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../../../../_model/marketplace";
import { Relationship, RelationshipType, Inheritance, Association } from "app/main/content/_model/meta/Relationship";
import { ClassDefintion } from "app/main/content/_model/meta/Class";

@Injectable({
  providedIn: 'root'
})
export class RelationshipService {

  constructor(
    private http: HttpClient
  ) { }

  getAllRelationships(marketplace: Marketplace) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/all`);
  }

  getRelationshipById(marketplace: Marketplace, id: string) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/${id}`);
  }

  getRelationshipsByStartId(marketplace: Marketplace, startId: string) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/start/${startId}/all`);
  }

  getRelationshipsByEndId(marketplace: Marketplace, endId: string) {
    return this.http.get(`${marketplace.url}/meta/core/relationship/end/${endId}/all`);
  }


  addRelationshipsInOneGo(marketplace: Marketplace, relationships: Relationship[]) {
    return this.http.post(`${marketplace.url}/meta/core/relationship/add`, relationships);
  }


  //TODO - entfernen??
  addRelationships(marketplace: Marketplace, ClassDefintion: ClassDefintion, relationships: any[]): Promise<any[]> {

    //Patch assigned ID
    let promises: Promise<any>[] = [];

    let ret = [];

    for (let r of relationships) {
      promises.push(this.createPromise(marketplace, r, ClassDefintion));
    }

    return Promise.all(promises).then((resolve) => {
      console.log(resolve); //TODO
      ret.push(...resolve);
      return ret;
    })

  }

  private createPromise(marketplace: Marketplace, r: Relationship, c: ClassDefintion) {

    return new Promise((resolve) => {

      if (r.classId1 == null) {
        r.classId1 = c.id;
      } else if (r.classId2 == null) {
        r.classId2 = c.id;
      }


      if (r.relationshipType == RelationshipType.INHERITANCE) {
        if ((r as Inheritance).superClassId == null) {
          (r as Inheritance).superClassId = c.id;
        }

        resolve(this.addInheritance(marketplace, r as Inheritance).toPromise().then((rel: any) => {
          resolve(rel);
        }));

      } else if (r.relationshipType == RelationshipType.ASSOCIATION) {
        resolve(this.addAssociation(marketplace, r as Association).toPromise().then((rel: any) => {
          resolve(rel);
        }));

      }
    });
  }

  addAssociation(marketplace: Marketplace, association: Association) {
    return this.http.put(`${marketplace.url}/meta/core/relationship/add-association`, association);
  }

  addInheritance(marketplace: Marketplace, inheritance: Inheritance) {
    return this.http.put(`${marketplace.url}/meta/core/relationship/add-inheritance`, inheritance);

  }

  deleteRelationships(marketplace: Marketplace, ids: String[]) {
    return this.http.put(`${marketplace.url}/meta/core/relationship/delete`, ids);
  }

}