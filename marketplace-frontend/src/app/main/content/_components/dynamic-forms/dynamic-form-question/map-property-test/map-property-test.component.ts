import { Component, OnInit } from '@angular/core';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant } from 'app/main/content/_model/participant';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { PropertyDefinition, PropertyType } from 'app/main/content/_model/meta/Property';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';

@Component({
  selector: 'app-map-property-test',
  templateUrl: './map-property-test.component.html',
  styleUrls: ['./map-property-test.component.scss']
})
export class MapPropertyTestComponent implements OnInit {

  mapPropertyDefinitions: PropertyDefinition<any>[];

  latMap: number;
  lngMap: number;

  markers: {lat: number, lng: number}[] = [];

  marketplace: Marketplace;



  constructor(
    private marketplaceService: CoreMarketplaceService,
    private helpSeekerService: CoreHelpSeekerService,

    private classDefinitionService: ClassDefinitionService,
    private propertyDefinitionService: PropertyDefinitionService,

    private loginService: LoginService) { }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.marketplace = marketplace;
        this.propertyDefinitionService.getAllPropertyDefinitons(marketplace).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
          this.mapPropertyDefinitions = propertyDefinitions.filter((property: PropertyDefinition<any>) => {
            return property.type == PropertyType.MAP;
          });

          console.log(this.mapPropertyDefinitions);

          this.prepareMap();
      });
    });


    });
  }

  prepareMap() {

    // this.latMap = this.mapPropertyDefinitions[0].properties[0].properties[0].values[0].value;
    // this.lngMap = this.mapPropertyDefinitions[0].properties[0].properties[1].values[0].value;

    // for (let property of this.mapProperties[0].properties) {
    //   this.markers.push({lat: property.properties[0].values[0].value, 
    //                      lng: property.properties[1].values[0].value});
    // }

    
  }

  moveMarker(evt: any) {
    console.log("Marker moved");
    console.log(evt);

    // this.lat = evt.coords.lat;
    // this.lng = evt.coords.lng;
 
  }

  addMarker(evt: any) {
    console.log("Marker set");
    console.log(evt)
  }


  // testConfiguratorAPI() {
  //   this.configuratorService.getAllConfigClasses(this.marketplace).toPromise().then(() => {
  //      console.log("get all SUCCESS"); 
  //   });

  //   this.configuratorService.getConfigClassById(this.marketplace, "TESTIDBLAH").toPromise().then(() => {
  //     console.log("get by id SUCCESS");
  //   });

  //   this.configuratorService.createNewConfigClass(this.marketplace, new ConfigurableClass()).toPromise().then(() => {
  //     console.log("create SUCCESS");
  //   });

  //   this.configuratorService.deleteConfigClasses(this.marketplace, "TESTID").toPromise().then(() => {
  //     console.log("delete SUCCESS");
  //   });

  //   let arr: ConfigurableObject[] = [];

  //   this.configuratorService.addObjectsToConfigClass(this.marketplace, "TESTID", arr).toPromise().then(() => {
  //     console.log("add SUCCESS");
  //   });

  //   let arr2: string[] = [];
  //   this.configuratorService.removeObjectFromConfigClass(this.marketplace, "TESTID", arr2).toPromise().then(() => {
  //     console.log("remove SUCCESS");
  //   });
  // }

}
