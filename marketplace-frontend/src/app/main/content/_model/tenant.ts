export class Tenant {
  id: string;
  name: string;
  description: string;
  homepage: string;
  image: any;
  primaryColor: string;
  secondaryColor: string;
  marketplaceId: string;
  tags: string[] = [];

  public constructor(init?: Partial<Tenant>) {
    Object.assign(this, init);
  }
}
