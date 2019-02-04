import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatTableDataSource } from '@angular/material';
import { fuseAnimations } from '@fuse/animations';

import { PropertyListItem, Property, PropertyKind } from "../_model/properties/Property";

import { LoginService } from '../_service/login.service';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Participant } from '../_model/participant';
import { Marketplace } from '../_model/marketplace';

import { PropertyService } from "../_service/property.service";
import { isNullOrUndefined } from 'util';



// export const testProperties: Property<string>[] = [
//   {id: null, name: 'TESTTEXTPROPERTY', value: 'TEXTEXTTEXT', kind: PropertyKind.TEXT, defaultValue: this.value},
//   {id: null, name: 'TESTNUMBERPROPERTY', value: '200', kind: PropertyKind.WHOLE_NUMBER, defaultValue: this.value},
//   {id: null, name: 'SHOULD_FAIL', value: "LInilnln", kind: PropertyKind.FLOAT_NUMBER, defaultValue: this.value}

// ]

@Component({
  selector: 'fuse-property-list',
  templateUrl: './property-list.component.html',
  styleUrls: ['./property-list.component.scss'],
  animations: fuseAnimations
})
export class FusePropertyListComponent implements OnInit {

  
  
  dataSource = new MatTableDataSource<PropertyListItem>();
  displayedColumns = ['id', 'name', 'value', 'kind'];

  marketplace: Marketplace;

  constructor(private router: Router,
    private propertyService: PropertyService,
    private loginService: LoginService,
    private helpSeekerService: CoreHelpSeekerService) {
    
    
    }

  ngOnInit() {
    //this.loadAllPropertiesLocal();
    this.loadAllPropertiesServer();
  }

  onRowSelect(p: Property<Object>) {
    console.log("Property Clicked: " + p.name );
    console.log("CURRENT URL: " + this.router.url)
    this.router.navigate(['/main/property/' + this.marketplace.id + '/' + p.id]);
    
    
    

    

    // if (!isNullOrUndefined(p.legalValues)) {
    //   console.log("\n legal values: \n")
    //   for (var i = 0; i < p.legalValues.length; i++) {
    //      console.log(p.legalValues[i]);
    //   }

     

      
    // }

    // if (!isNullOrUndefined(p.defaultValue)) {
    //   console.log("Default Val: " + p.defaultValue);
    // }

    // if (!isNullOrUndefined(p.rules)) {
    //   console.log("RULES FOR " + p.name);
    //   for (var i = 0; i < p.rules.length; i++) {
    //     console.log(p.rules[i].id + ": " + p.rules[i].kind);

    //   }
    // }

    // if (!isNullOrUndefined(p.kind)) {
    //   console.log("Property contains a " + p.kind + " value");
    // }

   // this.updateProperty(p);
    
    
  }

  loadAllPropertiesLocal() {
   console.log("no longer needed delet dis")
    // this.dataSource.data = this.propertyService.findAllProperties();

    
  }
  loadAllPropertiesServer() {
    console.log ("load props from Server...");

   
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.marketplace = marketplace;
          this.propertyService.findAllFromServer(marketplace).toPromise().then((pArr: PropertyListItem[]) => {
          this.dataSource.data = pArr;
        })}
      })
    });


    //TEST Type Conversion

    // var test = "123";
    // var i = +test;

    // var sum = i + 200;
    // var sumString = test + 200;
    // console.log("TESTSUM int: " + sum);
    // console.log("TESTSUM String: " + sumString);

    // var floatTest = "1234.999";
    // var f = parseFloat(floatTest);
    // var floatSum = f + 0.005;
    // console.log("TESTSUM float: " + floatSum);

  }

  newProperty() {
    console.log("clicked new Property");
    this.addProperty();
    // this.addProperty(testProperties[0]);
    // this.addProperty(testProperties[1]);
    // this.addProperty(testProperties[2]);
    
    

  }

 private addProperty() {
  console.log("TODO: Navigate to add Property Detail page");
    // this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
    //   this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
    //     if (!isNullOrUndefined(marketplace)) {
    //       this.propertyService.sendPropertytoServer(marketplace, <Property<string>>property).toPromise().then(() =>
    //       console.log("Sent")) ;
    //     }
    //   })
    // });
  }

  updateProperty(item: PropertyListItem) {
  console.log("clicked to update property " + item.id + " " + item.name + " TODO - link to detail page");
  // property.value = "UPDATED VALUE IS 666";
  // property.kind = PropertyKind.TEXT;
  // if (!isNullOrUndefined(property.rules)) {
  //   console.log("Property Rules" + property.rules[0].kind);

  // }


  // this.updatePropertySave(property);

  }

  updatePropertySave(property: Property<string>) {
    console.log("attempt to update");
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.helpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.propertyService.updatePropertyFromServer(marketplace, <Property<string>>property).toPromise().then(() => 
            console.log("Updated"));
          
        }
      })
    });
  }

}
