export class Tenant {
  id: string;
  name: string;
  description: string;
  homepage: string;
  imageFileName: string;
  primaryColor: string;
  secondaryColor: string;
  marketplaceId: string;
  tags: string[] = [];

  landingpageMessage: string;
  landingpageTitle: string;
  landingpageText: string;
  landingpageImageFileName: string;

  public constructor(init?: Partial<Tenant>) {
    Object.assign(this, init);
  }
}
