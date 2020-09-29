import { ImageWrapper } from "./image";

export class Tenant {
  id: string;
  name: string;
  description: string;
  homepage: string;
  imageId: string;
  primaryColor: string;
  secondaryColor: string;
  marketplaceId: string;
  tags: string[] = [];

  landingpageMessage: string;
  landingpageTitle: string;
  landingpageText: string;
  landingpageImageId: string;

  public constructor(init?: Partial<Tenant>) {
    Object.assign(this, init);
  }
}
