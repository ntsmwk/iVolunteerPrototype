import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { CoreMarketplaceService } from "app/main/content/_service/core-marketplace.service";
import { TenantService } from "app/main/content/_service/core-tenant.service";
import { Tenant } from "app/main/content/_model/tenant";

@Component({
  selector: "tenant-form",
  templateUrl: "tenant-form.component.html"
})
export class FuseTenantFormComponent implements OnInit {
  tenantForm: FormGroup;
  tenant: Tenant;

  constructor(
    formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private tenantService: TenantService
  ) {
    this.tenantForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined, Validators.required),
      primaryColor: new FormControl(undefined, Validators.required),
      secondaryColor: new FormControl(undefined, Validators.required)
    });
  }

  isEditMode() {
    return this.tenantForm.value.id !== null;
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.findTenant(params["tenantId"]));
  }

  private async findTenant(tenantId: string) {
    if (tenantId == null || tenantId.length === 0) {
      return;
    }
    this.tenant = <Tenant>(
      await this.tenantService.findById(tenantId).toPromise()
    );
    this.tenantForm.setValue({
      id: this.tenant.id,
      name: this.tenant.name,
      primaryColor: this.tenant.primaryColor,
      secondaryColor: this.tenant.secondaryColor
    });
  }

  save() {
    if (!this.tenantForm.valid) {
      return;
    }
    // this.tenantService
    //   .save(<Tenant>this.tenantForm.value)
    //   .toPromise()
    //   .then();
    // TODO save

    // this.marketplaceService
    //   .save(<Marketplace>this.marketplaceForm.value)
    //   .toPromise()
    //   .then(() => this.router.navigate(["/main/marketplace/all"]));
  }
}
