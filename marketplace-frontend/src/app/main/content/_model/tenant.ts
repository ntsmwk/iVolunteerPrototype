import { ImageWrapper } from './image';

export class Tenant {
  id: string;
  name: string;
  description: string;
  homepage: string;
  profileImage: ImageWrapper;
  primaryColor: string;
  secondaryColor: string;
  marketplaceId: string;
  tags: string[] = [];

  landingpageMessage: string;
  landingpageTitle: string;
  landingpageText: string;
  landingpageImage: ImageWrapper;

  public constructor(init?: Partial<Tenant>) {
    Object.assign(this, init);
  }
}
