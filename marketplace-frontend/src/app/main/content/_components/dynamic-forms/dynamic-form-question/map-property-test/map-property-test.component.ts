import { Component, OnInit } from '@angular/core';
import { PropertyService } from 'app/main/content/_service/property.service';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { CoreHelpSeekerService } from 'app/main/content/_service/core-helpseeker.service';
import { LoginService } from 'app/main/content/_service/login.service';
import { Participant } from 'app/main/content/_model/participant';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Property, PropertyKind } from 'app/main/content/_model/properties/Property';
import { getOrSetAsInMap } from '@angular/animations/browser/src/render/shared';

@Component({
  selector: 'app-map-property-test',
  templateUrl: './map-property-test.component.html',
  styleUrls: ['./map-property-test.component.scss']
})
export class MapPropertyTestComponent implements OnInit {

  mapProperties: Property<any>[];

  latMap: number;
  lngMap: number;

  markers: {lat: number, lng: number}[] = [];



  constructor(private propertyService: PropertyService,
    private marketplaceService: CoreMarketplaceService,
    private helpSeekerService: CoreHelpSeekerService,
    private loginService: LoginService) { }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        this.propertyService.getProperties(marketplace).toPromise().then((properties: Property<any>[]) => {
          this.mapProperties = properties.filter((property: Property<any>) => {
            return property.kind == PropertyKind.MAP;
          });

          console.log(this.mapProperties);

          this.prepareMap();
      });
    });


    });
  }

  prepareMap() {

    this.latMap = this.mapProperties[0].properties[0].properties[0].values[0].value;
    this.lngMap = this.mapProperties[0].properties[0].properties[1].values[0].value;

    for (let property of this.mapProperties[0].properties) {
      this.markers.push({lat: property.properties[0].values[0].value, 
                         lng: property.properties[1].values[0].value});
    }

    
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

}
