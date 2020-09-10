export class Tenant {
  id: string;
  name: string;
  homepage: string;
  image;
  primaryColor: string;
  secondaryColor: string;
  marketplaceId: string;

  public constructor(init?: Partial<Tenant>) {
    Object.assign(this, init);
  }
}
