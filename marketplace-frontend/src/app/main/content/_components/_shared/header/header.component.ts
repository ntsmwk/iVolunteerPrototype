import { Component, OnInit, Input } from "@angular/core";
import { Tenant } from "../../../_model/tenant";
import { TenantService } from "../../../_service/core-tenant.service";
import { ImageService } from "app/main/content/_service/image.service";
import { Image } from "app/main/content/_model/image";

@Component({
  selector: "customizable-header",
  templateUrl: "header.component.html"
})
export class HeaderComponent implements OnInit {
  @Input() headerText: string;
  @Input() tenant: Tenant;
  @Input() displayNavigateBack: boolean;

  image: Image;

  constructor(
    private tenantService: TenantService,
    private imageService: ImageService
  ) { }

  async ngOnInit() {
    // this.image = <Image>await this.imageService.findById(this.tenant.imageId);
    this.tenantService.initHeader(this.tenant);
  }

  navigateBack() {
    window.history.back();
  }
}
