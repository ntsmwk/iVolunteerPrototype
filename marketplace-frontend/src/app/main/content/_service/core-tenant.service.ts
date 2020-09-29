import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Tenant } from "../_model/tenant";
import { ImageService } from "./image.service";
import { Image } from "../_model/image";

@Injectable({
  providedIn: "root"
})
export class TenantService {
  constructor(private http: HttpClient, private imageService: ImageService) {}

  findAll() {
    return this.http.get(`/core/tenant`);
  }

  findByName(tenantName: string) {
    return this.http.get(`/core/tenant/name/${tenantName}`, {
      responseType: "text"
    });
  }

  findById(tenantId: string) {
    return this.http.get(`/core/tenant/${tenantId}`);
  }

  findByUserId(userId: string) {
    return this.http.get(`/core/tenant/user/${userId}`);
  }

  findByMarketplace(marketplaceId: string) {
    return this.http.get(`/core/tenant/marketplace/${marketplaceId}`);
  }

  save(tenant: Tenant) {
    if (tenant.id == null) {
      return this.http.post(`/core/tenant`, tenant);
    }
    return this.http.put(`/core/tenant`, tenant);
  }

  async getTenantImage(tenant: Tenant) {
    let img: Image = <Image>(
      await this.imageService.findById(tenant.imageId).toPromise()
    );
    return img.imageWrapper;
  }

  // async getTenantProfileImage(tenant: Tenant) {
  //   if (tenant == null) {
  //     return "/assets/images/avatars/profile.jpg";
  //   }

  //   let profileImg: Image = <Image>(
  //     await this.imageService.findById(tenant.imageId).toPromise()
  //   );
  //   const ret = this.imageService.getImgSourceFromImageWrapper(
  //     profileImg.imageWrapper
  //   );
  //   if (ret == null) {
  //     return "/assets/images/avatars/profile.jpg";
  //   } else {
  //     return ret;
  //   }
  // }

  async getTenantLandingPageImage(tenant: Tenant) {
    let landingImg: Image = <Image>(
      await this.imageService.findById(tenant.landingpageImageId).toPromise()
    );

    return this.imageService.getImgSourceFromImageWrapper(
      landingImg.imageWrapper
    );
  }

  initHeader(tenant: Tenant) {
    (<HTMLElement>document.querySelector(".header")).style.background =
      tenant.primaryColor;
  }
}
