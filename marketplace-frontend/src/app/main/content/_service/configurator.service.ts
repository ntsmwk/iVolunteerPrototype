import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Marketplace } from "../_model/marketplace";
import { ClassDefintion } from "../_model/meta/Class";
import { Relationship, Association, Inheritance, RelationshipType } from "../_model/meta/Relationship";
import { of } from "rxjs";
import { Property } from "../_model/meta/Property";

@Injectable({
    providedIn: 'root'
  })
  export class ConfiguratorService {
  
    constructor(
      private http: HttpClient
    ) { }

    // test(marketplace: Marketplace) {
    //     return this.http.get(`${marketplace.url}/configurator/test`);
    // }


    //
    //  OPERATIONS ON CONFIGURATION CLASSES
    //

    getAllConfigClasses(marketplace: Marketplace) {

      return this.http.get(`${marketplace.url}/configclass/all`);

    }

    getConfigClassById(marketplace: Marketplace, id: string) {

      return this.http.get(`${marketplace.url}/configclass/${id}`);

    }

    createNewConfigClass(marketplace: Marketplace, clazz: ClassDefintion) {

      return this.http.post(`${marketplace.url}/configclass/new`, clazz);

    }

    changeConfigClassName(marketplace: Marketplace, id: string, newName: string) {
      return this.http.put(`${marketplace.url}/configclass/${id}/change-name`, newName);
      
    }

    deleteConfigClasses(marketplace: Marketplace, ids: string[]) {
      return this.http.put(`${marketplace.url}/configclass/delete`, ids);
    }


    //
    //  OPERATIONS ON PROPERTIES
    //

    addPropertiesToConfigClassById(marketplace: Marketplace, id: string, propIds: String[]) {
      return this.http.put(`${marketplace.url}/configclass/${id}/add-properties-by-id`, propIds);
    }

    addPropertiesToConfigClass(marketplace: Marketplace, id: string, propsToAdd: Property<any>[]) {
      return this.http.put(`${marketplace.url}/configclass/${id}/add-properties`, propsToAdd);

    }

    removePropertiesFromConfigClass(marketplace: Marketplace, id: string, propIds: string[]) {
      return this.http.put(`${marketplace.url}/configclass/${id}/remove-properties`, propIds);
    }


    //
    //  OPERATIONS ON RELATIONSHIPS
    //

    findAllRelationships(marketplace: Marketplace) {
      return this.http.get(`${marketplace.url}/configclass/relationship/all`);
    }

    //TODO
    addRelationships(marketplace: Marketplace, configurableClass: ClassDefintion, relationships: any[]) {
    
      //Patch assigned ID
      let promises: Promise<any>[] = [];

      let ret = [];

      for (let r of relationships) {
        promises.push(this.createPromise(marketplace, r, configurableClass));
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
      return this.http.put(`${marketplace.url}/configclass/relationship/add-association`, association);
    }

    addInheritance(marketplace: Marketplace, inheritance: Inheritance) {
      return this.http.put(`${marketplace.url}/configclass/relationship/add-inheritance`, inheritance);

    }

    deleteRelationships (marketplace: Marketplace, ids: String[]) {
      return this.http.put(`${marketplace.url}/configclass/relationship/delete`, ids);
    }


  }