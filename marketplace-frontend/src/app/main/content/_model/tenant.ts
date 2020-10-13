export class Tenant {
  id: string;
  name: string;
  description: string;
  homepage: string;
  imagePath: string;
  primaryColor: string;
  secondaryColor: string;
  marketplaceId: string;
  tags: string[] = [];

  landingpageMessage: string;
  landingpageTitle: string;
  landingpageText: string;
  landingpageImagePath: string;

  public constructor(init?: Partial<Tenant>) {
    Object.assign(this, init);
  }
}
