import { Component, OnInit } from "@angular/core";
import {
  FormGroup,
  FormBuilder,
  FormControl,
  Validators
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { CoreMarketplaceService } from "app/main/content/_service/core-marketplace.service";

@Component({
  selector: "tenant-form",
  templateUrl: "tenant-form.component.html"
})
export class FuseTenantFormComponent implements OnInit {
  tenantForm: FormGroup;

  constructor(
    formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private marketplaceService: CoreMarketplaceService
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
    this.route.params.subscribe(params =>
      this.findMarketplace(params["marketplaceId"])
    );
  }

  private findMarketplace(tenantId: string) {
    if (tenantId == null || tenantId.length === 0) {
      return;
    }

    // this.marketplaceService
    //   .findById(marketplaceId)
    //   .toPromise()
    //   .then((marketplace: Marketplace) => {
    //     this.marketplaceForm.setValue({
    //       id: marketplace.id,
    //       name: marketplace.name,
    //       shortName: marketplace.shortName,
    //       url: marketplace.url
    //     });
    //   });
  }

  save() {
    if (!this.tenantForm.valid) {
      return;
    }

    // TODO save

    // this.marketplaceService
    //   .save(<Marketplace>this.marketplaceForm.value)
    //   .toPromise()
    //   .then(() => this.router.navigate(["/main/marketplace/all"]));
  }
}
