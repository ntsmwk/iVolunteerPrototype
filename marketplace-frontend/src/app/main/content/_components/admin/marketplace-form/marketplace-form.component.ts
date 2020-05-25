import { Component, OnInit } from "@angular/core";
import { isNullOrUndefined } from "util";
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import { Marketplace } from "../../../_model/marketplace";
import { MarketplaceService } from "../../../_service/core-marketplace.service";

@Component({
  templateUrl: "./marketplace-form.component.html",
  styleUrls: ["./marketplace-form.component.scss"],
})
export class FuseMarketplaceFormComponent implements OnInit {
  marketplaceForm: FormGroup;

  constructor(
    formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private marketplaceService: MarketplaceService
  ) {
    this.marketplaceForm = formBuilder.group({
      id: new FormControl(undefined),
      name: new FormControl(undefined, Validators.required),
      shortName: new FormControl(undefined, Validators.required),
      url: new FormControl(undefined, Validators.required),
    });
  }

  isEditMode() {
    return !isNullOrUndefined(this.marketplaceForm.value.id);
  }

  ngOnInit() {
    this.route.params.subscribe((params) =>
      this.findMarketplace(params["marketplaceId"])
    );
  }

  private findMarketplace(marketplaceId: string) {
    if (isNullOrUndefined(marketplaceId) || marketplaceId.length === 0) {
      return;
    }

    this.marketplaceService
      .findById(marketplaceId)
      .toPromise()
      .then((marketplace: Marketplace) => {
        this.marketplaceForm.setValue({
          id: marketplace.id,
          name: marketplace.name,
          shortName: marketplace.shortName,
          url: marketplace.url,
        });
      });
  }

  save() {
    if (!this.marketplaceForm.valid) {
      return;
    }

    this.marketplaceService
      .save(<Marketplace>this.marketplaceForm.value)
      .toPromise()
      .then(() => this.router.navigate(["/main/marketplace/all"]));
  }
}
