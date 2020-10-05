import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Tenant } from "../_model/tenant";
import { ImageService } from "./image.service";
import { Image } from "../_model/image";

@Injectable({
  providedIn: "root"
})
export class TenantService {
  constructor(private http: HttpClient, private imageService: ImageService) { }

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

  saveTenant(tenant: Tenant) {
    if (tenant.id == null) {
      return this.http.post(`/core/tenant/new`, tenant);
    }
    return this.http.put(`/core/tenant/update`, tenant);
  }


  
  async getTenantImage(tenant: Tenant) {
    let img: Image = <Image>await this.imageService.findById(tenant.imageId);
    if (!img) {
      return "/assets/images/avatars/profile.jpg";
    }

    //TODO fucked
    // let profileImg: Image = <Image>(
    //   await this.imageService.findById(tenant.imageId).toPromise()
    // );
    // const ret = this.imageService.getImgSourceFromImageWrapper(
    //   profileImg.imageWrapper
    // );
    // if (ret == null) {
    return "/assets/images/avatars/profile.jpg";
    // } else {
    //   return ret;
    // }
  }

  async getTenantLandingPageImage(tenant: Tenant) {
    //TODO fucked
    // let landingImg: Image = <Image>(
    //   await this.imageService.findById(tenant.landingpageImageId).toPromise()
    // );

    // return this.imageService.getImgSourceFromImageWrapper(
    //   landingImg.imageWrapper
    // );

    return "/assets/images/avatars/profile.jpg";
  }

  initHeader(tenant: Tenant) {
    (<HTMLElement>document.querySelector(".header")).style.background =
      tenant.primaryColor;
  }
}
