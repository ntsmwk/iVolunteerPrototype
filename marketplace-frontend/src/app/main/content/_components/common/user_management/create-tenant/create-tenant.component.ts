import { Component, OnInit } from "@angular/core";


@Component({
  selector: "create-tenant",
  templateUrl: "create-tenant.component.html",
  styleUrls: ["create-tenant.component.scss"],
})
export class CreateTenantComponent implements OnInit {

  isLoaded: boolean = false;

  constructor(

  ) { }

  async ngOnInit() {


    this.isLoaded = true;
  }

}
